package com.example.gato.fragmentos;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gato.R;
import com.example.gato.clases.Reporte;
import com.loopj.android.http.Base64;

public class DetalleReporteFragment extends Fragment implements View.OnClickListener {

    ImageView imgFoto;
    TextView lblNombre, lblTipoReporte, lblDescripcion, lblUbicacion;
    EditText txtComentario;
    Button btnComentar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleReporteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleReporteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleReporteFragment newInstance(String param1, String param2) {
        DetalleReporteFragment fragment = new DetalleReporteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalle_reporte, container, false);
        imgFoto = vista.findViewById(R.id.frgDetImgFoto);
        lblNombre = vista.findViewById(R.id.frgDetlblNombre);
        lblTipoReporte = vista.findViewById(R.id.frgDetlblTipo);
        lblDescripcion = vista.findViewById(R.id.frgDetlblDescripcion);
        lblUbicacion = vista.findViewById(R.id.frgDetlblUbicacion);
        txtComentario = vista.findViewById(R.id.frgDetTxtComentar);
        btnComentar = vista.findViewById(R.id.frgDetBtnComentar);

        btnComentar.setOnClickListener(this);

        Bundle bundle = getArguments();
        if(bundle == null) return vista;
        Reporte reporte = (Reporte)bundle.getSerializable("reporte");
        String foto = reporte.getEvidencia();
        byte[] fotoBytes = Base64.decode(foto, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes,0,fotoBytes.length);
        imgFoto.setImageBitmap(bitmap);
        lblNombre.setText(reporte.getCliente());
        lblTipoReporte.setText(reporte.getTipoIncidente());
        lblDescripcion.setText(reporte.getDescripcion());
        lblUbicacion.setText(reporte.getLinkUbicacion());

        return vista;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.frgDetBtnComentar)
            comentar();
    }

    private void comentar() {
    }
}