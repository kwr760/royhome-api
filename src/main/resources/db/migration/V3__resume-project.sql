CREATE TABLE public.project
(
    project_id    uuid NOT NULL,
    name          character varying(255),
    url           character varying(255),
    description   character varying(255),
    end_date      character varying(255),
    start_date    character varying(255),
    position      integer,
    resume_id     uuid
);
ALTER TABLE public.project OWNER TO postgres;

ALTER TABLE ONLY public.project ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);
ALTER TABLE ONLY public.project ADD CONSTRAINT project_resume_id_fk FOREIGN KEY (resume_id) REFERENCES public.resume(resume_id);

GRANT
ALL
ON SCHEMA public TO PUBLIC;

INSERT INTO project(project_id, name, url, description, start_date, position, resume_id)
SELECT project_id, project_name, url, description, start_date, pos, resume_id
FROM (VALUES (uuid_generate_v4(), 'Front-End of Personal Website - royhome.net',
              'https://github.com/kwr760/royhome-web', 'Build with Typescript, React, and Material-UI',
              '2019-09-01', 1),
             (uuid_generate_v4(), 'Back-End of Personal Website - royhome.net',
              'https://github.com/kwr760/royhome-api', 'Build with Spring, Kotlin, and Postgres',
              '2019-09-01', 2)
     ) AS data(project_id, project_name, url, description, start_date, pos)
         JOIN resume ON email = 'kroy760@gmail.com';
