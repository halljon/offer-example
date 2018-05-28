DELETE
FROM offer
WHERE merchant_id = 'full-stack-test-merchant-id';

INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'full-stack-test-offer-id-1001', 'full-stack-test-merchant-id', 'Some really interesting offer 1001',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-01 00:00:00', '2028-01-31 23:59:59',
  'A'
);
