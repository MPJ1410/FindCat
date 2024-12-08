package com.example.gato.fragmentos;

import static android.widget.Toast.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gato.R;
import com.example.gato.adaptadores.ReporteAdapter;
import com.example.gato.clases.Reporte;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnuncioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnuncioFragment extends Fragment implements View.OnClickListener {

    private static String urlMostrarTipoReporte = "http://gatitos.atwebpages.com/services/mostrarTiposReporte.php";
    private static String urlMostrarReportes = "http://gatitos.atwebpages.com/services/mostrarReportes.php";
    private final static String urlMostrarReportes2 = "http://gatitos.atwebpages.com/services/mostrarReportes2.php";
    RecyclerView recReportes;
    public static ArrayList<Reporte> lista;
    ReporteAdapter adapter = null;
    // TODO: Rename parameter arguments, choose names that match
    private LinearLayout contenedorReportes;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnuncioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnuncioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnuncioFragment newInstance(String param1, String param2) {
        AnuncioFragment fragment = new AnuncioFragment();
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
        View vista = inflater.inflate(R.layout.fragment_anuncio, container, false);
        recReportes = vista.findViewById(R.id.frgMenRecRepotes);
        lista = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recReportes.setLayoutManager (manager);
        adapter = new ReporteAdapter(lista);
        recReportes.setAdapter(adapter);

        mostrarReportes2();
        return vista;





    }

    private void mostrarReportes2() {
        AsyncHttpClient ahcReportes = new AsyncHttpClient();
        ahcReportes.get(urlMostrarReportes2, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode ==200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        lista.clear();
                        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //respertar el orden del constructor
                            System.out.println(jsonArray.getJSONObject(i).getString("id_reporte"));
                            System.out.println(jsonArray.getJSONObject(i).getString("imagen_gato"));
                            lista.add(new Reporte(jsonArray.getJSONObject(i).getInt("id_reporte"),
                                    jsonArray.getJSONObject(i).getString("imagen_gato"),
                                    jsonArray.getJSONObject(i).getString("descripcion"),
                                    jsonArray.getJSONObject(i).getString("tipo_reporte"),
                                    formato.parse(jsonArray.getJSONObject(i).getString("fecha_reporte")),
                                    jsonArray.getJSONObject(i).getString("link_ubicacion"),
                                    jsonArray.getJSONObject(i).getString("persona")
                            ));

                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException | ParseException e) {
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