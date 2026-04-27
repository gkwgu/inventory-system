package com.inventory.inventorySystem.mapper;

import com.inventory.inventorySystem.dto.SaleResponseDto;
import com.inventory.inventorySystem.model.SaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

        @Mapping(source = "product.article", target = "productArticle")
        @Mapping(source = "product.name", target = "productName")
        SaleResponseDto toDto(SaleEntity sale);
}
