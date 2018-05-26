DROP TABLE offer IF EXISTS;

DROP TABLE currency IF EXISTS;

CREATE TABLE currency (
  currency_code CHAR(3) PRIMARY KEY
);

CREATE TABLE offer (
  offer_id          VARCHAR(255) PRIMARY KEY,
  merchant_id       VARCHAR(255)        NOT NULL,
  description       VARCHAR(255)        NOT NULL,
  offering_id       VARCHAR(255)        NOT NULL, -- product or service id
  price             DECIMAL             NOT NULL,
  currency_code     CHAR(3)             NOT NULL,
  active_start_date DATETIME            NOT NULL,
  active_end_date   DATETIME            NOT NULL,
  status_code       CHAR(1) DEFAULT 'A' NOT NULL,
  FOREIGN KEY (currency_code) REFERENCES currency (currency_code)
);
