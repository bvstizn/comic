package com.mycompany.comiccollectorsystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ComicCollectorSystem sistema = new ComicCollectorSystem();

        // Ejemplo de carga desde CSV
        try {
            sistema.cargarComicsDesdeCSV("comics.csv");
            sistema.cargarUsuariosDesdeCSV("usuarios.csv");
        } catch (IOException e) {
            System.out.println("Error al cargar archivos: " + e.getMessage());
        }

        // Prueba de funcionalidades
        try {
            sistema.reservarComic("Spider-Man #1");
            sistema.liberarComic("Spider-Man #1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            sistema.guardarInformeComics("informe_comics.txt");
            sistema.guardarInformeUsuarios("informe_usuarios.txt");
        } catch (IOException e) {
            System.out.println("Error al guardar informes: " + e.getMessage());
        }
    }
}