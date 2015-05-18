CREATE TABLE guild
(
  id          BIGSERIAL NOT NULL,
  name        TEXT      NOT NULL,
  level       INTEGER   NOT NULL,
  count       INTEGER   NOT NULL,
  flag        TEXT      NOT NULL,
  token       TEXT,
  description TEXT
);

CREATE TABLE player
(
  id     BIGSERIAL  NOT NULL,
  name   TEXT       NOT NULL,
  sex    TEXT       NOT NULL,
  age    INTEGER    NOT NULL,
  level  INTEGER    NOT NULL,
  score BIGINT     NOT NULL,
  gid    BIGSERIAL
);

CREATE INDEX idx_guild_name ON guild
(
  name
);

CREATE INDEX idx_guild_level ON guild
(
  level
);

CREATE INDEX idx_player_name ON player
(
  name
);

CREATE INDEX idx_player_level ON player
(
  level
);

ALTER TABLE guild
  ADD CONSTRAINT pk_guild PRIMARY KEY (
    id);

ALTER TABLE player
  ADD CONSTRAINT pk_player PRIMARY KEY (
    id);

ALTER TABLE player
  ADD 
    FOREIGN KEY (gid)
      REFERENCES guild;

