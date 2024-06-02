--liquibase formatted sql
--changeset Mohamed:01-create-call_logs-schema

CREATE USER cl_admin WITH ENCRYPTED PASSWORD 'CL0_@dm!n';
-- GRANT ALL PRIVILEGES ON DATABASE call_log_database TO cl_admin;

CREATE SCHEMA IF NOT EXISTS call_logs_schema AUTHORIZATION cl_admin;
COMMIT;