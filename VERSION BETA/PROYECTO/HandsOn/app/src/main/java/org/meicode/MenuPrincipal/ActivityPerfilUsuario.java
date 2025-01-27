package org.meicode.MenuPrincipal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.meicode.handson.R;
import java.util.ArrayList;
import java.util.List;

public class ActivityPerfilUsuario extends AppCompatActivity {

    private RecyclerView rvNoticiasUsuario;
    private DatabaseHelper dbHelper;
    private NoticiasAdapter adapter;
    private List<Noticia> noticiasList;
    private ImageView imgProfile;
    private ImageView btnBack;
    private AppCompatButton btnUploadNews, btnLogout;
    private TextView tvEmail, tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        // Inicializa las vistas
        initViews();

        btnBack.setOnClickListener(v -> finish());

        // Inicializa la base de datos
        dbHelper = new DatabaseHelper(this);

        // Inicializa la lista de noticias
        noticiasList = new ArrayList<>();

        // Cargar datos del usuario
        int usuarioId = obtenerUsuarioActualId();
        cargarPerfilUsuario(usuarioId);

        // Cargar las noticias del usuario
        cargarNoticias(usuarioId);

        // Configurar acciones de los botones
        configurarAcciones();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Obtener el usuario actual
        int usuarioId = obtenerUsuarioActualId();

        // Recargar las noticias
        cargarNoticias(usuarioId);

        // Configurar el LayoutManager solo si no está configurado (optimización)
        if (rvNoticiasUsuario.getLayoutManager() == null) {
            rvNoticiasUsuario.setLayoutManager(new LinearLayoutManager(this));
        }

        // Notificar al adaptador que los datos han cambiado
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        imgProfile = findViewById(R.id.iconosubirfoto); // Foto de perfil
        tvNombre = findViewById(R.id.tvUsuarioNombre); // Nombre del usuario
        tvEmail = findViewById(R.id.tvUsuarioEmail); // Email del usuario
        rvNoticiasUsuario = findViewById(R.id.rvNoticiasUsuario); // RecyclerView
        btnBack = findViewById(R.id.btnBack); // Botón para volver atrás
        btnUploadNews = findViewById(R.id.btnUploadNews); // Botón para subir noticias
        btnLogout = findViewById(R.id.btnLogout); // Botón para cerrar sesión
    }

    private void cargarPerfilUsuario(int userId) {
        try (Cursor cursor = dbHelper.getUsuarioById(userId)) {

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));

                tvNombre.setText(apellido.isEmpty() ? nombre : nombre + " " + apellido);
                tvEmail.setText(email);
            } else {
                Toast.makeText(this, "No se encontraron datos para el usuario.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ActivityPerfilUsuario", "Error al cargar perfil: " + e.getMessage(), e);
            Toast.makeText(this, "Error al cargar perfil.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarNoticias(int usuarioId) {
        noticiasList.clear();
        Cursor cursor = dbHelper.getNoticiasByUsuario(usuarioId);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                    String contenido = cursor.getString(cursor.getColumnIndexOrThrow("contenido"));
                    String tags = cursor.getString(cursor.getColumnIndexOrThrow("tags")); // Asegúrate de que exista
                    String archivo = cursor.getString(cursor.getColumnIndexOrThrow("archivo")); // Asegúrate de que exista

                    Noticia noticia = new Noticia(
                            id, titulo, contenido, tags, archivo, usuarioId
                    );
                    noticiasList.add(noticia);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No se encontraron noticias.", Toast.LENGTH_SHORT).show();
            }
        } catch (IllegalArgumentException e) {
            Log.e("ActivityPerfilUsuario", "Error al cargar noticias: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (adapter == null) {
            configurarRecyclerView();
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    private void configurarRecyclerView() {
        rvNoticiasUsuario.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoticiasAdapter(noticiasList, this, new NoticiasAdapter.OnNoticiaListener() {
            @Override
            public void onEditarClick(Noticia noticia) {
                // Abre la actividad de edición
                Intent intent = new Intent(ActivityPerfilUsuario.this, EditarNoticiaActivity.class);
                intent.putExtra("noticia_id", noticia.getId());
                startActivity(intent);
            }

            @Override
            public boolean onEliminarClick(Noticia noticia) {
                // Lógica para eliminar la noticia
                boolean isDeleted = dbHelper.deleteNoticia(noticia.getId(), obtenerUsuarioActualId());
                if (isDeleted) {
                    noticiasList.remove(noticia);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ActivityPerfilUsuario.this, "Noticia eliminada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityPerfilUsuario.this, "Error al eliminar la noticia.", Toast.LENGTH_SHORT).show();
                }
                return isDeleted;
            }
        }, true); // Es Usuario
        rvNoticiasUsuario.setAdapter(adapter);

    }

    private void configurarAcciones() {
        btnBack.setOnClickListener(v -> finish());

        btnUploadNews.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityPerfilUsuario.this, activitysubirnoticias.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> cerrarSesion());
    }

    private void cerrarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        Intent intent = new Intent(this, ActivityIniciar.class);
        startActivity(intent);
        finish();
    }

    private int obtenerUsuarioActualId() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", -1);
        if (userId == -1) {
            Toast.makeText(this, "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ActivityIniciar.class));
            finish();
        }
        return userId;
    }
}
