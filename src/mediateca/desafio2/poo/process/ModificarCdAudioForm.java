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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;
import mediateca.desafio2.poo.db.ConexionDb;

/**
 *
 * @author glesi
 */
public class ModificarCdAudioForm extends JFrame {
    private JComboBox<String> cboCds;
    private JTextField txtTitulo, txtArtista, txtGenero, txtDuracion, txtNumeroCanciones, txtUnidadesDisponible;
    private JButton btnBuscar, btnModificar, btnCancelar;

    public ModificarCdAudioForm() {
        super("Modificar CD de Audio");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSeleccion = new JPanel(new BorderLayout());
        panelSeleccion.setBorder(new EmptyBorder(10, 10, 10, 10));

        cboCds = new JComboBox<>();
        panelSeleccion.add(cboCds, BorderLayout.CENTER);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCd();
            }
        });
        panelSeleccion.add(btnBuscar, BorderLayout.EAST);

        add(panelSeleccion, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Artista:"));
        txtArtista = new JTextField();
        panelFormulario.add(txtArtista);

        panelFormulario.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Duración:"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Número de Canciones:"));
        txtNumeroCanciones = new JTextField();
        panelFormulario.add(txtNumeroCanciones);

        panelFormulario.add(new JLabel("Unidades Disponibles:"));
        txtUnidadesDisponible = new JTextField();
        panelFormulario.add(txtUnidadesDisponible);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarCd();
            }
        });
        panelBotones.add(btnModificar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarCdsDisponibles();
    }

    private void buscarCd() {
        if(cboCds.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Debe seleccionar un CD de audio primero para buscar.");
            return;
        }
        String cdSeleccionado = cboCds.getSelectedItem().toString();
        String[] partes = cdSeleccionado.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de CD de audio seleccionado incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT * FROM materiales m JOIN cds_audio c ON m.id_materiales = c.id_materiales WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, codigoIdentificacion);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                txtTitulo.setText(resultSet.getString("titulo"));
                txtArtista.setText(resultSet.getString("artista"));
                txtGenero.setText(resultSet.getString("genero"));
                txtDuracion.setText(resultSet.getString("duracion"));
                txtNumeroCanciones.setText(String.valueOf(resultSet.getInt("numero_canciones")));
                txtUnidadesDisponible.setText(String.valueOf(resultSet.getInt("unidades_disponibles")));
            } else {
                JOptionPane.showMessageDialog(this, "CD de audio no encontrado.");
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el CD de audio: " + ex.getMessage());
        }
    }

    private void modificarCd() {
        String cdSeleccionado = cboCds.getSelectedItem().toString();
        String[] partes = cdSeleccionado.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de CD de audio seleccionado incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        String titulo = txtTitulo.getText();
        String artista = txtArtista.getText();
        String genero = txtGenero.getText();
        String duracion = txtDuracion.getText();
        int numeroCanciones = Integer.parseInt(txtNumeroCanciones.getText());
        int unidadesDisponibles = Integer.parseInt(txtUnidadesDisponible.getText());

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "UPDATE materiales m JOIN cds_audio c ON m.id_materiales = c.id_materiales SET titulo = ?, artista = ?, genero = ?, duracion = ?, numero_canciones = ?, unidades_disponibles = ? WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, titulo);
            statement.setString(2, artista);
            statement.setString(3, genero);
            statement.setString(4, duracion);
            statement.setInt(5, numeroCanciones);
            statement.setInt(6, unidadesDisponibles);
            statement.setString(7, codigoIdentificacion);

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "CD de audio modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el CD de audio.");
            }

            statement.close();
            conexion.close();

            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar el CD de audio: " + ex.getMessage());
        }
    }

    private void cargarCdsDisponibles() {
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT codigo_identificacion, CONCAT_WS(' - ', codigo_identificacion, titulo) AS cd FROM materiales WHERE estado = 'Disponible' AND codigo_identificacion like 'CD%'";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cboCds.addItem(resultSet.getString("cd"));
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los CDs de audio disponibles: " + ex.getMessage());
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
