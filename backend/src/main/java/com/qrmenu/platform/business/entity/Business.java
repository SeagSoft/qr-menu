package com.qrmenu.platform.business.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "business")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Business extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    @Column(name = "phone")
    private String phone;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
