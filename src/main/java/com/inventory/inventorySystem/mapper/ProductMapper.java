package com.inventory.inventorySystem.mapper;

import com.inventory.inventorySystem.dto.ProductCreateDto;
import com.inventory.inventorySystem.dto.ProductResponseDto;
import com.inventory.inventorySystem.dto.ProductShortDto;
import com.inventory.inventorySystem.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDto toDto(ProductEntity product);

    @Mapping(target = "category", ignore = true)
    ProductEntity toEntity(ProductCreateDto dto);

    @Mapping(target = "category", ignore = true)
    void update(ProductCreateDto dto, @MappingTarget ProductEntity product);

    ProductShortDto toShortDto(ProductEntity product);
}