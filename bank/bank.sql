create database bank;
use  bank;

create table registerCutomer(
username varchar(25) ,
pass varchar(20)not null,
mob varchar(20) not null,
bname varchar(20),
ifsc varchar(10),
acc_no long ,
balance double);

 alter table registerCutomer change acc_no acc_no int auto_increment primary key;
 insert into registerCutomer(acc_no,pass,mob)values(5632349,"","");
 
select * from registerCutomer;
select * from account;
select * from transaction;

create table account(
date date,
acc_no int ,
balance double,
foreign key(acc_no) references registerCutomer (acc_no));


create table transaction(
date varchar(20),
acc_no int,
balance double,
cr_dr varchar(10),
foreign key(acc_no) references registerCutomer (acc_no));



alter table account change date date varchar(20);