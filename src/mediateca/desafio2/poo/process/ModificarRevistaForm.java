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
public class ModificarRevistaForm extends JFrame {
    private JComboBox<String> cboRevistas;
    private JTextField txtTitulo, txtEditorial, txtIPeriodicidad, txtFechaPublicacion, txtUnidadesDisponible;
    private JButton btnBuscar, btnModificar, btnCancelar;

    public ModificarRevistaForm() {
        super("Modificar Revista");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSeleccion = new JPanel(new BorderLayout());
        panelSeleccion.setBorder(new EmptyBorder(10, 10, 10, 10));

        cboRevistas = new JComboBox<>();
        panelSeleccion.add(cboRevistas, BorderLayout.CENTER);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRevista();
            }
        });
        panelSeleccion.add(btnBuscar, BorderLayout.EAST);

        add(panelSeleccion, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("Periodicidad:"));
        txtIPeriodicidad = new JTextField();
        panelFormulario.add(txtIPeriodicidad);

        panelFormulario.add(new JLabel("Fecha de Publicación:"));
        txtFechaPublicacion = new JTextField();
        panelFormulario.add(txtFechaPublicacion);

        panelFormulario.add(new JLabel("Unidades Disponibles:"));
        txtUnidadesDisponible = new JTextField();
        panelFormulario.add(txtUnidadesDisponible);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarRevista();
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

        cargarRevistasDisponibles();
    }

    private void buscarRevista() {
        if(cboRevistas.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Debe seleccionar una revista primero para buscar.");
            return;
        }
        String revistaSeleccionada = cboRevistas.getSelectedItem().toString();
        String[] partes = revistaSeleccionada.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de revista seleccionada incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT * FROM materiales m JOIN revistas r ON m.id_materiales = r.id_materiales WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, codigoIdentificacion);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                txtTitulo.setText(resultSet.getString("titulo"));
                txtEditorial.setText(resultSet.getString("editorial"));
                txtIPeriodicidad.setText(resultSet.getString("periodicidad"));
                txtFechaPublicacion.setText(resultSet.getString("fecha_publicacion"));
                txtUnidadesDisponible.setText(String.valueOf(resultSet.getInt("unidades_disponibles")));
            } else {
                JOptionPane.showMessageDialog(this, "Revista no encontrada.");
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar la revista: " + ex.getMessage());
        }
    }

    private void modificarRevista() {
        String revistaSeleccionada = cboRevistas.getSelectedItem().toString();
        String[] partes = revistaSeleccionada.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de revista seleccionada incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        String titulo = txtTitulo.getText();
        String editorial = txtEditorial.getText();
        String periodicidad = txtIPeriodicidad.getText();
        String fechaPublicacion = txtFechaPublicacion.getText();
        int unidadesDisponibles = Integer.parseInt(txtUnidadesDisponible.getText());

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "UPDATE materiales m JOIN revistas r ON m.id_materiales = r.id_materiales SET titulo = ?, editorial = ?, periodicidad = ?, fecha_publicacion = ?, unidades_disponibles = ? WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, titulo);
            statement.setString(2, editorial);
            statement.setString(3, periodicidad);
            statement.setString(4, fechaPublicacion);
            statement.setInt(5, unidadesDisponibles);
            statement.setString(6, codigoIdentificacion);

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Revista modificada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar la revista.");
            }

            statement.close();
            conexion.close();

            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar la revista: " + ex.getMessage());
        }
    }

    private void cargarRevistasDisponibles() {
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT codigo_identificacion, CONCAT_WS(' - ', codigo_identificacion, titulo) AS revista FROM materiales WHERE estado = 'Disponible' AND codigo_identificacion like 'REV%'";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cboRevistas.addItem(resultSet.getString("revista"));
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar las revistas disponibles: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtEditorial.setText("");
        txtIPeriodicidad.setText("");
        txtFechaPublicacion.setText("");
        txtUnidadesDisponible.setText("");
    }
}
