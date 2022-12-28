CREATE SCHEMA IF NOT EXISTS tictactoe;

CREATE TABLE IF NOT EXISTS tictactoe.game
(
    game_id         uuid NOT NULL,
    state           int default 1,
    board           varchar(9) default '---------',
    created         timestamp default now(),
    modified        timestamp default now(),
    changed         int default 1
);
ALTER TABLE tictactoe.game OWNER TO postgres;
ALTER TABLE ONLY tictactoe.game ADD CONSTRAINT game_game_id_pkey PRIMARY KEY (game_id);
CREATE TRIGGER update_tictactoe_game BEFORE UPDATE ON tictactoe.game FOR EACH ROW EXECUTE PROCEDURE modify_stats();

CREATE TABLE IF NOT EXISTS tictactoe.player
(
    session_id      uuid NOT NULL,
    game_id         uuid NOT NULL,
    name            varchar(256) NOT NULL,
    piece           char,
    created         timestamp default now(),
    modified        timestamp default now(),
    changed         int default 1
    );
ALTER TABLE tictactoe.player OWNER TO postgres;
ALTER TABLE ONLY tictactoe.player ADD CONSTRAINT player_sessionid_pkey PRIMARY KEY (session_id);
CREATE TRIGGER update_tictactoe_player BEFORE UPDATE ON tictactoe.player FOR EACH ROW EXECUTE PROCEDURE modify_stats_when_changed();
