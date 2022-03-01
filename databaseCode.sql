create database if not exists etoyshop;
use etoyshop;
drop table if exists lego;
create table lego (
	id int,
	name varchar(50),
	price float,
	qty int,
	primary key (id)
);
insert into lego values (1001, 'bowser''s castle boss battle expansion set', 65, 40);
insert into lego values (1002, 'Mario''s house & Yoshi expansion set', 65, 40);
insert into lego values (1003, 'King Boo and the Haunted Yard Expansion set', 70,40);
insert into lego values (1004, 'CHaracter packs', 45, 40);
insert into lego values (1005, 'Toad''s Treasure Hunt Expansion Set', 65, 40);
insert into lego values (1006, 'Guarded Fortress Expansion set',65, 40);
drop table if exists plushtoys;
create table plushtoys (
	id int,
	name varchar(50),
	price float,
	qty int,
	primary key (id)
);
insert into plushtoys values (2001, 'Bobbleton pig', 39, 30);
insert into plushtoys values (2002, 'Cozy Crew Whale', 27, 30);
insert into plushtoys values (2003, 'Peanut Panda', 25, 30);
insert into plushtoys values (2004, 'Rumpletum Unicorn', 45, 30);
insert into plushtoys values (2005, 'Wriggidig Ant', 25, 30);
insert into plushtoys values (2006, 'Rondle Elephant', 35, 30);
drop table if exists puzzles;
create table puzzles (
	id int,
	name varchar(50),
	price float,
	qty int,
	primary key (id)
);
insert into puzzles values (3001, 'Elephant', 18, 45);
insert into puzzles values (3002, 'Wooden rack', 24, 45);
insert into puzzles values (3003, 'Klenba', 36, 45);
insert into puzzles values (3004, 'Flower', 37, 45);
insert into puzzles values (3005, 'The Simpsons', 40 ,45);
drop table if exists toys;
create table toys (
	id int,
	category varchar(50),
	name varchar(50),
	price float,
	qty int,
	primary key (id)
);
insert into toys values (1001, 'lego', 'bowser''s castle boss battle expansion set', 65, 40);
insert into toys values (1002, 'lego', 'Mario''s house & Yoshi expansion set', 65, 40);
insert into toys values (1003, 'lego', 'King Boo and the Haunted Yard Expansion set', 70,40);
insert into toys values (1004, 'lego', 'CHaracter packs', 45, 40);
insert into toys values (1005, 'lego', 'Toad''s Treasure Hunt Expansion Set', 65, 40);
insert into toys values (1006, 'lego', 'Guarded Fortress Expansion set',65, 40);
insert into toys values (2001, 'plushtoy', 'Bobbleton pig', 39, 30);
insert into toys values (2002, 'plushtoy', 'Cozy Crew Whale', 27, 30);
insert into toys values (2003, 'plushtoy', 'Peanut Panda', 25, 30);
insert into toys values (2004, 'plushtoy', 'Rumpletum Unicorn', 45, 30);
insert into toys values (2005, 'plushtoy', 'Wriggidig Ant', 25, 30);
insert into toys values (2006, 'plushtoy', 'Rondle Elephant', 35, 30);
insert into toys values (3001, 'puzzle', 'Elephant', 18, 45);
insert into toys values (3002, 'puzzle', 'Wooden rack', 24, 45);
insert into toys values (3003, 'puzzle', 'Klenba', 36, 45);
insert into toys values (3004, 'puzzle', 'Flower', 37, 45);
insert into toys values (3005, 'puzzle', 'The Simpsons', 40 ,45);
drop table if exists order_records;
create table order_records(
	id int,
	qty_ordered int,
	primary key (id)
);