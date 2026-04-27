package com.inventory.inventorySystem.service;

import com.inventory.inventorySystem.dto.ProductCreateDto;
import com.inventory.inventorySystem.dto.ProductResponseDto;
import com.inventory.inventorySystem.exception.CategoryNotFoundException;
import com.inventory.inventorySystem.exception.ProductAlreadyExistsException;
import com.inventory.inventorySystem.exception.ProductNotFoundException;
import com.inventory.inventorySystem.mapper.ProductMapper;
import com.inventory.inventorySystem.model.CategoryEntity;
import com.inventory.inventorySystem.model.ProductEntity;
import com.inventory.inventorySystem.repository.CategoryRepository;
import com.inventory.inventorySystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductResponseDto create(ProductCreateDto dto) {
        if (productRepository.existsByArticle(dto.article())) {
            throw new ProductAlreadyExistsException(dto.article());
        }

        CategoryEntity category = categoryRepository.findByNameIgnoreCase(dto.categoryName())
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryName()));

        ProductEntity product = productMapper.toEntity(dto);
        product.setCategory(category);

        return productMapper.toDto(productRepository.save(product));
    }

    public ProductResponseDto update(String article, ProductCreateDto dto) {
        ProductEntity product = productRepository.findByArticle(article)
                .orElseThrow(() -> new ProductNotFoundException(article));

        CategoryEntity category = categoryRepository.findByNameIgnoreCase(dto.categoryName())
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryName()));

        productMapper.update(dto, product);
        product.setCategory(category);

        return productMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findByArticle(String article) {
        return productRepository.findByArticle(article)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(article));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public void deleteByArticle(String article) {
        if (!productRepository.existsByArticle(article)) {
            throw new ProductNotFoundException(article);
        }
        productRepository.deleteByArticle(article);
    }
}
