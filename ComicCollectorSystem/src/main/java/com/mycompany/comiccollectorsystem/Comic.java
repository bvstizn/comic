package com.mycompany.comiccollectorsystem;
import java.util.Objects;

public class Comic implements Comparable<Comic> {
    private String titulo;
    private int año;
    private String autor;
    private boolean reservado;

    public Comic(String titulo, int año, String autor) {
        this.titulo = titulo;
        this.año = año;
        this.autor = autor;
        this.reservado = false;
    }

    public String getTitulo() { return titulo; }
    public int getAño() { return año; }
    public String getAutor() { return autor; }
    public boolean isReservado() { return reservado; }

    public void reservar() { this.reservado = true; }
    public void liberar() { this.reservado = false; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Comic)) return false;
        Comic otro = (Comic) obj;
        return Objects.equals(this.titulo, otro.titulo) &&
               Objects.equals(this.autor, otro.autor) &&
               this.año == otro.año;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, año, autor);
    }

    @Override
    public int compareTo(Comic otro) {
        int cmp = this.titulo.compareToIgnoreCase(otro.titulo);
        if (cmp == 0) {
            cmp = Integer.compare(this.año, otro.año);
            if (cmp == 0) {
                cmp = this.autor.compareToIgnoreCase(otro.autor);
            }
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "Comic{" +
               "titulo='" + titulo + '\'' +
               ", anio=" + año +
               ", autor='" + autor + '\'' +
               ", reservado=" + reservado +
               '}';
    }

    public String infoParaLista() {
        return titulo + ", " + año + ", " + autor + ", " + (reservado ? "Reservado" : "Disponible");
    }
}