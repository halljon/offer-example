INSERT INTO currency (
    currency_code
) VALUES (
    'GBP'    
);

INSERT INTO currency (
    currency_code
) VALUES (
    'USD'    
);

INSERT INTO offer (
    offer_id, merchant_id, description,
    offering_id, price, currency_code,
    active_start_date, active_end_date,
    status_code
) VALUES (
    'offer-id-1', 'merchant-id-1', 'Some really interesting offer',
    'offering-id-1', 12.34, 'GBP',
    '2018-01-01', '2018-01-31 23:59:59',
    'A'
);
