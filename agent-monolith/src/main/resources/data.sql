-- Baza nije create-drop (znaci jednom kada nesto insertujete ovde, ostaje u bazi cak i po ponovnom pokretanju aplikacije)
select * from user;

-- Roles
insert into role(id, name)
values(1, "ADMIN");

insert into role(id, name)
values(2, "AGENT");

insert into role(id, name)
values(3, "END_USER");

insert into role(id, name)
values(4, "VEHICLE");
--

-- Privileges
--insert into privilege() 
--values();