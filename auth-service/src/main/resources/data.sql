
-- Pre pakovanja u .jar, obavezno zakomentarisati sve delete komande iz svih .sql datoteka
--------------------------------

-- delete from rent_request;
-- delete from ad;
-- delete from location;
-- delete from image;
-- delete from vehicle;
-- delete from model_type;
-- delete from brand_type;
-- delete from gearbox_type;
-- delete from fuel_type;
-- delete from body_type;
-- delete from rate;
--  delete from users;
--  delete from user_roles;

delete from permission;
delete from roles;
delete from role_permissions;
  
------------- ROLE -----------------
insert into roles(id, name)
values (1, 'ROLE_USER');
insert into roles(id, name)
values (2, 'ROLE_AGENT');
insert into roles(id, name)
values (3, 'ROLE_ADMIN');
insert into roles(id, name)
values (4, 'ROLE_CRUD_VEHICLE');
insert into roles(id, name)
values (5, 'ROLE_CRUD_AD');
insert into roles(id, name)
values (6, 'ROLE_CHATTING_USER');
insert into roles(id, name)
values (7, 'ROLE_RENTING_USER');
insert into roles(id, name)
values (8, 'ROLE_RATING_COMMENTING_USER');
insert into roles(id, name)
values (9, 'ROLE_PHYSICALLY_RESERVE');

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
values (5, 'CRUD_AD_LIMITED');
insert into permission(id, name)
values (6, 'SEND_RENT_REQUESTS');
insert into permission(id, name)
values (7, 'CRUD_VEHICLE');
insert into permission(id, name)
values (8, 'POST_PRICELISTS');
insert into permission(id, name)
values (9, 'MANAGE_RENT_REQUESTS');
insert into permission(id, name)
values (10, 'PHYSICALLY_RESERVE_VEHICLE');
insert into permission(id, name)
values (11, 'COMPLETE_ACCESS');
insert into permission(id, name)
values (12, 'REGISTER_AGENCY');
insert into permission(id, name)
values (13, 'MANAGE_USERS');
insert into permission(id, name)
values (14, 'MANAGE_USER_PRIVILEGES');
insert into permission(id, name)
values (15, 'SEND_LOCATION');
insert into permission(id, name)
values (16, 'MANAGE_RATES_AND_COMMENTS');
insert into permission(id, name)
values (17, 'CRUD_AD');
insert into permission(id, name)
values (18, 'UPLOAD_IMAGES');
insert into permission(id, name)
values (19, 'READ_RATES');
insert into permission(id, name)
values (20, 'COMMENT');
insert into permission(id, name)
values (21, 'WRITE_REPORT');
insert into permission(id, name)
values (22, 'REQUEST_STATISTICS');
insert into permission(id, name)
values (23, 'TRACK_VEHICLES');
insert into permission(id, name)
values (24, 'MANAGE_VEHICLE_FEATURES');
insert into permission(id, name)
values (25, 'MANAGE_COMMENTS');

------- Permisije obicnog korisnika -------
insert into role_permissions(role_id, permission_id)
values (1, 4); --RENT_HISTORY
insert into role_permissions(role_id, permission_id)
values (1, 5); --CRUD_AD_LIMITED
insert into role_permissions(role_id, permission_id)
values (1, 7); --CRUD_VEHICLE
insert into role_permissions(role_id, permission_id)
values (1, 10); --PHYSICALLY_RESERVE_VEHICLE
insert into role_permissions(role_id, permission_id)
values (1, 8); --POST_PRICELISTS

	-- permisije za "pod-role" obicnog usera
	-- ROLE_RENTING_USER - obican user koji sme da rentira (dobija pri registraciji, gubi kad rentira - sve dok ne plati)
	insert into role_permissions(role_id, permission_id)
	values (7, 1); --RENT
	-- ROLE_CHATTING_USER - obican user koji sme da razmenjuje poruke (dobija kad mu se odobri request)
	insert into role_permissions(role_id, permission_id)
	values (6, 2); --CHAT
	-- ROLE_RATING_COMMENTING_USER - obican user koji sme da ocenjuje i komentarise (dobija kad se zavrsi period rentiranja)
	insert into role_permissions(role_id, permission_id)
	values (8, 20); --COMMENT
	insert into role_permissions(role_id, permission_id)
	values (8, 3); --RATE
------------------------------------------

------------- Permisije agenta -----------
insert into role_permissions(role_id, permission_id)
values (2, 17);	--CRUD_AD
insert into role_permissions(role_id, permission_id)
values (2, 2);  --CHAT
insert into role_permissions(role_id, permission_id)
values (2, 7); --CRUD_VEHICLE
insert into role_permissions(role_id, permission_id)
values (2, 8); --POST_PRICELISTS
insert into role_permissions(role_id, permission_id)
values (2, 9); --MANAGE_RENT_REQUESTS
insert into role_permissions(role_id, permission_id)
values (2, 10); --PHYSICALLY_RESERVE_VEHICLE
insert into role_permissions(role_id, permission_id)
values (2, 18); --UPLOAD_IMAGES
insert into role_permissions(role_id, permission_id)
values (2, 19); --READ_RATES
insert into role_permissions(role_id, permission_id)
values (2, 20); --COMMENT
insert into role_permissions(role_id, permission_id)
values (2, 21); --WRITE_REPORT
insert into role_permissions(role_id, permission_id)
values (2, 22); --REQUEST_STATISTICS
insert into role_permissions(role_id, permission_id)
values (2, 23); --TRACK_VEHICLES
------------------------------------------

------------ Permisije admina ------------
insert into role_permissions(role_id, permission_id)
values (3, 11); --COMPLETE_ACCESS
insert into role_permissions(role_id, permission_id)
values (3, 12); --REGISTER_AGENCY
insert into role_permissions(role_id, permission_id)
values (3, 13); --MANAGE_USERS
insert into role_permissions(role_id, permission_id)
values (3, 14); --MANAGE_USER_PRIVILEGES
insert into role_permissions(role_id, permission_id)
values (3, 16); --MANAGE_RATES_AND_COMMENTS
insert into role_permissions(role_id, permission_id)
values (3, 24); --MANAGE_VEHICLE_FEATURES


-------------------- KORISNICI ---------------------
-- sifre svih usera su <<user>>123, npr mila123
insert into users(id, email, name, password, surname, enabled, non_locked, last_password_change)
values (1, 'mila@maildrop.cc', 'Mila', '$2a$10$5EIPh4FLRCczl/.mnQhHP.8GmBJT2tL8kgBS1qDALVaybk6hh5o5.', 'Stojakovic', 1, 1, null);
insert into users(id, email, name, password, surname, enabled, non_locked, last_password_change)
values (2, 'zika@maildrop.cc', 'Zivorad', '$2a$10$ZECdbx5MAPP.a7YbmWBowOrub/B2r7FZlq9tvr3BTMInSuhnApUpy', 'Stamenkovic', 1, 1, null);
insert into users(id, email, name, password, surname, enabled, non_locked, last_password_change)
values (3, 'grga@maildrop.cc', 'Svetislav', '$2a$10$JOsaxc.iuu8Pw.Wlbdfci.dl7xP93fa7/i0tib6zjnEv.OLrIRP1i', 'Grgur', 1, 1, null);

-- mila user
insert into user_roles(user_id, role_id)
values (1, 1);
insert into user_roles(user_id, role_id)
values (1, 4);
insert into user_roles(user_id, role_id)
values (1, 5);
insert into user_roles(user_id, role_id)
values (1, 6);
insert into user_roles(user_id, role_id)
values (1, 7);
insert into user_roles(user_id, role_id)
values (1, 8);
insert into user_roles(user_id, role_id)
values (1, 9);

--zika agent
insert into user_roles(user_id, role_id)
values (2, 2);
insert into user_roles(user_id, role_id)
values (2, 4);
insert into user_roles(user_id, role_id)
values (2, 5);
insert into user_roles(user_id, role_id)
values (2, 6);
insert into user_roles(user_id, role_id)
values (2, 7);
insert into user_roles(user_id, role_id)
values (2, 8);
insert into user_roles(user_id, role_id)
values (2, 9);


--grga admin
insert into user_roles(user_id, role_id)
values (3, 3);
insert into user_roles(user_id, role_id)
values (3, 4);
insert into user_roles(user_id, role_id)
values (3, 5);
insert into user_roles(user_id, role_id)
values (3, 6);
insert into user_roles(user_id, role_id)
values (3, 7);
insert into user_roles(user_id, role_id)
values (3, 8);
insert into user_roles(user_id, role_id)
values (3, 9);
