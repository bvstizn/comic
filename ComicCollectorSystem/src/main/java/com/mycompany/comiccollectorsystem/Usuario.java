package com.mycompany.comiccollectorsystem;

import java.util.Objects;

public class Usuario implements Comparable<Usuario> {
    private String rut;
    private String nombre;

    public Usuario(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
    }

    public String getRut() { return rut; }
    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return Objects.equals(this.rut, otro.rut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }

    @Override
    public int compareTo(Usuario otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "rut='" + rut + '\'' +
               ", nombre='" + nombre + '\'' +
               '}';
    }
}