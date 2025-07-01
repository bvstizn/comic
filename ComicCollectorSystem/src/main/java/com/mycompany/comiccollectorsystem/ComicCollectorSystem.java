package com.mycompany.comiccollectorsystem;

import com.mycompany.comiccollectorsystem.exceptions.ComicNoEncontradoException;
import com.mycompany.comiccollectorsystem.exceptions.ComicYaReservadoException;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ComicCollectorSystem {
    private ArrayList<Comic> comics;
    private HashMap<String, Usuario> usuarios;
    private HashSet<Comic> comicUnicos;
    private HashSet<Usuario> usuarioUnicos;
    private TreeSet<Comic> catalogoOrdenado;
    private TreeSet<Usuario> usuariosOrdenados;

    public ComicCollectorSystem() {
        comics = new ArrayList<>();
        usuarios = new HashMap<>();
        comicUnicos = new HashSet<>();
        usuarioUnicos = new HashSet<>();
        catalogoOrdenado = new TreeSet<>();
        usuariosOrdenados = new TreeSet<>();
    }

    //Obtiene los Comics
    public List<Comic> getComics() {
        return comics;
    }

    //Obtiene usuarios
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

 
    public void agregarComic(Comic comic) {
        comics.add(comic);
        comicUnicos.add(comic);
        catalogoOrdenado.add(comic);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getRut(), usuario);
        usuarioUnicos.add(usuario);
        usuariosOrdenados.add(usuario);
    }

    public Comic buscarComic(String titulo) throws ComicNoEncontradoException {
        for (Comic comic : comics) {
            if (comic.getTitulo().equalsIgnoreCase(titulo)) {
                return comic;
            }
        }
        throw new ComicNoEncontradoException("El cómic '" + titulo + "' no existe en la tienda.");
    }

    public void reservarComic(String titulo) throws ComicNoEncontradoException, ComicYaReservadoException {
        Comic comic = buscarComic(titulo);
        if (comic.isReservado()) {
            throw new ComicYaReservadoException("El cómic '" + titulo + "' ya está reservado.");
        }
        comic.reservar();
    }

    public void liberarComic(String titulo) throws ComicNoEncontradoException {
        Comic comic = buscarComic(titulo);
        comic.liberar();
    }

    public void cargarComicsDesdeCSV(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                //FORMATO : Titulo, Año, Autor
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String titulo = partes[0].trim();
                    String añoStr = partes[1].trim();
                    String autor = partes[2].trim();
                    try {
                        int año = Integer.parseInt(añoStr);
                        if (titulo.isEmpty() || autor.isEmpty() || año < 1900 || año > 2100) continue;
                        Comic comic = new Comic(titulo, año, autor);
                        agregarComic(comic);
                    } catch (NumberFormatException e) {
                        // Ignorar líneas con año inválido
                        continue;
                    }
                }
            }
        }
    }

    public void cargarUsuariosDesdeCSV(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    Usuario usuario = new Usuario(partes[0].trim(), partes[1].trim());
                    agregarUsuario(usuario);
                }
            }
        }
    }

    public void guardarInformeComics(String rutaArchivo) throws IOException {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            for (Comic comic : comics) {
                writer.write(comic.infoParaLista() + "\n");
            }
        }
    }

    public void guardarInformeUsuarios(String rutaArchivo) throws IOException {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            for (Usuario usuario : usuarios.values()) {
                writer.write(usuario.getRut() + ", " + usuario.getNombre() + "\n");
            }
        }
    }
}