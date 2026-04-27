CREATE TABLE returns (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    date TIMESTAMP NOT NULL,
    reason TEXT,
    CONSTRAINT fk_return_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);