INSERT INTO resume.resume(resume_id, address, display_phone, email, name, phone, summary)
VALUES (uuid_generate_v4(), 'Issaquah, WA  98027', false, 'kroy760@gmail.com', 'Kevin Roy', '(425) 208-1223',
        'Diligent, Independent, Articulate.  Experienced Web Developer.  Prefers backend, enjoys UI considers ' ||
        'myself full stack. I have broad professional experience, looking to continue to focus on developing ' ||
        'complex application in a professional engineering environment.');
INSERT INTO resume.education(education_id, degree, school, graduation, position, resume_id)
SELECT education_id, degree, school, graduation, position, resume_id
FROM (VALUES (uuid_generate_v4(), 'Master of Science in Computer Science', ' University of Massachusetts at Lowell', 1,
              '2001-02-01'),
             (uuid_generate_v4(), 'Bachelor of Science in Computer Science', ' Worcester Polytechnic Institute', 2,
              '1994-05-01')
     ) AS data(education_id, degree, school, position, graduation)
         JOIN resume.resume ON email = 'kroy760@gmail.com';
INSERT INTO resume.skill_group(skill_group_id, name, position, experience_id, resume_id)
SELECT uuid_generate_v4(), skill, position, null, resume_id
FROM (VALUES ('Languages', 1),
             ('Frameworks', 2),
             ('Tools', 3),
             ('Databases', 4)
     ) AS data(skill, position)
         JOIN resume.resume ON email = 'kroy760@gmail.com';
INSERT INTO resume.skill(skill_id, name, position, group_id)
SELECT uuid_generate_v4(), skill, pos, skill_group.skill_group_id
FROM (VALUES ('Languages', 'TypeScript', 1),
             ('Languages', 'JavaScript', 2),
             ('Languages', 'HTML', 3),
             ('Languages', 'CSS', 4),
             ('Languages', 'Kotlin', 5),
             ('Languages', 'PHP', 6),
             ('Languages', 'Java', 7),
             ('Languages', 'C', 8),
             ('Languages', 'C++', 9),
             ('Languages', 'Perl', 10),
             ('Languages', 'bash', 11),
             ('Frameworks', 'React', 1),
             ('Frameworks', 'React Redux', 2),
             ('Frameworks', 'NodeJS', 3),
             ('Frameworks', 'jest', 4),
             ('Frameworks', 'enzyme', 5),
             ('Frameworks', 'testing-library/react', 6),
             ('Frameworks', 'webpack', 7),
             ('Frameworks', 'loadable', 8),
             ('Frameworks', 'Spring', 9),
             ('Frameworks', 'reactstrap', 10),
             ('Frameworks', 'flowtype', 11),
             ('Frameworks', 'Axios', 12),
             ('Frameworks', 'Material UI', 13),
             ('Frameworks', 'jQuery', 14),
             ('Frameworks', 'Resilence4J', 15),
             ('Frameworks', 'PL/SQL', 16),
             ('Frameworks', 'bootstrap', 17),
             ('Databases', 'PostgreSQL', 1),
             ('Databases', 'MySQL', 2),
             ('Databases', 'Oracle', 3),
             ('Databases', 'SQL Server', 4),
             ('Tools', 'git', 1),
             ('Tools', 'Jira', 2),
             ('Tools', 'Confluence', 3),
             ('Tools', 'Splunk', 4),
             ('Tools', 'docker', 5),
             ('Tools', 'Jenkins', 6),
             ('Tools', 'Datadog', 7),
             ('Tools', 'Gradle', 8),
             ('Tools', 'maven', 9),
             ('Tools', 'AWS', 10),
             ('Tools', 'IntelliJ', 11),
             ('Tools', 'vim', 12),
             ('Tools', 'PhantomJS', 13),
             ('Tools', 'ClearCase', 14)
     ) AS data(skill_group, skill, pos)
         JOIN resume.skill_group ON name = skill_group;
INSERT INTO resume.experience(experience_id, company, end_date, start_date, title, position, resume_id)
SELECT uuid_generate_v4(), company, end_date, start_date, title, pos, resume_id
FROM (VALUES (1, 'Software Development Engineer III', 'Expedia Group', '2018-06-25'::date, null, 1),
             (2, 'Technical Lead', 'Sterling Talent Solutions', '2012-05-01'::date, '2018-06-25'::date, 2),
             (3, 'Senior Software Engineer', 'Applied Discovery, Inc.', '2010-10-01'::date, '2012-04-01'::date, 3),
             (4, 'Software Engineer/Technical Lead', 'SofTech, Inc.', '2001-03-01'::date, '2010-10-01'::date, 4),
             (5, 'Contractor/Software Engineer', 'OrderTrust', '2000-02-01'::date, '2001-03-01'::date, 5),
             (6, 'Software Engineer', 'PSW Technology', '1999-04-01'::date, '2000-02-01'::date, 6),
             (7, 'Contractor', 'Celestica, Inc.', '1997-05-01'::date, '1999-04-01'::date, 7),
             (8, 'Project Leader/Software Engineer', 'Boston Technology, Inc.', '1995-12-01'::date, '1997-05-01'::date, 8),
             (9, 'Software Engineer', 'ESSENSE Systems, Inc.', '1994-08-01'::date, '1995-12-01'::date, 9)
     ) AS data(position, title, company, start_date, end_date, pos)
         JOIN resume.resume ON email = 'kroy760@gmail.com';
INSERT INTO resume.experience_description(description_id, name, position, experience_id)
SELECT uuid_generate_v4(), item, pos, experience_id
FROM (VALUES ('Expedia Group', 1, 'text',
              'Worked in an organization responsible for the messaging tool for customer agents.  This tool connects the ' ||
              'customer with the agent.  The tool provides a branded experience with the use of channels, allowing the ' ||
              'customer to communication with chat, email, service requests, and a voice experience.'),
             ('Expedia Group', 2, 'text',
              'The team rebuilt the application using React on the front-end and Java/Kotlin on the back-end.  I worked ' ||
              'primarily on the front-end but communicated through technical and product teams.'),
             ('Sterling Talent Solutions', 1, 'text',
              'A key contributor on developing a product in a small aggressive company to be acquired by a large industry ' ||
              'leader. We provided quality background screening and onboarding solutions. Worked in major areas of the company ' ||
              'and developed multiple large features individually and as a leader in the team.'),
             ('Sterling Talent Solutions', 2, 'text',
              'I worked on two major aspects of the company''s product. The first is a highly configurable form based data ' ||
              'collection framework. The second being the position and processing of the various searches and the surrounding ' ||
              'compliance.'),
             ('Applied Discovery, Inc.', 1, 'text',
              'Worked on team to design the next generation e-Discovery processing line using open source technologies.'),
             ('SofTech, Inc.', 1, 'text',
              'Worked on a product lifecycle management product to provide new features, maintenance and customer support ' ||
              'for medium-sized businesses. Managed Pro/C coding, data, large configuration loading, APIs, licensing, ' ||
              'installations and research third-party product technical issues.'),
             ('OrderTrust', 1, 'text',
              'Worked on main payment application in UNIX using C++ and Informix for provider of marketing and order ' ||
              'management services for online retailers and catalogs. Developed prototype value-added payment program in ' ||
              'J2EE and loyalty-based system of electronic coupons. Cleaned code and automated processes.'),
             ('PSW Technology', 1, 'text',
              'Worked for a consulting company for the IBM/Lotus Notes with 10 employees to port Lotus Notes and toolkits ' ||
              'to IBM AS/400 system and to correct bugs. Developed and convinced engineers to buy into solutions to problems.'),
             ('Celestica, Inc.', 1, 'text',
              'Developed, repaired and enhanced programs and processes for electronics manufacturer using in-house ' ||
              'client/server application system to manage flow of process lines. Developed and debugged client/server ' ||
              'applications in C, Informix Embedded SQL and KornShell and created new modules for integration with ' ||
              'X Windows program and ESQL.'),
             ('Boston Technology, Inc.', 1, 'text',
              'Worked in the leading communications company that implemented voicemail systems for large ' ||
              'telecommunications companies to lead feature development and bug fixing projects. Including a ' ||
              'traffic statistic feature for voicemail application and fixed bugs for various international ' ||
              'projects written in C/C++ and fixed bugs in billing systems.'),
             ('ESSENSE Systems, Inc.', 1, 'text',
              'Developed aspects of a data-driven GUI that empowered employees to manage their own human resource records.')
     ) AS data(item_company, pos, type, item)
         JOIN resume.experience ON company = item_company;
INSERT INTO resume.experience_bullet(bullet_id, name, position, experience_id)
SELECT uuid_generate_v4(), item, pos, experience_id
FROM (VALUES ('Expedia Group', 1, 'bullet',
              'Branded the Agent Messaging Tool: the project took the existing styling and converted it to our branded ' ||
              'UITK framework.'),
             ('Expedia Group', 2, 'bullet',
              'Agent Messaging Tool: Worked on various features, one of the larger features being a tool to protect PCI ' ||
              'information while the agent enters protected customer information.  This was a complex interaction between ' ||
              'multiple back-end system'),
             ('Expedia Group', 3, 'bullet',
              'Virtual Agent API Server: Worked on a Kotlin API server.  Created an asynchronous endpoint to change the state ' ||
              'of the conversation, from reserved to released.  As it was a new server setup/implemented basic server ' ||
              'functionality, such as retries, circuit breaker, haystack id, and others.'),
             ('Expedia Group', 4, 'bullet',
              'Rich Card Server: Created a server for a new "technology" called rich-cards, this server/repository with build ' ||
              'process.  This repository is used by multiple projects and requires the building of certain resources, the ' ||
              'packages and branded CSS files'),
             ('Expedia Group', 5, 'bullet',
              'Outbound voice:  this project enabled agents to place a softphone call from the agent messaging tool.'),
             ('Sterling Talent Solutions', 1, 'bullet',
              'Fair Chance: Implemented the current states requirements for a process similar to Individualized Assessment ' ||
              'including filling out state provided forms.'),
             ('Sterling Talent Solutions', 2, 'bullet',
              'Individualized Assessment: Implemented the ability for the customer to request and process a candidate''s ' ||
              'response to an adverse action being preformed on a candidate.'),
             ('Sterling Talent Solutions', 3, 'bullet',
              'eDispute: Implemented the ability for the candidate to review and dispute the reports or searches preformed on them.'),
             ('Sterling Talent Solutions', 4, 'bullet',
              'Form Review: Implemented the customer''s ability to review the customized forms, including sending the form ' ||
              'back to the candidate for modification.'),
             ('Sterling Talent Solutions', 5, 'bullet',
              'E-signature: Developed the signing experience to provide confirmation that the user is certifying their actions. ' ||
              'Including the generation of a PDF from HTML.'),
             ('Sterling Talent Solutions', 6, 'bullet',
              'Candidate Model Export: Provided a feature to enable customer to export the data about their candidates.'),
             ('Sterling Talent Solutions', 7, 'bullet',
              'Professional Services Billing System: Enable another team to easily bill a customer based on their ' ||
              'individual contracts.'),
             ('Applied Discovery, Inc.', 1, 'bullet',
              'Researched and evaluated various technologies: Parallel Processing Framework and grid (Globus/Condor/JPPF), ' ||
              'workflow technologies (jPBM), JBoss, RESTful, Hibernate, Boost, Oracle’s OutsideIn, Google’s Guice, ' ||
              'Talend and others.'),
             ('Applied Discovery, Inc.', 2, 'bullet',
              'Implemented Straw man of new system written in Java using a RESTful API.'),
             ('Applied Discovery, Inc.', 3, 'bullet',
              'Implemented an Object-Oriented Application (C++) to utilize Oracle’s OutsideIn Technology to parse and load ' ||
              'Microsoft Outlook pst file.'),
             ('SofTech, Inc.', 1, 'bullet',
              'Led technical effort to internationalize products into local languages that allowed sales in foreign markets.'),
             ('SofTech, Inc.', 2, 'bullet',
              'Designed and implemented revision editing tool that enabled administrators to define multiple revision ' ||
              'sequences and apply them to classes, which increased value of the company’s product line.'),
             ('SofTech, Inc.', 3, 'bullet',
              'Built feature that pushed files into replication servers to increase performance over the WAN.'),
             ('SofTech, Inc.', 4, 'bullet',
              'Transited to server-based report generation using libxml2 and libxslt technology to improve performance.'),
             ('SofTech, Inc.', 5, 'bullet',
              'Improved querying ability and performance by implementing a list like clause.'),
             ('SofTech, Inc.', 6, 'bullet',
              'Redesigned aspects of the product to enable developers to use QT technology that minimized maintenance ' ||
              'costs and enabled code reuse.'),
             ('SofTech, Inc.', 7, 'bullet',
              'Cross over to new code layers to resolve problems; demonstrated extra effort in fixing root causes, ' ||
              'keeping code simple, removing unused code and using build tools that increase productivity.'),
             ('SofTech, Inc.', 8, 'bullet',
              'Took initiative to rewrite header file architecture, which saved up to two hours of compile time.'),
             ('OrderTrust', 1, 'bullet',
              'Simplified code maintenance of the entire system by moving legacy code into standardized build environment.'),
             ('OrderTrust', 2, 'bullet',
              'Automated testing Dining À la Card system with new systems using Perl script that improved accuracy and ' ||
              'reduced time needed to test systems and removed element of human error.'),
             ('PSW Technology', 1, 'bullet',
              'Built portable code using a cross-compiler and corrected bugs using native AS/400 debugger.'),
             ('Celestica, Inc.', 1, 'bullet',
              'Redesigned process of changing bill-of-materials workstation structures that avoided deadlocks.'),
             ('Celestica, Inc.', 2, 'bullet',
              'Fixed key user interface, enabling users to step through process of building workstations and PCs.'),
             ('Celestica, Inc.', 3, 'bullet',
              'Determined report needs of users that included finding lost workstations and bottlenecks and implemented ' ||
              'intranet-accessible reports using C, KornShell, HTML and Sapphire CGI generator.')
     ) AS data(item_company, pos, type, item)
         JOIN resume.experience ON company = item_company;
INSERT INTO resume.skill_group(skill_group_id, name, position, experience_id, resume_id)
SELECT uuid_generate_v4(), name, pos, experience_id, null
FROM (VALUES ('Expedia Group', 'Technology', 1),
             ('Sterling Talent Solutions', 'Technology', 1),
             ('Applied Discovery, Inc.', 'Technology', 1),
             ('SofTech, Inc.', 'Technology', 1),
             ('OrderTrust', 'Technology', 1),
             ('PSW Technology', 'Technology', 1),
             ('Celestica, Inc.', 'Technology', 1),
             ('Boston Technology, Inc.', 'Technology', 1),
             ('ESSENSE Systems, Inc.', 'Technology', 1)
     ) AS data(item_company, name, pos)
         JOIN resume.experience ON company = item_company;
INSERT INTO resume.skill(skill_id, name, position, group_id)
SELECT uuid_generate_v4(), item, pos, skill_group_id
FROM (VALUES ('Expedia Group', 1, 'tech', 'Ubuntu'),
             ('Expedia Group', 2, 'tech', 'React'),
             ('Expedia Group', 3, 'tech', 'Kotlin'),
             ('Expedia Group', 4, 'tech', 'NodeJS'),
             ('Expedia Group', 5, 'tech', 'React Redux'),
             ('Expedia Group', 6, 'tech', 'webpack'),
             ('Expedia Group', 7, 'tech', 'jest'),
             ('Expedia Group', 8, 'tech', 'Spring'),
             ('Expedia Group', 9, 'tech', 'Enzyme'),
             ('Expedia Group', 10, 'tech', 'testing-library/react'),
             ('Expedia Group', 11, 'tech', 'AWS'),
             ('Expedia Group', 12, 'tech', 'Jenkins'),
             ('Expedia Group', 13, 'tech', 'Splunk'),
             ('Expedia Group', 14, 'tech', 'Datadog'),
             ('Sterling Talent Solutions', 1, 'tech', 'Ubuntu'),
             ('Sterling Talent Solutions', 2, 'tech', 'Apache'),
             ('Sterling Talent Solutions', 3, 'tech', 'MySQL'),
             ('Sterling Talent Solutions', 4, 'tech', 'PHP'),
             ('Sterling Talent Solutions', 5, 'tech', 'HTML'),
             ('Sterling Talent Solutions', 6, 'tech', 'JavaScript'),
             ('Sterling Talent Solutions', 7, 'tech', 'CSS'),
             ('Sterling Talent Solutions', 8, 'tech', 'PhantomJS'),
             ('Sterling Talent Solutions', 9, 'tech', 'IntelliJ'),
             ('Sterling Talent Solutions', 10, 'tech', 'PHPUnit'),
             ('Applied Discovery, Inc.', 1, 'tech', 'Java'),
             ('Applied Discovery, Inc.', 2, 'tech', 'C'),
             ('Applied Discovery, Inc.', 3, 'tech', 'C++'),
             ('Applied Discovery, Inc.', 4, 'tech', 'Windows'),
             ('Applied Discovery, Inc.', 5, 'tech', 'Unix'),
             ('Applied Discovery, Inc.', 6, 'tech', 'SQL Server'),
             ('SofTech, Inc.', 1, 'tech', 'C'),
             ('SofTech, Inc.', 2, 'tech', 'C++'),
             ('SofTech, Inc.', 3, 'tech', 'Unix'),
             ('SofTech, Inc.', 4, 'tech', 'SQL'),
             ('SofTech, Inc.', 5, 'tech', 'Perl'),
             ('OrderTrust', 1, 'tech', 'C++'),
             ('OrderTrust', 2, 'tech', 'Unix'),
             ('OrderTrust', 3, 'tech', 'SQL'),
             ('OrderTrust', 4, 'tech', 'Java'),
             ('OrderTrust', 4, 'tech', 'Perl'),
             ('PSW Technology', 1, 'tech', 'C'),
             ('PSW Technology', 2, 'tech', 'AS/400'),
             ('Celestica, Inc.', 1, 'tech', 'C/C++'),
             ('Celestica, Inc.', 2, 'tech', 'HP/UX'),
             ('Boston Technology, Inc.', 1, 'tech', 'C/C++'),
             ('Boston Technology, Inc.', 2, 'tech', 'SCO Unix'),
             ('Boston Technology, Inc.', 3, 'tech', 'SQL')
     ) AS data(item_company, pos, type, item)
         JOIN resume.experience ON company = item_company
         JOIN resume.skill_group ON skill_group.experience_id = experience.experience_id;
