DELETE
FROM offer
WHERE merchant_id LIKE 'full-stack-test%';

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'full-stack-test-offer-id-1001', 'full-stack-test-merchant-id', 'Interesting offer 1001',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-01 00:00:00', '2028-01-31 23:59:59',
  'A'
);

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'full-stack-test-offer-id-1002', 'full-stack-test-merchant-id', 'Interesting offer 1002',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-10 00:00:00', '2028-12-31 23:59:59',
  'A'
);

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'full-stack-test-offer-id-2002', 'full-stack-test-merchant-id-2', 'Interesting offer from another merchant',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-10 00:00:00', '2028-12-31 23:59:59',
  'A'
);
