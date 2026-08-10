package com.qrmenu.platform.ordering.repository;

import com.qrmenu.platform.ordering.entity.CustomerOrder;
import com.qrmenu.platform.ordering.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByBranchIdOrderByCreatedAtDesc(Long branchId);

    List<CustomerOrder> findByBranchIdAndStatusInOrderByCreatedAtAsc(Long branchId, List<OrderStatus> statuses);

    List<CustomerOrder> findByTableIdOrderByCreatedAtDesc(Long tableId);

    Optional<CustomerOrder> findByOrderNumber(String orderNumber);
}
