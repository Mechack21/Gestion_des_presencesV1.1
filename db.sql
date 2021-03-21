/*
SQLyog Community v12.4.3 (32 bit)
MySQL - 5.6.11-log : Database - db_presence
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_presence` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_presence`;

/*Table structure for table `t_agent` */

DROP TABLE IF EXISTS `t_agent`;

CREATE TABLE `t_agent` (
  `matricule` varchar(10) NOT NULL,
  `nom` varchar(20) DEFAULT NULL,
  `postnom` varchar(20) DEFAULT NULL,
  `prenom` varchar(15) DEFAULT NULL,
  `sexe` enum('M','F') DEFAULT NULL,
  `telephone` int(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `Photo` longblob,
  `idgrade` varchar(20) DEFAULT NULL,
  `iddiv` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`matricule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_agent` */

/*Table structure for table `t_direction` */

DROP TABLE IF EXISTS `t_direction`;

CREATE TABLE `t_direction` (
  `iddirect` varchar(20) NOT NULL,
  `libdirect` text,
  PRIMARY KEY (`iddirect`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_direction` */

/*Table structure for table `t_division` */

DROP TABLE IF EXISTS `t_division`;

CREATE TABLE `t_division` (
  `iddiv` varchar(20) NOT NULL,
  `libdiv` text,
  `iddirect` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`iddiv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_division` */

/*Table structure for table `t_grade` */

DROP TABLE IF EXISTS `t_grade`;

CREATE TABLE `t_grade` (
  `idgrade` varchar(20) NOT NULL,
  `libgrade` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idgrade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_grade` */

insert  into `t_grade`(`idgrade`,`libgrade`) values 
('G3','GRADUE');

/*Table structure for table `t_presences` */

DROP TABLE IF EXISTS `t_presences`;

CREATE TABLE `t_presences` (
  `idpresence` int(11) NOT NULL AUTO_INCREMENT,
  `datepres` date DEFAULT NULL,
  `HeurePres` time DEFAULT NULL,
  `Type` enum('Arrivé','Depart') DEFAULT NULL,
  `matricule` varchar(10) DEFAULT NULL,
  `observation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idpresence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_presences` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
