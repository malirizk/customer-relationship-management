--liquibase formatted sql
--changeset Mohamed:03-create-call_logs runOnChange:true

DROP TABLE IF EXISTS call_log;

CREATE TABLE call_log
(
  call_log_id        UUID PRIMARY KEY,
  customer_id        UUID,
  call_start         TIMESTAMP,
  ring_start         TIMESTAMP,
  answer_start       TIMESTAMP,
  call_end           TIMESTAMP,
  duration           REAL,
  caller_number      VARCHAR,
  destination_number VARCHAR,
  result             VARCHAR(255),
  create_time        TIMESTAMP,
  update_time        TIMESTAMP
  --FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

GRANT ALL PRIVILEGES ON call_logs_schema.call_log TO cl_admin;
