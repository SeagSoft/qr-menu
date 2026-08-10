package com.qrmenu.platform.table.repository;

import com.qrmenu.platform.table.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    List<RestaurantTable> findByBranchId(Long branchId);
    Optional<RestaurantTable> findByQrToken(String qrToken);
    boolean existsByBranchIdAndTableNumber(Long branchId, String tableNumber);
    boolean existsByQrToken(String qrToken);
}
