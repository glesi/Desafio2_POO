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
public class ListaLibrosDisponibles extends JFrame {
    private JTable tablaLibros;

    public ListaLibrosDisponibles() {
        super("Lista de Libros Disponibles");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tablaLibros = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        add(scrollPane, BorderLayout.CENTER);

        cargarLibrosDisponibles();
    }

    private void cargarLibrosDisponibles() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Páginas");
        model.addColumn("Editorial");
        model.addColumn("ISBN");
        model.addColumn("Año");

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT m.codigo_identificacion, m.titulo, l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion " +
                         "FROM materiales m " +
                         "JOIN libros l ON m.id_materiales = l.id_materiales " +
                         "WHERE m.estado = 'Disponible'";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Object[] fila = {
                    resultSet.getString("codigo_identificacion"),
                    resultSet.getString("titulo"),
                    resultSet.getString("autor"),
                    resultSet.getInt("numero_paginas"),
                    resultSet.getString("editorial"),
                    resultSet.getString("isbn"),
                    resultSet.getInt("anio_publicacion")
                };
                model.addRow(fila);
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los libros: " + ex.getMessage());
        }

        tablaLibros.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListaLibrosDisponibles ventana = new ListaLibrosDisponibles();
            ventana.setVisible(true);
        });
    }
}
