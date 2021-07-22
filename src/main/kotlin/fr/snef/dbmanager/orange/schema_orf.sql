
DROP FUNCTION IF EXISTS LAMBERT_IIe_TO_WGS84_LAT;
DROP FUNCTION IF EXISTS LAMBERT_IIe_TO_WGS84_LNG;

DROP TABLE IF EXISTS CELL_2G;
DROP TABLE IF EXISTS CELL_3G;
DROP TABLE IF EXISTS CELL_4G;
DROP TABLE IF EXISTS CELL_5G;
DROP TABLE IF EXISTS ANTENNA_TILT;
DROP TABLE IF EXISTS ANTENNA;
DROP TABLE IF EXISTS SITE;
DROP TABLE IF EXISTS CARRIER;
DROP TABLE IF EXISTS TECHNO;
DROP TABLE IF EXISTS SYSTEM;

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
CREATE FUNCTION LAMBERT_IIe_TO_WGS84_LAT(XLambert FLOAT, YLambert FLOAT)
RETURNS DOUBLE
DETERMINISTIC
BEGIN
	DECLARE a FLOAT;
	DECLARE f FLOAT;
	DECLARE b FLOAT;
	DECLARE e FLOAT;
	DECLARE he FLOAT;
	DECLARE n FLOAT;
	DECLARE c FLOAT;
	DECLARE Xs FLOAT;
	DECLARE Ys FloAT;
	DECLARE LambdaC FLOAT;
	DECLARE Lambda FLOAT;
	DECLARE Gamma FLOAT;
	DECLARE Phi FLOAT;
	DECLARE NN FLOAT;
	DECLARE NHeCosPhi FLOAT;
	DECLARE X FLOAT;
	DECLARE Y FLOAT;
	DECLARE Z FLOAT;
	DECLARE FX FLOAT;
	DECLARE FY FLOAT;
	DECLARE FZ FLOAT;
	DECLARE D FLOAT;
	DECLARE Rx FLOAT;
	DECLARE Ry FLOAT;
	DECLARE Rz FLOAT;
	DECLARE Tx FLOAT;
	DECLARE Ty FLOAT;

	DECLARE Tz FLOAT;
	DECLARE Dp1 FLOAT;
	DECLARE R2 FLOAT;
	DECLARE R3 FLOAT;
	DECLARE ae2 FLOAT;
	DECLARE Phi0 FLOAT;
	DECLARE Phi1 FLOAT;
	DECLARE R FLOAT;
	DECLARE L FLOAT;

	DECLARE ExpLi FLOAT;
	DECLARE Phiim1 FLOAT;
	DECLARE ESinP FLOAT;
	DECLARE Miaou FLOAT;
	DECLARE Phiip1 FLOAT;

	SET a = 6378249.2;
	SET f = 1 / 293.466021;
	SET b = a * (1 - f);
	SET e = SQRT((a * a - b * b) / (a * a));

	SET he = 0.0;

	SET n = 0.7289686274;
	SET c = 11745793.39;
	SET Xs = 600000.0;
	SET Ys = 8199695.768;

	SET LambdaC = 2.337229167 * PI() / 180.0;
	SET Gamma = ATAN ((XLambert - Xs) / (Ys - YLambert));
	SET Lambda = LambdaC + Gamma / n;

	SET R = SQRT((POWER(XLambert - Xs, 2) + POWER(YLambert - Ys, 2)));
	SET L = ( -1.0 / n) * LOG(ABS(R / c));

	SET ExpLi = EXP(L);
	SET Phiim1 = 2.0 * ATAN(ExpLi) - PI() / 2.0;
	SET ESinP = e * SIN(Phiim1);
	SET Miaou = (1.0 +ESinP) / (1.0 - ESinP);
	SET Miaou = POWER (Miaou, e / 2);
	SET Phi = 2.0 * ATAN(Miaou * ExpLi) - PI() / 2.0;

	WHILE(ABS(Phi - Phiim1)>=0.0000000001) DO
		SET ESinP = e * SIN(Phi);
		SET Miaou = (1.0 + ESinP) / (1.0 - ESinP);
		SET Miaou = POWER(Miaou, e / 2.0);
		SET Phiip1 = 2.0 * ATAN((Miaou) * ExpLi) - PI() / 2.0;
		SET Phiim1 = Phi;
		SET Phi = Phiip1;
	END WHILE;

	SET NN= a / SQRT(1.0 - POWER(e * SIN(Phi),2.0));

	SET NHeCosPhi = (NN +he) * COS(Phi);
	SET X = NHeCosPhi * COS(Lambda);
	SET Y = NHeCosPhi * SIN(Lambda);
	SET Z = (NN * (1.0 - e * e) +he) * SIN(Phi);

	SET D = 0.0;
	SET Rx = 0.0;
	SET Ry= 0.0;
	SET Rz = 0.0;
	SET Tx = -168.0;
	SET Ty = -060.0;
	SET Tz =  320.0;

	SET Dp1 = 1.0 + D;
	SET FX = Tx +X * Dp1 +Z * Ry - Y * Rz;
	SET FY =Ty +Y * Dp1 +X * Rz - Z * Rx;
	SET FZ =Tz +Z * Dp1 +Y * Rx - X * Ry;

	SET a = 6378137.0;
	SET f = 1.0 / 298.257223563;
	SET b = a * (1.0 - f);
	SET e = SQRT((a * a - b * b) / (a * a));

	SET Lambda = ATAN(FY / FX);
	SET R2 = SQRT((FX * FX) +(FY * FY));
	SET R3 = SQRT((R2 * R2) +(FZ * FZ));
	SET ae2 = a * e * e;

	SET Phi0 = ATAN(FZ / (R2 * (1.0 - ae2 / R3)));
	SET Phi1 = ATAN((FZ / R2) * 1.0 / (1.0 - ae2 * COS(Phi0) / (R2 * SQRT(1.0 - (e * SIN(Phi0)) * (e * SIN(Phi0))))));

	WHILE(ABS(Phi1 - Phi0)>0.0000000001) DO
		SET Phi1 = ATAN((FZ / R2) * 1 / (1 - ae2 * COS(Phi0) / (R2 * SQRT(1 - (e * SIN(Phi0)) * (e * SIN(Phi0))))));
		SET Phi0 = Phi1;
	END WHILE;

	SET Phi = Phi1;

	RETURN Phi * 180.0 / PI();
END$$

DELIMITER $$
CREATE FUNCTION LAMBERT_IIe_TO_WGS84_LNG(XLambert FLOAT, YLambert FLOAT)
RETURNS DOUBLE
DETERMINISTIC
BEGIN
	DECLARE a FLOAT;
	DECLARE f FLOAT;
	DECLARE b FLOAT;
	DECLARE e FLOAT;
	DECLARE he FLOAT;
	DECLARE n FLOAT;
	DECLARE c FLOAT;
	DECLARE Xs FLOAT;
	DECLARE Ys FloAT;
	DECLARE LambdaC FLOAT;
	DECLARE Lambda FLOAT;
	DECLARE Gamma FLOAT;
	DECLARE Phi FLOAT;
	DECLARE NN FLOAT;
	DECLARE NHeCosPhi FLOAT;
	DECLARE X FLOAT;
	DECLARE Y FLOAT;
	DECLARE Z FLOAT;
	DECLARE FX FLOAT;
	DECLARE FY FLOAT;
	DECLARE FZ FLOAT;
	DECLARE D FLOAT;
	DECLARE Rx FLOAT;
	DECLARE Ry FLOAT;
	DECLARE Rz FLOAT;
	DECLARE Tx FLOAT;
	DECLARE Ty FLOAT;

	DECLARE Tz FLOAT;
	DECLARE Dp1 FLOAT;
	DECLARE R2 FLOAT;
	DECLARE R3 FLOAT;
	DECLARE ae2 FLOAT;
	DECLARE Phi0 FLOAT;
	DECLARE Phi1 FLOAT;
	DECLARE R FLOAT;
	DECLARE L FLOAT;

	DECLARE ExpLi FLOAT;
	DECLARE Phiim1 FLOAT;
	DECLARE ESinP FLOAT;
	DECLARE Miaou FLOAT;
	DECLARE Phiip1 FLOAT;

	SET a = 6378249.2;
	SET f = 1 / 293.466021;
	SET b = a * (1 - f);
	SET e = SQRT((a * a - b * b) / (a * a));

	SET he = 0.0;

	SET n = 0.7289686274;
	SET c = 11745793.39;
	SET Xs = 600000.0;
	SET Ys = 8199695.768;

	SET LambdaC = 2.337229167 * PI() / 180.0;
	SET Gamma = ATAN ((XLambert - Xs) / (Ys - YLambert));
	SET Lambda = LambdaC + Gamma / n;

	SET R = SQRT((POWER(XLambert - Xs, 2) + POWER(YLambert - Ys, 2)));
	SET L = ( -1.0 / n) * LOG(ABS(R / c));

	SET ExpLi = EXP(L);
	SET Phiim1 = 2.0 * ATAN(ExpLi) - PI() / 2.0;
	SET ESinP = e * SIN(Phiim1);
	SET Miaou = (1.0 +ESinP) / (1.0 - ESinP);
	SET Miaou = POWER (Miaou, e / 2);
	SET Phi = 2.0 * ATAN(Miaou * ExpLi) - PI() / 2.0;

	WHILE(ABS(Phi - Phiim1)>=0.0000000001) DO
		SET ESinP = e * SIN(Phi);
		SET Miaou = (1.0 + ESinP) / (1.0 - ESinP);
		SET Miaou = POWER(Miaou, e / 2.0);
		SET Phiip1 = 2.0 * ATAN((Miaou) * ExpLi) - PI() / 2.0;
		SET Phiim1 = Phi;
		SET Phi = Phiip1;
	END WHILE;

	SET NN= a / SQRT(1.0 - POWER(e * SIN(Phi),2.0));

	SET NHeCosPhi = (NN +he) * COS(Phi);
	SET X = NHeCosPhi * COS(Lambda);
	SET Y = NHeCosPhi * SIN(Lambda);
	SET Z = (NN * (1.0 - e * e) +he) * SIN(Phi);

	SET D = 0.0;
	SET Rx = 0.0;
	SET Ry= 0.0;
	SET Rz = 0.0;
	SET Tx = -168.0;
	SET Ty = -060.0;
	SET Tz =  320.0;

	SET Dp1 = 1.0 + D;
	SET FX = Tx +X * Dp1 +Z * Ry - Y * Rz;
	SET FY =Ty +Y * Dp1 +X * Rz - Z * Rx;
	SET FZ =Tz +Z * Dp1 +Y * Rx - X * Ry;

	SET a = 6378137.0;
	SET f = 1.0 / 298.257223563;
	SET b = a * (1.0 - f);
	SET e = SQRT((a * a - b * b) / (a * a));

	SET Lambda = ATAN(FY / FX);
	SET R2 = SQRT((FX * FX) +(FY * FY));
	SET R3 = SQRT((R2 * R2) +(FZ * FZ));
	SET ae2 = a * e * e;

	SET Phi0 = ATAN(FZ / (R2 * (1.0 - ae2 / R3)));
	SET Phi1 = ATAN((FZ / R2) * 1.0 / (1.0 - ae2 * COS(Phi0) / (R2 * SQRT(1.0 - (e * SIN(Phi0)) * (e * SIN(Phi0))))));

	WHILE(ABS(Phi1 - Phi0)>0.0000000001) DO
		SET Phi1 = ATAN((FZ / R2) * 1 / (1 - ae2 * COS(Phi0) / (R2 * SQRT(1 - (e * SIN(Phi0)) * (e * SIN(Phi0))))));
		SET Phi0 = Phi1;
	END WHILE;

	SET Phi = Phi1;

	RETURN Lambda * 180.00 / PI();
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
    lac MEDIUMINT UNSIGNED NOT NULL,
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
    antenna_id INT UNSIGNED NULL,
    site_id INT NOT NULL,
    CONSTRAINT fk_CELL_ANTENNA_2G FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CELL_SITE_2G FOREIGN KEY (site_id)
        REFERENCES SITE (id)
        ON UPDATE CASCADE ON DELETE CASCADE
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
    antenna_id INT UNSIGNED NULL,
    site_id INT NOT NULL,
    CONSTRAINT fk_CELL_ANTENNA_5G FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CELL_SITE_5G FOREIGN KEY (site_id)
        REFERENCES SITE (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

create index cell_identity on CELL_5G (eci, tac, mcc, mnc);
create index fk_CARRIER1 on CELL_5G (carrier_id);
create index fk_CELL_4G_SYSTEM1_idx on CELL_5G (system_id);
create index fk_CELL_ANTENNA1_idx on CELL_5G (antenna_id);

CREATE TABLE SITE (
    id INT NOT NULL PRIMARY KEY,
    id_orf INT NOT NULL,
    code VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    altitude MEDIUMINT NOT NULL,
    is_prev TINYINT NOT NULL,
    CONSTRAINT SITE_code_uindex UNIQUE (code)
);

create index id_orf_idx on SITE (id_orf);

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
    CONSTRAINT antenna_UNIQUE UNIQUE (id , id_orf, site_id , sector_number , azimuth , reference , manufacturer , hba , is_installed),
    CONSTRAINT fk_ANTENNA_SITE1 FOREIGN KEY (site_id)
        REFERENCES SITE (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

create index fk_ANTENNA_SITE1_idx on ANTENNA (site_id);
create index index4 on ANTENNA (id_orf, sector_number, azimuth, hba);

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
    lac MEDIUMINT UNSIGNED NOT NULL,
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
    antenna_id INT UNSIGNED NULL,
    site_id INT NOT NULL,
    CONSTRAINT fk_CARRIER10 FOREIGN KEY (carrier_id)
        REFERENCES CARRIER (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_3G_SYSTEM1 FOREIGN KEY (system_id)
        REFERENCES `SYSTEM` (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_ANTENNA_3G FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CELL_SITE_3G FOREIGN KEY (site_id)
        REFERENCES SITE (id)
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
    antenna_id INT UNSIGNED NULL,
    site_id INT NOT NULL,
    CONSTRAINT fk_CARRIER1 FOREIGN KEY (carrier_id)
        REFERENCES CARRIER (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_4G_SYSTEM1 FOREIGN KEY (system_id)
        REFERENCES `SYSTEM` (id)
        ON UPDATE CASCADE,
    CONSTRAINT fk_CELL_ANTENNA_4G FOREIGN KEY (antenna_id)
        REFERENCES ANTENNA (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CELL_SITE_4G FOREIGN KEY (site_id)
        REFERENCES SITE (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

create index cell_identity on CELL_4G (eci, tac, mcc, mnc);
create index fk_CELL_4G_SYSTEM1_idx on CELL_4G (system_id);
create index fk_CELL_ANTENNA1_idx on CELL_4G (antenna_id);
create index fk_SYSTEM_TECHNO1_idx on `SYSTEM` (techno_id);

