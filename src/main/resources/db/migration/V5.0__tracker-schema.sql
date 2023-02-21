CREATE SCHEMA IF NOT EXISTS tracker;

CREATE TABLE IF NOT EXISTS tracker.group
(
    group_id        uuid NOT NULL,
    user_id         uuid NOT NULL,
    name            varchar(256) NOT NULL,
    created         timestamp default now(),
    modified        timestamp default now(),
    changed         int default 1
);
ALTER TABLE tracker.group OWNER TO postgres;
ALTER TABLE ONLY tracker.group ADD CONSTRAINT tracker_group_id_pkey PRIMARY KEY (group_id);
CREATE TRIGGER update_tracker_group BEFORE UPDATE ON tracker.group FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE IF NOT EXISTS tracker.activity
(
    activity_id     uuid NOT NULL,
    group_id        uuid NOT NULL,
    platform        varchar(256) NOT NULL,
    activity        varchar(256) NOT NULL,
    progress        varchar(256) NOT NULL,
    created         timestamp default now(),
    modified        timestamp default now(),
    changed         int default 1
);
ALTER TABLE tracker.activity OWNER TO postgres;
ALTER TABLE ONLY tracker.activity ADD CONSTRAINT tracker_activity_id_pkey PRIMARY KEY (activity_id);
CREATE TRIGGER update_tracker_activity BEFORE UPDATE ON tracker.activity FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

ALTER TABLE ONLY tracker.group ADD CONSTRAINT tracker_group_user_id_fk FOREIGN KEY (user_id) REFERENCES session.user(user_id);
ALTER TABLE ONLY tracker.activity ADD CONSTRAINT tracker_activity_group_id_fk FOREIGN KEY (group_id) REFERENCES tracker.group(group_id)
