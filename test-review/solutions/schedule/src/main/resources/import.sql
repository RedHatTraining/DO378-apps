insert into schedule (id,venue_id,date) values (101, 101,'2020-9-01');
alter sequence Schedule_SEQ restart with 102;
insert into schedule (id,venue_id,date) values (nextval('Schedule_SEQ'), 1,'2020-9-04');
insert into schedule (id,venue_id,date) values (nextval('Schedule_SEQ'), 1,'2020-9-05');
insert into schedule (id,venue_id,date) values (nextval('Schedule_SEQ'), 2,'2020-9-04');
