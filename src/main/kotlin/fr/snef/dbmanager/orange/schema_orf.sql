
DROP FUNCTION IF EXISTS LAMBERT_IIe_TO_WGS84_LAT;
DROP FUNCTION IF EXISTS LAMBERT_IIe_TO_WGS84_LNG;

/*
' |---------------------------------------------------------------------------------------------------------------|
' | Const | 1 'Lambert I | 2 'Lambert II | 3 'Lambert III | 4 'Lambert IV | 5 'Lambert II Etendue | 6 'Lambert 93 |
' |-------|--------------|---------------|----------------|---------------|-----------------------|---------------|
' |    n  | 0.7604059656 |  0.7289686274 |   0.6959127966 | 0.6712679322  |    0.7289686274       |  0.7256077650 |
' |-------|--------------|---------------|----------------|---------------|-----------------------|---------------|
' |    c  | 11603796.98  |  11745793.39  |   11947992.52  | 12136281.99   |    11745793.39        |  11754255.426 |
' |-------|--------------|---------------|----------------|---------------|-----------------------|---------------|
' |    Xs |   600000.0   |    600000.0   |   600000.0     |      234.358  |    600000.0           |     700000.0  |
' |-------|--------------|---------------|----------------|---------------|-----------------------|---------------|
' |    Ys | 5657616.674  |  6199695.768  |   6791905.085  |  7239161.542  |    8199695.768        | 12655612.050  |
' |---------------------------------------------------------------------------------------------------------------|
*/
DELIMITER $$
CREATE FUNCTION LAMBERT_IIe_TO_WGS84_LAT(X FLOAT, Y FLOAT)
RETURNS DOUBLE
DETERMINISTIC
BEGIN
    DECLARE n DOUBLE;
    DECLARE c DOUBLE;
    DECLARE xs DOUBLE;
    DECLARE ys DOUBLE;
    DECLARE e DOUBLE;
    DECLARE epsilon DOUBLE;
    DECLARE gamma DOUBLE;
    DECLARE latitude DOUBLE;
    DECLARE r DOUBLE;
    DECLARE isometricLatitude DOUBLE;
    DECLARE phi0 DOUBLE;
    DECLARE prevPhi DOUBLE;
    DECLARE tmpPhi DOUBLE;

    SET n = 0.7289686274;
    SET c = 11745793.39;
    SET xs = 600000.0;
    SET ys = 8199695.768;
    SET e = 0.08248325676;
    SET epsilon = 10e-11;

	SET r = SQRT(POWER(X - xs, 2) + POWER(Y - ys, 2));
	SET isometricLatitude = (-1 / n) * LOG(ABS(r / c));

	SET phi0 = 2 * ATAN(EXP(isometricLatitude)) - PI() / 2.0;

	SET prevPhi = phi0;
	SET latitude = phi0;

	SET tmpPhi = latitude;
	SET latitude = 2.0 * ATAN(POWER((1 + e * SIN(prevPhi)) / (1 - e * SIN(prevPhi)), e / 2) * EXP(isometriclatitude)) - PI() / 2.0;
	SET prevPhi = tmpPhi;

    WHILE ABS(latitude - prevPhi) < epsilon DO
		SET tmpPhi = latitude;
		SET latitude = 2.0 * ATAN(POWER((1 + e * SIN(prevPhi)) / (1 - e * SIN(prevPhi)), e / 2) * EXP(isometriclatitude)) - PI() / 2.0;
		SET prevPhi = tmpPhi;
    END WHILE;

    RETURN latitude * 180.00 / PI();
END$$

CREATE FUNCTION LAMBERT_IIe_TO_WGS84_LNG(X FLOAT, Y FLOAT)
RETURNS DOUBLE
DETERMINISTIC
BEGIN
	DECLARE lambda0 DOUBLE;
    DECLARE n DOUBLE;
    DECLARE xs DOUBLE;
    DECLARE ys DOUBLE;
    DECLARE gamma DOUBLE;
    DECLARE longitude DOUBLE;

    SET lambda0 = 2.337229167 * PI() / 180.00;
    SET n = 0.7289686274;
    SET xs = 600000.0;
    SET ys = 8199695.768;

	SET gamma = ATAN((X - xs) / (ys - Y));
	SET longitude = lambda0 + (gamma / n);

    RETURN longitude * 180.00 / PI();
END$$
DELIMITER ;


CREATE TABLE CARRIER (
    id TINYINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(3) NOT NULL
);

INSERT INTO `CARRIER` (`id`, `name`) VALUES
(1, 'SFR'),
(2, 'BYT'),
(3, 'ORF'),
(4, 'FRM');

CREATE TABLE CELL_2G (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    num_ci MEDIUMINT UNSIGNED NOT NULL,
    lac SMALLINT UNSIGNED NOT NULL,
    rac TINYINT UNSIGNED NOT NULL,
    bcch SMALLINT UNSIGNED NOT NULL,
    is_indoor TINYINT NOT NULL,
    frequency FLOAT NOT NULL,
    pw FLOAT NOT NULL,
    in_service TINYINT UNSIGNED NOT NULL,
    system_id TINYINT NOT NULL,
    carrier_id TINYINT NOT NULL,
    mcc TINYINT UNSIGNED NOT NULL,
    mnc TINYINT UNSIGNED NOT NULL,
    antenna_id INT UNSIGNED NOT NULL
);

create index cell_identity on CELL_2G (num_ci, lac, mcc, mnc);
create index fk_CARRIER1 on CELL_2G (carrier_id);
create index fk_CELL_ANTENNA1_idx on CELL_2G (antenna_id);

CREATE TABLE CELL_5G (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    eci BIGINT UNSIGNED NOT NULL,
    tac MEDIUMINT UNSIGNED NOT NULL,
    pci SMALLINT UNSIGNED NOT NULL,
    is_indoor TINYINT UNSIGNED NOT NULL,
    frequency FLOAT NOT NULL,
    pw FLOAT NOT NULL,
    in_service TINYINT UNSIGNED NOT NULL,
    system_id TINYINT NOT NULL,
    carrier_id TINYINT NOT NULL,
    mcc TINYINT UNSIGNED NOT NULL,
    mnc TINYINT UNSIGNED NOT NULL,
    antenna_id INT UNSIGNED NOT NULL
);

create index cell_identity on CELL_5G (eci, tac, mcc, mnc);
create index fk_CARRIER1 on CELL_5G (carrier_id);
create index fk_CELL_4G_SYSTEM1_idx on CELL_5G (system_id);
create index fk_CELL_ANTENNA1_idx on CELL_5G (antenna_id);

CREATE TABLE SITE (
    id INT NOT NULL PRIMARY KEY,
    code VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    altitude MEDIUMINT NOT NULL,
    is_prev TINYINT NOT NULL,
    CONSTRAINT SITE_code_uindex UNIQUE (code)
);

CREATE TABLE ANTENNA (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_orf INT NOT NULL,
    sector_number TINYINT UNSIGNED NOT NULL,
    azimuth SMALLINT NOT NULL,
    reference VARCHAR(50) NOT NULL,
    manufacturer VARCHAR(20) NOT NULL,
    is_installed TINYINT UNSIGNED DEFAULT 1 NOT NULL,
    hba FLOAT NOT NULL,
    site_id INT NOT NULL,
    CONSTRAINT antenna_UNIQUE UNIQUE (id , eqpt_id, site_id , sector_number , azimuth , reference , manufacturer , hba , is_installed),
    CONSTRAINT fk_ANTENNA_SITE1 FOREIGN KEY (site_id)
        REFERENCES SITE (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

create index fk_ANTENNA_SITE1_idx on ANTENNA (site_id);

CREATE TABLE TECHNO (
    id TINYINT NOT NULL PRIMARY KEY,
    name VARCHAR(5) NOT NULL
);

INSERT INTO `TECHNO` (`id`, `name`) VALUES
(1, '2G'),
(2, '3G'),
(3, '4G'),
(4, '5G');

CREATE TABLE `SYSTEM` (
    id TINYINT NOT NULL PRIMARY KEY,
    name VARCHAR(5) NOT NULL,
    techno_id TINYINT NOT NULL,
    CONSTRAINT fk_SYSTEM_TECHNO1 FOREIGN KEY (techno_id)
        REFERENCES TECHNO (id)
        ON UPDATE CASCADE
);

INSERT INTO `SYSTEM` (`id`, `name`, `techno_id`) VALUES
(1, 'G900', 1),
(2, 'G1800', 1),
(3, 'U900', 2),
(4, 'U2100', 2),
(5, 'L700', 3),
(6, 'L800', 3),
(7, 'L1800', 3),
(8, 'L2100', 3),
(9, 'L2600', 3),
(10, 'L3500', 3),
(11, 'N3500', 4),
(12, 'N1800', 4),
(13, 'N2100', 4),
(14, 'N2600', 4),
(15, 'U2200', 2);

CREATE TABLE ANTENNA_TILT (
    antenna_id INT UNSIGNED NOT NULL,
    system_id TINYINT NOT NULL,
    tilt SMALLINT NOT NULL,
    CONSTRAINT tilt_unique UNIQUE (antenna_id , system_id),
    CONSTRAINT fk_ANTENNA_TILT_ANTENNA1 FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_ANTENNA_TILT_SYSTEM1 FOREIGN KEY (system_id)
        REFERENCES `SYSTEM` (id)
        ON UPDATE CASCADE
);

create index fk_ANTENNA_TILT_ANTENNA1_idx on ANTENNA_TILT (antenna_id);

create index fk_ANTENNA_TILT_SYSTEM1_idx on ANTENNA_TILT (system_id);

CREATE TABLE CELL_3G (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    num_ci MEDIUMINT UNSIGNED NOT NULL,
    lac SMALLINT UNSIGNED NOT NULL,
    rac TINYINT UNSIGNED NOT NULL,
    scrambling_code SMALLINT UNSIGNED NOT NULL,
    is_indoor TINYINT NOT NULL,
    frequency FLOAT NOT NULL,
    pw FLOAT NOT NULL,
    in_service TINYINT UNSIGNED NOT NULL,
    system_id TINYINT NOT NULL,
    carrier_id TINYINT NOT NULL,
    mcc TINYINT UNSIGNED NOT NULL,
    mnc TINYINT UNSIGNED NOT NULL,
    antenna_id INT UNSIGNED NOT NULL,
    CONSTRAINT fk_CARRIER10 FOREIGN KEY (carrier_id)
        REFERENCES CARRIER (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_3G_SYSTEM1 FOREIGN KEY (system_id)
        REFERENCES `SYSTEM` (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_ANTENNA10 FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

create index cell_identity on CELL_3G (num_ci, lac, mcc, mnc);
create index fk_CARRIER1 on CELL_3G (carrier_id);
create index fk_CELL_3G_SYSTEM1_idx on CELL_3G (system_id);
create index fk_CELL_ANTENNA1_idx on CELL_3G (antenna_id);

CREATE TABLE CELL_4G (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    eci BIGINT UNSIGNED NOT NULL,
    tac MEDIUMINT UNSIGNED NOT NULL,
    pci SMALLINT UNSIGNED NOT NULL,
    is_indoor TINYINT UNSIGNED NOT NULL,
    frequency FLOAT NOT NULL,
    pw FLOAT NOT NULL,
    in_service TINYINT UNSIGNED NOT NULL,
    system_id TINYINT NOT NULL,
    carrier_id TINYINT NOT NULL,
    mcc TINYINT UNSIGNED NOT NULL,
    mnc TINYINT UNSIGNED NOT NULL,
    antenna_id INT UNSIGNED NOT NULL,
    CONSTRAINT fk_CARRIER1 FOREIGN KEY (carrier_id)
        REFERENCES CARRIER (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_4G_SYSTEM1 FOREIGN KEY (system_id)
        REFERENCES `SYSTEM` (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_ANTENNA1 FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

create index cell_identity on CELL_4G (eci, tac, mcc, mnc);
create index fk_CELL_4G_SYSTEM1_idx on CELL_4G (system_id);
create index fk_CELL_ANTENNA1_idx on CELL_4G (antenna_id);
create index fk_SYSTEM_TECHNO1_idx on `SYSTEM` (techno_id);

