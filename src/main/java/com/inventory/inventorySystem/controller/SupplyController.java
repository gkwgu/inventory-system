package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.dto.SupplyCreateDto;
import com.inventory.inventorySystem.dto.SupplyResponseDto;
import com.inventory.inventorySystem.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplyResponseDto create(@RequestBody SupplyCreateDto dto) {
        return supplyService.createSupply(dto);
    }
    @GetMapping
    public List<SupplyResponseDto> findAll() {
        return supplyService.findAll();
    }
}