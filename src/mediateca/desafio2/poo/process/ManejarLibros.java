/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mediateca.desafio2.poo.Libro;
import mediateca.desafio2.poo.db.ConexionDb;
import mediateca.desafio2.poo.util.Util;

/**
 *
 * @author glesi
 */
public class ManejarLibros {
    
    public static boolean agregarLibto(Libro libro) throws SQLException {
        
        try{
            ConexionDb objConexion = new ConexionDb();
            Connection conexion;
            conexion = objConexion.obtenerConexion();
            
            String codigoLibro = Util.generarCodigoLibro("LIB");
            
            if(codigoLibro == null) throw new SQLException("No fue posible generar el codigo unico de material.");
            
            System.out.println("Codigo generado: "+codigoLibro);
            
            String sql = "INSERT INTO materiales (codigo_identificacion, titulo, tipo_material, estado, unidades_disponibles) VALUES (?, ?, ?, 'Disponible', ?)";
            PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, codigoLibro);
            statement.setString(2, libro.getTitulo());
            statement.setString(3, "ESCRITO");
            statement.setInt(4, libro.getUnidadesDisponibles());
            
            statement.executeUpdate();

            int idMaterial;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                idMaterial = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del material insertado");
            }

            sql = "INSERT INTO libros (id_materiales, autor, numero_paginas, editorial, isbn, anio_publicacion) VALUES (?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idMaterial);
            statement.setString(2, libro.getAutor());
            statement.setInt(3, libro.getNumeroPaginas());
            statement.setString(4, libro.getEditorial());
            statement.setString(5, libro.getIsbn());
            statement.setInt(6, libro.getAnioPublicacion());
            
            statement.executeUpdate();

            statement.close();
            conexion.close();
            return true;
        } catch(SQLException e){
            System.out.println("Error agregando libro: " + e.getMessage());
            return false;
        }
    }
    
}
