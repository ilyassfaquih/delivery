-- liquibase formatted sql

-- changeset ilyas:3
CREATE TABLE order_items (
                             order_id BIGINT NOT NULL REFERENCES food_order(id),
                             menu_item_id BIGINT NOT NULL REFERENCES menu_item(id),
                             PRIMARY KEY (order_id, menu_item_id)
);