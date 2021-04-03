-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ospedale
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ospedale
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ospedale` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ospedale` ;

-- -----------------------------------------------------
-- Table `ospedale`.`reparto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`reparto` (
  `CodReparto` VARCHAR(45) NOT NULL,
  `NomeReparto` VARCHAR(45) NULL DEFAULT NULL,
  `N_PostiLetto` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`CodReparto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`ambulatorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`ambulatorio` (
  `CodAmbulatorio` VARCHAR(45) NOT NULL,
  `NomeAmbulatorio` VARCHAR(45) NULL DEFAULT NULL,
  `RefReparto` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`CodAmbulatorio`),
  INDEX `RefReparto_idx` (`RefReparto` ASC) VISIBLE,
  CONSTRAINT `RefReparto`
    FOREIGN KEY (`RefReparto`)
    REFERENCES `ospedale`.`reparto` (`CodReparto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`persona` (
  `CF` CHAR(16) NOT NULL,
  `Nome` VARCHAR(45) NULL DEFAULT NULL,
  `Cognome` VARCHAR(45) NULL DEFAULT NULL,
  `Data_Nascita` DATE NULL DEFAULT NULL,
  `Sesso` CHAR(1) NULL DEFAULT NULL,
  `Indirizzo_Residenza` VARCHAR(45) NULL DEFAULT NULL,
  `Telefono` CHAR(10) NULL DEFAULT NULL,
  `Citta` VARCHAR(45) NULL DEFAULT NULL,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`CF`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`gruppo_permessi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`gruppo_permessi` (
  `CodGruppo` INT(2) NOT NULL,
  `NomeGruppo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`CodGruppo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`utente` (
  `RefCF` CHAR(16) NOT NULL,
  `Password` VARCHAR(45) NULL DEFAULT NULL,
  `RefGruppoPermessi` INT(2) NULL DEFAULT NULL,
  `ConfermaSpedita` TINYINT(4) NULL DEFAULT '0',
  PRIMARY KEY (`RefCF`),
  INDEX `RefGruppoPermessi_idx` (`RefGruppoPermessi` ASC) VISIBLE,
  CONSTRAINT `RefCF`
    FOREIGN KEY (`RefCF`)
    REFERENCES `ospedale`.`persona` (`CF`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefGruppoPermessi`
    FOREIGN KEY (`RefGruppoPermessi`)
    REFERENCES `ospedale`.`gruppo_permessi` (`CodGruppo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`dottore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`dottore` (
  `RefCFDottore` CHAR(16) NOT NULL,
  `Specializzazione` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`RefCFDottore`),
  CONSTRAINT `RefCFDottore`
    FOREIGN KEY (`RefCFDottore`)
    REFERENCES `ospedale`.`utente` (`RefCF`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`tipo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`tipo` (
  `CodTipo` INT(11) NOT NULL,
  `NomeTipo` VARCHAR(45) NOT NULL,
  `RefReparto` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CodTipo`, `NomeTipo`),
  INDEX `RefReparto` (`RefReparto` ASC) VISIBLE,
  CONSTRAINT `tipo_ibfk_1`
    FOREIGN KEY (`RefReparto`)
    REFERENCES `ospedale`.`reparto` (`CodReparto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`ricetta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`ricetta` (
  `CodNRE` CHAR(15) NOT NULL,
  `Priorita` VARCHAR(45) NULL DEFAULT NULL,
  `RefTipo` INT(11) NULL DEFAULT NULL,
  `DataEmissione` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`CodNRE`),
  INDEX `RefTipo_idx` (`RefTipo` ASC) VISIBLE,
  CONSTRAINT `RefTipo`
    FOREIGN KEY (`RefTipo`)
    REFERENCES `ospedale`.`tipo` (`CodTipo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`visita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`visita` (
  `RefNRE` CHAR(15) NOT NULL,
  `RefCFPaziente` CHAR(16) NULL DEFAULT NULL,
  `Data` DATE NULL DEFAULT NULL,
  `OraInizio` TIME NULL DEFAULT NULL,
  `OraFine` TIME NULL DEFAULT NULL,
  `RefAmbulatorio` VARCHAR(45) NULL DEFAULT NULL,
  `RefCfDottore` VARCHAR(45) NULL DEFAULT NULL,
  `RemindSent` TINYINT(4) NULL DEFAULT '0',
  `NotificationSent` TINYINT(4) NULL DEFAULT '0',
  PRIMARY KEY (`RefNRE`),
  INDEX `RefCFPaziente_idx` (`RefCFPaziente` ASC) VISIBLE,
  INDEX `refAmbulatorio_idx` (`RefAmbulatorio` ASC) VISIBLE,
  INDEX `RefCfDottore1` (`RefCfDottore` ASC) VISIBLE,
  CONSTRAINT `RefAmbulatorio`
    FOREIGN KEY (`RefAmbulatorio`)
    REFERENCES `ospedale`.`ambulatorio` (`CodAmbulatorio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefCFPaziente`
    FOREIGN KEY (`RefCFPaziente`)
    REFERENCES `ospedale`.`persona` (`CF`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefCfDottore1`
    FOREIGN KEY (`RefCfDottore`)
    REFERENCES `ospedale`.`dottore` (`RefCFDottore`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefNRE`
    FOREIGN KEY (`RefNRE`)
    REFERENCES `ospedale`.`ricetta` (`CodNRE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`avvisoVisita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`avvisoVisita` (
  `RefNRE` CHAR(15) NOT NULL,
  `Avviso` VARCHAR(1000) NOT NULL,
  `Sent` TINYINT(4) NULL DEFAULT '0',
  PRIMARY KEY (`RefNRE`, `Avviso`),
  CONSTRAINT `RefNRE1`
    FOREIGN KEY (`RefNRE`)
    REFERENCES `ospedale`.`visita` (`RefNRE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`documento` (
  `CodDocumento` VARCHAR(45) NOT NULL,
  `NomeDocumento` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CodDocumento`, `NomeDocumento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`referto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`referto` (
  `RefVisita` CHAR(15) NOT NULL,
  `descrizione` VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (`RefVisita`),
  CONSTRAINT `RefVisita`
    FOREIGN KEY (`RefVisita`)
    REFERENCES `ospedale`.`visita` (`RefNRE`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`richiede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`richiede` (
  `RefTipo` INT(11) NOT NULL,
  `RefDocumento` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`RefTipo`, `RefDocumento`),
  INDEX `RefDocumento_idx` (`RefDocumento` ASC) VISIBLE,
  CONSTRAINT `RefDocumento`
    FOREIGN KEY (`RefDocumento`)
    REFERENCES `ospedale`.`documento` (`CodDocumento`),
  CONSTRAINT `RefTipo1`
    FOREIGN KEY (`RefTipo`)
    REFERENCES `ospedale`.`tipo` (`CodTipo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`ricovero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`ricovero` (
  `RefCFPaziente` CHAR(16) NOT NULL,
  `RefReparto` VARCHAR(45) NULL DEFAULT NULL,
  `dataInizio` DATE NOT NULL,
  `dataFine` DATE NULL DEFAULT NULL,
  `priorita` INT(5) NULL DEFAULT NULL,
  PRIMARY KEY (`RefCFPaziente`, `dataInizio`),
  INDEX `RefReparto_idx` (`RefReparto` ASC) VISIBLE,
  CONSTRAINT `RefCFPaziente1`
    FOREIGN KEY (`RefCFPaziente`)
    REFERENCES `ospedale`.`persona` (`CF`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefReparto2`
    FOREIGN KEY (`RefReparto`)
    REFERENCES `ospedale`.`reparto` (`CodReparto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`turnoambulatorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`turnoambulatorio` (
  `RefDottore` CHAR(16) NOT NULL,
  `RefAmbulatorio` VARCHAR(45) NULL DEFAULT NULL,
  `GiornoSettimana` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`RefDottore`, `GiornoSettimana`),
  INDEX `RefAmbulatorio1` (`RefAmbulatorio` ASC) VISIBLE,
  CONSTRAINT `RefAmbulatorio1`
    FOREIGN KEY (`RefAmbulatorio`)
    REFERENCES `ospedale`.`ambulatorio` (`CodAmbulatorio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefDottore`
    FOREIGN KEY (`RefDottore`)
    REFERENCES `ospedale`.`dottore` (`RefCFDottore`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ospedale`.`turnoreparto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ospedale`.`turnoreparto` (
  `RefCFDottore` CHAR(16) NOT NULL,
  `RefReparto` VARCHAR(45) NULL DEFAULT NULL,
  `GiornoSettimana` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`RefCFDottore`, `GiornoSettimana`),
  INDEX `RefReparto_idx` (`RefReparto` ASC) VISIBLE,
  CONSTRAINT `RefCFDottore2`
    FOREIGN KEY (`RefCFDottore`)
    REFERENCES `ospedale`.`dottore` (`RefCFDottore`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `RefReparto1`
    FOREIGN KEY (`RefReparto`)
    REFERENCES `ospedale`.`reparto` (`CodReparto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
