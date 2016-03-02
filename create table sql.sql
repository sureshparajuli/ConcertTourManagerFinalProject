use concert;

create table concert_tour(
id int PRIMARY KEY AUTO_INCREMENT,  
performer_name varchar(50), 
concert_datetime datetime, 
concert_venue varchar(50),
concert_city varchar(50),
total_budget decimal(50, 4),
total_wages_for_crew decimal(50,4), 
total_revenue decimal(50,4),
distance_travelled decimal(50,4));


insert into concert_tour (performer_name, concert_datetime, concert_venue, concert_city, total_budget
, total_wages_for_crew, total_revenue, distance_travelled)
values ('AC/DC', '2016-08-25', 'Wembly Stadium', 'London', 2000000, 1000000, 3000000, 15000);

select * from concert_tour