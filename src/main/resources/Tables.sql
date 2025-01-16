
-- Svuoto il database

DROP TRIGGER IF EXISTS tratte ON Corse;
DROP FUNCTION IF EXISTS divide_tratte();
DROP TABLE IF EXISTS Compagnia CASCADE;
DROP TABLE IF EXISTS Utenti CASCADE;
DROP TABLE IF EXISTS Tratte CASCADE;
DROP TABLE IF EXISTS Natanti CASCADE;
DROP TABLE IF EXISTS TipoNatante CASCADE;
DROP TABLE IF EXISTS Corse CASCADE;
DROP TABLE IF EXISTS Porto CASCADE;



-- Creo le tabelle

CREATE TABLE IF NOT EXISTS TipoNatante (
       nid SERIAL PRIMARY KEY NOT NULL,
       tipo_natante VARCHAR(20) NOT NULL,
       automezzi BOOLEAN NOT NULL
);



CREATE TABLE IF NOT EXISTS Utenti (
      username VARCHAR(16) PRIMARY KEY NOT NULL,
      password TEXT NOT NULL,
      usertype VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Compagnia (
     id SERIAL PRIMARY KEY NOT NULL,
     nome TEXT,
     telefono TEXT,
     email TEXT,
     sito TEXT
);

CREATE TABLE IF NOT EXISTS Porto (
    pid SERIAL PRIMARY KEY NOT NULL,
    comune TEXT NOT NULL,
    indirizzo TEXT NOT NULL,
    tel TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Natanti (
   id SERIAL PRIMARY KEY NOT NULL,
   nome TEXT NOT NULL,
   typeid INTEGER NOT NULL REFERENCES TipoNatante(nid),
   posti INTEGER NOT NULL,
   postiauto INTEGER DEFAULT 0,
   idcompagnia INTEGER NOT NULL REFERENCES Compagnia(id)
);


CREATE TABLE IF NOT EXISTS Corse (
    cid SERIAL PRIMARY KEY NOT NULL,
    nid INTEGER NOT NULL REFERENCES Natanti(id),
    idCompagnia INTEGER NOT NULL REFERENCES Compagnia(id),
    porto_partenza INTEGER NOT NULL REFERENCES Porto(pid),
    porto_arrivo INTEGER NOT NULL REFERENCES Porto(pid),
    scalo INTEGER DEFAULT NULL, --ARRAY[20],
    orario_partenza TIMESTAMP NOT NULL,
    orario_arrivo TIMESTAMP NOT NULL,
    orario_scalo TIMESTAMP DEFAULT NULL,
    data_inizio TIMESTAMP NOT NULL,
    data_fine TIMESTAMP NOT NULL,
    cadenza VARCHAR(10)[],
    prezzo REAL,
    prezzo_rid REAL,
    sovraprezzo_prenot REAL,
    sovraprezzo_bag REAL,
    inRitardo BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS Tratte(
  tid SERIAL PRIMARY KEY NOT NULL,
  cid INTEGER NOT NULL REFERENCES Corse(cid),
  porto_partenza INTEGER NOT NULL REFERENCES Porto(pid),
  orario_partenza TIMESTAMP NOT NULL,
  porto_arrivo INTEGER NOT NULL REFERENCES Porto(pid),
  orario_arrivo TIMESTAMP NOT NULL,
  posti INTEGER,
  postiauto INTEGER
);

-- Creo i trigger

CREATE OR REPLACE FUNCTION divide_tratte()
    RETURNS TRIGGER AS
$$
DECLARE
    n_id INTEGER;
    posti_totali INTEGER;
    posti_auto INTEGER;
BEGIN
    SELECT nid INTO n_id FROM Corse WHERE cid = NEW.cid;
    SELECT posti INTO posti_totali FROM Natanti WHERE id = n_id;
    SELECT postiauto INTO posti_auto FROM Natanti WHERE id = n_id;
    INSERT INTO Tratte(cid, porto_partenza, orario_partenza, porto_arrivo, orario_arrivo, posti,postiauto) VALUES (NEW.cid, NEW.porto_partenza, NEW.orario_partenza, NEW.porto_arrivo, NEW.orario_arrivo, posti_totali,posti_auto);
    IF NEW.scalo IS NOT NULL THEN
        INSERT INTO Tratte(cid, porto_partenza,orario_partenza, porto_arrivo, orario_arrivo, posti,postiauto) VALUES (NEW.cid, NEW.porto_partenza, NEW.orario_partenza, NEW.scalo,NEW.orario_scalo, posti_totali,posti_auto);
        INSERT INTO Tratte(cid, porto_partenza,orario_partenza, porto_arrivo, orario_arrivo, posti,postiauto) VALUES (NEW.cid, NEW.scalo, NEW.orario_scalo, NEW.porto_arrivo, NEW.orario_arrivo, posti_totali,posti_auto);
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION addCompagnia()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.usertype = 'Compagnia' THEN
    INSERT INTO Compagnia(nome) VALUES (NEW.username);
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER tratte
    AFTER INSERT ON Corse
    FOR EACH ROW
EXECUTE FUNCTION divide_tratte();

CREATE TRIGGER addCompagnia
    AFTER INSERT ON Utenti
    FOR EACH ROW
    EXECUTE FUNCTION addCompagnia();