create table dump_orf_dev.CARRIER
(
    id   tinyint(11) auto_increment
        primary key,
    name varchar(3) not null
);

create table dump_orf_dev.CELL_2G
(
    id         int unsigned auto_increment
        primary key,
    num_ci     mediumint unsigned not null,
    lac        smallint unsigned  not null,
    rac        tinyint unsigned   not null,
    bcch       smallint unsigned  not null,
    is_indoor  tinyint(3)         not null,
    frequency  float(6, 1)        not null,
    pw         float(3, 1)        not null,
    in_service tinyint unsigned   not null,
    system_id  tinyint            not null,
    carrier_id tinyint(3)         not null,
    mcc        tinyint unsigned   not null,
    mnc        tinyint unsigned   not null,
    antenna_id int unsigned       not null
);

create index cell_identity
    on dump_orf_dev.CELL_2G (num_ci, lac, mcc, mnc);

create index fk_CARRIER1
    on dump_orf_dev.CELL_2G (carrier_id);

create index fk_CELL_ANTENNA1_idx
    on dump_orf_dev.CELL_2G (antenna_id);

create table dump_orf_dev.CELL_5G
(
    id         int unsigned auto_increment
        primary key,
    eci        bigint unsigned    not null,
    tac        mediumint unsigned not null,
    pci        smallint unsigned  not null,
    is_indoor  tinyint unsigned   not null,
    frequency  float(5, 1)        not null,
    pw         float(3, 1)        not null,
    in_service tinyint unsigned   not null,
    system_id  tinyint            not null,
    carrier_id tinyint(3)         not null,
    mcc        tinyint unsigned   not null,
    mnc        tinyint unsigned   not null,
    antenna_id int unsigned       not null
);

create index cell_identity
    on dump_orf_dev.CELL_5G (eci, tac, mcc, mnc);

create index fk_CARRIER1
    on dump_orf_dev.CELL_5G (carrier_id);

create index fk_CELL_4G_SYSTEM1_idx
    on dump_orf_dev.CELL_5G (system_id);

create index fk_CELL_ANTENNA1_idx
    on dump_orf_dev.CELL_5G (antenna_id);

create table dump_orf_dev.SITE
(
    id int not null primary key,
    code      varchar(10)  not null,
    name      varchar(50)  not null,
    latitude  float(10, 6) not null,
    longitude float(10, 6) not null,
    altitude  smallint     not null,
    is_prev    tinyint(1)   not null,
    constraint SITE_code_uindex
        unique (code)
);

create table dump_orf_dev.ANTENNA
(
    id            int unsigned auto_increment
        primary key,
    sector_number tinyint unsigned           not null,
    azimuth       smallint                   not null,
    reference     varchar(50)                not null,
    manufacturer  varchar(20)                not null,
    is_installed  tinyint unsigned default 1 not null,
    hba           float(8, 3)                not null,
    site_id     varchar(10)                not null,
    constraint antenna_UNIQUE
        unique (id, site_id, sector_number, azimuth, reference, manufacturer, hba, is_installed),
    constraint fk_ANTENNA_SITE1
        foreign key (site_id) references dump_orf_dev.SITE (id)
            on update cascade on delete cascade
);

create index fk_ANTENNA_SITE1_idx
    on dump_orf_dev.ANTENNA (site_code);

create table dump_orf_dev.TECHNO
(
    id   tinyint    not null
        primary key,
    name varchar(5) not null
);

create table dump_orf_dev.`SYSTEM`
(
    id        tinyint    not null
        primary key,
    name      varchar(5) not null,
    techno_id tinyint    not null,
    constraint fk_SYSTEM_TECHNO1
        foreign key (techno_id) references dump_orf_dev.TECHNO (id)
            on update cascade
);

create table dump_orf_dev.ANTENNA_TILT
(
    antenna_id int unsigned     not null,
    system_id  tinyint          not null,
    tilt       tinyint unsigned not null,
    constraint tilt_unique
        unique (antenna_id, system_id),
    constraint fk_ANTENNA_TILT_ANTENNA1
        foreign key (antenna_id) references dump_orf_dev.ANTENNA (id)
            on update cascade on delete cascade,
    constraint fk_ANTENNA_TILT_SYSTEM1
        foreign key (system_id) references dump_orf_dev.`SYSTEM` (id)
            on update cascade
);

create index fk_ANTENNA_TILT_ANTENNA1_idx
    on dump_orf_dev.ANTENNA_TILT (antenna_id);

create index fk_ANTENNA_TILT_SYSTEM1_idx
    on dump_orf_dev.ANTENNA_TILT (system_id);

create table dump_orf_dev.CELL_3G
(
    id              int unsigned auto_increment
        primary key,
    num_ci          mediumint unsigned not null,
    lac             smallint unsigned  not null,
    rac             tinyint unsigned   not null,
    scrambling_code smallint unsigned  not null,
    is_indoor       tinyint(3)         not null,
    frequency       float(5, 1)        not null,
    pw              float(3, 1)        not null,
    in_service      tinyint unsigned   not null,
    system_id       tinyint            not null,
    carrier_id      tinyint(3)         not null,
    mcc             tinyint unsigned   not null,
    mnc             tinyint unsigned   not null,
    antenna_id      int unsigned       not null,
    constraint fk_CARRIER10
        foreign key (carrier_id) references dump_orf_dev.CARRIER (id)
            on update cascade,
    constraint fk_CELL_3G_SYSTEM1
        foreign key (system_id) references dump_orf_dev.`SYSTEM` (id)
            on update cascade,
    constraint fk_CELL_ANTENNA10
        foreign key (antenna_id) references dump_orf_dev.ANTENNA (id)
            on update cascade on delete cascade
);

create index cell_identity
    on dump_orf_dev.CELL_3G (num_ci, lac, mcc, mnc);

create index fk_CARRIER1
    on dump_orf_dev.CELL_3G (carrier_id);

create index fk_CELL_3G_SYSTEM1_idx
    on dump_orf_dev.CELL_3G (system_id);

create index fk_CELL_ANTENNA1_idx
    on dump_orf_dev.CELL_3G (antenna_id);

create table dump_orf_dev.CELL_4G
(
    id         int unsigned auto_increment
        primary key,
    eci        bigint unsigned    not null,
    tac        mediumint unsigned not null,
    pci        smallint unsigned  not null,
    is_indoor  tinyint unsigned   not null,
    frequency  float(5, 1)        not null,
    pw         float(3, 1)        not null,
    in_service tinyint unsigned   not null,
    system_id  tinyint            not null,
    carrier_id tinyint(3)         not null,
    mcc        tinyint unsigned   not null,
    mnc        tinyint unsigned   not null,
    antenna_id int unsigned       not null,
    constraint fk_CARRIER1
        foreign key (carrier_id) references dump_orf_dev.CARRIER (id)
            on update cascade,
    constraint fk_CELL_4G_SYSTEM1
        foreign key (system_id) references dump_orf_dev.`SYSTEM` (id)
            on update cascade,
    constraint fk_CELL_ANTENNA1
        foreign key (antenna_id) references dump_orf_dev.ANTENNA (id)
            on update cascade on delete cascade
);

create index cell_identity
    on dump_orf_dev.CELL_4G (eci, tac, mcc, mnc);

create index fk_CELL_4G_SYSTEM1_idx
    on dump_orf_dev.CELL_4G (system_id);

create index fk_CELL_ANTENNA1_idx
    on dump_orf_dev.CELL_4G (antenna_id);

create index fk_SYSTEM_TECHNO1_idx
    on dump_orf_dev.`SYSTEM` (techno_id);

