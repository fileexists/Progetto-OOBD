INSERT INTO Utenti (username, password, usertype) VALUES ('hemonios', 'hemo1234', 'Utente');
INSERT INTO Utenti (username, password, usertype) VALUES ('medmar', '12345678', 'Compagnia');
INSERT INTO Utenti (username, password, usertype) VALUES ('example', '1234567890', 'Compagnia');
INSERT INTO TipoNatante (tipo_natante, automezzi) VALUES ('Traghetto', true), ('Aliscafo', false), ('Motonave', false);
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Molo Beverello ', 'Molo Beverello, 1, 80133 Napoli NA', '0812346789');
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Porto dí Ischia', 'Via Porto, 25, 80077 Ischia NA', '0811111111');
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Porto Turistico di Capri', 'Marina di Caterola, 80073 Capri NA', '0816381459');
INSERT INTO Porto(comune, indirizzo,tel) VALUES('Porto di Pozzuoli', 'Lungomare C. Colombo, 17, 80078 Pozzuoli NA', '0812556678');
INSERT INTO Natanti(nome,typeid,posti,postiauto, idcompagnia) VALUES ('Meraviglia', 1, 300,50,1);
INSERT INTO Natanti(nome,typeid,posti,idcompagnia) VALUES ('Amilcare', 2, 320,1);
INSERT INTO Natanti(nome,typeid,posti,idcompagnia) VALUES ('Astra', 3, 150,1);
INSERT INTO Natanti(nome,typeid,posti,idcompagnia) VALUES ('Miryam',2,300,2);


INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-01 16:00:00.000000', '2025-01-01 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-04 16:00:00.000000', '2025-01-04 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-05 16:00:00.000000', '2025-01-05 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-08 16:00:00.000000', '2025-01-08 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-11 16:00:00.000000', '2025-01-11 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-12 16:00:00.000000', '2025-01-12 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-15 16:00:00.000000', '2025-01-15 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-18 16:00:00.000000', '2025-01-18 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES (1, 1, 1, 2, null, '2025-01-19 16:00:00.000000', '2025-01-19 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-01-22 16:00:00.000000', '2025-01-22 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-01-25 16:00:00.000000', '2025-01-25 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-01-26 16:00:00.000000', '2025-01-26 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-01-29 16:00:00.000000', '2025-01-29 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-01 16:00:00.000000', '2025-02-01 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-02 16:00:00.000000', '2025-02-02 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-05 16:00:00.000000', '2025-02-05 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-08 16:00:00.000000', '2025-02-08 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-09 16:00:00.000000', '2025-02-09 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-12 16:00:00.000000', '2025-02-12 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-15 16:00:00.000000', '2025-02-15 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-16 16:00:00.000000', '2025-02-16 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-19 16:00:00.000000', '2025-02-19 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-22 16:00:00.000000', '2025-02-22 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-23 16:00:00.000000', '2025-02-23 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-02-26 16:00:00.000000', '2025-02-26 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-01 16:00:00.000000', '2025-03-01 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-02 16:00:00.000000', '2025-03-02 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-05 16:00:00.000000', '2025-03-05 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-08 16:00:00.000000', '2025-03-08 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-09 16:00:00.000000', '2025-03-09 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-12 16:00:00.000000', '2025-03-12 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-15 16:00:00.000000', '2025-03-15 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-16 16:00:00.000000', '2025-03-16 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-19 16:00:00.000000', '2025-03-19 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-22 16:00:00.000000', '2025-03-22 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-23 16:00:00.000000', '2025-03-23 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-26 16:00:00.000000', '2025-03-26 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-29 16:00:00.000000', '2025-03-29 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 1, 1, 1, 2, null, '2025-03-30 16:00:00.000000', '2025-03-30 16:45:00.000000', null, '2025-01-01 00:00:00.000000', '2025-03-31 00:00:00.000000', '{Mercoledi,Sabato,Domenica}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 2, 1, 3, 4, null, '2025-04-03 16:20:00.000000', '2025-04-03 17:15:00.000000', null, '2025-03-31 00:00:00.000000', '2025-04-30 00:00:00.000000', '{Giovedi}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 2, 1, 3, 4, null, '2025-04-10 16:20:00.000000', '2025-04-10 17:15:00.000000', null, '2025-03-31 00:00:00.000000', '2025-04-30 00:00:00.000000', '{Giovedi}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 2, 1, 3, 4, null, '2025-04-17 16:20:00.000000', '2025-04-17 17:15:00.000000', null, '2025-03-31 00:00:00.000000', '2025-04-30 00:00:00.000000', '{Giovedi}', 7.5, 4.5, 9, 6);
INSERT INTO corse (nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) VALUES ( 2, 1, 3, 4, null, '2025-04-24 16:20:00.000000', '2025-04-24 17:15:00.000000', null, '2025-03-31 00:00:00.000000', '2025-04-30 00:00:00.000000', '{Giovedi}', 7.5, 4.5, 9, 6);

