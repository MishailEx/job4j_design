create table students(
     id serial primary key,
     name varchar(255)
 );
 
 create table lessons(
     id serial primary key,
     name varchar(255)
 );
 
 create table students_lessons(
     id serial primary key,
     student_id int references students(id),
     lesson_id int references lessons(id)
 );