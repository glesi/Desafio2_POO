/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateca.desafio2.poo.process;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import mediateca.desafio2.poo.db.ConexionDb;
import mediateca.desafio2.poo.util.Util;

/**
 *
 * @author glesi
 */
public class AgregarCdAudioForm extends JFrame {
    private JTextField txtTitulo, txtArtista, txtGenero, txtDuracion, txtNumeroCanciones, txtUnidadesDisponible;
    private JComboBox<String> cboEstado;
    private JButton btnAgregar, btnCancelar;

    public AgregarCdAudioForm() {
        super("Agregar CD de Audio");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(7, 2, 10, 10));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Artista:"));
        txtArtista = new JTextField();
        panelFormulario.add(txtArtista);

        panelFormulario.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Duración (HH:MM:SS):"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);
        
        panelFormulario.add(new JLabel("Número de Canciones:"));
        txtNumeroCanciones = new JTextField();
        panelFormulario.add(txtNumeroCanciones);
        
        panelFormulario.add(new JLabel("Unidades Disponibles:"));
        txtUnidadesDisponible = new JTextField();
        panelFormulario.add(txtUnidadesDisponible);
        
        panelFormulario.add(new JLabel("Estado:"));
        cboEstado = new JComboBox<>(new String[]{"Disponible", "No Disponible"});
        panelFormulario.add(cboEstado);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCdAudio();
            }
        });
        panelBotones.add(btnAgregar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCancelar);
        
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarCdAudio() {
        String titulo = txtTitulo.getText();
        String artista = txtArtista.getText();
        String genero = txtGenero.getText();
        String duracion = txtDuracion.getText();
        int numeroCanciones = Integer.parseInt(txtNumeroCanciones.getText());

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "INSERT INTO materiales (codigo_identificacion, titulo, tipo_material, estado, unidades_disponibles) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, Util.generarCodigoLibro("CDA"));
            statement.setString(2, titulo);
            statement.setString(3, "Audiovisual");
            statement.setString(4, cboEstado.getSelectedItem().toString());
            statement.setString(5, txtUnidadesDisponible.getText());

            // Ejecutar la consulta para insertar el material
            statement.executeUpdate();

            // Obtener el ID del material insertado
            int idMaterial = -1;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                idMaterial = rs.getInt(1);
            } else {
                // Manejar el caso en que no se obtiene el ID
                throw new SQLException("No se pudo obtener el ID del material insertado");
            }

            // Preparar la consulta SQL para insertar un CD de audio
            sql = "INSERT INTO cds_audio (id_materiales, artista, genero, duracion, numero_canciones) VALUES (?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idMaterial);
            statement.setString(2, artista);
            statement.setString(3, genero);
            statement.setString(4, duracion);
            statement.setInt(5, numeroCanciones);

            // Ejecutar la consulta para insertar el CD de audio
            statement.executeUpdate();

            // Cerrar la conexión y liberar recursos
            statement.close();
            conexion.close();

            JOptionPane.showMessageDialog(this, "CD de Audio agregado correctamente.");
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el CD de Audio: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtArtista.setText("");
        txtGenero.setText("");
        txtDuracion.setText("");
        txtNumeroCanciones.setText("");
        txtUnidadesDisponible.setText("");
    }
}