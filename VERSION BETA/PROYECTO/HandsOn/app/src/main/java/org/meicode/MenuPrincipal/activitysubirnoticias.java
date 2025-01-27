package org.meicode.MenuPrincipal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.meicode.handson.R;

public class activitysubirnoticias extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_FILE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subirnoticias);

        // Initialize views
        ImageView btnBack = findViewById(R.id.btnAtras);
        AppCompatButton btnSubir = findViewById(R.id.btnSubir);
        AppCompatButton btnDescartar = findViewById(R.id.btnDescartar);
        AppCompatButton btnSeleccionarArchivo = findViewById(R.id.btnSeleccionarArchivo);

        // Action for the back button
        btnBack.setOnClickListener(v -> finish());

        btnSubir.setOnClickListener(v -> {

            EditText etTitulo = findViewById(R.id.etNombreNoticia);
            EditText etContenido = findViewById(R.id.etContenido);
            EditText etRequisitos = findViewById(R.id.etRequisitos); // Nuevo campo

            String titulo = etTitulo.getText().toString().trim();
            String contenido = etContenido.getText().toString().trim();
            String requisitos = etRequisitos.getText().toString().trim();

            // Validación de campos vacíos
            if (titulo.isEmpty() || contenido.isEmpty() || requisitos.isEmpty()) {
                Toast.makeText(activitysubirnoticias.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validaciones adicionales
            if (!esTituloValido(titulo)) {
                Toast.makeText(this, "El título contiene caracteres no válidos.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!esContenidoValido(contenido)) {
                Toast.makeText(this, "El contenido contiene caracteres no válidos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener usuario ID desde SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            int usuarioId = sharedPreferences.getInt("USER_ID", -1);

            if (usuarioId == -1) {
                Toast.makeText(activitysubirnoticias.this, "Sesión expirada. Inicia sesión nuevamente.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insertar noticia en la base de datos
            DatabaseHelper dbHelper = new DatabaseHelper(activitysubirnoticias.this);
            boolean isInserted = dbHelper.addNoticia(titulo, contenido, requisitos, usuarioId);

            if (isInserted) {
                Toast.makeText(activitysubirnoticias.this, "Noticia subida exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activitysubirnoticias.this, ActivityPerfilUsuario.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(activitysubirnoticias.this, "Error al subir la noticia", Toast.LENGTH_SHORT).show();
            }
        });




        // Action for "Descartar" button
        btnDescartar.setOnClickListener(v -> {
            Toast.makeText(activitysubirnoticias.this, "Noticia descartada", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Action for "Seleccionar Archivo" button
        btnSeleccionarArchivo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            startActivityForResult(Intent.createChooser(intent, "Selecciona un archivo"), REQUEST_CODE_SELECT_FILE);
        });
    }

    // Expresión regular para validar el título (no solo números ni caracteres repetitivos)
    private boolean esTituloValido(String titulo) {
        // Permitir letras, números, espacios y caracteres especiales
        // No permitir solo números o caracteres especiales repetidos
        String regex = "^(?![\\d\\W_]+$)(?![\\W_]+$)(?![\\s]+$).{3,}$";
        return titulo.matches(regex);
    }

    // Expresión regular para validar el contenido (similar al título)
    private boolean esContenidoValido(String contenido) {
        // Permitir letras, números, espacios y caracteres especiales
        // No permitir solo números o caracteres especiales repetidos
        String regex = "^(?![\\d\\W_]+$)(?![\\W_]+$)(?![\\s]+$).{3,}$";
        return contenido.matches(regex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_FILE) {
            if (resultCode == RESULT_OK && data != null) {
                Uri selectedFileUri = data.getData();

                if (selectedFileUri != null) {
                    String fileName = getFileName(selectedFileUri);
                    Toast.makeText(this, "Archivo seleccionado: " + fileName, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "No se pudo seleccionar un archivo", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Selección de archivo cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to get the file name from a URI
    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
