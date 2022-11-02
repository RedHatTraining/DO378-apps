insert into Session (id, schedule) values ('s-1-1', 1);
insert into Session (id, schedule) values ('s-1-2', 2);
insert into Session (id, schedule) values ('s-2-1', 1);

insert into Speaker (id, uuid, name) values (nextval('hibernate_sequence'), 's-1-1', 'Emmanuel');
insert into Speaker (id, uuid, name) values (nextval('hibernate_sequence'), 's-1-2', 'Clement');
insert into Speaker (id, uuid, name) values (nextval('hibernate_sequence'), 's-1-3', 'Alex');
insert into Speaker (id, uuid, name) values (nextval('hibernate_sequence'), 's-1-4', 'Burr');

insert into Session_Speaker (Session_id, speakers_id) select 's-1-1', id from Speaker where name='Emmanuel' ;
insert into Session_Speaker (Session_id, speakers_id) select 's-1-2', id from Speaker where name='Burr' ;
insert into Session_Speaker (Session_id, speakers_id) select 's-2-1', id from Speaker where name='Alex' ;
