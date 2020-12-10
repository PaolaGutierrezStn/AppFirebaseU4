package mx.edu.utng.appfirebaseu4.model;

public class Libro {

    private String uid;
    private String Nombre;
    private String Autor;
    private String Editorial;

    public Libro() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String editorial) {
        Editorial = editorial;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}

