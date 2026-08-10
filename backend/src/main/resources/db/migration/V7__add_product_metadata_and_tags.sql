ALTER TABLE product ADD COLUMN calories INT;
ALTER TABLE product ADD COLUMN prep_time_minutes INT;

CREATE TABLE product_tags (
    product_id BIGINT NOT NULL,
    tag VARCHAR(100) NOT NULL,
    CONSTRAINT fk_product_tags_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
