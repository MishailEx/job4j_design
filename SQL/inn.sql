create table inn(
     id serial primary key,
     number int
 );

 create table person(
     id serial primary key,
     name varchar(255),
	 inn_id int references inn(id),
 );
