--delete from fuel_type;
--delete from body_type;
--delete from gearbox_type;
--delete from model_type;
--delete from brand_type;
--
--delete from bundled_requests;
--delete from location;
--delete from price_list;
delete from rent_request;


--insert into brand_type(id, deleted, name)
--values (100, false, 'Mazda');
----
--insert into model_type(id, deleted, name, brand_type_id)
--values (100, false, '6', 100);
--
--insert into body_type(id, deleted, name)
--values (100, false, 'Sport Coupe');
--
--insert into fuel_type(id, deleted, name)
--values (100, false, 'Electric');
--
--insert into gearbox_type(id, deleted, name)
--values (100, false, 'Semi-Automatic');
--
--insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
--values(100, false, 20, 520, 20, 3, 1);
--
--insert into location(id, country, city)
--values (100, 'Serbia', 'Novi Sad');
--
--insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
--values (100, 1, 0, 0, 50000, 100, 100, 100, 100, 2);
--
--insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
--values(100, 'Bulevar oslobodjenja 100', '2020-05-27 00:00:00.000000', '2020-07-16 00:00:00.000000', 0, 1, null, 0, '2020-05-27 17:20:12.039000', 100, 100, 100);
--
--
insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (100, '2020-05-29 00:00:00.000000', 1, 'RESERVED', '2020-06-15 00:00:00.000000', 100, null, null);