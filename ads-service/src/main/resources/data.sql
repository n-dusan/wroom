delete from rent_request_ads;
delete from rent_request;
delete from rate;
delete from ad;

insert into brand_type(id, deleted, name)
values (1, false, 'Audi');
insert into brand_type(id, deleted, name)
values (2, false, 'Škoda');
insert into brand_type(id, deleted, name)
values (3, false, 'Opel');
insert into brand_type(id, deleted, name)
values (4, false, 'Volkswagen');

insert into model_type(id, deleted, name, brand_type_id)
values (1, false, 'R8', 1);
insert into model_type(id, deleted, name, brand_type_id)
values (2, false, 'Octavia', 2);
insert into model_type(id, deleted, name, brand_type_id)
values (3, false, 'Astra', 3);
insert into model_type(id, deleted, name, brand_type_id)
values (4, false, 'Passat', 4);

insert into body_type(id, deleted, name)
values (1, false, 'Sedan');
insert into body_type(id, deleted, name)
values (2, false, 'Wagon');
insert into body_type(id, deleted, name)
values (3, false, 'Hatchback');
insert into body_type(id, deleted, name)
values (4, false, 'Coupe');
insert into body_type(id, deleted, name)
values (5, false, 'Pickup');
insert into body_type(id, deleted, name)
values (6, false, 'Van');

insert into fuel_type(id, deleted, name)
values (1, false, 'Diesel');
insert into fuel_type(id, deleted, name)
values (2, false, 'Gasoline');
insert into fuel_type(id, deleted, name)
values (3, false, 'Petrol');

insert into gearbox_type(id, deleted, name)
values (1, false, 'Manual');
insert into gearbox_type(id, deleted, name)
values (2, false, 'Automatic');
--------------------------------------------

-- Vehicle
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (1, 1, 0, 0, 50000, 4, 3, 1, 2, 2);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (2, 0, 0, 0, 70000, 3, 3, 1, 3, 2);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (3, 1, 3, 0, 100000, 1, 2, 2, 4, 1);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (4, 0, 4, 0, 90000, 1, 3, 1, 3, 1);

-- Location
insert into location(id, country, city)
values (1, 'Arizona', 'Phoenix');
insert into location(id, country, city)
values (2, 'China', 'Wuhan');
insert into location(id, country, city)
values (3, 'Spain', 'Madrid');
insert into location(id, country, city)
values (4, 'Italy', 'Rome');

-- Price list
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(1, false, 20, 642, 23, 3, 1);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(2, false, 10, 435, 30, 5, 1);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(3, false, 25, 500, 35, 7, 1);

-- Ads
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(1, 'Via del Corso 15', '2020-05-29 00:00:00.000000', '2020-07-16 00:00:00.000000', 0, 1, null, 0, '2020-05-28 17:20:12.039000', 4, 1, 1);
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(2, 'Via del Corso 15', '2020-05-29 00:00:00.000000', '2020-07-25 00:00:00.000000', 0, 1, null, 0, '2020-05-28 17:20:12.039000', 4, 2, 2);

insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(3, 'Calle de Toledo 101', '2020-05-29 00:00:00.000000', '2020-07-25 00:00:00.000000', 0, 1, null, 0, '2020-04-28 17:20:12.039000', 2, 2, 3);
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(4, 'Calle de Toledo 101', '2020-05-29 00:00:00.000000', '2020-08-15 00:00:00.000000', 0, 1, null, 0, '2020-05-15 17:20:12.039000', 2, 3, 4);