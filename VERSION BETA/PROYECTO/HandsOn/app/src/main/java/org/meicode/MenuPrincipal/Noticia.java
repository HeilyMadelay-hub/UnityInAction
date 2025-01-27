package org.meicode.MenuPrincipal;

public class Noticia {
    private int id;
    private String titulo;
    private String contenido;
    private String requisitos; // Campo adicional
    private String tags;
    private String archivo;
    private String nombreAutor; // Campo adicional
    private String apellidoAutor; // Campo adicional
    private int usuarioId;

    // Constructor actualizado
    public Noticia(int id, String titulo, String contenido, String requisitos, String tags, String archivo,
                   String nombreAutor, String apellidoAutor, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.requisitos = requisitos;
        this.tags = tags;
        this.archivo = archivo;
        this.nombreAutor = nombreAutor;
        this.apellidoAutor = apellidoAutor;
        this.usuarioId = usuarioId;
    }

    // Constructor con campos esenciales
    public Noticia(int id, String titulo, String contenido, String tags, String archivo, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.requisitos = null; // Por defecto
        this.tags = tags;
        this.archivo = archivo;
        this.nombreAutor = null; // Por defecto
        this.apellidoAutor = null; // Por defecto
        this.usuarioId = usuarioId;
    }

    // Constructor para guardar con parámetros en null
    public Noticia(int usuarioId) {
        this.id = 0; // Por defecto
        this.titulo = null;
        this.contenido = null;
        this.requisitos = null;
        this.tags = null;
        this.archivo = null;
        this.nombreAutor = null;
        this.apellidoAutor = null;
        this.usuarioId = usuarioId;
    }


    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    // Métodos getter
    public String getRequisitos() { return requisitos; }
    public String getNombreAutor() { return nombreAutor; }
    public String getApellidoAutor() { return apellidoAutor; }



    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
