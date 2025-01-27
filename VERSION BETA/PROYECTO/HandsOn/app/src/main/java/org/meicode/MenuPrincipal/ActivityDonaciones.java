package org.meicode.MenuPrincipal;

import static org.meicode.handson.R.*;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.meicode.handson.R;

public class ActivityDonaciones extends AppCompatActivity {

    EditText editTextOrganizacion, editTextFecha, editTextNombreCompleto, editTextNumeroTarjeta, editTextCVV, editTextFechaCaducidad;
    RadioGroup radioGroupMetodosPago;
    AppCompatButton btnDonar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donaciones);

        editTextOrganizacion = findViewById(R.id.editTextOrganizacion);
        editTextFecha = findViewById(R.id.editTextFechaDelDiaDeOrganizacion);
        editTextNombreCompleto = findViewById(R.id.editTextNombreCompleto);
        editTextNumeroTarjeta = findViewById(R.id.editTextNumeroTarjeta);
        editTextCVV = findViewById(R.id.editTextCVV);
        editTextFechaCaducidad = findViewById(R.id.editTextFechaCaducidad);
        radioGroupMetodosPago = findViewById(R.id.radioGroupMetodosPago);
        btnDonar = findViewById(R.id.btnDonar);

        ImageView devueltaflecha = findViewById(R.id.flecha_regresar);
        devueltaflecha.setOnClickListener(v -> finish());

        btnDonar.setOnClickListener(v -> procesarDonacion());
    }

    // Método para validar los campos ingresados
    private boolean validarCampos(String organizacion, String fecha, String nombreCompleto, String numeroTarjeta, String cvv) {
        if (organizacion.isEmpty() || fecha.isEmpty() || nombreCompleto.isEmpty() || numeroTarjeta.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!organizacion.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "La organización solo puede contener letras y espacios.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Toast.makeText(this, "La fecha debe estar en el formato dd/mm/yyyy.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!nombreCompleto.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "El nombre completo solo puede contener letras y espacios.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (numeroTarjeta.length() != 12) {
            Toast.makeText(this, "El número de tarjeta debe tener exactamente 12 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cvv.length() != 3) {
            Toast.makeText(this, "El CVV debe tener exactamente 3 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    private void procesarDonacion() {

        String organizacion = editTextOrganizacion.getText().toString().trim();
        String fecha = editTextFecha.getText().toString().trim();
        String nombreCompleto = editTextNombreCompleto.getText().toString().trim();
        String numeroTarjeta = editTextNumeroTarjeta.getText().toString().trim();
        String cvv = editTextCVV.getText().toString().trim();

        if (!validarCampos(organizacion, fecha, nombreCompleto, numeroTarjeta, cvv)) return;

        // Obtener el método de pago seleccionado
        String metodoPago = obtenerMetodoPago();
        if (metodoPago == null) {
            Toast.makeText(this, "Por favor, seleccione un método de pago.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Validar que la organización exista
        if (!validarOrganizacion(dbHelper, organizacion)) return;

        // Validar que la organización exista en la base de datos
        try (Cursor organizacionCursor = dbHelper.getOrganizacionByNombre(organizacion)) {
            if (organizacionCursor == null || !organizacionCursor.moveToFirst()) {
                Toast.makeText(this, "La organización no existe en la base de datos.", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        // Validar que el donante exista
        try (Cursor cursor = dbHelper.getDonanteByTarjetaPasswordYCvv(numeroTarjeta, nombreCompleto, cvv)) {
            if (cursor != null && cursor.moveToFirst()) {
                // Si el donante existe, registrar la donación
                int donanteId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                registrarDonacion(dbHelper, donanteId, organizacion, fecha, metodoPago);
            } else {
                Toast.makeText(this, "Donante no encontrado. Verifique los datos ingresados.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para validar que la organización exista en la base de datos
    private boolean validarOrganizacion(DatabaseHelper dbHelper, String organizacion) {
        try (Cursor organizacionCursor = dbHelper.getOrganizacionByNombre(organizacion)) {
            if (organizacionCursor == null || !organizacionCursor.moveToFirst()) {
                Toast.makeText(this, "La organización no existe en la base de datos.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    // Método para registrar la donación
    private void registrarDonacion(DatabaseHelper dbHelper, int donanteId, String organizacion, String fecha, String metodoPago) {
        boolean resultado = dbHelper.addDonacion(donanteId, organizacion, fecha, 0.0, metodoPago);

        if (resultado) {
            Toast.makeText(this, "Donación registrada exitosamente.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al registrar la donación.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obtener el método de pago seleccionado
    private String obtenerMetodoPago() {
        int metodoPagoId = radioGroupMetodosPago.getCheckedRadioButtonId();
        if (metodoPagoId == R.id.radioButtonBizum) return "Bizum";
        if (metodoPagoId == R.id.radioButtonVisa) return "Visa";
        if (metodoPagoId == R.id.radioButtonMastercard) return "Mastercard";
        return null;
    }
}
