/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mediateca.desafio2.poo.db.ConexionDb;

/**
 *
 * @author glesi
 */
public class Util {
    
    public static String generarCodigoLibro(String tipoMaterial) {
        String codigo = ""; // Inicializamos el código

        try {
            // Realizar la conexión a la base de datos
            ConexionDb conexionDb = new ConexionDb();
            Connection conexion = conexionDb.obtenerConexion();

            // Consulta para obtener el ID más alto de la tabla de materiales
            String consultaIdMaximo = "SELECT MAX(id_materiales) AS max_id FROM materiales";
            try (Statement statementIdMaximo = conexion.createStatement(); ResultSet resultSetIdMaximo = statementIdMaximo.executeQuery(consultaIdMaximo)) {
                
                // Verificar si se encontró el ID máximo
                if (resultSetIdMaximo.next()) {
                    int maxId = resultSetIdMaximo.getInt("max_id");
                    // Generar el código de identificación interna sumando 1 al ID máximo y concatenando el prefijo
                    codigo = tipoMaterial + String.format("%05d", maxId + 1); // Formato para asegurar que tenga 5 dígitos
                }
                // Cerrar recursos

            }
            conexionDb.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error al generar el código de libro: " + e.getMessage());
        }

        return codigo;
    }

    
}
