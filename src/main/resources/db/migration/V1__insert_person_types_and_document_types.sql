-- Flyway migration: Insert person types and document types

INSERT INTO person_type (type) VALUES ('Propietario');
INSERT INTO person_type (type) VALUES ('Dueño');

INSERT INTO document_type (type) VALUES ('CC');
INSERT INTO document_type (type) VALUES ('TI');
INSERT INTO document_type (type) VALUES ('CE');
INSERT INTO document_type (type) VALUES ('RC');
INSERT INTO document_type (type) VALUES ('PASAPORTE');
INSERT INTO document_type (type) VALUES ('NIT');
INSERT INTO document_type (type) VALUES ('NUIP');
