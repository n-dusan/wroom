-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
select * from user;

-- Privileges
insert into privilege(id, name) 
values(1, 'RENT_REQUESTS_CREATE');