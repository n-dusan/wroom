
-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
-- U create-drop rezimu, ukoliko sql skripta ponovo insertuje već unete vrednosti (MySQL ume da ne dropuje i redove unutar tabela)
-- onda dodajte delete na tabelu koju želite da očistite, time se brišu svi redovi iz te tabele
--------------------------------
 delete from users;
 delete from user_roles;
 delete from permission;
 delete from role_permissions;
 delete from roles;
------------- ROLE -----------------
insert into roles(id, name)
values (1, 'ROLE_USER');
insert into roles(id, name)
values (2, 'ROLE_AGENT');
insert into roles(id, name)
values (3, 'ROLE_ADMIN');
--insert into roles(id, name)
--values (4, 'ROLE_BANNED_USER');
insert into roles(id, name)
--values (5, 'ROLE_VEHICLE');
--insert into roles(id, name)
values (6, 'ROLE_CHATTING_USER');
insert into roles(id, name)
values (7, 'ROLE_RENTING_USER');
insert into roles(id, name)
values (8, 'ROLE_RATING_COMMENTING_USER');

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
values (5, 'POST_ADS_LIMITED');
insert into permission(id, name)
values (6, 'SEND_RENT_REQUESTS');
insert into permission(id, name)
values (7, 'POST_VEHICLES');
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
values (17, 'POST_ADS');
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
values (1, 5); --POST_ADS_LIMITED

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
values (2, 17);	--POST_ADS
insert into role_permissions(role_id, permission_id)
values (2, 2);  --CHAT
insert into role_permissions(role_id, permission_id)
values (2, 7); --POST_VEHICLES
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
--insert into role_permissions(role_id, permission_id)
--values (3, 25); --MANAGE_COMMENTS

------------------------------------------

------------ Permisije vozila ------------
--insert into role_permissions(role_id, permission_id)
--values (3, 15); --SEND_LOCATION
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
insert into user_roles(user_id, role_id)
values (1, 7);

--zika agent
insert into user_roles(user_id, role_id)
values (2, 2);

--grga admin
insert into user_roles(user_id, role_id)
values (3, 3);

