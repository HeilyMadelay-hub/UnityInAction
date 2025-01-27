package org.meicode.MenuPrincipal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "voluntariado.db";
    private static final int DATABASE_VERSION = 10; // Incrementa la versión debido a cambios en la estructura

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tipo_usuario TEXT, " +
                "nombre TEXT, " +
                "apellido TEXT, " +
                "email TEXT UNIQUE, " +
                "telefono TEXT, " +
                "password TEXT, " +
                "foto_perfil TEXT)");

        db.execSQL("CREATE TABLE Organizacion (" +
                "id INTEGER PRIMARY KEY, " +
                "nombre_organizacion TEXT, " +
                "tipo TEXT, " +
                "nif_cif TEXT, " +
                "direccion TEXT, " +
                "telefono TEXT, " +
                "email_contacto TEXT, " +
                "FOREIGN KEY(id) REFERENCES Usuario(id))");

        db.execSQL("CREATE TABLE Noticia ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "contenido TEXT NOT NULL, " +
                "tags TEXT DEFAULT '', " +
                "archivo TEXT DEFAULT NULL, " +
                "requisitos TEXT DEFAULT '', " +
                "usuario_id INTEGER NOT NULL, " +
                "necesidades TEXT DEFAULT '', " + // Agregar la columna aquí
                "ubicacion TEXT DEFAULT '', " +
                "fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "es_publica INTEGER DEFAULT 1, " +
                "fuente TEXT DEFAULT NULL, " +
                "FOREIGN KEY(usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ");");


        db.execSQL("CREATE TABLE Donante (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "organizacion TEXT, " +
                "fecha TEXT, " +
                "monto REAL, " +
                "metodo_pago TEXT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT, " +
                "numero_tarjeta TEXT NOT NULL, " +
                "cvv TEXT NOT NULL, " +
                "direccion TEXT, " +
                "motivacion TEXT, " +
                "FOREIGN KEY(usuario_id) REFERENCES Usuario(id))");


        db.execSQL("CREATE TABLE Donacion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER, " +
                "organizacion TEXT, " +
                "fecha TEXT, " +
                "monto REAL, " +
                "metodo_pago TEXT, " +
                "FOREIGN KEY(usuario_id) REFERENCES Usuario(id))");



        db.execSQL("CREATE TABLE Voluntario (" +
                "id INTEGER PRIMARY KEY, " +
                "experiencia TEXT, " +
                "disponibilidad TEXT, " +
                "motivacion TEXT, " +
                "contacto_emergencia TEXT, " +
                "FOREIGN KEY(id) REFERENCES Usuario(id))");

        db.execSQL("CREATE TABLE Profesional (" +
                "id INTEGER PRIMARY KEY, " +
                "profesion TEXT, " +
                "numero_colegiatura TEXT, " +
                "experiencia_certificados TEXT, " +
                "disponibilidad TEXT, " +
                "motivacion TEXT, " +
                "FOREIGN KEY(id) REFERENCES Usuario(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        db.execSQL("DROP TABLE IF EXISTS Organizacion");
        db.execSQL("DROP TABLE IF EXISTS Noticia");
        db.execSQL("DROP TABLE IF EXISTS Donante");
        db.execSQL("DROP TABLE IF EXISTS Voluntario");
        db.execSQL("DROP TABLE IF EXISTS Profesional");
        onCreate(db);
    }

    // --- Métodos CRUD ---

    public boolean addUsuario(String tipoUsuario, String nombre, String apellido, String email, String telefono, String password, String fotoPerfil) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tipo_usuario", tipoUsuario);
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("email", email.trim().toLowerCase());
        values.put("telefono", telefono);
        values.put("password", password.trim());
        values.put("foto_perfil", fotoPerfil);

        long result = db.insert("Usuario", null, values);
        db.close();
        return result != -1;
    }

    public boolean addOrganizacion(int userId, String nombre, String tipo, String nifCif, String direccion, String telefono, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", userId);
        values.put("nombre_organizacion", nombre);
        values.put("tipo", tipo);
        values.put("nif_cif", nifCif);
        values.put("direccion", direccion);
        values.put("telefono", telefono);
        values.put("email_contacto", email.trim().toLowerCase());

        long result = db.insert("Organizacion", null, values);
        db.close();
        return result != -1;
    }

    public boolean addVoluntario(int userId, String experiencia, String disponibilidad, String motivacion, String contactoEmergencia) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", userId);
        values.put("experiencia", experiencia);
        values.put("disponibilidad", disponibilidad);
        values.put("motivacion", motivacion);
        values.put("contacto_emergencia", contactoEmergencia);

        long result = db.insert("Voluntario", null, values);
        db.close();
        return result != -1;
    }

    public boolean addProfesional(int userId, String profesion, String numeroColegiatura, String experienciaCertificados,
                                  String disponibilidad, String motivacion) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", userId);
        values.put("profesion", profesion);
        values.put("numero_colegiatura", numeroColegiatura);
        values.put("experiencia_certificados", experienciaCertificados);
        values.put("disponibilidad", disponibilidad);
        values.put("motivacion", motivacion);

        long result = db.insert("Profesional", null, values);
        db.close();
        return result != -1;
    }

    public boolean addDonante(int userId, String nombre, String cvv,String correo, String numerotarjeta, String metodoPago, String direccion, String motivacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("usuario_id", userId); // Antes: user_id
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("cvv", cvv); // asegúrate de que esta variable no sea nula
        values.put("numero_tarjeta", numerotarjeta); // Antes: numerotarjeta
        values.put("metodo_pago", metodoPago); // Antes: metodoPago
        values.put("direccion", direccion); // Debe existir en la tabla
        values.put("motivacion", motivacion); // Debe existir en la tabla

        long result = db.insert("Donante", null, values);
        return result != -1;
    }



    // Método para obtener datos del "Reportero Ciudadano" por ID
    public Cursor getReporteroCiudadanoById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Usuario WHERE id = ? AND tipo_usuario = ?";
        String[] queryArgs = new String[]{String.valueOf(userId), "Reportero Ciudadano"};

        return db.rawQuery(query, queryArgs);
    }

    // Método para obtener datos del "Voluntario" por ID
    public Cursor getVoluntarioById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Usuario WHERE id = ? AND tipo_usuario = ?";
        String[] queryArgs = new String[]{String.valueOf(userId), "Voluntario"};

        return db.rawQuery(query, queryArgs);
    }

    // Método para obtener datos del "Profesional" por ID
    public Cursor getProfesionalById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Usuario WHERE id = ? AND tipo_usuario = ?";
        String[] queryArgs = new String[]{String.valueOf(userId), "Profesional"};

        return db.rawQuery(query, queryArgs);
    }



    public boolean validarDonante(String nombre, String numeroTarjeta, String cvv) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM Donante WHERE nombre = ? AND numero_tarjeta = ? AND cvv = ?",
                new String[]{nombre, numeroTarjeta, cvv}
        );

        boolean existe = cursor.moveToFirst(); // Devuelve true si hay una fila que coincide
        cursor.close();
        db.close();
        return existe;
    }


    public Cursor getUsuarioPorTipoEmailYPassword(String tipoUsuario, String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM Usuario WHERE tipo_usuario = ? AND email = ? AND password = ?",
                new String[]{tipoUsuario, email, password}
        );
    }

    public int getLastInsertedUserId() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int lastId = -1;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT last_insert_rowid() AS id", null);
            if (cursor != null && cursor.moveToFirst()) {
                lastId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error al obtener el último ID insertado: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return lastId;
    }

    public Cursor getTodasLasNoticias() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Noticia ORDER BY fecha_hora DESC", null);
    }


    public Cursor getNoticiasPublicas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Noticia WHERE es_publica = 1", null);
    }

    public boolean deleteNoticia(int noticiaId, int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(
                "Noticia",
                "id = ? AND usuario_id = ?",
                new String[]{String.valueOf(noticiaId), String.valueOf(usuarioId)}
        );
        db.close();
        return rowsAffected > 0;
    }

    public Cursor getNoticiasByUsuario(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Noticia WHERE usuario_id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(usuarioId)});
    }

    public Cursor getUsuarioById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM Usuario WHERE id = ?",
                new String[]{String.valueOf(id)}
        );
    }


    public Cursor getOrganizacionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM Organizacion WHERE id = ?",
                new String[]{String.valueOf(id)}
        );
    }


    public Cursor getNoticiasById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT titulo, contenido, tags, ubicacion, necesidades FROM Noticia WHERE id = ?", new String[]{String.valueOf(id)});
    }


    public Cursor getOrganizacionByNombre(String nombreOrganizacion) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                "Organizacion",
                null,
                "nombre_organizacion = ?",
                new String[]{nombreOrganizacion.trim()},
                null, null, null
        );
    }

    public Cursor getDonanteByTarjetaPasswordYCvv(String numeroTarjeta, String nombre, String cvv) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                "Donante",
                new String[]{"id", "nombre", "correo"},
                "numero_tarjeta = ? AND nombre = ? AND cvv = ?",
                new String[]{numeroTarjeta, nombre, cvv},
                null, null, null
        );
    }

    // Método genérico para obtener un usuario por ID y tipo
    public Cursor getUsuarioPorIdYTipo(int userId, String tipoUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Usuario WHERE id = ? AND tipo_usuario = ?";
        String[] queryArgs = new String[]{String.valueOf(userId), tipoUsuario};

        return db.rawQuery(query, queryArgs);
    }

    public boolean addDonacion(int donanteId, String organizacion, String fecha, double monto, String metodoPago) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("usuario_id", donanteId);
        values.put("organizacion", organizacion);
        values.put("fecha", fecha);
        values.put("monto", monto);
        values.put("metodo_pago", metodoPago);
        long result = db.insert("Donacion", null, values);
        db.close();
        return result != -1;
    }

    public boolean isEmailRegistered(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean isRegistered = false;

        try {
            db = this.getReadableDatabase();
            // Consulta para verificar si el correo existe
            cursor = db.query(
                    "Usuario",                   // Tabla
                    new String[]{"id"},          // Columnas a consultar
                    "email = ?",                 // Condición WHERE
                    new String[]{email.trim()},  // Argumentos del WHERE
                    null,                        // Group By
                    null,                        // Having
                    null                         // Order By
            );

            // Verificamos si la consulta tiene resultados
            isRegistered = (cursor != null && cursor.getCount() > 0);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error verificando el correo: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return isRegistered;
    }

    public boolean addNoticia(String titulo, String contenido, String requisitos, int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("contenido", contenido);
        values.put("requisitos", requisitos); // Agregar el nuevo campo
        values.put("usuario_id", usuarioId);

        long result = db.insert("Noticia", null, values);
        db.close();

        return result != -1;
    }


    public boolean updateNoticia(int id, String titulo, String contenido, String tags, String ubicacion, String necesidades, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo", titulo);
        values.put("contenido", contenido);
        values.put("tags", tags);
        values.put("ubicacion", ubicacion);
        values.put("necesidades", necesidades); // Actualizar la columna necesidades

        if (imagePath != null) {
            values.put("archivo", imagePath); // Asegúrate de usar el nombre correcto de la columna para la imagen
        }

        int rowsAffected = db.update("Noticia", values, "id=?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }
}


