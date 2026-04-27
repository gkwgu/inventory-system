package com.inventory.inventorySystem.service;

import com.inventory.inventorySystem.dto.CategoryCreateDto;
import com.inventory.inventorySystem.dto.CategoryResponseDto;
import com.inventory.inventorySystem.dto.ProductShortDto;
import com.inventory.inventorySystem.exception.BigCategoryNotFoundException;
import com.inventory.inventorySystem.exception.CategoryAlreadyExistsException;
import com.inventory.inventorySystem.exception.CategoryNotEmptyException;
import com.inventory.inventorySystem.exception.CategoryNotFoundException;
import com.inventory.inventorySystem.mapper.CategoryMapper;
import com.inventory.inventorySystem.mapper.ProductMapper;
import com.inventory.inventorySystem.model.BigCategoryEntity;
import com.inventory.inventorySystem.model.CategoryEntity;
import com.inventory.inventorySystem.repository.BigCategoryRepository;
import com.inventory.inventorySystem.repository.CategoryRepository;
import com.inventory.inventorySystem.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final BigCategoryRepository bigCategoryRepository;

    public CategoryResponseDto create(CategoryCreateDto dto) {
        BigCategoryEntity big = bigCategoryRepository
                .findByNameIgnoreCase(dto.bigCategoryName())
                .orElseThrow(() ->
                        new BigCategoryNotFoundException(dto.bigCategoryName()));

        if (categoryRepository.existsByNameIgnoreCaseAndBigCategory(
                dto.name(), big)) {
            throw new CategoryAlreadyExistsException(dto.name());
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(dto.name());
        category.setBigCategory(big);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductShortDto> findProductsByCategoryName(String categoryName) {

        CategoryEntity category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));

        return productRepository.findAllByCategory_Id(category.getId())
                .stream()
                .map(productMapper::toShortDto)
                .toList();
    }

    public void deleteByName(String categoryName) {

        CategoryEntity category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));

        if (!productRepository.findAllByCategory_Id(category.getId()).isEmpty()) {
            throw new CategoryNotEmptyException(categoryName);
        }

        categoryRepository.delete(category);
    }
}
