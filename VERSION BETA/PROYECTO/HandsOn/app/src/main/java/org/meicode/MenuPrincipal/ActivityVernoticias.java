package org.meicode.MenuPrincipal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.meicode.handson.R;

import java.util.ArrayList;
import java.util.List;
public class ActivityVernoticias extends AppCompatActivity {

    private EditText searchBar;
    private ImageView btnUploadNews;

    private List<Noticia> noticiasList; // Lista completa de noticias
    private RecyclerView rvNoticiasUsuario;
    private DatabaseHelper dbHelper;
    private NoticiasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vernoticias);

        // Inicializar vistas y variables
        rvNoticiasUsuario = findViewById(R.id.rvNoticias);
        searchBar = findViewById(R.id.searchBar);
        btnUploadNews = findViewById(R.id.btnUploadNews);
        noticiasList = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        // Configurar RecyclerView
        configurarRecyclerView();

        btnUploadNews.setOnClickListener(v -> {
            if (verificarSesion()) {
                // Redirigir al perfil del usuario
                Intent intent = new Intent(ActivityVernoticias.this, ActivityPerfilUsuario.class);
                startActivity(intent);
            } else {
                // Redirigir a la pantalla de inicio de sesión
                Toast.makeText(this, "Debe iniciar sesión para subir noticias.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityVernoticias.this, ActivityIniciar.class); // Corregido
                startActivity(intent);
            }
        });


        // Acción para el botón de la flecha (Volver atrás)
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Cargar noticias
        cargarNoticias();

        // Configurar búsqueda en tiempo real
        configurarBusqueda();
    }

    private void configurarRecyclerView() {
        rvNoticiasUsuario.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoticiasAdapter(noticiasList, this, null, false); // No hay edición/eliminación en este caso
        rvNoticiasUsuario.setAdapter(adapter);
    }

    private void configurarBusqueda() {
        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarNoticias(s.toString()); // Llamar al método para filtrar noticias
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
    }

    private void filtrarNoticias(String query) {
        List<Noticia> filteredList = new ArrayList<>();
        for (Noticia noticia : noticiasList) {
            if (noticia.getTitulo().toLowerCase().contains(query.toLowerCase()) ||
                    noticia.getContenido().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(noticia);
            }
        }
        adapter.updateList(filteredList); // Método para actualizar la lista en el adaptador
    }

    private void cargarNoticias() {
        noticiasList.clear();

        // Siempre cargar las noticias públicas
        Cursor cursor = dbHelper.getNoticiasPublicas();
        cargarNoticiasDesdeCursor(cursor);

        Log.d("ActivityVernoticias", "Noticias cargadas: " + noticiasList.size());

        // Actualizar la vista
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void cargarNoticiasDesdeCursor(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String contenido = cursor.getString(cursor.getColumnIndexOrThrow("contenido"));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow("tags"));
                String archivo = cursor.getString(cursor.getColumnIndexOrThrow("archivo"));
                String ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")); // Ubicación
                String fechaHora = cursor.getString(cursor.getColumnIndexOrThrow("fecha_hora"));
                boolean esPublica = cursor.getInt(cursor.getColumnIndexOrThrow("es_publica")) == 1;
                String fuente = cursor.getString(cursor.getColumnIndexOrThrow("fuente"));
                int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id"));

                // Crear una instancia de Noticia con los datos disponibles
                noticiasList.add(new Noticia(
                        id, titulo, contenido, tags, archivo,  usuarioId
                ));
            } while (cursor.moveToNext());

            cursor.close(); // Cerrar el cursor para liberar recursos
        }
    }


    private boolean verificarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false);
        Log.d("ActivityVernoticias", "Verificar sesión: " + isLoggedIn);
        return isLoggedIn; // Retorna true si hay sesión activa
    }

}

