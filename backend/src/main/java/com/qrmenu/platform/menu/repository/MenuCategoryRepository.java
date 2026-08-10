package com.qrmenu.platform.menu.repository;

import com.qrmenu.platform.menu.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findByBusinessIdOrderBySortOrderAsc(Long businessId);
    boolean existsByBusinessIdAndName(Long businessId, String name);
}
