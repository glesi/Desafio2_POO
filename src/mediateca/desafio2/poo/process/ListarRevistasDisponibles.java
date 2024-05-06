/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo.process;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import mediateca.desafio2.poo.db.ConexionDb;

/**
 *
 * @author glesi
 */
public class ListarRevistasDisponibles extends JFrame {
    private JTable tablaRevistas;

    public ListarRevistasDisponibles() {
        super("Lista de Revistas Disponibles");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tablaRevistas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaRevistas);
        add(scrollPane, BorderLayout.CENTER);

        cargarRevistasDisponibles();
    }

    private void cargarRevistasDisponibles() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Título");
        model.addColumn("Editorial");
        model.addColumn("Periodicidad");
        model.addColumn("Fecha de Publicación");

        try {
            ConexionDb objConexion = new ConexionDb();
            try (Connection conexion = objConexion.obtenerConexion()) {
                String sql = "SELECT m.codigo_identificacion, m.titulo, r.editorial, r.periodicidad, r.fecha_publicacion " +
                        "FROM materiales m " +
                        "JOIN revistas r ON m.id_materiales = r.id_materiales " +
                        "WHERE m.estado = 'Disponible'";
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    Object[] fila = {
                        resultSet.getString("codigo_identificacion"),
                        resultSet.getString("titulo"),
                        resultSet.getString("editorial"),
                        resultSet.getString("periodicidad"),
                        resultSet.getString("fecha_publicacion")
                    };
                    model.addRow(fila);
                }
                
                resultSet.close();
                statement.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar las revistas: " + ex.getMessage());
        }

        tablaRevistas.setModel(model);
    }
}