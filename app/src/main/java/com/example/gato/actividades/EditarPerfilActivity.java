package com.example.gato.actividades;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gato.R;
import com.example.gato.clases.Usuario;
import com.example.gato.sqlite.Gatos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class EditarPerfilActivity extends AppCompatActivity {

    private static final String urlMostrarPersona = "http://gatitos.atwebpages.com/services/mostrarPersona.php";
    private static String urlMostrarDistrito = "http://gatitos.atwebpages.com/services/mostrarDistritos.php";

    // Declaración de variables para los elementos de la vista
    private EditText lblEditNombres, lblEditApellidos, lblEditDocumento, lblEditTelefono, lblEditCorreo, lblEditContrasena, lblEditConfirmarContrasena;
    private Spinner cmbDistrito;
    private Button btnCancelar, btnEditar;

    Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Inicializa los elementos de la vista
        lblEditNombres = findViewById(R.id.lblEditNombres);
        lblEditApellidos = findViewById(R.id.lblEditApellidos);
        lblEditDocumento = findViewById(R.id.lblEditDocumento);
        lblEditTelefono = findViewById(R.id.lblEditTelefono);
        lblEditCorreo = findViewById(R.id.lblEditCorreo);
        //lblEditContrasena = findViewById(R.id.lblEditContrasena);
        //lblEditConfirmarContrasena = findViewById(R.id.lblEditConfirmarContrasena);
        cmbDistrito = findViewById(R.id.cmbDistrito);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEditar = findViewById(R.id.btnEditar);

        usuario = (Usuario)getIntent().getSerializableExtra("usuario");

        System.out.println("ESTO ES ESDITAR PERFIL");
        System.out.println(usuario.getId());

        cargarDatosPersona();

        btnCancelar.setOnClickListener(v -> finish());

        btnEditar.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarDatosPerfil();
                finish();
            }
        });
        llenarDistritos();
    }

    private void llenarDistritos() {

        //Crear un objeto para realizar la tarea asincronahacia en web services
        AsyncHttpClient ahcMostrarDistrito = new AsyncHttpClient();
        //crear un adatador por defecto al spiner
        cmbDistrito.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, new String[]{"--Seleccione Distrito--"}));
        //ejecutar ña tarea asincronica
        ahcMostrarDistrito.get(urlMostrarDistrito, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[] distritos = new String[jsonArray.length() + 1];
                        distritos[0] = "-- Seleccione Distrito --";
                        for (int i = 1; i < jsonArray.length() + 1; i++) {
                            distritos[i] = jsonArray.getJSONObject(i - 1).getString("nombre_distrito");
                        }
                        cmbDistrito.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item, distritos));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR: " + statusCode, Toast.LENGTH_LONG).show();
            }
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void cargarDatosPersona() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id_Persona", String.valueOf(usuario.getId()));

        client.get(urlMostrarPersona, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            lblEditNombres.setText(jsonArray.getJSONObject(i).getString("nombre"));
                            lblEditApellidos.setText(jsonArray.getJSONObject(i).getString("apellidos"));
                            lblEditDocumento.setText(jsonArray.getJSONObject(i).getString("dni"));
                            lblEditTelefono.setText(jsonArray.getJSONObject(i).getString("celular"));
                            lblEditCorreo.setText(jsonArray.getJSONObject(i).getString("correo"));
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });

    }
    // Método para validar los campos
    private boolean validarCampos() {
        if (lblEditNombres.getText().toString().trim().isEmpty()) {
            lblEditNombres.setError("Ingrese sus nombres");
            return false;
        }
        if (lblEditApellidos.getText().toString().trim().isEmpty()) {
            lblEditApellidos.setError("Ingrese sus apellidos");
            return false;
        }
        if (lblEditDocumento.getText().toString().trim().isEmpty()) {
            lblEditDocumento.setError("Ingrese su documento");
            return false;
        }
        if (lblEditTelefono.getText().toString().trim().isEmpty()) {
            lblEditTelefono.setError("Ingrese su teléfono");
            return false;
        }
        if (lblEditCorreo.getText().toString().trim().isEmpty()) {
            lblEditCorreo.setError("Ingrese su correo");
            return false;
        }
        return true;
    }

    private void guardarDatosPerfil() {
        // Aquí podrías llamar a otro servicio para guardar los cambios en el servidor
        Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
    }
}