CREATE TABLE item (
  id          INTEGER PRIMARY KEY AUTOINCREMENT,
  name        TEXT,
  description TEXT,
  usages      NUMBER,
  avgNumberInLine NUMBER,
  firstseen   INTEGER,
  lastseen    INTEGER
);

CREATE TABLE listitem (
  listid  INTEGER NOT NULL,
  itemid  INTEGER NOT NULL,
  amount  NUMBER,
  FOREIGN KEY(listid) REFERENCES list(id),
  FOREIGN KEY(itemid) REFERENCES item(id)
);

CREATE TABLE list (
  id          INTEGER PRIMARY KEY AUTOINCREMENT,
  name        TEXT,
  description TEXT,
  created     INTEGER
);

INSERT INTO item (name, description, usages, avgNumberInLine, firstseen, lastseen)
  VALUES ('Bananas', 'Second best fruit', 0, 0, 0, 0);

INSERT INTO item (name, description, usages, avgNumberInLine, firstseen, lastseen)
  VALUES ('Lime', 'The best fruit', 0, 0, 0, 0);
