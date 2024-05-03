package com.example.collection.Modelo;

public class Discos1 {
    private String Anio;
    private String FechaAdquisicion;
    private String Formato;

    public Discos1() {
        // Constructor vacío requerido por Firebase
    }

    public Discos1(String anio, String fechaAdquisicion, String formato) {
        this.Anio = anio;
        this.FechaAdquisicion = fechaAdquisicion;
        this.Formato = formato;
    }

    public String getAnio() {
        return Anio;
    }

    public void setAnio(String anio) {
        this.Anio = anio;
    }

    public String getFechaAdquisicion() {
        return FechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.FechaAdquisicion = fechaAdquisicion;
    }

    public String getFormato() {
        return Formato;
    }

    public void setFormato(String formato) {
        this.Formato = formato;
    }

    @Override
    public String toString() {
        return "Discos1{" +
                "anio='" + Anio + '\'' +
                ", fechaAdquisicion='" + FechaAdquisicion + '\'' +
                ", formato='" + Formato + '\'' +
                '}';
    }
}
