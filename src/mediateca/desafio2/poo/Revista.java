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
public class Revista extends MaterialEscrito {
    private int idRevistas;
    private String editorial;
    private String periodicidad;
    private String fechaPublicacion;

    // Constructores, getters y setters

    public Revista(int idRevistas, String editorial, String periodicidad, String fechaPublicacion) {
        this.idRevistas = idRevistas;
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }   

    @Override
    public void mostrarDetalles() {
        super.mostrarDetalles();
        System.out.println("ID de la revista: " + idRevistas);
        System.out.println("Editorial: " + editorial);
        System.out.println("Periodicidad: " + periodicidad);
        System.out.println("Fecha de publicación: " + fechaPublicacion);
    }

    public int getIdRevistas() {
        return idRevistas;
    }

    public void setIdRevistas(int idRevistas) {
        this.idRevistas = idRevistas;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String toString() {
        return "Revista{" + "idRevistas=" + idRevistas + ", editorial=" + editorial + ", periodicidad=" + periodicidad + ", fechaPublicacion=" + fechaPublicacion + '}';
    }

}

