-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
--------------------------------
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
