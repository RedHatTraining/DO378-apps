
insert into Associate (id, name)
 values (1, 'Jaime');
insert into Associate (id, name)
 values (2, 'Pablo');


insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Desk', '0','150.50', 1);
insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Online Learning', '1','75.00', 1);

insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Books', '0','50.00', 1);

insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Internet', '1','20.00', 1);

insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Phone', '0','15.00', 1);

 insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Bookshelf', '0','150.50', 1);
insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Printer Cartridges', '1','15.00', 2);

insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Online Learning', '0','50.00', 2);

insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Internet', '1','20.00', 2);

insert into Expense (id, name, paymentmethod, amount, associate_id)
 values (nextval('expense_seq'), 'Phone', '0','15.00', 2);
