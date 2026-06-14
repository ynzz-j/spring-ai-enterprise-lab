CREATE TABLE order_report (
  id BIGINT PRIMARY KEY,
  order_id VARCHAR(64) NOT NULL,
  product_name VARCHAR(128) NOT NULL,
  customer_level VARCHAR(32) NOT NULL,
  order_month VARCHAR(16) NOT NULL,
  amount DECIMAL(12, 2) NOT NULL,
  status VARCHAR(32) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

-- Sensitive fields such as mobile, email and id_card are deliberately not included
-- in this report table. Chapter 03 focuses on safe read-only analytics.

