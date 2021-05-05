create sequence seq1 start 900;

create table villiansftw.account (
	accountid int4 default nextval('account_seq') not null,
	CONSTRAINT account_pk PRIMARY KEY (accountid)
);

ALTER TABLE villiansftw.account ADD customerid int4 NULL;
ALTER TABLE bank_schema.account ADD checkingid int4 NULL;

insert into employee(username,pw)
values ('employee1','password1');
select * from villiansftw.merchandise;

create table offer(
of_id serial primary key,
cust_id int4 references customer (customer_id),
merch_id int4 references merch (merch_id),
offer real check (offer > 0)
);

alter table offer rename column offer to customer_offer;

update offer set customer_offer = 100000.00
where of_id = 2;

insert into offer (cust_id,merch_id,customer_offer) values (1, 5, 100000);

delete from offer where of_id = 10000;

alter table merch add column status varchar default 'Not Owned';
alter table employee add column manager boolean default false;
alter table employee drop column manager;
insert into employee (username, pw, manager) values ('manager1', 'managerpw', true);
alter table employee add constraint uniquectm_const unique(username);
alter table employee add constraint uniquepw_const unique(pw);

create table bill(
bill_id serial primary key,
cust_id int4 references customer (customer_id), -- you can get names here
merch_id int4 references merch (merch_id), -- name of merch
of_id int4 references offer (of_id),-- you can get offer price
weekly_payments real not null check (weekly_payments > 0),
total_payments real
);

select c.customer_id, c.first_name, c.last_name, o.merch_id,  m.name, o.customer_offer from customer c
join offer o
on c.customer_id = o.cust_id 
join merch m
on o.merch_id = m.merch_id
where customer_id = 1;
-- This is telling I dont need to have customer names on the offer table, i dont need merch name on the offer table,
-- result is 1 steven nguyen 5 rock 100,000

insert into offer (customer_offer) values (2000);


insert into merch (name, price) values ('Crocodile Skin Umbrella', '50000');

select * from AMT.merch order by price desc;

INSERT into offer (cust_id, merch_id, customer_offer) values (1,5,5);

select o.merch_id, m.name, m.price,o.customer_offer from offer o join merch m on o.merch_id = m.merch_id order by merch_id ;
alter table offer add column review varchar not null default 'Under Review';

select of_id, merch_id, customer_offer, cust_id from offer;
update offer set review = 'Accepted' where of_id = 5;
delete from offer where of_id = '5';


---- inserting random data to customer
insert into customer (first_name, last_name, username, password) values ('Itch', 'Boast', 'iboast0', 'QKpmuHqchB');
insert into customer (first_name, last_name, username, password) values ('Kermit', 'Palomba', 'kpalomba1', 'x6ObGss');
insert into customer (first_name, last_name, username, password) values ('Bette', 'Jandourek', 'bjandourek2', 'WCG8IdTd2Gj1');
insert into customer (first_name, last_name, username, password) values ('Ginevra', 'Bodycomb', 'gbodycomb3', 'KP5XiRr3UZ7');
insert into customer (first_name, last_name, username, password) values ('Ericka', 'Hostan', 'ehostan4', 'dt569aj');
insert into customer (first_name, last_name, username, password) values ('Kanya', 'Gagg', 'kgagg5', 'vezGHff');
insert into customer (first_name, last_name, username, password) values ('Dru', 'Povey', 'dpovey6', 'wMwPK4Mzof8');
insert into customer (first_name, last_name, username, password) values ('Yetta', 'Belliss', 'ybelliss7', 'A04PJ5gzzu1E');
insert into customer (first_name, last_name, username, password) values ('Adolpho', 'Fallow', 'afallow8', 'QueOYqTzzoN');
insert into customer (first_name, last_name, username, password) values ('Vonnie', 'Matousek', 'vmatousek9', 'hr1w4iZrexbL');

alter table employee add column employee_name varchar;
insert into employee (name) values ('Steven') where employee_id = 1; 
alter table employee drop column name;
update employee set employee_name = 'Steven' where employee_id = 3;
insert into employee (username, pw,employee_name) values ('employee2', 'password2', 'Raymond');
insert into employee (username, pw,employee_name) values ('employee3', 'password3', 'Uri');
insert into employee (username, pw,employee_name) values ('employee4', 'password4', 'Robbie');

insert into merch (name, price) values ('Meat Looking Rock', 100000);
insert into merch (name, price) values ('Latex dalmatian suit', 1925);
insert into merch (name, price) values ('Painted Ceramic Coaster', 4000);
insert into merch (name, price) values ('Chrome Browser Pillow', 1000);
insert into merch (name, price) values ('Fork With Googly-Eyes', 34876);
delete from merch where merch_id = 5;

update offer set review = 'Accepted' where of_id = ?;
delete from offer where merch_id = 16 and review = 'Under Review';
insert into offer (cust_id, merch_id, customer_offer) values (4, 14, 1000);
insert into offer (cust_id, merch_id, customer_offer) values (6, 14, 1622);
insert into offer (cust_id, merch_id, customer_offer) values (7, 14, 1111);
insert into offer (cust_id, merch_id, customer_offer) values (8, 14, 2222);
delete from merch where merch_id = 5;
update offer set review = 'Under Review' where of_id = 9;

INSERT INTO AMT.bill (cust_id, merch_id, of_id, remaining_payments) values (1,11,1,599);
INSERT INTO AMT.bill (cust_id, merch_id, of_id, remaining_payments) values (1,12,2,1234);
delete from bill where bill_id = 4;

update bill set weekly_payments = 0, total_payments = (select remaining_payments from bill where cust_id = 1), remaining_payments = 0
where cust_id = 1;

select total_payments / 72 from bill where cust_id = 1;

select * from AMT.bill join AMT.merch on AMT.bill.merch_id = AMT.merch.merch_id join AMT.customer
on AMT.bill.cust_id = AMT.customer.customer_id 
join AMT.offer
on AMT.bill.of_id = AMT.offer.of_id where AMT.bill.cust_id = 1;

select AMT.merch.name from AMT.bill join AMT.merch on AMT.bill.merch_id = AMT.merch.merch_id where AMT.bill.cust_id = 1;
select * from bill;
select * from AMT.offer join AMT.customer on AMT.offer.cust_id = AMT.customer.customer_id where AMT.offer.cust_id = 5;
delete from offer where of_id = 18;

select manager FROM AMT.employee where username = 'manager2';

insert into employee(username,pw,employee_name) values ('davincki1','davcinckip','dav');
insert into employee (username,pw,employee_name) values ('davincki2','davcinckip2','dav2');
insert into employee (username,pw,employee_name) values ('davincki3','davcinckip3','dav3');
insert into employee (username,pw,employee_name) values ('davincki4','davcinckip4','dav4');
insert into employee (username,pw,employee_name) values ('davincki5','davcinckip5','dav5');
delete from merch where merch_id = 20;
delete from bill where bill_id =5;