
CREATE DATABASE IF NOT EXISTS TiendaOnline;

USE TiendaOnline;

CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (nombre) VALUES
( 'administrador'),
('vendedor'),
('cliente');

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rol_id INT NOT NULL,
    apellido VARCHAR(2550) NULL,
    contrasena VARCHAR(255)  NULL,
    correo VARCHAR(250)  NULL UNIQUE,
    nombre VARCHAR(20)  NULL,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

INSERT INTO usuarios (rol_id, apellido, contrasena, correo, nombre) VALUES
(2, 'Gomez', '5678', 'gomez@example.com', 'Maria'),
(3, 'Lopez', 'abcd', 'lopez@example.com', 'Carlos');
