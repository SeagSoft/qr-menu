package com.qrmenu.platform.menu.repository;

import com.qrmenu.platform.menu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIdOrderBySortOrderAsc(Long categoryId);
    boolean existsByCategoryIdAndName(Long categoryId, String name);
}
