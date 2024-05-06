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
import java.sql.SQLException;
import mediateca.desafio2.poo.Libro;

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

        try {
            Libro libro = new Libro();
            libro.setTitulo(txtTitulo.getText());
            libro.setUnidadesDisponibles(Integer.parseInt(txtUnidadesDisponible.getText()));
            libro.setAutor(txtAutor.getText());
            libro.setNumeroPaginas(Integer.parseInt(txtPaginas.getText()));
            libro.setEditorial(txtEditorial.getText());
            libro.setIsbn(txtISBN.getText());
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            
            if(ManejarLibros.agregarLibto(libro)){
                JOptionPane.showMessageDialog(this, "Libro agregado correctamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el libro");
            }          
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
