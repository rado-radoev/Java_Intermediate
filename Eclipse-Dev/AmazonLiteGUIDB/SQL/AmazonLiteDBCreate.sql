SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema AmazonLite
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `AmazonLite` ;
CREATE SCHEMA IF NOT EXISTS `AmazonLite` DEFAULT CHARACTER SET utf8 ;
USE `AmazonLite` ;

-- -----------------------------------------------------
-- Table `AmazonLite`.`BOOK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AmazonLite`.`BOOK` ;

CREATE TABLE IF NOT EXISTS `AmazonLite`.`BOOK` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(45) NOT NULL,
  `AUTHOR` VARCHAR(45) NOT NULL,
  `LENGTH` DOUBLE(10,2) NOT NULL,
  `RELEASEDATE` VARCHAR(10) NULL DEFAULT NULL,
  `PUBLISHER` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AmazonLite`.`CD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AmazonLite`.`CD` ;

CREATE TABLE IF NOT EXISTS `AmazonLite`.`CD` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(45) NOT NULL,
  `AUTHOR` VARCHAR(45) NOT NULL,
  `LENGTH` DECIMAL(10,2) NOT NULL,
  `RELEASEDATE` VARCHAR(20) NULL DEFAULT NULL,
  `HITSINGLE` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AmazonLite`.`DVD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AmazonLite`.`DVD` ;

CREATE TABLE IF NOT EXISTS `AmazonLite`.`DVD` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `TITLE` VARCHAR(45) NOT NULL,
  `AUTHOR` VARCHAR(45) NOT NULL,
  `LENGTH` DECIMAL(10,2) NOT NULL,
  `RELEASEDATE` DATE NOT NULL,
  `BONUSSCENES` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
