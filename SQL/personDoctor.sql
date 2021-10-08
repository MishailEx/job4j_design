create table person(
     id serial primary key,
     name varchar(255)
 );

 create table doctor(
     id serial primary key,
     name varchar(255)
 );

 create table person_doctor(
     id serial primary key,
     person_id int references person(id),
     doctor_id int references doctor(id)
 ); 