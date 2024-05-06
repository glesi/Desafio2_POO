/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo.process;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mediateca.desafio2.poo.db.ConexionDb;

/**
 *
 * @author glesi
 */
public class ListarDvdDisponibles extends JFrame {
    private JTable tablaDvds;

    public ListarDvdDisponibles() {
        super("Lista de DVD Disponibles");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tablaDvds = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaDvds);
        add(scrollPane, BorderLayout.CENTER);

        cargarDvdsDisponibles();
    }

    private void cargarDvdsDisponibles() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Título");
        model.addColumn("Director");
        model.addColumn("Duración");
        model.addColumn("Género");

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT m.codigo_identificacion, m.titulo, d.director, d.duracion, d.genero " +
                    "FROM materiales m " +
                    "JOIN dvds d ON m.id_materiales = d.id_materiales " +
                    "WHERE m.estado = 'Disponible'";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] fila = {
                    resultSet.getString("codigo_identificacion"),
                    resultSet.getString("titulo"),
                    resultSet.getString("director"),
                    resultSet.getString("duracion"),
                    resultSet.getString("genero")
                };
                model.addRow(fila);
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los DVD: " + ex.getMessage());
        }

        tablaDvds.setModel(model);
    }    
}
