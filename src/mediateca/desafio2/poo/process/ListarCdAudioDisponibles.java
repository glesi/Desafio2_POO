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
public class ListarCdAudioDisponibles extends JFrame {
    private JTable tablaCdsAudio;

    public ListarCdAudioDisponibles() {
        super("Lista de CDs de Audio Disponibles");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tablaCdsAudio = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaCdsAudio);
        add(scrollPane, BorderLayout.CENTER);

        cargarCdsAudioDisponibles();
    }

    private void cargarCdsAudioDisponibles() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Título");
        model.addColumn("Artista");
        model.addColumn("Género");
        model.addColumn("Duración");
        model.addColumn("Número de Canciones");

        try {
            ConexionDb objConexion = new ConexionDb();
            try (Connection conexion = objConexion.obtenerConexion()) {
                String sql = "SELECT m.codigo_identificacion, m.titulo, ca.artista, ca.genero, ca.duracion, ca.numero_canciones " +
                        "FROM materiales m " +
                        "JOIN cds_audio ca ON m.id_materiales = ca.id_materiales";
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    Object[] fila = {
                        resultSet.getString("codigo_identificacion"),
                        resultSet.getString("titulo"),
                        resultSet.getString("artista"),
                        resultSet.getString("genero"),
                        resultSet.getString("duracion"),
                        resultSet.getInt("numero_canciones")
                    };
                    model.addRow(fila);
                }
                
                resultSet.close();
                statement.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los CDs de audio: " + ex.getMessage());
        }

        tablaCdsAudio.setModel(model);
    }
    
}
