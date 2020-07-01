-- delete from ad;
--delete from comment;
--delete from location;

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
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(1, false, 20, 642, 23, 3, 1);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(2, false, 10, 435, 30, 5, 1);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(3, false, 25, 500, 35, 7, 1);

insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(4, false, 10, 435, 30, 5, 2);
insert into price_list(id, deleted, discount, pricecdw, price_per_day, price_per_mile, user_id)
values(5, false, 25, 500, 35, 7, 2);


---- Ads
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id, local_id)
values(1, 'Via del Corso 15', '2020-05-29 00:00:00.000000', '2020-07-16 00:00:00.000000', 0, 1, null, 0, '2020-05-28 17:20:12.039000', 4, 1, 1, 1);
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id, local_id)
values(2, 'Via del Corso 15', '2020-05-29 00:00:00.000000', '2020-07-25 00:00:00.000000', 0, 1, null, 0, '2020-05-28 17:20:12.039000', 4, 2, 2, 2);

insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id, local_id)
values(3, 'Calle de Toledo 101', '2020-05-29 00:00:00.000000', '2020-07-25 00:00:00.000000', 0, 1, null, 0, '2020-04-28 17:20:12.039000', 2, 2, 3, 3);
insert into ad (id, address, available_from, available_to, deleted, gps, mile_limit, mile_limit_enabled, publish_date, location_id, price_list_id, vehicle_id, local_id)
values(4, 'Calle de Toledo 101', '2020-05-29 00:00:00.000000', '2020-08-15 00:00:00.000000', 0, 1, null, 0, '2020-05-15 17:20:12.039000', 2, 3, 4, 4);

----Comment
----insert into comment (id, approved, client_username, content, deleted, title, ad_id, comment_date)
----values(1, false, 'Mila', 'Sadrzaj komentara', false, 'Naslov komentara', 1, '2020-08-15 00:00:00.000000')
-- Comments
insert into comment(id, local_id, approved, content, comment_date, deleted, rate, title, ad_id, client_id, reply_id, reply, client_username)
values(1, null, true, 'This vehicle is great', '2020-06-05 11:00:00.000000', false, 9, 'Nice', 1, 1, 3, false, 'mila@maildrop.cc');
insert into comment(id, local_id, approved, content, comment_date, deleted, rate, title, ad_id, client_id, reply, client_username)
values(2, null, true, 'Great service', '2020-06-08 12:00:00.000000', false, 8, 'OK', 1, 2, false, 'mila@maildrop.cc');
--insert into comment(id, local_id, approved, content, comment_date, deleted, ad_id, client_id, reply, client_username, local_id)
--values(3, null, true, 'Thank you very much' , '2020-06-08 10:00:00.000000', false, 1, 2, true, 'zika@maildrop.cc', 3);

insert into comment(id, local_id, approved, content, comment_date, deleted, rate, title, ad_id, client_id, reply_id, reply, client_username)
values(3, null, true, 'Not happy', '2020-06-04 12:00:00.000000', false, 8, 'As an admin im offended', 2, 3, null, false, 'grga@maildrop.cc');
