package com.example.gato.actividades;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.gato.R;
import com.example.gato.clases.Gaturro;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class NuevoGatoActivity extends AppCompatActivity  implements  View.OnClickListener{

    private EditText lblNombre, lblRaza, lblEdad;
    private Spinner cmbSexo, cmbTamano;
    private Button btnCancelar, btnAgregar,btnTomarFoto;
    ImageView imgGaturro;
    Gaturro gaturro;
    private static String urlAgregarGato ="http://gatitos.atwebpages.com/services/agregarGaturros.php";

    static final int REQUEST_TAKE_PHOTO = 1;
    String sRutaFotoTemporal;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gato); // Configura tu layout principal.

        // Inicializa tus vistas
        lblNombre = findViewById(R.id.lblNombre);
        lblRaza = findViewById(R.id.lblRaza);
        lblEdad = findViewById(R.id.lblEdad);
        cmbSexo = findViewById(R.id.cmbSexo);
        cmbTamano = findViewById(R.id.cmbTamano);
        btnCancelar = findViewById(R.id.btnCancelar);
        imgGaturro = findViewById(R.id.actNuevGatoImg);
        btnTomarFoto = findViewById(R.id.frgMasBtnTomarGato);
        btnAgregar = findViewById(R.id.btnAgregarGaturro); // Asegúrate de que este botón exista en tu XML.
        verificarPermisos();
        btnTomarFoto.setOnClickListener(this);
        gaturro = (Gaturro) getIntent().getSerializableExtra("gaturro");

        btnAgregar.setOnClickListener(this);

        // Configuración de listeners
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza la Activity cuando se cancela.
            }
        });

    }



    private boolean validarCampos() {
        if (lblNombre.getText().toString().trim().isEmpty()) {
            lblNombre.setError("Ingrese el nombre");
            return false;
        }
        if (lblRaza.getText().toString().trim().isEmpty()) {
            lblRaza.setError("Ingrese la raza");
            return false;
        }
        if (lblEdad.getText().toString().trim().isEmpty()) {
            lblEdad.setError("Ingrese la edad");
            return false;
        }
        return true;
    }
    private void verificarPermisos() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    1000);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "¡Foto tomada con éxito!", Toast.LENGTH_SHORT).show();
                    if (photoURI != null) {
                        // Muestra la imagen en tu ImageView
                        imgGaturro.setImageURI(photoURI);
                    }
                } else {
                    Toast.makeText(this, "¡Se canceló tomar la foto!", Toast.LENGTH_SHORT).show();

                    // Elimina la foto temporal si existe
                    if (sRutaFotoTemporal != null) {
                        File temp = new File(sRutaFotoTemporal);
                        if (temp.exists()) {
                            temp.delete();
                        }
                    }
                }
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Crea un nombre único para el archivo basado en la fecha y hora actual
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "JPEG_" + timeStamp;

        // Obtén el directorio para guardar las imágenes
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println(directorio);

        // Crea un archivo temporal
        File imagen = File.createTempFile(fileName, ".jpg", directorio);

        // Guarda la ruta del archivo temporal
        sRutaFotoTemporal = imagen.getAbsolutePath();

        return imagen;
    }

    private void tomarFoto() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent iTomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.gato.fileprovider", createImageFile());
                iTomarFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(iTomarFoto, REQUEST_TAKE_PHOTO);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Los Gatitos");
                builder.setMessage("No se puede acceder a la cámara");
                builder.setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void limpiarFormulario() {
        lblEdad.setText("");
        lblNombre.setText("");
        lblRaza.setText("");
        cmbSexo.setSelection(0); // Establecer el primer elemento como seleccionado
        imgGaturro.setImageResource(0); // Restablecer la imagen del reporte
        cmbTamano.setSelection(0); // Establecer el primer elemento como seleccionado

    }
    private String image_view_to_base64(ImageView jiv_foto, int maxSizeBytes) {
        try {
            // Obtener el bitmap del ImageView
            Bitmap bitmap = ((BitmapDrawable) jiv_foto.getDrawable()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            int quality = 100; // Iniciar con calidad máxima
            do {
                stream.reset(); // Resetear el stream para intentar de nuevo
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
                quality -= 5; // Reducir la calidad en cada intento
            } while (stream.toByteArray().length > maxSizeBytes && quality > 0);

            // Convertir los datos a Base64
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Retorna null si ocurre un error
        }
    }
    private  void enviarGaturro(){
        //gaturro= (Gaturro) getIntent().getSerializableExtra("gaturro");
        AsyncHttpClient ahcAgregarGaturro = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String base64Image = image_view_to_base64(imgGaturro, 470130);
        params.put("nombre_gato", lblNombre.getText().toString());
        params.put("raza", lblRaza.getText().toString());
        params.put("edad", lblEdad.getText().toString());
        params.put("sexo", cmbSexo.getSelectedItem().toString());
        params.put("tamano", cmbTamano.getSelectedItem().toString());
        params.put("imagen_gaturro", base64Image);

        ahcAgregarGaturro.post(urlAgregarGato, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if (statusCode == 200) {

                    int status = rawJsonResponse.isEmpty() ? 0 : Integer.parseInt(rawJsonResponse);
                    System.out.println(lblRaza.getText().toString());
                    if (status == 1) {

                        // Usa ActivityName.this para acceder al contexto de la actividad.
                        Toast.makeText(getApplicationContext(), "Reporte enviado", Toast.LENGTH_SHORT).show();
                        limpiarFormulario(); // Limpia los campos del formulario
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al enviar reporte", Toast.LENGTH_SHORT).show();
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




    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.frgMasBtnTomarGato)
            tomarFoto();
        else if(view.getId() == R.id.btnCancelar)
            finish();
        else if (view.getId()==R.id.btnAgregarGaturro){
            enviarGaturro();
            Toast.makeText(getApplicationContext(), "GATO SUBIDO", Toast.LENGTH_SHORT).show();
            finish();
        }


    }


}