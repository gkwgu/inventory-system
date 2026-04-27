package com.inventory.inventorySystem.mapper;

import com.inventory.inventorySystem.dto.InventoryItemResponseDto;
import com.inventory.inventorySystem.model.InventoryItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {

    @Mapping(source = "product.article", target = "productArticle")
    @Mapping(source = "product.name", target = "productName")
    InventoryItemResponseDto toDto(InventoryItemEntity entity);
}
