ALTER TABLE ONLY tictactoe.player ADD CONSTRAINT game_player_game_id_fk FOREIGN KEY (game_id) REFERENCES tictactoe.game(game_id);
ALTER TABLE ONLY tictactoe.player ADD CONSTRAINT game_player_session_id_fk FOREIGN KEY (session_id) REFERENCES session.session(session_id);
