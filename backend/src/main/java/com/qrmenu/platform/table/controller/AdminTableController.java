package com.qrmenu.platform.table.controller;

import com.qrmenu.platform.table.dto.CreateTableRequest;
import com.qrmenu.platform.table.dto.RestaurantTableDto;
import com.qrmenu.platform.table.dto.UpdateTableRequest;
import com.qrmenu.platform.table.service.RestaurantTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tables")
@RequiredArgsConstructor
public class AdminTableController {

    private final RestaurantTableService tableService;

    @PostMapping
    public ResponseEntity<RestaurantTableDto> create(@Valid @RequestBody CreateTableRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tableService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.getById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RestaurantTableDto>> getByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(tableService.getByBranchId(branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableDto> update(@PathVariable Long id, @Valid @RequestBody UpdateTableRequest request) {
        return ResponseEntity.ok(tableService.update(id, request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<RestaurantTableDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.toggleActive(id));
    }
}
