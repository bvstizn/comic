package com.mycompany.comiccollectorsystem.exceptions;

public class ComicNoEncontradoException extends Exception {
    public ComicNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}