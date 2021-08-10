SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


DROP TABLE IF EXISTS `ANTENNA`;
CREATE TABLE `ANTENNA` (
  `id` int(10) UNSIGNED NOT NULL,
  `sector_number` tinyint(3) UNSIGNED NOT NULL,
  `azimuth` smallint(6) NOT NULL,
  `reference` varchar(30) NOT NULL,
  `manufacturer` varchar(20) NOT NULL,
  `is_installed` tinyint(3) UNSIGNED NOT NULL DEFAULT '1',
  `hba` float(8,3) NOT NULL,
  `site_code` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ANTENNA_TILT`;
CREATE TABLE `ANTENNA_TILT` (
  `antenna_id` int(10) UNSIGNED NOT NULL,
  `system_id` tinyint(4) NOT NULL,
  `tilt` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `CARRIER`;
CREATE TABLE `CARRIER` (
  `id` tinyint(11) NOT NULL,
  `name` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `CARRIER` (`id`, `name`) VALUES
(1, 'SFR'),
(2, 'BYT'),
(3, 'ORF'),
(4, 'FRM');

DROP TABLE IF EXISTS `CELL_2G`;
CREATE TABLE `CELL_2G` (
  `id` int(10) UNSIGNED NOT NULL,
  `num_ci` mediumint(8) UNSIGNED NOT NULL,
  `lac` smallint(5) UNSIGNED NOT NULL,
  `rac` tinyint(3) UNSIGNED NOT NULL,
  `BCCH` smallint(5) UNSIGNED NOT NULL,
  `is_indoor` tinyint(3) NOT NULL,
  `frequency` float(6,1) NOT NULL,
  `pw` float(3,1) NOT NULL,
  `in_service` tinyint(3) UNSIGNED NOT NULL,
  `system_id` tinyint(4) NOT NULL,
  `carrier_id` tinyint(3) NOT NULL,
  `mcc` tinyint(3) UNSIGNED NOT NULL,
  `mnc` tinyint(3) UNSIGNED NOT NULL,
  `antenna_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `CELL_3G`;
CREATE TABLE `CELL_3G` (
  `id` int(10) UNSIGNED NOT NULL,
  `num_ci` mediumint(8) UNSIGNED NOT NULL,
  `lac` smallint(5) UNSIGNED NOT NULL,
  `rac` tinyint(3) UNSIGNED NOT NULL,
  `scrambling_code` smallint(5) UNSIGNED NOT NULL,
  `is_indoor` tinyint(3) NOT NULL,
  `frequency` float(5,1) NOT NULL,
  `pw` float(3,1) NOT NULL,
  `in_service` tinyint(3) UNSIGNED NOT NULL,
  `system_id` tinyint(4) NOT NULL,
  `carrier_id` tinyint(3) NOT NULL,
  `mcc` tinyint(3) UNSIGNED NOT NULL,
  `mnc` tinyint(3) UNSIGNED NOT NULL,
  `antenna_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `CELL_4G`;
CREATE TABLE `CELL_4G` (
  `id` int(10) UNSIGNED NOT NULL,
  `eci` bigint(20) UNSIGNED NOT NULL,
  `tac` mediumint(8) UNSIGNED NOT NULL,
  `pci` smallint(5) UNSIGNED NOT NULL,
  `is_indoor` tinyint(3) UNSIGNED NOT NULL,
  `frequency` float(5,1) NOT NULL,
  `pw` float(3,1) NOT NULL,
  `in_service` tinyint(3) UNSIGNED NOT NULL,
  `system_id` tinyint(4) NOT NULL,
  `carrier_id` tinyint(3) NOT NULL,
  `mcc` tinyint(3) UNSIGNED NOT NULL,
  `mnc` tinyint(3) UNSIGNED NOT NULL,
  `antenna_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `CELL_5G`;
CREATE TABLE `CELL_5G` (
  `id` int(10) UNSIGNED NOT NULL,
  `eci` bigint(20) UNSIGNED NOT NULL,
  `tac` mediumint(8) UNSIGNED NOT NULL,
  `pci` smallint(5) UNSIGNED NOT NULL,
  `is_indoor` tinyint(3) UNSIGNED NOT NULL,
  `frequency` float(5,1) NOT NULL,
  `pw` float(3,1) NOT NULL,
  `in_service` tinyint(3) UNSIGNED NOT NULL,
  `system_id` tinyint(4) NOT NULL,
  `carrier_id` tinyint(3) NOT NULL,
  `mcc` tinyint(3) UNSIGNED NOT NULL,
  `mnc` tinyint(3) UNSIGNED NOT NULL,
  `antenna_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `SITE`;
CREATE TABLE `SITE` (
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `latitude` float(10,6) NOT NULL,
  `longitude` float(10,6) NOT NULL,
  `altitude` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `SYSTEM`;
CREATE TABLE `SYSTEM` (
  `id` tinyint(4) NOT NULL,
  `name` varchar(5) NOT NULL,
  `techno_id` tinyint(4) NOT NULL,
  `bande` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `SYSTEM` (`id`, `name`, `techno_id`, `bande`) VALUES
(1, 'G900', 1, 'GSM 900'),
(2, 'G1800', 1, 'GSM 1800'),
(3, 'U900', 2, 'WCDMA VIII 900'),
(4, 'U2100', 2, 'WCDMA I 2100'),
(5, 'L700', 3, 'LTE B28'),
(6, 'L800', 3, 'LTE B20'),
(7, 'L1800', 3, 'LTE B3'),
(8, 'L2100', 3, 'LTE B1'),
(9, 'L2600', 3, 'LTE B7'),
(10, 'L3500', 3, 'LTE B78'),
(11, 'N3500', 4, 'NR B78'),
(12, 'N1800', 4, 'NR B3'),
(13, 'N2100', 4, 'NR B1'),
(14, 'N2600', 4, 'NR B7'),
(15, 'U2200', 2, 'WCDMA I 2100');

DROP TABLE IF EXISTS `TECHNO`;
CREATE TABLE `TECHNO` (
  `id` tinyint(4) NOT NULL,
  `name` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `TECHNO` (`id`, `name`) VALUES
(1, '2G'),
(2, '3G'),
(3, '4G'),
(4, '5G');


ALTER TABLE `ANTENNA`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `antenna_UNIQUE` (`site_code`,`sector_number`,`azimuth`,`reference`,`manufacturer`,`hba`,`is_installed`),
  ADD KEY `fk_ANTENNA_SITE1_idx` (`site_code`);

ALTER TABLE `ANTENNA_TILT`
  ADD UNIQUE KEY `tilt_unique` (`antenna_id`,`system_id`),
  ADD KEY `fk_ANTENNA_TILT_ANTENNA1_idx` (`antenna_id`),
  ADD KEY `fk_ANTENNA_TILT_SYSTEM1_idx` (`system_id`);

ALTER TABLE `CARRIER`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `CELL_2G`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_CELL_ANTENNA1_idx` (`antenna_id`),
  ADD KEY `fk_CARRIER1` (`carrier_id`),
  ADD KEY `cell_identity` (`num_ci`,`lac`,`mcc`,`mnc`);

ALTER TABLE `CELL_3G`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_CELL_ANTENNA1_idx` (`antenna_id`),
  ADD KEY `fk_CARRIER1` (`carrier_id`),
  ADD KEY `fk_CELL_3G_SYSTEM1_idx` (`system_id`),
  ADD KEY `cell_identity` (`num_ci`,`lac`,`mcc`,`mnc`);

ALTER TABLE `CELL_4G`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_CELL_ANTENNA1_idx` (`antenna_id`),
  ADD KEY `fk_CARRIER1` (`carrier_id`),
  ADD KEY `fk_CELL_4G_SYSTEM1_idx` (`system_id`),
  ADD KEY `cell_identity` (`eci`,`tac`,`mcc`,`mnc`);

ALTER TABLE `CELL_5G`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_CELL_ANTENNA1_idx` (`antenna_id`),
  ADD KEY `fk_CARRIER1` (`carrier_id`),
  ADD KEY `fk_CELL_4G_SYSTEM1_idx` (`system_id`),
  ADD KEY `cell_identity` (`eci`,`tac`,`mcc`,`mnc`);

ALTER TABLE `SITE`
  ADD PRIMARY KEY (`code`);

ALTER TABLE `SYSTEM`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_SYSTEM_TECHNO1_idx` (`techno_id`);

ALTER TABLE `TECHNO`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `ANTENNA`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE `CARRIER`
  MODIFY `id` tinyint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `CELL_2G`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE `CELL_3G`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE `CELL_4G`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE `CELL_5G`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;


ALTER TABLE `ANTENNA`
  ADD CONSTRAINT `fk_ANTENNA_SITE1` FOREIGN KEY (`site_code`) REFERENCES `SITE` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ANTENNA_TILT`
  ADD CONSTRAINT `fk_ANTENNA_TILT_ANTENNA1` FOREIGN KEY (`antenna_id`) REFERENCES `ANTENNA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ANTENNA_TILT_SYSTEM1` FOREIGN KEY (`system_id`) REFERENCES `SYSTEM` (`id`) ON UPDATE CASCADE;

ALTER TABLE `CELL_3G`
  ADD CONSTRAINT `fk_CARRIER10` FOREIGN KEY (`carrier_id`) REFERENCES `CARRIER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_CELL_3G_SYSTEM1` FOREIGN KEY (`system_id`) REFERENCES `SYSTEM` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_CELL_ANTENNA10` FOREIGN KEY (`antenna_id`) REFERENCES `ANTENNA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `CELL_4G`
  ADD CONSTRAINT `fk_CARRIER1` FOREIGN KEY (`carrier_id`) REFERENCES `CARRIER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_CELL_4G_SYSTEM1` FOREIGN KEY (`system_id`) REFERENCES `SYSTEM` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_CELL_ANTENNA1` FOREIGN KEY (`antenna_id`) REFERENCES `ANTENNA` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `SYSTEM`
  ADD CONSTRAINT `fk_SYSTEM_TECHNO1` FOREIGN KEY (`techno_id`) REFERENCES `TECHNO` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
