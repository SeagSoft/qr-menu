package com.qrmenu.platform.branch.controller;

import com.qrmenu.platform.branch.dto.BranchDto;
import com.qrmenu.platform.branch.dto.CreateBranchRequest;
import com.qrmenu.platform.branch.dto.UpdateBranchRequest;
import com.qrmenu.platform.branch.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminBranchController {

    private final BranchService branchService;

    public AdminBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/branches")
    public ResponseEntity<BranchDto> create(@Valid @RequestBody CreateBranchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.create(request));
    }

    @GetMapping("/branches/{id}")
    public ResponseEntity<BranchDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getById(id));
    }

    @GetMapping("/business/{businessId}/branches")
    public ResponseEntity<List<BranchDto>> getByBusinessId(@PathVariable Long businessId) {
        return ResponseEntity.ok(branchService.getByBusinessId(businessId));
    }

    @PutMapping("/branches/{id}")
    public ResponseEntity<BranchDto> update(@PathVariable Long id, @Valid @RequestBody UpdateBranchRequest request) {
        return ResponseEntity.ok(branchService.update(id, request));
    }

    @PatchMapping("/branches/{id}/toggle")
    public ResponseEntity<BranchDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.toggleActive(id));
    }
}
