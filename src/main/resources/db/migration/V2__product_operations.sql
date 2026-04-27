CREATE TABLE supplies (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    date TIMESTAMP NOT NULL,
    comment TEXT,
    CONSTRAINT fk_supply_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);

CREATE TABLE sales (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    date TIMESTAMP NOT NULL,
    CONSTRAINT fk_sale_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);