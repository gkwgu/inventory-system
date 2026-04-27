package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.dto.InventoryItemCreateDto;
import com.inventory.inventorySystem.dto.InventoryItemResponseDto;
import com.inventory.inventorySystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.inventory.inventorySystem.dto.InventoryResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createInventory() {
        return inventoryService.createInventory();
    }

    @PostMapping("/{inventoryId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryItemResponseDto addItem(
            @PathVariable Long inventoryId,
            @RequestBody InventoryItemCreateDto dto
    ) {
        return inventoryService.addItem(inventoryId, dto);
    }

    @PostMapping("/{inventoryId}/complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void complete(@PathVariable Long inventoryId) {
        inventoryService.completeInventory(inventoryId);
    }

    @GetMapping
    public List<InventoryResponseDto> findAll() {
        return inventoryService.findAll();
    }
}