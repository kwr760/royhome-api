CREATE SCHEMA IF NOT EXISTS access;

CREATE TABLE IF NOT EXISTS access.access_log
(
    access_id uuid NOT NULL,
    browser_id uuid NOT NULL,
    session_id uuid NOT NULL,
    browser_info varchar(1024),
    user_id uuid,
    token varchar(10240),
    create_ts timestamp,
    last_ts timestamp,
    reload_times int
);
ALTER TABLE access.access_log OWNER TO postgres;

ALTER TABLE ONLY access.access_log ADD CONSTRAINT log_pkey PRIMARY KEY (access_id);
