create table cars(
id serial primary key,
name varchar(255),
colour text,
doors integer);
insert into cars (name, colour, doors) values ('honda', 'white', 4);
update cars set name = 'mazda';
delete from cars;
select * from cars;