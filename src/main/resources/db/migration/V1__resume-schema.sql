CREATE
EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;

CREATE TABLE public.education
(
    education_id uuid NOT NULL,
    degree       character varying(255),
    graduation   character varying(255),
    school       character varying(255),
    position     integer,
    resume_id    uuid
);
ALTER TABLE public.education OWNER TO postgres;

CREATE TABLE public.experience
(
    experience_id uuid NOT NULL,
    company       character varying(255),
    end_date      character varying(255),
    start_date    character varying(255),
    title         character varying(255),
    position      integer,
    resume_id     uuid
);
ALTER TABLE public.experience OWNER TO postgres;

CREATE TABLE public.experience_bullet
(
    bullet_id     uuid NOT NULL,
    name          character varying(1024),
    position      integer,
    experience_id uuid
);
ALTER TABLE public.experience_bullet OWNER TO postgres;

CREATE TABLE public.experience_description
(
    description_id uuid NOT NULL,
    name           character varying(1024),
    position       integer,
    experience_id  uuid
);
ALTER TABLE public.experience_description OWNER TO postgres;

CREATE TABLE public.resume
(
    resume_id     uuid    NOT NULL,
    address       character varying(255),
    display_phone boolean NOT NULL,
    email         character varying(255),
    name          character varying(255),
    phone         character varying(255),
    summary       character varying(1024)
);
ALTER TABLE public.resume OWNER TO postgres;

CREATE TABLE public.skill
(
    skill_id uuid NOT NULL,
    name     character varying(255),
    position integer,
    group_id uuid
);
ALTER TABLE public.skill OWNER TO postgres;

CREATE TABLE public.skill_group
(
    skill_group_id uuid NOT NULL,
    name           character varying(255),
    position       integer,
    experience_id  uuid,
    resume_id      uuid
);
ALTER TABLE public.skill_group OWNER TO postgres;

ALTER TABLE ONLY public.education ADD CONSTRAINT education_pkey PRIMARY KEY (education_id);
ALTER TABLE ONLY public.experience_bullet ADD CONSTRAINT experience_bullet_pkey PRIMARY KEY (bullet_id);
ALTER TABLE ONLY public.experience_description ADD CONSTRAINT experience_description_pkey PRIMARY KEY (description_id);
ALTER TABLE ONLY public.experience ADD CONSTRAINT experience_pkey PRIMARY KEY (experience_id);
ALTER TABLE ONLY public.resume ADD CONSTRAINT resume_pkey PRIMARY KEY (resume_id);
ALTER TABLE ONLY public.skill_group ADD CONSTRAINT skill_group_pkey PRIMARY KEY (skill_group_id);
ALTER TABLE ONLY public.skill ADD CONSTRAINT skill_pkey PRIMARY KEY (skill_id);
ALTER TABLE ONLY public.skill ADD CONSTRAINT skill_skill_group_id_fk FOREIGN KEY (group_id) REFERENCES public.skill_group(skill_group_id);
ALTER TABLE ONLY public.experience_bullet ADD CONSTRAINT experience_bullet_experience_id_fk FOREIGN KEY (experience_id) REFERENCES public.experience(experience_id);
ALTER TABLE ONLY public.education ADD CONSTRAINT education_resume_id_fk FOREIGN KEY (resume_id) REFERENCES public.resume(resume_id);
ALTER TABLE ONLY public.experience ADD CONSTRAINT experience_resume_id_fk FOREIGN KEY (resume_id) REFERENCES public.resume(resume_id);
ALTER TABLE ONLY public.experience_description ADD CONSTRAINT experience_description_experience_id_fk FOREIGN KEY (experience_id) REFERENCES public.experience(experience_id);
ALTER TABLE ONLY public.skill_group ADD CONSTRAINT skill_group_resume_id_fk FOREIGN KEY (resume_id) REFERENCES public.resume(resume_id);
ALTER TABLE ONLY public.skill_group ADD CONSTRAINT skill_group_experience_id_fk FOREIGN KEY (experience_id) REFERENCES public.experience(experience_id);

GRANT
ALL
ON SCHEMA public TO PUBLIC;
