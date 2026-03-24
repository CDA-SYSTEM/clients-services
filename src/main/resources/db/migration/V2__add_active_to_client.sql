-- V2__add_active_to_client.sql
ALTER TABLE client ADD COLUMN active BOOLEAN DEFAULT TRUE;