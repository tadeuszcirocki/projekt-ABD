CREATE TABLE [Pracownik] (
	ID int identity NOT NULL,
	Imie varchar(255) NOT NULL,
	Nazwisko varchar(255) NOT NULL,
	Pesel int NOT NULL,
  CONSTRAINT [PK_PRACOWNIK] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Maszyna] (
	ID int identity NOT NULL,
	Rodzaj varchar(255) NOT NULL,
	Pojemnosc_silnika float NOT NULL,
	KM int NOT NULL,
	Vin int NOT NULL,
	Data_rejestracji date NOT NULL,
  CONSTRAINT [PK_MASZYNA] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Pole] (
	ID int identity NOT NULL,
	Rozmiar float NOT NULL,
	Roslina_ID int NOT NULL,
	Gleba varchar(255) NOT NULL,
  CONSTRAINT [PK_POLE] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Nawoz] (
	ID int identity NOT NULL,
	Rodzaj varchar(255) NOT NULL,
	Iloœæ float NOT NULL,
	Data_waznosci date NOT NULL,
  CONSTRAINT [PK_NAWOZ] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Akcja] (
	ID bigint identity NOT NULL,
	Rodzaj varchar(255) NOT NULL,
	Data date NOT NULL,
	Pracownik_ID int NOT NULL,
	Maszyna_ID int NOT NULL,
	Pole_ID int NOT NULL,
	Nawoz_ID int NOT NULL,
	Nasiona_ID int NOT NULL,
  CONSTRAINT [PK_AKCJA] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Nasiona] (
	ID int identity NOT NULL,
	data_wa¿noœci date NOT NULL,
	Ilosc int NOT NULL,
	Rodzaj varchar(255) NOT NULL,
  CONSTRAINT [PK_NASIONA] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Silos] (
	ID int identity NOT NULL,
	Pojemnosc float NOT NULL,
	Czy_wolny bit NOT NULL,
	Zapelnienie int NOT NULL,
	Roslina_ID int NOT NULL,
  CONSTRAINT [PK_SILOS] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Roslina] (
	ID int identity NOT NULL,
	Rodzaj varchar(255) NOT NULL,
	Nazwa varchar(255) NOT NULL,
	Ile_rosnie int NOT NULL,
  CONSTRAINT [PK_ROSLINA] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [Uzytkownicy] (
	ID int identity NOT NULL,
	Login varchar(255) NOT NULL,
	Haslo varchar(255) NOT NULL,
	Typ_uzytkownika varchar(255) NOT NULL,
  CONSTRAINT [PK_UZYTKOWNICY] PRIMARY KEY CLUSTERED
  (
  [ID] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO


ALTER TABLE [Pole] WITH CHECK ADD CONSTRAINT [Pole_fk0] FOREIGN KEY ([Roslina_ID]) REFERENCES [Roslina]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Pole] CHECK CONSTRAINT [Pole_fk0]
GO


ALTER TABLE [Akcja] WITH CHECK ADD CONSTRAINT [Akcja_fk0] FOREIGN KEY ([Pracownik_ID]) REFERENCES [Pracownik]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Akcja] CHECK CONSTRAINT [Akcja_fk0]
GO
ALTER TABLE [Akcja] WITH CHECK ADD CONSTRAINT [Akcja_fk1] FOREIGN KEY ([Maszyna_ID]) REFERENCES [Maszyna]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Akcja] CHECK CONSTRAINT [Akcja_fk1]
GO
ALTER TABLE [Akcja] WITH CHECK ADD CONSTRAINT [Akcja_fk2] FOREIGN KEY ([Pole_ID]) REFERENCES [Pole]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Akcja] CHECK CONSTRAINT [Akcja_fk2]
GO
ALTER TABLE [Akcja] WITH CHECK ADD CONSTRAINT [Akcja_fk3] FOREIGN KEY ([Nawoz_ID]) REFERENCES [Nawoz]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Akcja] CHECK CONSTRAINT [Akcja_fk3]
GO
ALTER TABLE [Akcja] WITH CHECK ADD CONSTRAINT [Akcja_fk4] FOREIGN KEY ([Nasiona_ID]) REFERENCES [Nasiona]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Akcja] CHECK CONSTRAINT [Akcja_fk4]
GO


ALTER TABLE [Silos] WITH CHECK ADD CONSTRAINT [Silos_fk0] FOREIGN KEY ([Roslina_ID]) REFERENCES [Roslina]([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [Silos] CHECK CONSTRAINT [Silos_fk0]
GO


