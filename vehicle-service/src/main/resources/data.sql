-- otkomentarisati deletove ako testirate u lokalu
-- Pre pakovanja u .jar, obavezno zakomentarisati sve delete komande iz svih .sql datoteka
-- delete from rent_request_ads;
-- delete from rent_request;
-- delete from rate;
-- delete from ad;
-- delete from vehicle;
-- delete from model_type;
-- delete from brand_type;
-- delete from body_type;
-- delete from gearbox_type;
-- delete from fuel_type;

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

-- Vehicle
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (1, 1, 0, 0, 50000, 4, 3, 1, 2, 2);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (2, 0, 0, 0, 70000, 3, 3, 1, 3, 2);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (3, 1, 3, 0, 100000, 1, 2, 2, 4, 1);
insert into vehicle(id, cdw, child_seats, deleted, mileage, body_type_id, fuel_type_id, gearbox_type_id, model_type_id, owner_id)
values (4, 0, 4, 0, 90000, 1, 3, 1, 3, 1);