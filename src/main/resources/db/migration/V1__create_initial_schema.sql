-- Esquema alineado con entidades JPA (nombres snake_case por convención Spring)

CREATE TABLE person_type (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE document_type (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE client (
    id BIGSERIAL PRIMARY KEY,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    birth_date DATE,
    identity VARCHAR(20) NOT NULL,
    direccion VARCHAR(100),
    celular VARCHAR(15),
    email VARCHAR(100),
    document_type_id BIGINT REFERENCES document_type (id),
    person_type_id BIGINT REFERENCES person_type (id)
);
