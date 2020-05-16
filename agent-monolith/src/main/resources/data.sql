-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
select * from user;

insert into roles(id, name) 
values (1, 'ROLE_USER');