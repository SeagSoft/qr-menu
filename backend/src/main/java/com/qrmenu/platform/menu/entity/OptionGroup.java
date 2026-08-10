package com.qrmenu.platform.menu.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "option_group")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "min_select", nullable = false)
    private int minSelect = 0;

    @Builder.Default
    @Column(name = "max_select", nullable = false)
    private int maxSelect = 1;

    @Builder.Default
    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();
}
