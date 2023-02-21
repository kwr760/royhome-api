ALTER TABLE ONLY session.session ADD CONSTRAINT user_session_user_id_fk FOREIGN KEY (user_id) REFERENCES session.user(user_id);
