package com.qrmenu.platform.table.entity;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_table", uniqueConstraints = {
        @UniqueConstraint(name = "uq_branch_table", columnNames = {"branch_id", "table_number"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTable extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "table_number", nullable = false)
    private String tableNumber;

    @Column(name = "qr_token", nullable = false, unique = true)
    private String qrToken;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
