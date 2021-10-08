create table person(
    id serial primary key,
    name varchar(255),
    military_rank_id int references military_rank(id) unique
);
