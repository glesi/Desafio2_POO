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
public class ModificarDvdForm extends JFrame {
    private JComboBox<String> cboDvds;
    private JTextField txtTitulo, txtDirector, txtDuracion, txtGenero, txtUnidadesDisponible;
    private JButton btnBuscar, btnModificar, btnCancelar;

    public ModificarDvdForm() {
        super("Modificar DVD");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSeleccion = new JPanel(new BorderLayout());
        panelSeleccion.setBorder(new EmptyBorder(10, 10, 10, 10));

        cboDvds = new JComboBox<>();
        panelSeleccion.add(cboDvds, BorderLayout.CENTER);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarDvd();
            }
        });
        panelSeleccion.add(btnBuscar, BorderLayout.EAST);

        add(panelSeleccion, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Director:"));
        txtDirector = new JTextField();
        panelFormulario.add(txtDirector);

        panelFormulario.add(new JLabel("Duración:"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Unidades Disponibles:"));
        txtUnidadesDisponible = new JTextField();
        panelFormulario.add(txtUnidadesDisponible);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDvd();
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

        cargarDvdsDisponibles();
    }

    private void buscarDvd() {
        if(cboDvds.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Debe seleccionar un DVD primero para buscar.");
            return;
        }
        String dvdSeleccionado = cboDvds.getSelectedItem().toString();
        String[] partes = dvdSeleccionado.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de DVD seleccionado incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT * FROM materiales m JOIN dvds d ON m.id_materiales = d.id_materiales WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, codigoIdentificacion);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                txtTitulo.setText(resultSet.getString("titulo"));
                txtDirector.setText(resultSet.getString("director"));
                txtDuracion.setText(resultSet.getString("duracion"));
                txtGenero.setText(resultSet.getString("genero"));
                txtUnidadesDisponible.setText(String.valueOf(resultSet.getInt("unidades_disponibles")));
            } else {
                JOptionPane.showMessageDialog(this, "DVD no encontrado.");
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el DVD: " + ex.getMessage());
        }
    }

    private void modificarDvd() {
        String dvdSeleccionado = cboDvds.getSelectedItem().toString();
        String[] partes = dvdSeleccionado.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de DVD seleccionado incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        String titulo = txtTitulo.getText();
        String director = txtDirector.getText();
        String duracion = txtDuracion.getText();
        String genero = txtGenero.getText();
        int unidadesDisponibles = Integer.parseInt(txtUnidadesDisponible.getText());

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "UPDATE materiales m JOIN dvds d ON m.id_materiales = d.id_materiales SET titulo = ?, director = ?, duracion = ?, genero = ?, unidades_disponibles = ? WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, titulo);
            statement.setString(2, director);
            statement.setString(3, duracion);
            statement.setString(4, genero);
            statement.setInt(5, unidadesDisponibles);
            statement.setString(6, codigoIdentificacion);

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "DVD modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el DVD.");
            }

            statement.close();
            conexion.close();

            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar el DVD: " + ex.getMessage());
        }
    }

    private void cargarDvdsDisponibles() {
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT codigo_identificacion, CONCAT_WS(' - ', codigo_identificacion, titulo) AS dvd FROM materiales WHERE estado = 'Disponible' AND codigo_identificacion like 'DVD%'";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cboDvds.addItem(resultSet.getString("dvd"));
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los DVDs disponibles: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDirector.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
        txtUnidadesDisponible.setText("");
    }
}
