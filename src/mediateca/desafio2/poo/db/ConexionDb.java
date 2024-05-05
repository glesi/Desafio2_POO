/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author JC
 */
public class ConexionDb {
	private Connection conexion;
	private ResultSet rs;
	private Statement stm;

    public ConexionDb() throws SQLException {
    	try
    	{
            //cargar driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Se obtiene la conexión con la base de datos.
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mediateca_db", "root", "");
            
            // permite ejecutar consultas SQL sin parametros
            stm = conexion.createStatement();

            System.out.println("Conexión establecida con éxito.");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se pudo cargar el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR: Fallo en SQL: " + e.getMessage());
        }
    }

    //codigo para consultar datos
    

    //codigo para insertar datos
    

    //codigo para actualizar datos
    
    
    //cerrar rs
    public void closeResulset(){
    	try{
    		if (rs != null){
    			rs.close();
    		}
    	}catch (SQLException e) {
    	//Error SQL: login/pasword sentencia sql erronea
    		System.out.println("ERROR:Fallo en SQL: " + e.getMessage());
    	}
    }

    // cerrar stm
    public void closeStatement(){
    	try{
    		if (stm != null){
    			stm.close();
    		}
    	} catch (SQLException e) {
    	//Error SQL: login/pasword sentencia sql erronea
    		System.out.println("ERROR:Fallo en SQL: " + e.getMessage());
    	}
    }

    //cerrar conexion
    public void closeConnection() {
    	try {
    		if (conexion != null){
    			conexion.close();
    		}
    	} catch (SQLException e) {
    	//Error SQL: login/pasword sentencia sql erronea
    		System.out.println("ERROR:Fallo en SQL: " + e.getMessage());
    	}
    }

    // Otros métodos para ejecutar consultas.
}
