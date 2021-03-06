INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'repository-test-offer-id-1001', 'repository-test-merchant-id-1', 'Interesting offer 1001',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-01', '2018-01-31 23:59:59',
  'C'
);

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'repository-test-offer-id-1004', 'repository-test-merchant-id-1', 'Interesting offer 1004',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-01 00:00:00', '2018-01-31 23:59:59',
  'A'
);

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'repository-test-offer-id-2001', 'repository-test-merchant-id-2', 'Interesting offer',
  'offering-id-99', 12.34, 'GBP',
  '2018-01-01', '2018-01-31 23:59:59',
  'A'
);

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'repository-test-offer-id-1005', 'repository-test-merchant-id-1', 'Interesting offer',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-01 00:00:00', '2018-01-15 23:59:59',
  'A'
);

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'repository-test-offer-id-1006', 'repository-test-merchant-id-1', 'Interesting offer',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-14 00:00:00', '2018-01-20 23:59:59',
  'A'
);
