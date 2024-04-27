/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo;

/**
 *
 * @author glesi
 */
public class Libro extends MaterialEscrito {
    
    private int idLibros;
    private String autor;
    private int numeroPaginas;
    private String editorial;
    private String isbn;
    private int anioPublicacion;

    // Constructores, getters y setters
    public Libro(int idLibros, String autor, int numeroPaginas, String editorial, String isbn, int anioPublicacion) {
        this.idLibros = idLibros;
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
        this.editorial = editorial;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
    }
    
    public int getIdLibros() {
        return idLibros;
    }

    public void setIdLibros(int idLibros) {
        this.idLibros = idLibros;
    }
    
    

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    @Override
    public void mostrarDetalles() {
        super.mostrarDetalles();
        System.out.println("ID del libro: " + idLibros);
        System.out.println("Autor: " + autor);
        System.out.println("Número de páginas: " + numeroPaginas);
        System.out.println("Editorial: " + editorial);
        System.out.println("ISBN: " + isbn);
        System.out.println("Año de publicación: " + anioPublicacion);
    }
}