package com.example.gato.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.gato.clases.Gaturro;
import com.example.gato.fragmentos.DetalleGaturroFragment;

import java.util.List;

public class GaturroAdapter extends RecyclerView.Adapter<GaturroAdapter.ViewHolder>{

    private List<Gaturro> listaGaturro;

    public GaturroAdapter(List<Gaturro> listaGaturro) { this.listaGaturro = listaGaturro; }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gaturro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gaturro gaturro = listaGaturro.get(position);
        String foto = gaturro.getImagen();
        byte [] fotoBytes = Base64.decode(foto,Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes,0,fotoBytes.length);
        holder.imgFoto.setImageBitmap(bitmap);
        holder.lblNombre.setText(gaturro.getNombre());
        holder.lblEdad.setText(String.valueOf(gaturro.getEdad()));
        holder.lblRaza.setText(gaturro.getRaza());
        holder.cardItemGaturro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("gaturro", gaturro);
                Fragment detalle = new DetalleGaturroFragment();
                detalle.setArguments(bundle);
                ft.replace(R.id.menRelArea,detalle);
                ft.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaGaturro.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardItemGaturro;
        ImageView imgFoto;
        TextView lblNombre, lblEdad, lblRaza;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardItemGaturro = itemView.findViewById(R.id.itmCrdItemGaturro);
            imgFoto = itemView.findViewById(R.id.itmImgFoto);
            lblNombre = itemView.findViewById(R.id.itmLblNombre);
            lblEdad = itemView.findViewById(R.id.itmLblEdad);
            lblRaza = itemView.findViewById(R.id.itmLblRaza);

        }
    }
}
