-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
-- U create-drop rezimu, ukoliko sql skripta ponovo insertuje već unete vrednosti (MySQL ume da ne dropuje i redove unutar tabela)
-- onda dodajte delete na tabelu koju želite da očistite, time se brišu svi redovi iz te tabele
--------------------------------

delete from users;
delete from user_roles;

insert into roles(id, name)
values (1, 'ROLE_USER');

insert into roles(id, name)
values (2, 'ROLE_AGENT');

insert into roles(id, name)
values (3, 'ROLE_ADMIN');
-----------------------------------
insert into permission(id, name)
values (1, 'RENT_REQUEST_CREATE');

----------------------------------
insert into role_permissions(role_id, permission_id)
values (1, 1);


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