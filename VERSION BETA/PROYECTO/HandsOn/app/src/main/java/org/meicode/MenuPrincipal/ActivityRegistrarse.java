package org.meicode.MenuPrincipal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import org.meicode.handson.R;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class ActivityRegistrarse extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgProfile;
    private Uri selectedImageUri;
    private DatabaseHelper dbHelper;
    private LinearLayout dynamicFormContainer;
    private Spinner spinnerUserType;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        loadProfileImage(uri);
                    } else {
                        Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Selección de imagen cancelada", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        dbHelper = new DatabaseHelper(this);

        // Inicializamos elementos UI
        initializeUI();

        // Configuramos el spinner para tipos de usuario
        setupUserTypeSpinner();
    }

    private void initializeUI() {
        imgProfile = findViewById(R.id.iconosubirfoto);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        AppCompatButton btnUploadPhoto = findViewById(R.id.btnUpload);
        btnUploadPhoto.setOnClickListener(v -> openGallery());

        AppCompatButton btnRegister = findViewById(R.id.btnLog);
        btnRegister.setOnClickListener(v -> registerUser());

        dynamicFormContainer = findViewById(R.id.dynamicFormContainer);
        spinnerUserType = findViewById(R.id.spinnerUserType);
    }

    private void setupUserTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.user_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String userType = parent.getItemAtPosition(position).toString();
                dynamicFormContainer.removeAllViews();
                addFieldsBasedOnUserType(userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("ActivityRegistrarse", "Nada seleccionado");
            }
        });
    }

    private void addFieldsBasedOnUserType(String userType) {
        switch (userType) {
            case "Organización":
                addOrganizationFields(dynamicFormContainer);
                break;
            case "Reportero Ciudadano":
                addReporterFields(dynamicFormContainer);
                break;
            case "Voluntario":
                addVolunteerFields(dynamicFormContainer);
                break;
            case "Profesional":
                addProfessionalFields(dynamicFormContainer);
                break;
            case "Donante":
                addDonorFields(dynamicFormContainer);
                break;
            default:
                Toast.makeText(this, "Tipo de usuario no reconocido", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, PICK_IMAGE_REQUEST);
            } else {
                launchGalleryIntent();
            }
        } else {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
            } else {
                launchGalleryIntent();
            }
        }
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void loadProfileImage(Uri uri) {
        try {
            Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
            imgProfile.setImageBitmap(bitmap);
            selectedImageUri = uri;
        } catch (IOException e) {
            Toast.makeText(this, "Error al cargar la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {

        String userType = spinnerUserType.getSelectedItem().toString();
        if (userType.isEmpty()) {
            Toast.makeText(this, "Seleccione un tipo de usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = getDynamicFieldValue(dynamicFormContainer, "Correo electrónico de contacto");
        if (email.isEmpty()) {
            Toast.makeText(this, "El correo electrónico es obligatorio.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación de email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Correo electrónico inválido.", Toast.LENGTH_SHORT).show();
            return;
        }


        if (dbHelper.isEmailRegistered(email)) {
            Toast.makeText(this, "El correo ya está registrado. Use otro.", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = generateDummyUserId();
        boolean success = false;

        if (!validateFieldsForUserType(userType)) {
            return; // Si la validación falla, no continuar con el registro
        }


        switch (userType) {
            case "Organización":
                success = handleOrganizacion(dynamicFormContainer, userId);
                break;
            case "Reportero Ciudadano":
                success = handleReporter(dynamicFormContainer, userId);
                break;
            case "Voluntario":
                success = handleVolunteer(dynamicFormContainer, userId);
                break;
            case "Profesional":
                success = handleProfessional(dynamicFormContainer, userId);
                break;
            case "Donante":
                success = handleDonor(dynamicFormContainer, userId);
                break;
        }

        if (success) {
            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFieldsForUserType(String userType) {
        boolean isValid = true;

        // Verificar todos los campos dependiendo del tipo de usuario
        switch (userType) {
            case "Organización":
                isValid = validateOrganizationFields();
                break;
            case "Reportero Ciudadano":
                isValid = validateReporterFields();
                break;
            case "Voluntario":
                isValid = validateVolunteerFields();
                break;
            case "Profesional":
                isValid = validateProfessionalFields();
                break;
            case "Donante":
                isValid = validateDonorFields();
                break;
            default:
                isValid = false;
                Toast.makeText(this, "Tipo de usuario no reconocido", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }


    private boolean validateVolunteerFields() {
        String nombre = getDynamicFieldValue(dynamicFormContainer, "Nombre completo");
        String correo = getDynamicFieldValue(dynamicFormContainer, "Correo electrónico de contacto");
        String telefono = getDynamicFieldValue(dynamicFormContainer, "Teléfono");
        String experiencia = getDynamicFieldValue(dynamicFormContainer, "Área de experiencia");
        String disponibilidad = getDynamicFieldValue(dynamicFormContainer, "Disponibilidad de horarios");
        String motivacion = getDynamicFieldValue(dynamicFormContainer, "Motivación para ayudar");

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || experiencia.isEmpty() || disponibilidad.isEmpty() || motivacion.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para el voluntario", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que el nombre solo contenga letras
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            Toast.makeText(this, "El nombre solo debe contener letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que el correo tenga un formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar teléfono: debe comenzar con '+' seguido de dos dígitos para el prefijo y 9 dígitos para el número
        if (!telefono.matches("^\\+\\d{2}\\d{9}$")) {
            Toast.makeText(this, "El teléfono debe comenzar con '+' seguido de dos dígitos de prefijo y 9 dígitos del número", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la experiencia solo contenga letras
        if (!experiencia.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            Toast.makeText(this, "El área de experiencia solo debe contener letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la disponibilidad contenga letras, números y símbolos permitidos
        if (!disponibilidad.matches("^[a-zA-Z0-9:\\-\\s]+$")) {
            Toast.makeText(this, "La disponibilidad debe contener letras, números y un formato válido de horario", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la motivación solo contenga letras
        if (!motivacion.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            Toast.makeText(this, "La motivación solo debe contener letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean validateProfessionalFields() {
        String nombre = getDynamicFieldValue(dynamicFormContainer, "Nombre completo");
        String correo = getDynamicFieldValue(dynamicFormContainer, "Correo electrónico de contacto");
        String telefono = getDynamicFieldValue(dynamicFormContainer, "Teléfono");
        String profesion = getDynamicFieldValue(dynamicFormContainer, "Profesión y especialización");
        String colegiatura = getDynamicFieldValue(dynamicFormContainer, "Número de colegiatura/profesional");
        String experiencia = getDynamicFieldValue(dynamicFormContainer, "Experiencia o certificados");

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || correo.isEmpty() || profesion.isEmpty() || telefono.isEmpty() || colegiatura.isEmpty() || experiencia.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para el profesional", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que el nombre solo contenga letras y espacios
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            Toast.makeText(this, "El nombre solo debe contener letras y espacios", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que el correo tenga un formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar teléfono: debe comenzar con '+' seguido de dos dígitos para el prefijo y 9 dígitos para el número
        if (!telefono.matches("^\\+\\d{2}\\d{9}$")) {
            Toast.makeText(this, "El teléfono debe comenzar con '+' seguido de dos dígitos de prefijo y 9 dígitos del número", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la profesión solo contenga letras y espacios
        if (!profesion.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            Toast.makeText(this, "La profesión solo debe contener letras y espacios", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la colegiatura/profesional sea un número (sin letras)
        if (!colegiatura.matches("^\\d+$")) {
            Toast.makeText(this, "El número de colegiatura debe contener solo números", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la experiencia permita letras, símbolos de escritura y puntuación
        if (!experiencia.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s.,!¡'\"/()\\-]+$")) {
            Toast.makeText(this, "La experiencia solo puede contener letras, números y símbolos de escritura", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Si todas las validaciones son correctas
    }

    private boolean validateOrganizationFields() {
        String nombre = getDynamicFieldValue(dynamicFormContainer, "Nombre de la organización");
        String tipo = getDynamicFieldValue(dynamicFormContainer, "Tipo de organización");
        String nifCif = getDynamicFieldValue(dynamicFormContainer, "Número de identificación fiscal (NIF/CIF)");
        String direccion = getDynamicFieldValue(dynamicFormContainer, "Dirección");
        String telefono = getDynamicFieldValue(dynamicFormContainer, "Número de teléfono");
        String email = getDynamicFieldValue(dynamicFormContainer, "Correo electrónico de contacto");
        String password = getDynamicFieldValue(dynamicFormContainer, "Contraseña");

        if (nombre.isEmpty() || tipo.isEmpty() || nifCif.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos de la organización", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que el nombre de la organización y tipo contengan solo letras (y espacios)
        if (!nombre.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "El nombre de la organización debe contener solo letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!tipo.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "El tipo de organización debe contener solo letras", Toast.LENGTH_SHORT).show();
            return false;
        }


        // Validar que el NIF tenga 5 números
        if (!nifCif.matches("\\d{5}")) {
            Toast.makeText(this, "El NIF debe tener exactamente 5 números", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el formato de la dirección (puede ser algo básico como tener al menos un número y una calle)
        if (!direccion.matches(".*\\d.*")) {
            Toast.makeText(this, "La dirección debe tener un número de calle", Toast.LENGTH_SHORT).show();
            return false;
        }


        // Validar teléfono: debe comenzar con '+' seguido de dos dígitos para el prefijo y 9 dígitos para el número
        if (!telefono.matches("^\\+\\d{2}\\d{9}$")) {
            Toast.makeText(this, "El teléfono debe comenzar con '+' seguido de dos dígitos de prefijo y 9 dígitos del número", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private int generateDummyUserId() {
        return (int) (Math.random() * 10000);
    }

    private boolean validateReporterFields() {
        String nombre = getDynamicFieldValue(dynamicFormContainer, "Nombre completo");
        String correo = getDynamicFieldValue(dynamicFormContainer, "Correo electrónico de contacto");
        String telefono = getDynamicFieldValue(dynamicFormContainer, "Teléfono (opcional)");
        String descripcion = getDynamicFieldValue(dynamicFormContainer, "Descripción de la noticia");
        String ubicacion = getDynamicFieldValue(dynamicFormContainer, "Ubicación de la noticia");
        String fecha = getDynamicFieldValue(dynamicFormContainer, "Fecha y hora del evento");
        String fuente = getDynamicFieldValue(dynamicFormContainer, "Fuente de la información (si no fue testigo directo)");

        if (nombre.isEmpty() || correo.isEmpty() || descripcion.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para el reportero", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que el nombre solo contenga letras
        if (!nombre.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "El nombre de la organización debe contener solo letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar teléfono: debe comenzar con '+' seguido de dos dígitos para el prefijo y 9 dígitos para el número
        if (!telefono.matches("^\\+\\d{2}\\d{9}$")) {
            Toast.makeText(this, "El teléfono debe comenzar con '+' seguido de dos dígitos de prefijo y 9 dígitos del número", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la descripción contenga solo letras y números, pero no solo caracteres especiales
        if (!descripcion.matches("[a-zA-Z0-9 ]+")) {
            Toast.makeText(this, "La descripción debe contener solo letras y números", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar ubicación: solo letras y números
        if (!ubicacion.matches("[a-zA-Z0-9 ]+")) {
            Toast.makeText(this, "La ubicación debe contener solo letras y números", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar fecha con el formato adecuado (dd/MM/yyyy HH:mm)
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
            Toast.makeText(this, "La fecha debe estar en el formato dd/MM/yyyy HH:mm", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar que la fuente contenga solo letras
        if (!fuente.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "La fuente de la información debe contener solo letras", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private String getDynamicFieldValue(LinearLayout container, String hint) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                String editTextHint = editText.getHint() != null ? editText.getHint().toString() : "";
                if (hint.equals(editTextHint)) {
                    return editText.getText().toString().trim();
                }
            }
        }
        Log.d("getDynamicFieldValue", "No se encontró un campo con el hint: " + hint);
        return ""; // Retorna vacío si no se encuentra el campo
    }


    private void addEditTextField(LinearLayout container, String hint) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        container.addView(editText);
    }

    private void addOrganizationFields(LinearLayout container) {
        addEditTextField(container, "Nombre de la organización");
        addEditTextField(container, "Tipo de organización");
        addEditTextField(container, "Número de identificación fiscal (NIF/CIF)");
        addEditTextField(container, "Dirección");
        addEditTextField(container, "Número de teléfono");
        addEditTextField(container, "Correo electrónico de contacto");
        addEditTextField(container, "Contraseña");
    }

    private void addReporterFields(LinearLayout container) {
        addEditTextField(container, "Nombre completo");
        addEditTextField(container, "Correo electrónico de contacto");
        addEditTextField(container, "Teléfono (opcional)");
        addEditTextField(container, "Descripción de la noticia");
        addEditTextField(container, "Ubicación de la noticia");
        addEditTextField(container, "Fecha y hora del evento");
        addEditTextField(container, "Fuente de la información (si no fue testigo directo)");
        addEditTextField(container, "Contraseña");
    }

    private void addVolunteerFields(LinearLayout container) {
        addEditTextField(container, "Nombre completo");
        addEditTextField(container, "Correo electrónico de contacto");
        addEditTextField(container, "Teléfono");
        addEditTextField(container, "Área de experiencia o habilidades específicas");
        addEditTextField(container, "Disponibilidad de horarios");
        addEditTextField(container, "Motivación para ayudar (opcional)");
        addEditTextField(container, "Datos de emergencia (nombre y contacto de emergencia, opcional)");
        addEditTextField(container, "Contraseña");
    }

    private void addProfessionalFields(LinearLayout container) {
        addEditTextField(container, "Nombre completo");
        addEditTextField(container, "Correo electrónico de contacto");
        addEditTextField(container, "Teléfono");
        addEditTextField(container, "Profesión y especialización");
        addEditTextField(container, "Número de colegiatura/profesional (si aplica)");
        addEditTextField(container, "Experiencia o certificados relevantes");
        addEditTextField(container, "Disponibilidad para colaborar");
        addEditTextField(container, "Motivación para colaborar (opcional)");
        addEditTextField(container, "Contraseña");
    }

    private void addDonorFields(LinearLayout container) {
        addEditTextField(container, "Nombre completo o seudónimo");
        addEditTextField(container, "Correo electrónico de contacto");
        addEditTextField(container, "Cantidad a donar");
        addEditTextField(container, "Método de pago");
        addEditTextField(container, "Numero Tarjeta");
        addEditTextField(container, "CVV");
        addEditTextField(container, "Fecha de caducidad de la tarjeta");
        addEditTextField(container, "Dirección postal (opcional)");
        addEditTextField(container, "Motivación para donar (opcional)");
        addEditTextField(container, "Contraseña");
    }

    private boolean handleOrganizacion(LinearLayout container, int userId) {
        String nombreOrganizacion = getDynamicFieldValue(container, "Nombre de la organización");
        String tipo = getDynamicFieldValue(container, "Tipo de organización");
        String nifCif = getDynamicFieldValue(container, "Número de identificación fiscal (NIF/CIF)");
        String direccion = getDynamicFieldValue(container, "Dirección");
        String telefono = getDynamicFieldValue(container, "Número de teléfono");
        String emailContacto = getDynamicFieldValue(container, "Correo electrónico de contacto");
        String password = getDynamicFieldValue(container, "Contraseña");

        if (nombreOrganizacion.isEmpty() || tipo.isEmpty() || nifCif.isEmpty() || direccion.isEmpty() ||
                emailContacto.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para la organización", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean userCreated = dbHelper.addUsuario(
                "Organización",
                nombreOrganizacion,
                "",
                emailContacto,
                telefono,
                password,
                null
        );

        if (userCreated) {
            int newUserId = dbHelper.getLastInsertedUserId();

            return dbHelper.addOrganizacion(
                    newUserId,
                    nombreOrganizacion,
                    tipo,
                    nifCif,
                    direccion,
                    telefono,
                    emailContacto
            );
        } else {
            Toast.makeText(this, "Error al registrar la organización.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean handleReporter(LinearLayout container, int userId) {
        String nombre = getDynamicFieldValue(container, "Nombre completo");
        String correo = getDynamicFieldValue(container, "Correo electrónico de contacto");
        String telefono = getDynamicFieldValue(container, "Teléfono (opcional)");
        String descripcionNoticia = getDynamicFieldValue(container, "Descripción de la noticia");
        String ubicacion = getDynamicFieldValue(container, "Ubicación de la noticia");
        String fechaHora = getDynamicFieldValue(container, "Fecha y hora del evento");
        String fuente = getDynamicFieldValue(container, "Fuente de la información (si no fue testigo directo)");
        String password = getDynamicFieldValue(container, "Contraseña");

        if (nombre.isEmpty() || correo.isEmpty() || descripcionNoticia.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para el reportero", Toast.LENGTH_SHORT).show();
            return false;
        }

        return dbHelper.addUsuario(
                "Reportero Ciudadano",
                nombre,
                "",
                correo,
                telefono,
                password,
                null
        );
    }

    private boolean handleVolunteer(LinearLayout container, int userId) {
        String nombre = getDynamicFieldValue(container, "Nombre completo");
        String correo = getDynamicFieldValue(container, "Correo electrónico de contacto");
        String telefono = getDynamicFieldValue(container, "Teléfono");
        String experiencia = getDynamicFieldValue(container, "Área de experiencia o habilidades específicas");
        String disponibilidad = getDynamicFieldValue(container, "Disponibilidad de horarios");
        String motivacion = getDynamicFieldValue(container, "Motivación para ayudar (opcional)");
        String emergencia = getDynamicFieldValue(container, "Datos de emergencia (nombre y contacto de emergencia, opcional)");
        String password = getDynamicFieldValue(container, "Contraseña");

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || experiencia.isEmpty() || disponibilidad.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para el voluntario", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean userCreated = dbHelper.addUsuario(
                "Voluntario",
                nombre,
                "",
                correo,
                telefono,
                password,
                null
        );

        if (userCreated) {
            int newUserId = dbHelper.getLastInsertedUserId();

            return dbHelper.addVoluntario(
                    newUserId,
                    experiencia,
                    disponibilidad,
                    motivacion,
                    emergencia
            );
        } else {
            Toast.makeText(this, "Error al registrar el voluntario.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean handleProfessional(LinearLayout container, int userId) {
        String nombre = getDynamicFieldValue(container, "Nombre completo");
        String correo = getDynamicFieldValue(container, "Correo electrónico de contacto");
        String telefono = getDynamicFieldValue(container, "Teléfono");
        String profesion = getDynamicFieldValue(container, "Profesión y especialización");
        String colegiatura = getDynamicFieldValue(container, "Número de colegiatura/profesional (si aplica)");
        String experienciaCertificados = getDynamicFieldValue(container, "Experiencia o certificados relevantes");
        String disponibilidad = getDynamicFieldValue(container, "Disponibilidad para colaborar");
        String motivacion = getDynamicFieldValue(container, "Motivación para colaborar (opcional)");
        String password = getDynamicFieldValue(container, "Contraseña");

        if (nombre.isEmpty() || correo.isEmpty() || profesion.isEmpty() || disponibilidad.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos requeridos para el profesional", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean userCreated = dbHelper.addUsuario(
                "Profesional",
                nombre,
                "",
                correo,
                telefono,
                password,
                null
        );

        if (userCreated) {
            int newUserId = dbHelper.getLastInsertedUserId();

            return dbHelper.addProfesional(
                    newUserId,
                    profesion,
                    colegiatura,
                    experienciaCertificados,
                    disponibilidad,
                    motivacion
            );
        } else {
            Toast.makeText(this, "Error al registrar el profesional.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateDonorFields() {
        String nombre = getDynamicFieldValue(dynamicFormContainer, "Nombre completo o seudónimo");
        String correo = getDynamicFieldValue(dynamicFormContainer, "Correo electrónico de contacto");
        String cantidad = getDynamicFieldValue(dynamicFormContainer, "Cantidad a donar");
        String metodoPago = getDynamicFieldValue(dynamicFormContainer, "Método de pago");
        String numerotarjeta = getDynamicFieldValue(dynamicFormContainer, "Numero Tarjeta");
        String CVV = getDynamicFieldValue(dynamicFormContainer, "CVV");
        String fechadecaducidad = getDynamicFieldValue(dynamicFormContainer, "Fecha de caducidad de la tarjeta").trim();
        String direccion = getDynamicFieldValue(dynamicFormContainer, "Dirección postal (opcional)");
        String motivacion = getDynamicFieldValue(dynamicFormContainer, "Motivación para donar (opcional)");

        // Validar nombre
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            Toast.makeText(this, "El nombre solo debe contener letras y espacios", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar correo
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar cantidad
        if (!cantidad.matches("^\\d+(\\.\\d{1,2})?$")) {
            Toast.makeText(this, "La cantidad debe ser un número válido con hasta dos decimales", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar método de pago
        if (!metodoPago.equalsIgnoreCase("Bizum") &&
                !metodoPago.equalsIgnoreCase("Visa") &&
                !metodoPago.equalsIgnoreCase("Mastercard")) {
            Toast.makeText(this, "El método de pago debe ser Bizum, Visa o Mastercard", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar número de tarjeta
        if (!numerotarjeta.matches("^\\d{12}$")) {
            Toast.makeText(this, "El número de tarjeta debe tener exactamente 12 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar CVV
        if (!CVV.matches("^\\d{3}$")) {
            Toast.makeText(this, "El CVV debe tener exactamente 3 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar formato y vigencia de la fecha de caducidad
        if (!isValidExpiryDate(fechadecaducidad)) {
            return false;
        }

        // Validar dirección opcional
        if (!direccion.isEmpty() && !direccion.matches("^Dirección:.*")) {
            Toast.makeText(this, "La dirección debe comenzar con 'Dirección:' seguido de la información postal", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar motivación opcional
        if (!motivacion.isEmpty() && motivacion.length() > 200) {
            Toast.makeText(this, "La motivación para donar no debe exceder 200 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidExpiryDate(String fechadecaducidad) {
        // Limpia la entrada para eliminar espacios en blanco
        fechadecaducidad = fechadecaducidad.trim();

        // Log para verificar qué está recibiendo el método
        Log.d("isValidExpiryDate", "Fecha ingresada: " + fechadecaducidad);

        // Validar que el formato sea MM/yyyy
        // Ahora el año debe tener 4 dígitos
        if (!fechadecaducidad.matches("^(0[1-9]|1[0-2])/(\\d{4})$")) {
            Toast.makeText(this, "La fecha debe estar en el formato MM/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            // Configurar el formato MM/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            sdf.setLenient(false); // Desactivar análisis flexible
            Date expiryDate = sdf.parse(fechadecaducidad);

            // Establecer la fecha al último día del mes
            Calendar cal = Calendar.getInstance();
            cal.setTime(expiryDate);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Último día del mes

            // Verificar si la fecha ya ha pasado
            if (cal.getTime().before(new Date())) {
                Toast.makeText(this, "La tarjeta ya ha caducado", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (java.text.ParseException e) {
            // Manejo de errores durante el análisis de la fecha
            Log.e("isValidExpiryDate", "Error al analizar la fecha: " + fechadecaducidad, e);
            Toast.makeText(this, "Error al analizar la fecha de caducidad", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }




    private boolean handleDonor(LinearLayout container, int userId) {
        // Obtención de valores de los campos dinámicos
        String nombre = getDynamicFieldValue(container, "Nombre completo o seudónimo");
        String correo = getDynamicFieldValue(container, "Correo electrónico de contacto");
        String cantidad = getDynamicFieldValue(container, "Cantidad a donar");
        String metodoPago = getDynamicFieldValue(container, "Método de pago");
        String numerotarjeta = getDynamicFieldValue(container, "Numero Tarjeta");
        String CVV = getDynamicFieldValue(container, "CVV");
        String fechadecaducidad = getDynamicFieldValue(container, "Fecha de caducidad de la tarjeta").trim();
        String direccionPostal = getDynamicFieldValue(container, "Dirección postal (opcional)");
        String motivacion = getDynamicFieldValue(container, "Motivación para donar (opcional)");
        String password = getDynamicFieldValue(container, "Contraseña");

        // Validación de los campos requeridos
        if (!validateDonorFields()) {
            return false; // Detener si cualquier validación falla
        }

        // Registro del usuario en la base de datos
        boolean userCreated = dbHelper.addUsuario(
                "Donante",
                nombre,
                "",
                correo,
                "",
                password,
                null
        );

        if (userCreated) {
            int newUserId = dbHelper.getLastInsertedUserId();

            // Registro del donante en la base de datos
            return dbHelper.addDonante(
                    newUserId,
                    nombre,
                    CVV,
                    correo,
                    numerotarjeta,
                    metodoPago,
                    direccionPostal.isEmpty() ? null : direccionPostal,
                    motivacion.isEmpty() ? null : motivacion
            );
        } else {
            Toast.makeText(this, "Error al registrar el donante", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}


