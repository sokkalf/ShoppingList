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
  FOREIGN KEY(listid) REFERENCES list(id),
  FOREIGN KEY(itemid) REFERENCES item(id)
);

CREATE TABLE list (
  id          INTEGER PRIMARY KEY AUTOINCREMENT,
  name        TEXT,
  description TEXT,
  created_date INTEGER
);

