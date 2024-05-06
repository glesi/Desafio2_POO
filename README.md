# Base de datos para nuestro proyecto de mediateca:
# Crear base de datos
CREATE DATABASE mediateca_db;

# crear tabla para materiales
CREATE TABLE materiales (
    id_materiales INT AUTO_INCREMENT PRIMARY KEY,
    codigo_identificacion VARCHAR(10) NOT NULL UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    tipo_material ENUM('Escrito', 'Audiovisual') NOT NULL,
    estado ENUM('Disponible', 'No Disponible') NOT NULL DEFAULT 'Disponible',
    unidades_disponibles INT NOT NULL
);

# crear tabla libros
CREATE TABLE libros (
    id_libros INT AUTO_INCREMENT PRIMARY KEY,
	id_materiales INT,
    autor VARCHAR(255) NOT NULL,
    numero_paginas INT NOT NULL,
    editorial VARCHAR(255) NOT NULL,
    isbn VARCHAR(17),
    anio_publicacion YEAR NOT NULL,
    FOREIGN KEY (id_materiales) REFERENCES materiales(id_materiales)
);

# crear tabla revista
CREATE TABLE revistas (
    id_revistas INT AUTO_INCREMENT PRIMARY KEY,
	id_materiales INT,
	editorial VARCHAR(200),
    periodicidad VARCHAR(255) NOT NULL,
    fecha_publicacion DATE NOT NULL,
    FOREIGN KEY (id_materiales) REFERENCES materiales(id_materiales)
);

# crear tabla cds
CREATE TABLE cds_audio (
    id_cds_audio INT AUTO_INCREMENT PRIMARY KEY,
	id_materiales INT,
    artista VARCHAR(255) NOT NULL,
    genero VARCHAR(255) NOT NULL,
    duracion TIME NOT NULL,
    numero_canciones INT NOT NULL,
    FOREIGN KEY (id_materiales) REFERENCES materiales(id_materiales)
);

# crear tabla dvds
CREATE TABLE dvds (
    id_dvds INT AUTO_INCREMENT PRIMARY KEY,
	id_materiales INT,
    director VARCHAR(255) NOT NULL,
    duracion TIME NOT NULL,
    genero VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_materiales) REFERENCES materiales(id_materiales)
);