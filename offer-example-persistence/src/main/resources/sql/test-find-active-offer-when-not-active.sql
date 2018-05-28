INSERT INTO offer (
  offer_id, merchant_id, description,
  offering_id, price, currency_code,
  active_start_date, active_end_date,
  status_code
) VALUES (
  'repository-test-offer-id-1001', 'repository-test-merchant-id-1', 'Some really interesting offer 1001',
  'offering-id-1', 12.34, 'GBP',
  '2018-01-01', '2018-01-31 23:59:59',
  'C'
);
