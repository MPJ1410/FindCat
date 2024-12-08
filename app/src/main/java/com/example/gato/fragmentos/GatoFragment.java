package com.example.gato.fragmentos;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gato.R;
import com.example.gato.actividades.NuevoGatoActivity;
import com.example.gato.actividades.VerGatoActivity;
import com.example.gato.actividades.EditarGatoActivity;
import com.example.gato.adaptadores.GaturroAdapter;
import com.example.gato.adaptadores.GaturroAdapter;
import com.example.gato.adaptadores.ReporteAdapter;
import com.example.gato.clases.Gaturro;
import com.example.gato.clases.Gaturro;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GatoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GatoFragment extends Fragment  implements View.OnClickListener{
    private final static String urlMostrarGaturros = "http://gatitos.atwebpages.com/services/mostrarGaturros.php";
    RecyclerView recGaturros;
    public static ArrayList<Gaturro> lista;
    GaturroAdapter adapter = null;
    private LinearLayout contenedorGaturros;
    private Button btnAgregar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageButton imgBtnNuevoGato;
    private ImageButton imgBtnVerGato;
    private ImageButton imgBtnEditarGato;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GatoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GatoFragment newInstance(String param1, String param2) {
        GatoFragment fragment = new GatoFragment();
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
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_gato, container, false);
        recGaturros = view.findViewById(R.id.frgMenRecGaturros);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recGaturros.setLayoutManager (manager);
        adapter = new GaturroAdapter(lista);
        recGaturros.setAdapter(adapter);
        mostrarGaturros();
        imgBtnNuevoGato = view.findViewById(R.id.frgMenImgBtnNuevoGato);

        imgBtnNuevoGato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NuevoGatoActivity.class);
                startActivity(intent);
            }
        });



        mostrarGaturros();


        return view;
    }

    private void mostrarGaturros() {
        AsyncHttpClient ahcGaturros = new AsyncHttpClient();
        ahcGaturros.get(urlMostrarGaturros, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode==200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            System.out.println(jsonArray.getJSONObject(i).getString("id_gato"));
                            System.out.println(jsonArray.getJSONObject(i).getString("nombre_gato"));
                            lista.add(new Gaturro(jsonArray.getJSONObject(i).getInt("id_gato"),
                                    jsonArray.getJSONObject(i).getString("nombre_gato"),
                                    jsonArray.getJSONObject(i).getString("raza"),
                                    jsonArray.getJSONObject(i).getString("edad"),
                                    jsonArray.getJSONObject(i).getString("sexo"),
                                    jsonArray.getJSONObject(i).getString("tamano"),
                                    jsonArray.getJSONObject(i).getString("imagen_gaturro")
                            ));

                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getContext(), "ERROR"+statusCode, LENGTH_LONG).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}