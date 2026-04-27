CREATE TABLE inventories (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('DRAFT', 'COMPLETED'))
);

CREATE TABLE inventory_items (
    id BIGSERIAL PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    expected_quantity INTEGER NOT NULL CHECK (expected_quantity >= 0),
    actual_quantity INTEGER NOT NULL CHECK (actual_quantity >= 0),
    difference INTEGER NOT NULL,

    CONSTRAINT fk_inventory_item_inventory
        FOREIGN KEY (inventory_id)
        REFERENCES inventories(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_inventory_item_product
        FOREIGN KEY (product_id)
        REFERENCES products(id),

    CONSTRAINT uq_inventory_product
        UNIQUE (inventory_id, product_id)
);

CREATE INDEX idx_inventory_items_inventory_id
    ON inventory_items(inventory_id);

CREATE INDEX idx_inventory_items_product_id
    ON inventory_items(product_id);