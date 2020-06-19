
--delete from bundled_requests;
--delete from location;
--delete from price_list;
delete from rent_request;
delete from message;


insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (1, '2020-06-04 00:00:00.000000', 1, 'RESERVED', '2020-06-15 00:00:00.000000', 1, null, null);


insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (2, '2020-05-29 00:00:00.000000', 1, 'COMPLETED', '2020-06-01 00:00:00.000000', 1, null, null);
insert into rent_request(id, from_date, requested_user_id, status, to_date, ad_id, bundle_id, rent_report_id)
values (3, '2020-06-01 00:00:00.000000', 1, 'COMPLETED', '2020-06-03 00:00:00.000000', 1, null, null);
