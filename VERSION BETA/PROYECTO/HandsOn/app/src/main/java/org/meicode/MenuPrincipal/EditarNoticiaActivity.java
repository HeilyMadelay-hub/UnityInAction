package org.meicode.MenuPrincipal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.meicode.handson.R;

public class EditarNoticiaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_IMAGE = 101; // Código de solicitud para selección de imagen
    private int noticiaId;
    private DatabaseHelper dbHelper;
    private EditText etTitulo, etContenido, etTags, etUbicacion,etNecesidades;
    private Button btnGuardar;
    private Button btnSeleccionarArchivo; // Botón para seleccionar archivo
    private Uri selectedImageUri;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_noticia);

        // Inicializar vistas
        etTitulo = findViewById(R.id.etTitulo);
        etContenido = findViewById(R.id.etContenido);
        etTags = findViewById(R.id.etTags);
        etUbicacion = findViewById(R.id.etLugar);
        etNecesidades= findViewById(R.id.etNecesidades);

        btnGuardar = findViewById(R.id.btnActualizar);
        btnSeleccionarArchivo = findViewById(R.id.btnSeleccionarArchivo);
        ImageView btnAtras = findViewById(R.id.btnAtras);

        dbHelper = new DatabaseHelper(this);

        // Action for the back button
        btnAtras.setOnClickListener(v -> finish());

        // Obtener el ID de la noticia desde el Intent
        noticiaId = getIntent().getIntExtra("NOTICIA_ID", -1);
        Log.d("EditarNoticia", "noticiaId recibido: " + noticiaId);

        if (noticiaId != -1) {
            // Cargar los datos de la noticia para poder editarla
            cargarDatosNoticia();
        } else {
            Toast.makeText(this, "No se pudo obtener el ID de la noticia", Toast.LENGTH_SHORT).show();
        }


        // Acción del botón "Seleccionar Archivo"
        btnSeleccionarArchivo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*"); // Tipo MIME para imágenes
            startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), REQUEST_CODE_SELECT_IMAGE);
        });


        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString().trim();
            String contenido = etContenido.getText().toString().trim();
            String tags = etTags.getText().toString().trim();
            String ubicacion = etUbicacion.getText().toString().trim();
            String necesidades = etNecesidades.getText().toString().trim();

            // Validar campos vacíos
            if (titulo.isEmpty() || contenido.isEmpty() || necesidades.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            String imagePath = selectedImageUri != null ? selectedImageUri.toString() : null;

            // Actualizar la noticia
            boolean isUpdated = dbHelper.updateNoticia(noticiaId, titulo, contenido, tags, ubicacion, necesidades, imagePath);
            if (isUpdated) {
                Toast.makeText(this, "Noticia actualizada exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar la noticia", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // Obtener el URI de la imagen seleccionada

            if (selectedImageUri != null) {
                String fileName = getFileName(selectedImageUri);
                Toast.makeText(this, "Imagen seleccionada: " + fileName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se pudo seleccionar una imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;

        if ("content".equals(uri.getScheme())) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (displayNameIndex != -1) { // Asegúrate de que la columna exista
                            result = cursor.getString(displayNameIndex);
                        }
                    }
                } finally {
                    cursor.close();
                }
            }
        }

        // Si no se pudo obtener el nombre del archivo desde el cursor, intenta obtenerlo del URI
        if (result == null) {
            String path = uri.getPath();
            int cut = path != null ? path.lastIndexOf('/') : -1;
            if (cut != -1 && path != null) {
                result = path.substring(cut + 1);
            }
        }

        return result != null ? result : "archivo_desconocido"; // Devuelve un nombre predeterminado si no se encuentra
    }


    private void cargarDatosNoticia() {
        Cursor cursor = dbHelper.getNoticiasById(noticiaId); // Consulta
        if (cursor != null && cursor.moveToFirst()) {
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
            String contenido = cursor.getString(cursor.getColumnIndexOrThrow("contenido"));
            String tags = cursor.getString(cursor.getColumnIndexOrThrow("tags"));
            String ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"));
            String necesidades = cursor.getString(cursor.getColumnIndexOrThrow("necesidades"));

            etTitulo.setText(titulo);
            etContenido.setText(contenido);
            etTags.setText(tags);
            etUbicacion.setText(ubicacion);
            etNecesidades.setText(necesidades);
            cursor.close();
        } else {
            Log.e("EditarNoticiaActivity", "No se encontraron datos para noticiaId: " + noticiaId);
            Toast.makeText(this, "No se encontraron datos para esta noticia", Toast.LENGTH_SHORT).show();
        }
    }


    private void cargarNoticia() {
        Cursor cursor = dbHelper.getNoticiasById(noticiaId);
        if (cursor != null && cursor.moveToFirst()) {
            etTitulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            etContenido.setText(cursor.getString(cursor.getColumnIndexOrThrow("contenido")));
            etTags.setText(cursor.getString(cursor.getColumnIndexOrThrow("tags")));
            etUbicacion.setText(cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")));
            cursor.close();
        }
    }
}
