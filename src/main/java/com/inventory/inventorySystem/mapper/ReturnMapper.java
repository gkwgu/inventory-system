package com.inventory.inventorySystem.mapper;

import com.inventory.inventorySystem.dto.ReturnResponseDto;
import com.inventory.inventorySystem.model.ReturnEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReturnMapper {

    @Mapping(source = "product.article", target = "productArticle")
    @Mapping(source = "product.name", target = "productName")
    ReturnResponseDto toDto(ReturnEntity returnEntity);
}
