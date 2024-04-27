/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo;

import mediateca.desafio2.poo.enums.Estado;

/**
 *
 * @author glesi
 */
public class MaterialEscrito extends Material {
    
    private Estado estado;
    private int unidadesDisponibles;

    // Constructores, getters y setters

    @Override
    public void mostrarDetalles() {
        super.mostrarDetalles();
        System.out.println("Estado: " + estado);
        System.out.println("Unidades disponibles: " + unidadesDisponibles);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }
    
    
}


