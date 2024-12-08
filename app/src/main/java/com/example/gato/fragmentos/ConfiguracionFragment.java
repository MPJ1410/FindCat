package com.example.gato.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gato.R;
import com.example.gato.actividades.ContactenosActivity;
import com.example.gato.actividades.EditarPerfilActivity;
import com.example.gato.actividades.PoliticaPrivacidadActivity;
import com.example.gato.actividades.SesionActivity;
import com.example.gato.actividades.TerminoyConActivity;
import com.example.gato.clases.Usuario;
import com.example.gato.sqlite.Gatos;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracionFragment extends Fragment implements View.OnClickListener{
    TextView editarPerfil, cerrarSesion, contactenos, terminos, politica;

    Usuario usuario = new Usuario();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracionFragment newInstance(String param1, String param2) {
        ConfiguracionFragment fragment = new ConfiguracionFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        // Inicializa lblEditarPerfil después de inflar el layout
        editarPerfil = view.findViewById(R.id.LblEditarPerfil);
        cerrarSesion = view.findViewById(R.id.frgCfgLblCerrar);
        contactenos = view.findViewById(R.id.frgCfgLblContacto);
        terminos = view.findViewById(R.id.frgCfgLblTerminos);
        politica = view.findViewById(R.id.frgCfgLblPoliticas);

        // Si lblEditarPerfil tiene algún evento de clic, lo puedes definir aquí
        editarPerfil.setOnClickListener(this);
        cerrarSesion.setOnClickListener(this);
        contactenos.setOnClickListener(this);
        terminos.setOnClickListener(this);
        politica.setOnClickListener(this);

        usuario = (Usuario)getActivity().getIntent().getSerializableExtra("usuario");

        System.out.println("CONFIGURACION1");
        System.out.println(usuario.getId());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.LblEditarPerfil) {
            Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
            intent.putExtra("usuario", usuario);
            System.out.println("CONFIGURACION2");
            System.out.println(usuario.getId());
            startActivity(intent);
        } else if (view.getId() == R.id.frgCfgLblContacto) {
            Intent intent = new Intent(getActivity(), ContactenosActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.frgCfgLblTerminos) {
            Intent intent = new Intent(getActivity(), TerminoyConActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.frgCfgLblPoliticas) {
            Intent intent = new Intent(getActivity(), PoliticaPrivacidadActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.frgCfgLblCerrar) {
            cerrar();
        }
    }

    private void cerrar() {
        Gatos bv = new Gatos(getContext());
        bv.eliminarUsuarios();
        Intent sesion = new Intent(getContext(), SesionActivity.class);
        startActivity(sesion);
        getActivity().finish();
    }
}