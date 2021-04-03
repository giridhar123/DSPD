use ospedale;

-- GRUPPO PERMESSI (COdGruppo, NomeGruppo)
DELETE FROM Gruppo_Permessi;
insert into Gruppo_Permessi value(1,'Paziente');
insert into Gruppo_Permessi values(2,'Medico');
insert into Gruppo_Permessi values(3,'Amministratore');

-- PERSONA (CF, Nome, Cognome, Data_Nascita, Sesso, Indirizzo_Residenza, Telefono, Citta, Email)
DELETE FROM Persona;
insert into Persona values ('pregpp97m06l112b', 'Giuseppe', 'Peri', '1997/08/06', 'M', 'Via Luigi Pirandello,4', '3922474469', 'Scillato', 'peppep97@gmail.com');
insert into Persona values ('dgrgrl98f12r091f','Girolamo','Dei Girolami', '1998/06/12','M','Via dei Pini,56','3331235691', 'Palermo', 'gdg1998@gmail.com'); -- ospite
insert into Persona values ('ppippr99b02e041m','Paperino','Pippo','1999/02/24','M','Via dei Topi,6','3691508754', 'Caltanissetta', 'pippo99@gmail.com'); -- ospite
insert into Persona values ('scldgi62e23a018p','Diego','Scalco','1962/05/23','M','Corso Umberto,1','3706923458', 'Catania', 'scalco62@gmail.com'); -- ospite
insert into Persona values ('nblrcc80i10t014h','Riccardo','Nobile','1980/09/10','M','Via Garibaldi,8a','3924568187', 'Scillato', 'richi80@gmail.com'); -- ospite
insert into Persona values ('plnrld82c26d048x','Eraldo','Apolloni','1982/03/26','M','Via degli Arcipelaghi,28','3266445698','Mazara del Vallo','eraldo82@gmail.com'); -- medico
insert into Persona values ('mrtfbz70i12f098p','Fabrizio','Marotta','1970/09/12','M','Via ragazzi del 99,99','3205486545','Campobello di Licata','marotta70@libero.it'); -- medico
insert into Persona values ('sccnna90d01c092m','Anna','Stoccaretto','1990/04/01','F','Viale delle fanciulle,77','3465986324','Agrigento','anna1990@yahoo-com'); -- medico
insert into Persona values ('vltmra85a01b076j','Maria','Valletta','1985/01/1','F','Via Caravaggio,58','3934215684','Milazzo','vallettamaria@gmail.com'); -- medico
insert into Persona values ('mnzgpp75l30c074e','Giuseppe','Minozzi','1975/10/30','M','Via Scillato,1','3892567410','Capo DOrlando','MinozziG30@gmail.com'); -- medico
insert into Persona values ('mrzctt86m20e091f','Cetti','Marzapane','1986/11/20','F','Via Santi Romano,5','3287459632','Trappitello', 'marzapane1@gmail.com'); -- medico
insert into Persona values ('brtfrc72b44g273z','Federica','Bertolino','1972/02/04','F','Via lolloso,1','3669258841','Catania', 'bertolino12@gmail.com'); -- medico
insert into Persona values ('znasdk93e05g273n','Sadok','Zaiene','1993/05/05','M','Via cannavazzo,3','3334467564','Siracusa','zaiene95@gmail.com'); -- medico
insert into Persona values ('lbrlrt91a19g273r','Alberto','Albertazzi','1991/01/19','M','Via Francia,4','3912543421','Menfi', 'albertazzi55@gmail.com'); -- medico
insert into Persona values ('vlncst95d52f839j','Cristina','Valenziano','1995/04/12','F','Via Caserta,11','3202690332','Caltanissetta', 'valenziano44@gmail.com'); -- medico
insert into Persona values ('rsocra87b23i089k','Chiara','Rosa','1987/02/23','F','Via dei fiori,76','3913652897','Mazara del Vallo', 'rosa23@virgilio.it'); -- medico
insert into Persona values ('cstvlr90f01k097i','Valerio','Castolo','1990/06/01','M','Via Caserta,11','3523698741','Pescara', 'valerio.castolo@.com'); -- medico
insert into Persona values ('cnadln83n12b082j','Dylan','Cane','1983/12/12','M','Via deserto,2','3470125896','Finale', 'Dylan.cane@gmail.com'); -- medico
insert into Persona values ('izorst72a01n034m','Aristo','Iezzo','1972/01/01','M','Via marco polo,49','3460894571','Scillato', 'Aristo999@gmail.com'); -- medico
insert into Persona values ('rstcrm88b11m089c','Carmen','Rosetta','1988/02/11','F','Via Febbraio,11','3916584321','Sciacca', 'Rosetta.carmen@libero.com'); -- medico
insert into Persona values ('rpigpp89l27b081f','Giuseppe','Ripe','1989/10/27','M','Via Garibaldi,18','3925684123','Palermo', 'GiuseppeRipe@yahoo.com'); -- medico
insert into Persona values ('rmnvlr77m29t032h','Valeria','Romano','1977/11/29','F','Via dello Stadio,4','3200500789','Cinisi', 'RomanoValeria@hotmail.it'); -- medico
insert into Persona values ('frgmrk91h28c061m','Marika','Frangia','1991/08/28','F','Via Marconi,18','3701245938','Terrasini', 'Marika91@gmail.com'); -- medico
insert into Persona values ('bncrgd62a31o081e','Ermenegildo','Bianchi','1962/01/31','M','Via Pascoli,9','3801256984','Canicatti', 'Banchiermene@hotmail.com'); -- medico
insert into Persona values ('rssmra87e05v065h','Mario','Rossi','1987/05/01','M','Via Monfenera,22','3914586213','Enna', 'Mariorossisempreio@libero.it'); -- medico
insert into Persona values ('gdiggg83i20z027h','Giada','Treggi','1983/09/20','F','Via Bellagamba,3','3333232000','Marsala', 'GiadaGGG@gmail.com'); -- medico
insert into Persona values ('prkgsp60n25e091v','Giuseppina','Parker','1960/12/25','M','Via Pasquale Natale,25','3452531997','Favara', 'GiusiParker@hotmail.com'); -- medico
insert into Persona values ('rdgcrs72c02u091c', 'Rodrigo', 'Cristiano', '1980/05/7', 'M', 'Via delle Palme, 2', '3287898666', 'Thiene', 'RodrigoCristiano72@gmail.com'); -- medico

-- UTENTE (RefCF, Password, RefGruppoPermessi, ConfermaSpedita)
DELETE FROM Utente;
insert into Utente values ('pregpp97m06l112b', 'leone', 3, 1);
insert into Utente values ('scldgi62e23a018p', 'qwerty', 1, 1);
insert into Utente values('dgrgrl98f12r091f', '1234', 1, 1);
insert into Utente values ('plnrld82c26d048x','abcd',2, 1);
insert into Utente values ('mrtfbz70i12f098p','bcad',2, 1);
insert into Utente values ('sccnna90d01c092m','cadb',2, 1);
insert into Utente values ('vltmra85a01b076j','dbac',2, 1);
insert into Utente values ('mnzgpp75l30c074e','dcba',2, 1);
insert into Utente values('mrzctt86m20e091f','ciao',2, 1);
insert into Utente values('brtfrc72b44g273z','lollo',2, 1);
insert into Utente values('znasdk93e05g273n', 'toxic',2, 1);
insert into Utente values('lbrlrt91a19g273r', 'kappa',2, 1);
insert into Utente values('vlncst95d52f839j', 'cirro',2, 1);
insert into Utente values('rsocra87b23i089k', '1234',2, 1);
insert into Utente values('cstvlr90f01k097i', '2341',2, 1);
insert into Utente values('cnadln83n12b082j', '4321',2, 1);
insert into Utente values('izorst72a01n034m', '3421',2, 1);
insert into Utente values('rstcrm88b11m089c', '2143',2, 1);
insert into Utente values('rpigpp89l27b081f', '3124',2, 1);
insert into Utente values('rmnvlr77m29t032h', '1423',2, 1);
insert into Utente values('frgmrk91h28c061m', '1324',2, 1);
insert into Utente values('rdgcrs72c02u091c', '4231',2, 1);
insert into Utente values('bncrgd62a31o081e', '5678',2, 1);
insert into Utente values('rssmra87e05v065h', '8765',2, 1);
insert into Utente values('gdiggg83i20z027h', '7541',2, 1);
insert into Utente values('prkgsp60n25e091v', '0000',2, 1);


-- DOTTORE (RefCFDottore, Specializzazione)
DELETE FROM Dottore;
-- cardiologi
insert into dottore values ('mnzgpp75l30c074e','Cardiologo');
insert into dottore values ('mrzctt86m20e091f','Cardiologo'); 
insert into dottore values('cnadln83n12b082j','Cardiologo');  
insert into dottore values('rssmra87e05v065h','Cardiologo'); 
-- oculisti
insert into dottore values ('vltmra85a01b076j','Oculista'); 
insert into dottore values ('brtfrc72b44g273z','Oculista'); 
insert into dottore values('izorst72a01n034m','Oculista');
insert into dottore values ('bncrgd62a31o081e','Oculista');
-- fisioterapisti
insert into dottore values ('sccnna90d01c092m','Fisioterapista');
insert into dottore values ('znasdk93e05g273n','Fisioterapista');
insert into dottore values('rstcrm88b11m089c','Fisioterapista');
insert into dottore values('prkgsp60n25e091v','Fisioterapista');
-- neurologi
insert into dottore values ('mrtfbz70i12f098p','Neurologo');
insert into dottore values ('lbrlrt91a19g273r','Neurologo');
insert into dottore values('rpigpp89l27b081f','Neurologo');
insert into dottore values('rdgcrs72c02u091c','Neurologo');
-- urologi
insert into dottore values ('plnrld82c26d048x','Urologo');
insert into dottore values ('vlncst95d52f839j','Urologo');
insert into dottore values('rmnvlr77m29t032h','Urologo');
insert into dottore values('rsocra87b23i089k','Urologo'); 
-- gastroenterologi
insert into dottore values('cstvlr90f01k097i','Gastroenteorologo');
insert into dottore values('frgmrk91h28c061m','Gastroenteorologo');
insert into dottore values('gdiggg83i20z027h','Gastroenteorologo');


-- REPARTO (CodReparto, NomeReparto, N_PostiLetto)
DELETE FROM reparto;
insert into reparto values (01,'Oculistica',8);
insert into reparto values (02,'Cardiologia',15);
insert into reparto values (03,'Neurologia',20);
insert into reparto values (04,'Gastroenterologia',13);
insert into reparto values (05,'Fisioterapia',10);
insert into reparto values (06,'Urologia',12);
insert into reparto values(07, 'Otorinolaringolatria', 09);
-- insert into reparto values(08, 'Anestesiologia', 05);
-- insert into reparto values(09, 'Cardiochirurgia', 10);
-- insert into reparto values(10, 'Chirurgia', 07);
-- insert into reparto values(11, 'Dermatologia', 03);

-- TURNI REPARTO (RefCFDottre, RefReparto, GiornoSetttimana)
DELETE FROM turnoreparto;
-- oculistica
insert into turnoreparto values ('vltmra85a01b076j',01,'lunedi');
insert into turnoreparto values ('vltmra85a01b076j',01,'martedi');
insert into turnoreparto values ('vltmra85a01b076j',01,'venerdi');
insert into turnoreparto values ('brtfrc72b44g273z',01,'mercoledi');
insert into turnoreparto values ('brtfrc72b44g273z',01,'giovedi');
insert into turnoreparto values ('brtfrc72b44g273z',01,'venerdi');
insert into turnoreparto values ('vltmra85a01b076j',01,'sabato');
insert into turnoreparto values ('brtfrc72b44g273z',01,'domenica');
-- cardiologia
insert into turnoreparto values ('mnzgpp75l30c074e',02,'mercoledi');
insert into turnoreparto values ('mrzctt86m20e091f',02,'giovedi');
insert into turnoreparto values ('mnzgpp75l30c074e',02,'venerdi');
insert into turnoreparto values ('mrzctt86m20e091f',02,'lunedi');
insert into turnoreparto values ('mnzgpp75l30c074e',02,'martedi');
insert into turnoreparto values ('mrzctt86m20e091f',02,'sabato');
insert into turnoreparto values ('mnzgpp75l30c074e',02,'domenica');
-- neurologia
insert into turnoreparto values ('mrtfbz70i12f098p',03,'giovedi');
insert into turnoreparto values ('lbrlrt91a19g273r',03,'lunedi');
insert into turnoreparto values ('lbrlrt91a19g273r',03,'martedi');
insert into turnoreparto values ('mrtfbz70i12f098p',03,'mercoledi');
insert into turnoreparto values ('mrtfbz70i12f098p',03,'sabato');
insert into turnoreparto values ('mrtfbz70i12f098p',03,'venerdi');
insert into turnoreparto values ('lbrlrt91a19g273r',03,'domenica');
-- gastroenterologia
insert into turnoreparto values ('frgmrk91h28c061m',04,'lunedi');
insert into turnoreparto values ('cstvlr90f01k097i',04,'martedi');
insert into turnoreparto values ('cstvlr90f01k097i',04,'mercoledi');
insert into turnoreparto values ('cstvlr90f01k097i',04,'giovedi');
insert into turnoreparto values ('frgmrk91h28c061m',04,'venerdi');
insert into turnoreparto values ('frgmrk91h28c061m',04,'sabato');
insert into turnoreparto values ('frgmrk91h28c061m',04,'domenica');
-- fisioterapia
insert into turnoreparto values ('sccnna90d01c092m',05,'lunedi');
insert into turnoreparto values ('sccnna90d01c092m',05,'martedi');
insert into turnoreparto values ('znasdk93e05g273n',05,'mercoledi');
insert into turnoreparto values ('znasdk93e05g273n',05,'giovedi');
insert into turnoreparto values ('znasdk93e05g273n',05,'venerdi');
insert into turnoreparto values ('sccnna90d01c092m',05,'sabato');
insert into turnoreparto values ('znasdk93e05g273n',05,'domenica');
-- urologia
insert into turnoreparto values ('vlncst95d52f839j',06,'lunedi');
insert into turnoreparto values ('plnrld82c26d048x',06,'martedi');
insert into turnoreparto values ('vlncst95d52f839j',06,'mercoledi');
insert into turnoreparto values ('plnrld82c26d048x',06,'giovedi');
insert into turnoreparto values ('plnrld82c26d048x',06,'venerdi');
insert into turnoreparto values ('plnrld82c26d048x',06,'sabato');
insert into turnoreparto values ('vlncst95d52f839j',06,'domenica');

-- AMBULATORIO (CodAmbulatorio, NomeAmbulatorio, RefReparto)
DELETE FROM ambulatorio;
insert into ambulatorio values ('O1','Oculistica1',01);
insert into ambulatorio values ('O2','Oculistica2',01);
insert into ambulatorio values ('C1','Cardiologia1',02);
insert into ambulatorio values ('C2','Cardiologia2',02);
insert into ambulatorio values ('N1','Neurologia1',03);
insert into ambulatorio values ('N2','Neurologia2',03);
insert into ambulatorio values ('G1','Gastroenterologia1',04);
insert into ambulatorio values ('F1','Fisioterapia1',05);
insert into ambulatorio values ('F2','Fisioterapia2',05);
insert into ambulatorio values ('U1','Urologia1',06);
insert into ambulatorio values ('U2','Urologia2',06);
-- nuovi ambulatori
-- inserimento in ambulatorio
-- insert into ambulatorio values('A1', 'Anestesiologia1',07);
-- insert into ambulatorio values('A2', 'Anestesiologia2',07);
-- insert into ambulatorio values('A3', 'Anestesiologia3',07);
-- insert into ambulatorio values('AU1', 'Audiologia1',08);
-- insert into ambulatorio values('AU2', 'Audiologia2',08);
-- insert into ambulatorio values('C1', 'cardiochirurgia1',09);
-- insert into ambulatorio values('C2', 'cardiochirurgia2',09);
-- insert into ambulatorio values('CH1', 'chirurgia1',10); 
-- insert into ambulatorio values('CH2', 'chirurgia2',10);
-- insert into ambulatorio values('AN1', 'andrologia1',11);
-- insert into ambulatorio values('AN2', 'andrologia2',11);

-- TurnoAmbulatorio
DELETE FROM turnoambulatorio;
-- oculistica
insert into turnoambulatorio values('izorst72a01n034m','O1', 'lunedi');
insert into turnoambulatorio values('bncrgd62a31o081e','O2', 'lunedi');
insert into turnoambulatorio values('izorst72a01n034m','O1', 'martedi');
insert into turnoambulatorio values('bncrgd62a31o081e','O2', 'martedi');
insert into turnoambulatorio values('izorst72a01n034m','O1', 'mercoledi');
insert into turnoambulatorio values('izorst72a01n034m','O1', 'giovedi');
insert into turnoambulatorio values('bncrgd62a31o081e','O1', 'venerdi');
insert into turnoambulatorio values('bncrgd62a31o081e','O1', 'sabato');
-- cardiologia
insert into turnoambulatorio values('cnadln83n12b082j','C1', 'lunedi');
insert into turnoambulatorio values('rssmra87e05v065h','C2', 'lunedi');
insert into turnoambulatorio values('cnadln83n12b082j','C1', 'martedi');
insert into turnoambulatorio values('rssmra87e05v065h','C1', 'mercoledi');
insert into turnoambulatorio values('cnadln83n12b082j','C2', 'mercoledi');
insert into turnoambulatorio values('rssmra87e05v065h','C1', 'giovedi');
insert into turnoambulatorio values('cnadln83n12b082j','C1', 'venerdi');
insert into turnoambulatorio values('rssmra87e05v065h','C1', 'sabato');
-- neurologia
insert into turnoambulatorio values('rdgcrs72c02u091c','N1', 'lunedi');
insert into turnoambulatorio values('rpigpp89l27b081f','N2', 'martedi');
insert into turnoambulatorio values('rdgcrs72c02u091c','N1', 'martedi');
insert into turnoambulatorio values('rpigpp89l27b081f','N2', 'mercoledi');
insert into turnoambulatorio values('rdgcrs72c02u091c','N1', 'mercoledi');
insert into turnoambulatorio values('rpigpp89l27b081f','N1', 'giovedi');
insert into turnoambulatorio values('rdgcrs72c02u091c','N1', 'venerdi');
insert into turnoambulatorio values('rpigpp89l27b081f','N1', 'sabato');
-- gastroenterologia
insert into turnoambulatorio values('gdiggg83i20z027h','G1', 'lunedi');
insert into turnoambulatorio values('gdiggg83i20z027h','G1', 'martedi');
insert into turnoambulatorio values('gdiggg83i20z027h','G1', 'mercoledi');
insert into turnoambulatorio values('gdiggg83i20z027h','G1', 'giovedi');
insert into turnoambulatorio values('gdiggg83i20z027h','G1', 'venerdi');
insert into turnoambulatorio values('gdiggg83i20z027h','G1', 'sabato');
-- fisioterapia
insert into turnoambulatorio values('rstcrm88b11m089c','F1', 'lunedi');  
insert into turnoambulatorio values('prkgsp60n25e091v','F2', 'lunedi');
insert into turnoambulatorio values('rstcrm88b11m089c','F1', 'martedi');
insert into turnoambulatorio values('prkgsp60n25e091v','F1', 'mercoledi');
insert into turnoambulatorio values('rstcrm88b11m089c','F1', 'giovedi');
insert into turnoambulatorio values('prkgsp60n25e091v','F1', 'venerdi');
insert into turnoambulatorio values('rstcrm88b11m089c','F2', 'venerdi');
insert into turnoambulatorio values('prkgsp60n25e091v','F1', 'sabato');
-- urologia
insert into turnoambulatorio values('rmnvlr77m29t032h','U1', 'lunedi');
insert into turnoambulatorio values('rmnvlr77m29t032h','U1', 'martedi');
insert into turnoambulatorio values('rsocra87b23i089k','U2', 'martedi');
insert into turnoambulatorio values('rmnvlr77m29t032h','U1', 'mercoledi');
insert into turnoambulatorio values('rsocra87b23i089k','U1', 'giovedi');
insert into turnoambulatorio values('rmnvlr77m29t032h','U2', 'giovedi');
insert into turnoambulatorio values('rsocra87b23i089k','U1', 'venerdi');
insert into turnoambulatorio values('rmnvlr77m29t032h','U1', 'sabato');

-- TIPO(CodTipo, NomeTipo, RefReparto)
DELETE FROM tipo;
insert into tipo values (001,'cardiologica',02);
insert into tipo values (002,'oculistica',01);
insert into tipo values (003,'fisioterapeutica',05);
insert into tipo values (004,'neurologica',03);
insert into tipo values (005,'gastroenterlogica',04);
insert into tipo values(006,'urologica',06);
insert into tipo values(007,'otorinolaringolatrica', 07);
-- insert into tipo values(008,'cardiochirurgica');
-- insert into tipo values(009, 'chirurgica');
-- insert into tipo values(010,'andrologica');

-- DOCUMENTO (CodDocumento, NomeDocuemto)
DELETE FROM documento;
insert into documento values ('01', 'raggi');
insert into documento values ('02', 'ecografia');
insert into documento values('03', 'TAC');

-- RICHIEDE (RefTipo, RefDocumento)
DELETE FROM richiede;
insert into richiede values (002, '01');
insert into richiede values(001, '02');
insert into richiede values(004, '03');

-- RICETTA(CodNRE, Priorita, RefTipo, DataEmissione)
DELETE FROM ricetta;
insert into ricetta values ('050A00381647906','P',002, '2019-02-01');
insert into ricetta values ('070X00569513458','U',001, '2019-01-15');
insert into ricetta values ('010B00486523346','D',005, '2019-01-01');
insert into ricetta values ('030H00846963515','B',003, '2019-01-18');
insert into ricetta values ('020D00369645156','D',005, '2019-02-02');
insert into ricetta values ('080C34652235789','P',002, '2019-01-21');
insert into ricetta values('060B00678987654','P',001, '2019-02-04');
insert into ricetta values('080D00674583254','P',004, '2019-02-14'); 
insert into ricetta values('070E00974487432','U',005, '2019-01-07');
insert into ricetta values('010Z00673318854','B',003, '2019-01-23');
insert into ricetta values('030F00428939653','D',002, '2019-01-27');
insert into ricetta values('090E00328961619','P',003, '2019-01-30');
insert into ricetta values('070S00672287617','U',004, '2019-02-12');
insert into ricetta values('060B00528987654','P',003, '2019-02-04');
insert into ricetta values('020X00218933671','D',005, '2019-02-10');
insert into ricetta values('030W00118678622','P',001, '2019-01-25');


-- VISITA(RefNRE, RefCFPaziente, Data, OraInizio, OraFine, RefAmbulatorio, RefCFDottore, RemindSent, NotificationSent)
DELETE FROM visita;
insert into Visita values ('070X00569513458','ppippr99b02e041m','2019/01/09','10:00','10:30','C2', 'cnadln83n12b082j', 1, 1);
insert into Visita values ('050A00381647906','scldgi62e23a018p','2019/02/05','15:00','15:30','O1', 'izorst72a01n034m', 1, 1);
insert into Visita values ('010B00486523346','dgrgrl98f12r091f','2019/03/05','15:00','15:30','G1', null, 0, 1);
insert into Visita values ('020D00369645156','nblrcc80i10t014h','2019/03/07','17:00','17:30','G1', null, 0, 1);
insert into Visita values ('030H00846963515','scldgi62e23a018p','2019/03/08','15:00','15:30','G1', null, 0, 1);

-- REFFERTO(RefVisita, descrizione)
DELETE FROM referto;
insert into referto values('070X00569513458', "il paziente si deve sottoporre a una cura di anticoagulanti del sangue");
insert into referto values('050A00381647906', "il paziente deve subire un intervento");





