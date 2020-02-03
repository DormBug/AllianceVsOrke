DROP TABLE IF EXISTS `colony`;
CREATE TABLE  `colony` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Population_Count` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UK_laegx79yvhugpswosjvnky2vw` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Text` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UK_laegx79yvhugpswosjvnky2vt` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;