CREATE SCHEMA IF NOT EXISTS access;

CREATE
EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;

CREATE TABLE IF NOT EXISTS access.log
(
    log_id uuid NOT NULL,
    browser_id uuid NOT NULL,
    session_id uuid NOT NULL,
    browser_info varchar(320),
    user_id uuid,
    token varchar(10240),
    create_ts timestamptz,
    last_ts timestamptz,
    reload_times int
);
ALTER TABLE access.log OWNER TO postgres;

ALTER TABLE ONLY access.log ADD CONSTRAINT log_pkey PRIMARY KEY (log_id);
