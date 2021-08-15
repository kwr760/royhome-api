CREATE TABLE resume.project
(
    project_id    uuid NOT NULL,
    name          varchar(255),
    url           varchar(255),
    description   varchar(255),
    end_date      date,
    start_date    date,
    position      int,
    resume_id     uuid
);
ALTER TABLE resume.project OWNER TO postgres;

ALTER TABLE ONLY resume.project ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);
ALTER TABLE ONLY resume.project ADD CONSTRAINT project_resume_id_fk FOREIGN KEY (resume_id) REFERENCES resume.resume(resume_id);

INSERT INTO resume.project(project_id, name, url, description, start_date, position, resume_id)
SELECT project_id, project_name, url, description, start_date, pos, resume_id
FROM (VALUES (uuid_generate_v4(), 'Front-End of Personal Website - royhome.net',
              'https://github.com/kwr760/royhome-web', 'Build with Typescript, React, and Material-UI',
              '2019-09-01'::date, 1),
             (uuid_generate_v4(), 'Back-End of Personal Website - royhome.net',
              'https://github.com/kwr760/royhome-api', 'Build with Spring, Kotlin, and Postgres',
              '2019-09-01'::date, 2)
     ) AS data(project_id, project_name, url, description, start_date, pos)
         JOIN resume.resume ON email = 'kroy760@gmail.com';
