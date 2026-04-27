package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.dto.ProductCreateDto;
import com.inventory.inventorySystem.dto.ProductResponseDto;
import com.inventory.inventorySystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@RequestBody ProductCreateDto dto) {
        return productService.create(dto);
    }

    @PutMapping("/{article}")
    public ProductResponseDto update(
            @PathVariable String article,
            @RequestBody ProductCreateDto dto
    ) {
        return productService.update(article, dto);
    }

    @GetMapping("/{article}")
    public ProductResponseDto findByArticle(@PathVariable String article) {
        return productService.findByArticle(article);
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll();
    }

    @DeleteMapping("/{article}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String article) {
        productService.deleteByArticle(article);
    }
}