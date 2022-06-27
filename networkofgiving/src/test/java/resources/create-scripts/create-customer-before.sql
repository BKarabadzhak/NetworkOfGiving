delete from customers;

insert into customers(id, age, balance, gender, location, name, password, username) values
(1, 20, 99300, 'Male', 'Bulgaria', 'Ivan', '$2a$10$qvX.D/Rzu8mmjqAKmcW1Hurg68XNqd0rvmqjOFYydWMbXON.i0t1C', 'Ivan');

alter sequence hibernate_sequence restart with 20;