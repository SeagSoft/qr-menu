CREATE TABLE restaurant_table (
    id BIGSERIAL PRIMARY KEY,
    business_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    table_number VARCHAR(50) NOT NULL,
    qr_token VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_table_business FOREIGN KEY (business_id) REFERENCES business(id) ON DELETE CASCADE,
    CONSTRAINT fk_table_branch FOREIGN KEY (branch_id) REFERENCES branch(id) ON DELETE CASCADE,
    CONSTRAINT uq_branch_table UNIQUE (branch_id, table_number)
);
