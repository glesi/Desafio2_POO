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
public class CdAudio extends MaterialAudiovisual {
    private int idCdsAudio;
    private String artista;
    private int duracion;
    private int numeroCanciones;

    // Constructores, getters y setters
    public CdAudio(int idCdsAudio, String artista, int duracion, int numeroCanciones, String genero, int idMateriales, String codigoIdentificacion, String titulo, TipoMaterial tipoMaterial, Estado estado) {
        super(genero, idMateriales, codigoIdentificacion, titulo, tipoMaterial, estado);
        this.idCdsAudio = idCdsAudio;
        this.artista = artista;
        this.duracion = duracion;
        this.numeroCanciones = numeroCanciones;
    }

    public int getIdCdsAudio() {
        return idCdsAudio;
    }

    public void setIdCdsAudio(int idCdsAudio) {
        this.idCdsAudio = idCdsAudio;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }
    
    @Override
    public void mostrarDetalles() {
        super.mostrarDetalles();
        System.out.println("ID del CD de audio: " + idCdsAudio);
        System.out.println("Artista: " + artista);
        System.out.println("Duración: " + duracion);
        System.out.println("Número de canciones: " + numeroCanciones);
    }
}

