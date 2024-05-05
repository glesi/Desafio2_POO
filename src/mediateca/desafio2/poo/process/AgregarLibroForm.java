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
import java.sql.Statement;
import mediateca.desafio2.poo.db.ConexionDb;
import mediateca.desafio2.poo.util.Util;

/**
 *
 * @author glesi
 */
public class AgregarLibroForm extends JFrame {
    private JTextField txtTitulo, txtAutor, txtPaginas, txtEditorial, txtISBN, txtAnio, txtUnidadesDisponible;
    private JButton btnAgregar, btnCancelar;

    public AgregarLibroForm() {
        super("Agregar Libro");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(7, 2, 10, 10));

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

        panelFormulario.add(new JLabel("Unidades Disponibles:"));
        txtUnidadesDisponible = new JTextField();
        panelFormulario.add(txtUnidadesDisponible);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarLibro();
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

    private void agregarLibro() {
        String titulo = txtTitulo.getText();
        int unidades = Integer.parseInt(txtUnidadesDisponible.getText());
        String autor = txtAutor.getText();
        int paginas = Integer.parseInt(txtPaginas.getText());
        String editorial = txtEditorial.getText();
        String isbn = txtISBN.getText();
        int anio = Integer.parseInt(txtAnio.getText());

        try {
            ConexionDb objConexion = new ConexionDb();
            Connection conexion;
            conexion = objConexion.obtenerConexion();
            
            String codigoLibro = Util.generarCodigoLibro("LIB");
            
            if(codigoLibro == null) throw new SQLException("No fue posible generar el codigo unico de material.");
            
            System.out.println("Codigo generado: "+codigoLibro);
            
            String sql = "INSERT INTO materiales (codigo_identificacion, titulo, tipo_material, estado, unidades_disponibles) VALUES (?, ?, ?, 'Disponible', ?)";
            PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, codigoLibro);
            statement.setString(2, titulo);
            statement.setString(3, "ESCRITO");
            statement.setInt(4, unidades);
            
            statement.executeUpdate();

            int idMaterial;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                idMaterial = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del material insertado");
            }

            sql = "INSERT INTO libros (id_materiales, autor, numero_paginas, editorial, isbn, anio_publicacion) VALUES (?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idMaterial);
            statement.setString(2, autor);
            statement.setInt(3, paginas);
            statement.setString(4, editorial);
            statement.setString(5, isbn);
            statement.setInt(6, anio);
            
            statement.executeUpdate();

            statement.close();
            conexion.close();
            
            JOptionPane.showMessageDialog(this, "Libro agregado correctamente.");
            limpiarCampos();
        } catch (SQLException ex) {
            System.out.println("Error al agregar el libro: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error al agregar el libro: " + ex.getMessage());
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
