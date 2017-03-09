CREATE TABLE Loper (brikkenr INT,
                    navn VARCHAR(50),
                    klasse CHAR(10),
                    klubb VARCHAR(20),
                    starttid INT,
                    lopstid INT,
                    status CHAR(3),
                    PRIMARY KEY(brikkenr));

CREATE TABLE Reg (sekvnr INT,
                  brikkenr INT,
                  postnr INT,
                  tid INT,
                  PRIMARY KEY(sekvnr, brikkenr));

CREATE TABLE Loype (lnr INT,
                    sekvnr INT,
                    postnr INT,
                    PRIMARY KEY(lnr, sekvnr));

CREATE TABLE Klasse (klassenavn CHAR(10),
                     lnr INT,
                     PRIMARY KEY(klassenavn));

INSERT INTO Loper VALUES (123123, 'Fritz', 'H50', 'Old stars', 0, 0, 'dns'),
(123124, 'Måns', 'H50', 'Old stars', 0, 0, 'dns'),
(123125, 'Olle', 'H50', 'Old stars', 0, 0, 'dns');

INSERT INTO Klasse VALUES ('H50', 1);

INSERT INTO Loype VALUES (1, 0, 0), (1, 1, 31), (1, 2, 32), (1, 3, 33), (1, 4, 34), (1, 5, 35), (1, 6, 36), (1, 7, 37), (1, 8, 150), (1, 9, 175);