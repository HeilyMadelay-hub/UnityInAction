package org.meicode.MenuPrincipal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.meicode.handson.R;

public class ActivityIniciar extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Spinner spinnerUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarsesion);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        spinnerUserType = findViewById(R.id.spinnerUserType);


        ImageView btnBack = findViewById(R.id.flecha_regresar);
        btnBack.setOnClickListener(v -> finish());

        // Configurar el adaptador del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.user_typesB,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        // Configura el botón de iniciar sesión
        findViewById(R.id.btnIniciarSesionr).setOnClickListener(v -> iniciarSesion());
    }


    private void iniciarSesion() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String userType = spinnerUserType.getSelectedItem().toString();

        // Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty() || userType.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación de formato de email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, ingresa un correo electrónico válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        try (Cursor cursor = dbHelper.getUsuarioPorTipoEmailYPassword(userType, email, password)) {
            if (cursor != null && cursor.moveToFirst()) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("USER_ID", userId);
                editor.putString("USER_TYPE", userType);
                editor.putString("USER_EMAIL", email);
                editor.putBoolean("IS_LOGGED_IN", true);
                editor.apply();

                Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ActivityVernoticias.class));
                finish();
            } else {
                Toast.makeText(this, "Usuario no encontrado o credenciales incorrectas.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
