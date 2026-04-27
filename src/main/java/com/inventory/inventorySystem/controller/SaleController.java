package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.dto.SaleCreateDto;
import com.inventory.inventorySystem.dto.SaleResponseDto;
import com.inventory.inventorySystem.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponseDto create(@RequestBody SaleCreateDto dto) {
        return saleService.createSale(dto);
    }
    @GetMapping
    public List<SaleResponseDto> findAll() {
        return saleService.findAll();
    }
    @GetMapping("/by-date")
    public List<SaleResponseDto> findByDate(@RequestParam String date) {
        return saleService.findByDate(date);
    }
}