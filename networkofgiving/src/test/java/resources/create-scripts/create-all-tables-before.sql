delete from customer_charity_volunteers;
delete from customer_charity_donates;
delete from customer_charity;
delete from customers;
delete from charities;

insert into customers(id, age, balance, gender, location, name, password, username) values
(1, 20, 99300, 'Male', 'Bulgaria', 'Ivan', '$2a$10$qvX.D/Rzu8mmjqAKmcW1Hurg68XNqd0rvmqjOFYydWMbXON.i0t1C', 'Ivan'),
(2, 19, 99950, 'Male', 'Bulgaria', 'Boris', '$2a$10$qvX.D/Rzu8mmjqAKmcW1Hurg68XNqd0rvmqjOFYydWMbXON.i0t1C', 'Boris'),
(3, 21, 99400, 'Male', 'Bulgaria', 'Vasil', '$2a$10$qvX.D/Rzu8mmjqAKmcW1Hurg68XNqd0rvmqjOFYydWMbXON.i0t1C', 'Vasil');


insert into charities(id, description, donation_current, donation_required, image,
participants_current, participants_required, status, title) values
(4, 'Charity 1', 0, 1000, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
0, 10, 'IN_PROCCESS', 'Charity 1'),
(5, 'Charity 2', null, null, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
0, 5, 'IN_PROCCESS', 'Charity 2'),
(6, 'Charity 3', 0, 1000, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
null, null, 'IN_PROCCESS', 'Charity 3'),
(7, 'Charity 4', 250, 1000, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
1, 10, 'IN_PROCCESS', 'Charity 4'),
(8, 'Charity 5', 500, 1000, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
0, 10, 'IN_PROCCESS', 'Charity 5'),
(9, 'Charity 6', 600, 1250, 'd4a7d1df-d411-4baa-bbc8-62410abeb0aab2d16360-71de-404d-bedc-2b626943367c-euc1rfcxyaepwdy.jpeg',
1, 10, 'IN_PROCCESS', 'Charity 6');


insert into customer_charity(customer_id, charity_id) values
(1, 4),
(1, 6),
(1, 9),
(2, 5),
(3, 7),
(3, 8);


insert into customer_charity_donates (amount, charity_id, customer_id) values
(200, 7, 1),
(50, 7, 2),
(500, 8, 1),
(600, 9, 3);


insert into customer_charity_volunteers (customer_id, charity_id) values
(1, 7),
(2, 9);

-- alter sequence hibernate_sequence restart with 30;
