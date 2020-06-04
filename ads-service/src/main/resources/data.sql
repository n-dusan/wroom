delete from rent_request_ads;
delete from rent_request;
delete from rate;
delete from ad;

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