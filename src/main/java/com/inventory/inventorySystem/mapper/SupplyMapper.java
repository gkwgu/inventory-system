package com.inventory.inventorySystem.mapper;

import com.inventory.inventorySystem.dto.SupplyResponseDto;
import com.inventory.inventorySystem.model.SupplyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplyMapper {

        @Mapping(source = "product.article", target = "productArticle")
        @Mapping(source = "product.name", target = "productName")
        SupplyResponseDto toDto(SupplyEntity supply);
}
