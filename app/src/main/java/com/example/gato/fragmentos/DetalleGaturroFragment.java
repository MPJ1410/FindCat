package com.example.gato.fragmentos;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gato.R;
import com.example.gato.clases.Gaturro;

public class DetalleGaturroFragment extends Fragment  implements View.OnClickListener{

    private DetalleGaturroViewModel mViewModel;



    public static DetalleGaturroFragment newInstance() {
        return new DetalleGaturroFragment();
    }

    ImageView imgFoto;
    TextView lblNombre, lblEdad, lblRaza, sexo , tamaño;
    Button btnRegresar;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalle_gaturro, container, false);

        imgFoto = vista.findViewById(R.id.frgDetImgFoto);
        lblNombre = vista.findViewById(R.id.frgDetlblNombreGaturro);
        lblEdad = vista.findViewById(R.id.frgDetlblEdadGaturro);
        lblRaza = vista.findViewById(R.id.frgDetlblTipoRaza);
        sexo = vista.findViewById(R.id.frgDetlblSexoGaturro);


        Bundle bundle = getArguments();
        if (bundle== null) return vista;
        Gaturro gaturro = (Gaturro)bundle.getSerializable("gaturro");
        String foto = gaturro.getImagen();
        byte[] fotoBytes = Base64.decode(foto, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes,0,fotoBytes.length);
        imgFoto.setImageBitmap(bitmap);
        lblNombre.setText(gaturro.getNombre());
        lblEdad.setText(gaturro.getEdad());
        lblRaza.setText(gaturro.getRaza());
        sexo.setText(gaturro.getSexo());


        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleGaturroViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {


    }
}