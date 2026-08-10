CREATE TABLE service_requests (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    table_id BIGINT NOT NULL,
    type VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    message TEXT,
    resolved_by BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_sr_branch FOREIGN KEY (branch_id) REFERENCES branch(id),
    CONSTRAINT fk_sr_table FOREIGN KEY (table_id) REFERENCES restaurant_table(id),
    CONSTRAINT fk_sr_resolved_by FOREIGN KEY (resolved_by) REFERENCES staff_user(id)
);

CREATE INDEX idx_service_requests_branch_status ON service_requests(branch_id, status);
CREATE INDEX idx_service_requests_table ON service_requests(table_id);
