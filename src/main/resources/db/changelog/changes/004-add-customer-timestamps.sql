--liquibase formatted sql
--changeset foodreservation:add-customer-timestamps
ALTER TABLE customer ADD COLUMN created_at TIMESTAMP;
ALTER TABLE customer ADD COLUMN updated_at TIMESTAMP;