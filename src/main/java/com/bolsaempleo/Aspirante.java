package main.java.com.bolsaempleo;

public class Aspirante {
    private String cedula;
    private String nombre;
    private int edad;
    private int experiencia;
    private String profesion;
    private String telefono;

    public Aspirante(String cedula, String nombre, int edad, int experiencia, String profesion, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.experiencia = experiencia;
        this.profesion = profesion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public String getProfesion() {
        return profesion;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Aspirante{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", experiencia=" + experiencia +
                ", profesion='" + profesion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
