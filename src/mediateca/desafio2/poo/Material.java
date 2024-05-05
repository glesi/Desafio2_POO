/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo;

import mediateca.desafio2.poo.enums.Estado;
import mediateca.desafio2.poo.enums.TipoMaterial;
import mediateca.desafio2.poo.interfaces.IMaterial;

/**
 *
 * @author glesi
 */
public abstract class Material implements IMaterial {
    private int idMateriales;
    private String codigoIdentificacion;
    private String titulo;
    private TipoMaterial tipoMaterial;
    private Estado estado;

    // Constructores, getters y setters

    public Material() {} 

    public Material(int idMateriales, String codigoIdentificacion, String titulo, TipoMaterial tipoMaterial, Estado estado) {
        this.idMateriales = idMateriales;
        this.codigoIdentificacion = codigoIdentificacion;
        this.titulo = titulo;
        this.tipoMaterial = tipoMaterial;
        this.estado = estado;
    }
 
    public int getIdMateriales() {
        return idMateriales;
    }

    public void setIdMateriales(int idMateriales) {
        this.idMateriales = idMateriales;
    }

    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("ID: " + idMateriales);
        System.out.println("Código: " + codigoIdentificacion);
        System.out.println("Título: " + titulo);
        System.out.println("Tipo de material: " + tipoMaterial);
    }
    
    @Override
    public void agregarMaterial(){
    
    }
}

