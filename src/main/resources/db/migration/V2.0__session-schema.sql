CREATE SCHEMA IF NOT EXISTS session;

CREATE TABLE IF NOT EXISTS session.session
(
    session_id      uuid NOT NULL,
    browser_id      uuid NOT NULL,
    user_id         uuid,
    expiration      timestamp,
    dark_mode       varchar(32),
    created         timestamp default now(),
    modified        timestamp default now(),
    changed         int default 1
);
ALTER TABLE session.session OWNER TO postgres;
ALTER TABLE ONLY session.session ADD CONSTRAINT session_pkey PRIMARY KEY (session_id);
CREATE TRIGGER update_session_session BEFORE UPDATE ON session.session FOR EACH ROW EXECUTE PROCEDURE modify_stats();

CREATE TABLE IF NOT EXISTS session.user
(
    user_id         uuid default uuid_generate_v4(),
    email           varchar(255),
    context         varchar(255),
    created         timestamp default now(),
    modified        timestamp default now(),
    changed         int default 1
);
ALTER TABLE session.user OWNER TO postgres;
ALTER TABLE ONLY session.user ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
CREATE TRIGGER update_session_user BEFORE UPDATE ON session.user FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

