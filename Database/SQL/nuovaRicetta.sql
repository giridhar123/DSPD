/*
CodiceTipo		Nome Tipo
1						cardiologica
2						oculistica
3						fisioterapeutica
4						neutologica
5						gastoenterologica
6						urologica
7						otorinolaringolatrica
*/
SET @NRE  =  inserisciQuiNre;
SET @PRIORITA = inserisciQuiPriorita;
SET @REFTIPO = inserisciQuiIlCodiceDelTipo;
SET @DATAEMISSIONE =inserisciQuiLaDataDiEmissione;

INSERT INTO ricetta
values
(@NRE, @PRIORITA, @REFTIPO, @DATAEMISSIONE);