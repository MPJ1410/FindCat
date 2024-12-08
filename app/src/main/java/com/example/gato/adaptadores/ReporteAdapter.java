package com.example.gato.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gato.R;
import com.example.gato.clases.Reporte;
import com.example.gato.fragmentos.DetalleReporteFragment;
import com.loopj.android.http.Base64;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ViewHolder>{
    private List<Reporte> listaReporte ;

    public ReporteAdapter(List<Reporte> listaReporte) {
        this.listaReporte = listaReporte;
    }

    @NonNull
    @Override
    public ReporteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reporte, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteAdapter.ViewHolder holder, int position) {
        Reporte reporte = listaReporte.get(position);
        String foto = reporte.getEvidencia();
        System.out.println(reporte.getCliente());
        //esto te permite descargar y la imagen y volverla a decodificar el base 64

        byte [] fotoBytes = Base64.decode(foto,Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes,0,fotoBytes.length);
        System.out.println(bitmap);
        holder.imgFoto.setImageBitmap(bitmap);
        holder.lblTipoIncidente.setText(reporte.getTipoIncidente());
        holder.lblDescripcion.setText(reporte.getCliente());

        Date fechaHora = reporte.getFechaHora();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.lblfecha.setText(formato.format(fechaHora));
        holder.cardItemReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("reporte", reporte);
                Fragment detalle = new DetalleReporteFragment();
                detalle.setArguments(bundle);
                ft.replace(R.id.menRelArea,detalle);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {

        return listaReporte.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardItemReporte;
        ImageView imgFoto;
        TextView lblTipoIncidente, lblfecha, lblDescripcion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardItemReporte = itemView.findViewById(R.id.itmCrdItemReporte);
            imgFoto = itemView.findViewById(R.id.itmImgFoto);
            lblTipoIncidente = itemView.findViewById(R.id.itmLblTipoReporte);
            lblDescripcion = itemView.findViewById(R.id.itmLblDescripcion);
            lblfecha= itemView.findViewById(R.id.itmLblFechaHora);
        }
    }
}
