-- Reasignar todos los clients con 'Propietario' a 'Dueño'
UPDATE client
SET person_type_id = (SELECT id FROM person_type WHERE type = 'Dueño')
WHERE person_type_id = (SELECT id FROM person_type WHERE type = 'Propietario');

-- Eliminar 'Propietario'
DELETE FROM person_type WHERE type = 'Propietario';

-- Renombrar 'Dueño' a minúscula
UPDATE person_type SET type = 'dueño' WHERE type = 'Dueño';

-- Insertar nuevo tipo 'responsable'
INSERT INTO person_type (type) VALUES ('responsable');
