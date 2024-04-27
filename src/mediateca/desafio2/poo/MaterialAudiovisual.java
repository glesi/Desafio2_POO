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
public class MaterialAudiovisual extends Material {
    private String genero;

    // Constructores, getters y setters
    public MaterialAudiovisual(String genero, int idMateriales, String codigoIdentificacion, String titulo, TipoMaterial tipoMaterial, Estado estado) {
        super(idMateriales, codigoIdentificacion, titulo, tipoMaterial, estado);
        this.genero = genero;
    }
    

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public void mostrarDetalles() {
        super.mostrarDetalles();
        System.out.println("Género: " + genero);
    }

}
