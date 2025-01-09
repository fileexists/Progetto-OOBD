INSERT INTO Utenti (username, password, usertype) VALUES ('root', 'root', 'Admin');
INSERT INTO Utenti (username, password, usertype) VALUES ('hemonios', 'hemo1234', 'Utente');
INSERT INTO Utenti (username, password, usertype) VALUES ('medmar', '12345678', 'Compagnia');
INSERT INTO Utenti (username, password, usertype) VALUES ('example', '1234567890', 'Compagnia');
INSERT INTO TipoNatante (tipo_natante, automezzi) VALUES ('Traghetto', true), ('Aliscafo', false), ('Motonave', false);
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Molo Beverello ', 'Molo Beverello, 1, 80133 Napoli NA', '0812346789');
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Porto dí Ischia', 'Via Porto, 25, 80077 Ischia NA', '0811111111');
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Porto Turistico di Capri', 'Marina di Caterola, 80073 Capri NA', '0816381459');
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Porto di Pozzuoli', 'Lungomare C. Colombo, 17, 80078 Pozzuoli NA', '0812556678');