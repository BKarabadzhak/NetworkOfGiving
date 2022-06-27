delete from customer_charity;
delete from customers;
delete from charities;

insert into customers(id, age, balance, gender, location, name, password, username) values
(1, 20, 99300, 'Male', 'Bulgaria', 'Ivan', '$2a$10$qvX.D/Rzu8mmjqAKmcW1Hurg68XNqd0rvmqjOFYydWMbXON.i0t1C', 'Ivan');


insert into charities(id, description, donation_current, donation_required, image,
participants_current, participants_required, status, title) values
(2, 'Charity 1', 250, 1000, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
1, 10, 'IN_PROCCESS', 'Charity 1');


insert into customer_charity(customer_id, charity_id) values (1, 2);


alter sequence hibernate_sequence restart with 20;