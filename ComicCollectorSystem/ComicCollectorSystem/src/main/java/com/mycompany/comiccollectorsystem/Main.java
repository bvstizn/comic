package com.mycompany.comiccollectorsystem;

import com.mycompany.comiccollectorsystem.exceptions.ComicNoEncontradoException;
import com.mycompany.comiccollectorsystem.exceptions.ComicYaReservadoException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ComicCollectorSystem sistema = new ComicCollectorSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> listarComics();
                case 2 -> buscarComic();
                case 3 -> agregarComic();
                case 4 -> reservarComic();
                case 5 -> liberarComic();
                case 6 -> guardarInformes();
                case 7 -> cargarComicsUsuarios();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n---- Menú de Comic Collector ----");
        System.out.println("1. Listar cómics");
        System.out.println("2. Buscar cómic por título");
        System.out.println("3. Agregar cómic");
        System.out.println("4. Reservar cómic");
        System.out.println("5. Liberar cómic");
        System.out.println("6. Guardar informes");
        System.out.println("7. Cargar cómics y usuarios desde CSV");
        System.out.println("0. Salir");
    }

    private static void listarComics() {
        if (sistema.getComics().isEmpty()) {
            System.out.println("No hay cómics en el sistema.");
        } else {
            System.out.println("\nListado de cómics:");
            for (Comic c : sistema.getComics()) {
                System.out.println(c.infoParaLista());
            }
        }
    }

    private static void buscarComic() {
        System.out.print("Ingrese el título a buscar: ");
        String titulo = scanner.nextLine().trim();
        try {
            Comic comic = sistema.buscarComic(titulo);
            System.out.println("Encontrado: " + comic.infoParaLista());
        } catch (ComicNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void agregarComic() {
        String titulo = leerTextoNoVacio("Título del cómic: ");
        int anio = leerAnio();
        String autor = leerTextoNoVacio("Autor: ");
        Comic comic = new Comic(titulo, anio, autor);
        sistema.agregarComic(comic);
        System.out.println("Cómic agregado correctamente.");
    }

    private static void reservarComic() {
        String titulo = leerTextoNoVacio("Título a reservar: ");
        try {
            sistema.reservarComic(titulo);
            System.out.println("Cómic reservado.");
        } catch (ComicNoEncontradoException | ComicYaReservadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void liberarComic() {
        String titulo = leerTextoNoVacio("Título a liberar: ");
        try {
            sistema.liberarComic(titulo);
            System.out.println("Cómic liberado.");
        } catch (ComicNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void guardarInformes() {
        try {
            sistema.guardarInformeComics("informe_comics.txt");
            sistema.guardarInformeUsuarios("informe_usuarios.txt");
            System.out.println("Informes guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar informes: " + e.getMessage());
        }
    }

    private static void cargarComicsUsuarios() {
        System.out.print("Ruta del archivo de cómics (ej: comics.csv): ");
        String rutaComics = scanner.nextLine().trim();
        System.out.print("Ruta del archivo de usuarios (ej: usuarios.csv): ");
        String rutaUsuarios = scanner.nextLine().trim();
        try {
            sistema.cargarComicsDesdeCSV(rutaComics);
            sistema.cargarUsuariosDesdeCSV(rutaUsuarios);
            System.out.println("Datos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar archivos: " + e.getMessage());
        }
    }

    // Métodos utilitarios de validación
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static int leerAnio() {
        int anio;
        do {
            anio = leerEntero("Año del cómic: ");
            if (anio < 1900 || anio > 2100) {
                System.out.println("Ingrese un año válido (entre 1900 y 2100).");
            }
        } while (anio < 1900 || anio > 2100);
        return anio;
    }

    private static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

}