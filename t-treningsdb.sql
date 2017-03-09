CREATE TABLE Periode(
	PeriodeID		INT,
    Fra				DATE,
    Til				DATE,
    PRIMARY KEY(PeriodeID)
);

CREATE TABLE Trening(
    Treningsnr		INT,
    Navn		    VARCHAR(50),	
    Dato		    DATE,
    Varighet		INT,
    Form		    INT,
    Treningsformål	VARCHAR(50),
    Prestasjon		INT,	
    Tips		    VARCHAR(50),
    PeriodeID		INT, 
    PRIMARY KEY(Treningsnr),
    FOREIGN KEY(PeriodeID) REFERENCES Periode(PeriodeID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Utendørsaktivitet(
    Temperatur		DECIMAL,
    Værtype		    VARCHAR(20),
    Treningsnr		INT,
    PRIMARY KEY(Treningsnr),
    FOREIGN KEY(Treningsnr) REFERENCES Trening(Treningsnr) ON DELETE CASCADE ON UPDATE CASCADE
); 
  
CREATE TABLE Innendørsaktivitet(
    Ventilasjon		VARCHAR(25),
    AntallTilskuere	INT,
    Treningsnr		INT,
    FOREIGN KEY(Treningsnr) REFERENCES Trening(Treningsnr) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(Treningsnr)
);
    
CREATE TABLE Øvelse(
    Øvelsesnavn     VARCHAR(50),
    Beskrivelse     VARCHAR(300),
    Resultat        VARCHAR(100),
    Kategorinavn    VARCHAR(50), 
    PRIMARY KEY (Øvelsesnavn)
);

CREATE TABLE Datapunkt(
	PunktNr         INT,
    Tid             INT, 
    Puls            INT, 
    Lengdegrad      VARCHAR(30),
    Breddegrad      VARCHAR(30), 
    HøydeOverHavet  INT,
    Øvelsesnavn     VARCHAR(50),
    Treningsnr      INT,
	FOREIGN KEY (Øvelsesnavn) REFERENCES Øvelse(Øvelsesnavn) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Treningsnr) REFERENCES Trening(Treningsnr) ON DELETE CASCADE ON UPDATE CASCADE, 
    PRIMARY KEY (PunktNr,Øvelsesnavn,Treningsnr)
);

CREATE TABLE KondisjonOgStyrke(
    Navn VARCHAR(50),
    Belastning      INT(10),
    AntallRep       INT,
    AntallSet       INT, 
    FOREIGN KEY (Navn) REFERENCES Øvelse(Øvelsesnavn)  ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (Navn)    
);

CREATE TABLE Utholdenhet(
    Navn    VARCHAR(50),
    Lengde          INT, 
    FOREIGN KEY (Navn) REFERENCES Øvelse(Øvelsesnavn)  ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (Navn)     
);

CREATE TABLE UtførØvelse(
	Øvelsesnavn     VARCHAR(50),
    Treningsnr		INT,
    FOREIGN KEY (Øvelsesnavn) REFERENCES Øvelse(Øvelsesnavn) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (Treningsnr) REFERENCES Trening(Treningsnr) ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (Øvelsesnavn,TreningsNr)
);

DELETE FROM Trening;
INSERT INTO Kategori VALUES
('KondisjonOgStyrke'),('Utholdenhet');

INSERT INTO Periode VALUES
(1,'2017-1-1','2017-1-8'),
(2,'2017-1-30','2017-2-2'),
(3,'2017-2-20','2017-2-28');

INSERT INTO Trening VALUES
(1,'Styrke','2017-1-2',2,5,'Stronk power',4,'Sumo',1),
(2,'Arm','2017-2-28',1,6,'Stronk arms',5,'Flex',3);

INSERT INTO Innendørsaktivitet VALUES
('Good vent',2,1),
('Too hot',3,2);

INSERT INTO Øvelse VALUES
('Lift', 'Do u even lift bro','Result here', 'KondisjonOgStyrke'),
('Namee', 'Wut','Result here', 'Utholdenhet');

INSERT INTO UtførØvelse VALUES
(1,1), (1,2);