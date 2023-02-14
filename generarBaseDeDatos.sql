CREATE DATABASE hotel_alura;
USE hotel_alura;

CREATE TABLE reservas(
	ID INT NOT NULL,
    FechaEntrada VARCHAR(50) NOT NULL,
    FechaSalida VARCHAR(50) NOT NULL,
    Valor DOUBLE NOT NULL,
    MetodoDePago VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);
CREATE TABLE huespedes(
	ID INT AUTO_INCREMENT,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    FechaNacimiento VARCHAR(45) NOT NULL,
    Nacionalidad VARCHAR(50) NOT NULL,
    Telefono VARCHAR(50) NOT NULL,
    IdReserva INT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (IdReserva) REFERENCES reservas(ID)
);