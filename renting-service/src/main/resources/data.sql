
--delete from bundled_requests;
--delete from location;
--delete from price_list;
delete from rent_request;
delete from message;


insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (1, '2020-06-23 00:00:00.000000', 1, 'COMPLETED', '2020-06-23 00:00:00.000000', 1, null, null);
insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (2, '2020-06-04 00:00:00.000000', 1, 'RESERVED', '2020-06-15 00:00:00.000000', 1, null, null);
insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (3, '2020-05-29 00:00:00.000000', 1, 'COMPLETED', '2020-06-01 00:00:00.000000', 1, null, null);
insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (4, '2020-06-01 00:00:00.000000', 1, 'COMPLETED', '2020-06-03 00:00:00.000000', 1, null, null);
insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (5, '2020-06-29 00:00:00.000000', 1, 'PENDING', '2020-06-30 00:00:00.000000', 1, null, null);

-- Messages
insert into message(id, content, date, rent_request_id, title, from_user, to_user_id)
values(1, 'Hello Monolith, how are you doing?', '2020-06-05 10:00:00.000000', 1, 'Hello', 'zika@maildrop.cc', 1);
insert into message(id, content, date, rent_request_id, title, to_user, from_user_id)
values(2, 'Hello Ziko, good.', '2020-06-05 11:00:00.000000', 1, 'Hello-Reply', 'zika@maildrop.cc', 1);

