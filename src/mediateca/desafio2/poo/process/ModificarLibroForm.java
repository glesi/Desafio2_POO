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
public class ModificarLibroForm extends JFrame {
    private JComboBox<String> cboLibros;
    private JTextField txtTitulo, txtAutor, txtPaginas, txtEditorial, txtISBN, txtAnio, txtUnidadesDisponible;
    private JButton btnBuscar, btnModificar, btnCancelar;

    public ModificarLibroForm() {
        super("Modificar Libro");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSeleccion = new JPanel(new BorderLayout());
        panelSeleccion.setBorder(new EmptyBorder(10, 10, 10, 10));

        cboLibros = new JComboBox<>();
        panelSeleccion.add(cboLibros, BorderLayout.CENTER);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLibro();
            }
        });
        panelSeleccion.add(btnBuscar, BorderLayout.EAST);

        add(panelSeleccion, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Número de Páginas:"));
        txtPaginas = new JTextField();
        panelFormulario.add(txtPaginas);

        panelFormulario.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("ISBN:"));
        txtISBN = new JTextField();
        panelFormulario.add(txtISBN);

        panelFormulario.add(new JLabel("Año de Publicación:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarLibro();
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
        
        cargarLibrosDisponibles();
    }

    private void buscarLibro() {
        if(cboLibros.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Debe seleccionar un libro primero para dar a Buscar!");
            return;
        }
        String libroSeleccionado = cboLibros.getSelectedItem().toString();
        String[] partes = libroSeleccionado.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de libro seleccionado incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT * FROM materiales m JOIN libros l ON m.id_materiales = l.id_materiales WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, codigoIdentificacion);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                txtTitulo.setText(resultSet.getString("titulo"));
                txtAutor.setText(resultSet.getString("autor"));
                txtPaginas.setText(String.valueOf(resultSet.getInt("numero_paginas")));
                txtEditorial.setText(resultSet.getString("editorial"));
                txtISBN.setText(resultSet.getString("isbn"));
                txtAnio.setText(String.valueOf(resultSet.getInt("anio_publicacion")));
            } else {
                JOptionPane.showMessageDialog(this, "Libro no encontrado.");
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el libro: " + ex.getMessage());
        }
    }

    private void modificarLibro() {
        String libroSeleccionado = cboLibros.getSelectedItem().toString();
        String[] partes = libroSeleccionado.split(" - ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de libro seleccionado incorrecto.");
            return;
        }
        String codigoIdentificacion = partes[0];
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int paginas = Integer.parseInt(txtPaginas.getText());
        String editorial = txtEditorial.getText();
        String isbn = txtISBN.getText();
        int anio = Integer.parseInt(txtAnio.getText());
        int unidadesDisponibles = Integer.parseInt(txtUnidadesDisponible.getText());

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "UPDATE materiales m JOIN libros l ON m.id_materiales = l.id_materiales SET titulo = ?, autor = ?, numero_paginas = ?, editorial = ?, isbn = ?, anio_publicacion = ?, m.unidades_disponibles = ? WHERE m.codigo_identificacion = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, titulo);
            statement.setString(2, autor);
            statement.setInt(3, paginas);
            statement.setString(4, editorial);
            statement.setString(5, isbn);
            statement.setInt(6, anio);
            statement.setInt(7, unidadesDisponibles);
            statement.setString(8, codigoIdentificacion);

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Libro modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el libro.");
            }

            statement.close();
            conexion.close();
            
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar el libro: " + ex.getMessage());
        }
    }
    
    private void cargarLibrosDisponibles() {
        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion = objConexion.obtenerConexion();

            String sql = "SELECT codigo_identificacion, CONCAT_WS(' - ', codigo_identificacion, titulo) AS libro FROM materiales WHERE estado = 'Disponible' AND codigo_identificacion like 'LIB%'";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cboLibros.addItem(resultSet.getString("libro"));
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los libros disponibles: " + ex.getMessage());
        }
    }
    
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtPaginas.setText("");
        txtEditorial.setText("");
        txtISBN.setText("");
        txtAnio.setText("");
        txtUnidadesDisponible.setText("");
    }
   
}
