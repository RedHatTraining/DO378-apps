
insert into Associate (id, name)
 values (1, 'Jaime');
insert into Associate (id, name)
 values (2, 'Pablo');


insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Desk', '0','150.50', 1);
 
insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Online Learning', '1','75.00', 1);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Books', '0','50.00', 1);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Internet', '1','20.00', 1);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Phone', '0','15.00', 1);

 insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Bookshelf', '0','150.50', 1);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Printer Cartridges', '1','15.00', 2);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Online Learning', '0','50.00', 2);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Internet', '1','20.00', 2);

insert into Expense (uuid, id, name, paymentmethod, amount, associate_id)
 values (gen_random_uuid(), nextval('Expense_SEQ'), 'Phone', '0','15.00', 2);
