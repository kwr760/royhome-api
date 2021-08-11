CREATE SCHEMA IF NOT EXISTS resume;

CREATE
EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;

CREATE OR REPLACE FUNCTION modify_stats_when_changed()
RETURNS TRIGGER AS $$
BEGIN
  IF row(NEW.*) IS DISTINCT FROM row(OLD.*) THEN
    NEW.changed = OLD.changed + 1;
    NEW.modified = now();
RETURN NEW;
ELSE
    RETURN OLD;
END IF;
END;
$$ language 'plpgsql';

CREATE OR REPLACE FUNCTION modify_stats()
RETURNS TRIGGER AS $$
BEGIN
  NEW.changed = OLD.changed + 1;
  NEW.modified = now();
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TABLE IF NOT EXISTS resume.education
(
    education_id uuid NOT NULL,
    degree       varchar(255),
    graduation   varchar(255),
    school       varchar(255),
    position     int,
    resume_id    uuid,
    created      timestamp default now(),
    modified     timestamp default now(),
    changed      int default 1
);
ALTER TABLE resume.education OWNER TO postgres;
CREATE TRIGGER update_resume_education BEFORE UPDATE ON resume.education FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE resume.experience
(
    experience_id uuid NOT NULL,
    company       varchar(255),
    end_date      date,
    start_date    date,
    title         varchar(255),
    position      int,
    resume_id     uuid,
    created       timestamp default now(),
    modified      timestamp default now(),
    changed       int default 1
);
ALTER TABLE resume.experience OWNER TO postgres;
CREATE TRIGGER update_resume_experience BEFORE UPDATE ON resume.experience FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE resume.experience_bullet
(
    bullet_id     uuid NOT NULL,
    name          varchar(1024),
    position      int,
    experience_id uuid,
    created       timestamp default now(),
    modified      timestamp default now(),
    changed       int default 1
);
ALTER TABLE resume.experience_bullet OWNER TO postgres;
CREATE TRIGGER update_resume_experience_bullet BEFORE UPDATE ON resume.experience_bullet FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE resume.experience_description
(
    description_id uuid NOT NULL,
    name           varchar(1024),
    position       int,
    experience_id  uuid,
    created        timestamp default now(),
    modified       timestamp default now(),
    changed        int default 1
);
ALTER TABLE resume.experience_description OWNER TO postgres;
CREATE TRIGGER update_resume_description BEFORE UPDATE ON resume.experience_description FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE resume.resume
(
    resume_id     uuid    NOT NULL,
    address       varchar(255),
    display_phone boolean NOT NULL,
    email         varchar(255),
    name          varchar(255),
    phone         varchar(255),
    summary       varchar(1024),
    created       timestamp default now(),
    modified      timestamp default now(),
    changed       int default 1
);
ALTER TABLE resume.resume OWNER TO postgres;
CREATE TRIGGER update_resume_resume BEFORE UPDATE ON resume.resume FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE resume.skill
(
    skill_id uuid NOT NULL,
    name     varchar(255),
    position int,
    group_id uuid,
    created  timestamp default now(),
    modified timestamp default now(),
    changed  int default 1
);
ALTER TABLE resume.skill OWNER TO postgres;
CREATE TRIGGER update_resume_skill BEFORE UPDATE ON resume.skill FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

CREATE TABLE resume.skill_group
(
    skill_group_id uuid NOT NULL,
    name           varchar(255),
    position       int,
    experience_id  uuid,
    resume_id      uuid,
    created        timestamp default now(),
    modified       timestamp default now(),
    changed        int default 1
);
ALTER TABLE resume.skill_group OWNER TO postgres;
CREATE TRIGGER update_resume_skill_group BEFORE UPDATE ON resume.skill_group FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();

ALTER TABLE ONLY resume.education ADD CONSTRAINT education_pkey PRIMARY KEY (education_id);
ALTER TABLE ONLY resume.experience_bullet ADD CONSTRAINT experience_bullet_pkey PRIMARY KEY (bullet_id);
ALTER TABLE ONLY resume.experience_description ADD CONSTRAINT experience_description_pkey PRIMARY KEY (description_id);
ALTER TABLE ONLY resume.experience ADD CONSTRAINT experience_pkey PRIMARY KEY (experience_id);
ALTER TABLE ONLY resume.resume ADD CONSTRAINT resume_pkey PRIMARY KEY (resume_id);
ALTER TABLE ONLY resume.skill_group ADD CONSTRAINT skill_group_pkey PRIMARY KEY (skill_group_id);
ALTER TABLE ONLY resume.skill ADD CONSTRAINT skill_pkey PRIMARY KEY (skill_id);
ALTER TABLE ONLY resume.skill ADD CONSTRAINT skill_skill_group_id_fk FOREIGN KEY (group_id) REFERENCES resume.skill_group(skill_group_id);
ALTER TABLE ONLY resume.experience_bullet ADD CONSTRAINT experience_bullet_experience_id_fk FOREIGN KEY (experience_id) REFERENCES resume.experience(experience_id);
ALTER TABLE ONLY resume.education ADD CONSTRAINT education_resume_id_fk FOREIGN KEY (resume_id) REFERENCES resume.resume(resume_id);
ALTER TABLE ONLY resume.experience ADD CONSTRAINT experience_resume_id_fk FOREIGN KEY (resume_id) REFERENCES resume.resume(resume_id);
ALTER TABLE ONLY resume.experience_description ADD CONSTRAINT experience_description_experience_id_fk FOREIGN KEY (experience_id) REFERENCES resume.experience(experience_id);
ALTER TABLE ONLY resume.skill_group ADD CONSTRAINT skill_group_resume_id_fk FOREIGN KEY (resume_id) REFERENCES resume.resume(resume_id);
ALTER TABLE ONLY resume.skill_group ADD CONSTRAINT skill_group_experience_id_fk FOREIGN KEY (experience_id) REFERENCES resume.experience(experience_id);
