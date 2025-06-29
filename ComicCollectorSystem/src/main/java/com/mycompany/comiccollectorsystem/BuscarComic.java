package com.mycompany.comiccollectorsystem;

import java.util.List;

public class BuscarComic {

    public static void buscarPorTitulo(List<Comic> comics, String consulta) {
        boolean encontrado = false;
        System.out.println("Resultados de búsqueda por título para: \"" + consulta + "\"");
        for (Comic comic : comics) {
            if (comic.getTitulo().toLowerCase().contains(consulta.toLowerCase())) {
                System.out.println(comic.getTitulo() + " - " + comic.getAutor()
                        + (comic.isReservado() ? " (Reservado)" : " (Disponible)"));
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron cómics que coincidan con la búsqueda.");
        }
    }

    public static void buscarPorTituloOAutor(List<Comic> comics, String consulta) {
        boolean encontrado = false;
        String consultaLower = consulta.toLowerCase();
        System.out.println("Resultados de búsqueda para: \"" + consulta + "\"");
        for (Comic comic : comics) {
            if (comic.getTitulo().toLowerCase().contains(consultaLower)
                    || comic.getAutor().toLowerCase().contains(consultaLower)) {
                System.out.println(comic.getTitulo() + " - " + comic.getAutor()
                        + (comic.isReservado() ? " (Reservado)" : " (Disponible)"));
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron cómics que coincidan con la búsqueda.");
        }
    }
}