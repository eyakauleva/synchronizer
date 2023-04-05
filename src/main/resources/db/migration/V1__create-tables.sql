
create table events
(
    id varchar(100),
	name varchar(100) not null,
	description varchar(1000) not null,
	category varchar(100) not null,
	event_time timestamp not null,
	price numeric(10,2) not null,
	primary key (id)
);

create table tickets(
	id varchar(100),
	user_id bigint null,
	event_id varchar(100) not null,
	quantity int not null,
	price decimal(10, 2) not null,
	primary key (id),
	foreign key (event_id) references events (id) on update cascade on delete set null
);