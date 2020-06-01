-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
-- U create-drop rezimu, ukoliko sql skripta ponovo insertuje već unete vrednosti (MySQL ume da ne dropuje i redove unutar tabela)
-- onda dodajte delete na tabelu koju želite da očistite, time se brišu svi redovi iz te tabele
--------------------------------

delete from users;
delete from user_roles;
delete from permission;
delete from roles;
delete from role_permissions;
delete from ad;
delete from location;
delete from rent_request;
delete from rent_request_ads;
delete from vehicle;
delete from model_type;
delete from brand_type;
delete from gearbox_type;
delete from fuel_type;
delete from body_type;
delete from rate;

------------- ROLE -----------------
insert into roles(id, name)
values (1, 'ROLE_USER');
insert into roles(id, name)
values (2, 'ROLE_AGENT');
insert into roles(id, name)
values (3, 'ROLE_ADMIN');
insert into roles(id, name)
values (4, 'ROLE_BANNED_USER');

----------- PERMISIJE ----------------
insert into permission(id, name)
values (1, 'RENT');
insert into permission(id, name)
values (2, 'CHAT');
insert into permission(id, name)
values (3, 'RATE');
insert into permission(id, name)
values (4, 'RENT_HISTORY');
insert into permission(id, name)
values (5, 'POST_ADS');

------- Permisije obicnog korisnika -------
insert into role_permissions(role_id, permission_id)
values (1, 1);
insert into role_permissions(role_id, permission_id)
values (1, 2);
insert into role_permissions(role_id, permission_id)
values (1, 3);
insert into role_permissions(role_id, permission_id)
values (1, 4);
insert into role_permissions(role_id, permission_id)
values (1, 5);
------------------------------------------

------------- Permisije agenta -----------

------------------------------------------


------------ Permisije admina ------------

------------------------------------------


----- Permisije banovanog korisnika -----

------------------------------------------


------------------ KORISNICI ---------------------
-- sifre svih usera su <<user>>123, npr mila123
insert into users(id, email, name, password, surname, enabled, non_locked)
values (1, 'mila@maildrop.cc', 'Mila', '$2a$10$5EIPh4FLRCczl/.mnQhHP.8GmBJT2tL8kgBS1qDALVaybk6hh5o5.', 'Stojakovic', 1, 1);
insert into users(id, email, name, password, surname, enabled, non_locked)
values (2, 'zika@maildrop.cc', 'Zivorad', '$2a$10$ZECdbx5MAPP.a7YbmWBowOrub/B2r7FZlq9tvr3BTMInSuhnApUpy', 'Stamenkovic', 1, 1);
insert into users(id, email, name, password, surname, enabled, non_locked)
values (3, 'grga@maildrop.cc', 'Svetislav', '$2a$10$JOsaxc.iuu8Pw.Wlbdfci.dl7xP93fa7/i0tib6zjnEv.OLrIRP1i', 'Grgur', 1, 1);

-- mila user
insert into user_roles(user_id, role_id)
values (1, 1);

--zika agent
insert into user_roles(user_id, role_id)
values (2, 2);

--grga admin
insert into user_roles(user_id, role_id)
values (3, 3);

---------------Vehicle features-------------
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
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile)
values(1, false, 20, 642, 23, 3);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile)
values(2, false, 10, 435, 30, 5);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile)
values(3, false, 25, 500, 35, 7);

-- Vehicle
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (1, 1, 0, 0, 50000, 4, 3, 1, 2, 2);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (2, 0, 0, 0, 70000, 3, 3, 1, 3, 2);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (3, 1, 3, 0, 100000, 1, 2, 2, 4, 1);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (4, 0, 4, 0, 90000, 1, 3, 1, 3, 1);

-- Ads
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(1, 'Via del Corso 15', '2020-05-29 00:00:00.000000', '2020-07-16 00:00:00.000000', 0, 1, null, 0, '2020-05-28 17:20:12.039000', 4, 1, 1);
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(2, 'Via del Corso 15', '2020-05-29 00:00:00.000000', '2020-07-25 00:00:00.000000', 0, 1, null, 0, '2020-05-28 17:20:12.039000', 4, 2, 2);

insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(3, 'Calle de Toledo 101', '2020-05-29 00:00:00.000000', '2020-07-25 00:00:00.000000', 0, 1, null, 0, '2020-04-28 17:20:12.039000', 2, 2, 3);
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id)
values(4, 'Calle de Toledo 101', '2020-05-29 00:00:00.000000', '2020-08-15 00:00:00.000000', 0, 1, null, 0, '2020-05-15 17:20:12.039000', 2, 3, 4);


-- Rent Requests
insert into rent_request(id, from_date, status, to_date, rent_report_id, requested_user_id)
values(1, '2020-06-05 10:00:00.000000', 'PAID', '2020-06-15 00:00:00.000000', null, 1); --Opel Astra iz Wuhana
insert into rent_request_ads(rent_request_id, ads_id)
values(1, 4);

-- Rates
insert into rate(id, rating, ad_id)
values (1, 5, 1);
insert into rate(id, rating, ad_id)
values (2, 4, 1);
insert into rate(id, rating, ad_id)
values (3, 3, 2);
insert into rate(id, rating, ad_id)
values (4, 4, 2);
insert into rate(id, rating, ad_id)
values (5, 3, 3);