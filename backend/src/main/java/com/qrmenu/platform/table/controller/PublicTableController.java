package com.qrmenu.platform.table.controller;

import com.qrmenu.platform.table.dto.RestaurantTableDto;
import com.qrmenu.platform.table.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/tables")
@RequiredArgsConstructor
public class PublicTableController {

    private final RestaurantTableService tableService;

    @GetMapping("/token/{qrToken}")
    public ResponseEntity<RestaurantTableDto> getByQrToken(@PathVariable String qrToken) {
        return ResponseEntity.ok(tableService.getByQrToken(qrToken));
    }
}
