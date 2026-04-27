package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.dto.ReturnCreateDto;
import com.inventory.inventorySystem.dto.ReturnResponseDto;
import com.inventory.inventorySystem.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnController {

    private final ReturnService returnService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReturnResponseDto create(@RequestBody ReturnCreateDto dto) {
        return returnService.createReturn(dto);
    }

    @GetMapping
    public List<ReturnResponseDto> findAll() {
        return returnService.findAll();
    }
}
