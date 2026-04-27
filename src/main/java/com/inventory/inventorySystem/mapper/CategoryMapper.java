package com.inventory.inventorySystem.mapper;

import com.inventory.inventorySystem.dto.CategoryCreateDto;
import com.inventory.inventorySystem.dto.CategoryResponseDto;
import com.inventory.inventorySystem.model.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryCreateDto dto);

    CategoryResponseDto toDto(CategoryEntity category);
}
