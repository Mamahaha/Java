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

CREATE TABLE character
(
  id     BIGSERIAL  NOT NULL,
  name   TEXT       NOT NULL,
  sex    TEXT       NOT NULL,
  age    INTEGER    NOT NULL,
  level  INTEGER    NOT NULL,
  record BIGINT     NOT NULL,
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

CREATE INDEX idx_character_name ON character
(
  name
);

CREATE INDEX idx_character_level ON character
(
  level
);

ALTER TABLE guild
  ADD CONSTRAINT pk_guild PRIMARY KEY (
    id);

ALTER TABLE character
  ADD CONSTRAINT pk_character PRIMARY KEY (
    id);

ALTER TABLE character
  ADD 
    FOREIGN KEY (gid)
      REFERENCES guild;

