package com.mycompany.comiccollectorsystem;

import com.mycompany.comiccollectorsystem.exceptions.ComicNoEncontradoException;
import com.mycompany.comiccollectorsystem.exceptions.ComicYaReservadoException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
                case 8 -> listarUsuarios();
                case 9 -> agregarUsuario();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n---- Menú de Comic Collector ----");
        System.out.println("1. Listar cómics");
        System.out.println("2. Buscar cómic por título o autor (coincidencias)");
        System.out.println("3. Agregar cómic");
        System.out.println("4. Reservar cómic");
        System.out.println("5. Liberar cómic");
        System.out.println("6. Guardar informes");
        System.out.println("7. Cargar cómics y usuarios desde CSV");
        System.out.println("8. Listar usuarios");
        System.out.println("9. Agregar usuario");
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
        System.out.print("Ingrese texto a buscar (en título o autor): ");
        String consulta = scanner.nextLine().trim();
        BuscarComic.buscarPorTituloOAutor(sistema.getComics(), consulta);
    }

    private static void agregarComic() {
        String titulo = leerTextoNoVacio("Título del cómic: ");
        int año = leerAnio();
        String autor = leerTextoNoVacio("Autor: ");
        Comic comic = new Comic(titulo, año, autor);
        sistema.agregarComic(comic);
        System.out.println("Cómic agregado correctamente.");
    }

    private static void reservarComic() {
        boolean hayDisponibles = false;
        System.out.println("Cómics disponibles para reservar:");
        for (Comic c : sistema.getComics()) {
            if (!c.isReservado()) {
                System.out.println(c.infoParaLista());
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay cómics disponibles para reservar.");
            return;
        }
        String titulo = leerTextoNoVacio("Título a reservar: ");
        try {
            sistema.reservarComic(titulo);
            System.out.println("Cómic reservado.");
        } catch (ComicNoEncontradoException | ComicYaReservadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void liberarComic() {
        boolean hayReservados = false;
        System.out.println("Cómics reservados (puede liberar):");
        for (Comic c : sistema.getComics()) {
            if (c.isReservado()) {
                System.out.println(c.infoParaLista());
                hayReservados = true;
            }
        }
        if (!hayReservados) {
            System.out.println("No hay cómics reservados para liberar.");
            return;
        }
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

    private static void listarUsuarios() {
        if (sistema.getUsuarios().isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("\nListado de usuarios:");
            for (Usuario u : sistema.getUsuarios().values()) {
                System.out.println(u.getRut() + " - " + u.getNombre());
            }
        }
    }

    // VALIDACIÓN RUT y NOMBRE
    private static void agregarUsuario() {
        String rut;
        String nombre;

        do {
            System.out.print("Rut del usuario: ");
            rut = scanner.nextLine().trim();
            if (!esRutValido(rut)) {
                System.out.println("Formato de RUT inválido. Debe ser de la forma 1.111.111-1 o 11.111.111-1 (8 o 9 dígitos). Intente nuevamente.");
            }
        } while (!esRutValido(rut));

        do {
            System.out.print("Nombre del usuario: ");
            nombre = scanner.nextLine().trim();
            if (!esNombreValido(nombre)) {
                System.out.println("Nombre inválido. Solo se permiten letras y espacios. Intente nuevamente.");
            }
        } while (!esNombreValido(nombre));

        Usuario usuario = new Usuario(rut, nombre);
        sistema.agregarUsuario(usuario);
        System.out.println("Usuario agregado correctamente.");
    }

    // Utilidades para validación de RUT y nombre
    private static boolean esRutValido(String rut) {
        // Permite formatos como 1.234.567-8 o 12.345.678-9 (8 o 9 números antes del guion)
        String regex = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$";
        String regexSinPuntos = "^\\d{7,8}-[\\dkK]$";
        return rut.matches(regex) || rut.matches(regexSinPuntos);
    }

    private static boolean esNombreValido(String nombre) {
        // Solo letras (incluye tildes) y espacios, al menos 2 caracteres
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,}$";
        return nombre.matches(regex);
    }

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
        int año;
        do {
            año = leerEntero("Año de publicación (1900-2100): ");
            if (año < 1900 || año > 2100) {
                System.out.println("Ingrese un año válido entre 1900 y 2100.");
            }
        } while (año < 1900 || año > 2100);
        return año;
    }

    private static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }
}