package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.dto.CategoryCreateDto;
import com.inventory.inventorySystem.dto.CategoryResponseDto;
import com.inventory.inventorySystem.dto.ProductShortDto;
import com.inventory.inventorySystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(@RequestBody CategoryCreateDto dto) {
        return categoryService.create(dto);
    }

    @GetMapping
    public List<CategoryResponseDto> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{name}/products")
    public List<ProductShortDto> findProductsByCategory(
            @PathVariable String name
    ) {
        return categoryService.findProductsByCategoryName(name);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name) {
        categoryService.deleteByName(name);
    }
}