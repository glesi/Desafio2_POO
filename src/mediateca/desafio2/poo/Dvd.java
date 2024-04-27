/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo;

import mediateca.desafio2.poo.enums.Estado;
import mediateca.desafio2.poo.enums.TipoMaterial;

/**
 *
 * @author glesi
 */
public class Dvd extends MaterialAudiovisual {
    private int idDvds;
    private String director;
    private int duracion;

    // Constructores, getters y setters
    public Dvd(int idDvds, String director, int duracion, String genero, int idMateriales, String codigoIdentificacion, String titulo, TipoMaterial tipoMaterial, Estado estado) {
        super(genero, idMateriales, codigoIdentificacion, titulo, tipoMaterial, estado);
        this.idDvds = idDvds;
        this.director = director;
        this.duracion = duracion;
    }

    public int getIdDvds() {
        return idDvds;
    }

    public void setIdDvds(int idDvds) {
        this.idDvds = idDvds;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    
    @Override
    public void mostrarDetalles() {
        super.mostrarDetalles();
        System.out.println("ID del DVD: " + idDvds);
        System.out.println("Director: " + director);
        System.out.println("Duración: " + duracion);
    }
}