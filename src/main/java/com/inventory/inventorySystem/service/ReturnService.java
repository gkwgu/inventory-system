package com.inventory.inventorySystem.service;

import com.inventory.inventorySystem.dto.ReturnCreateDto;
import com.inventory.inventorySystem.dto.ReturnResponseDto;
import com.inventory.inventorySystem.exception.ProductNotFoundException;
import com.inventory.inventorySystem.exception.ReturnNotFoundException;
import com.inventory.inventorySystem.mapper.ReturnMapper;
import com.inventory.inventorySystem.model.ProductEntity;
import com.inventory.inventorySystem.model.ReturnEntity;
import com.inventory.inventorySystem.model.SaleEntity;
import com.inventory.inventorySystem.repository.ProductRepository;
import com.inventory.inventorySystem.repository.ReturnRepository;
import com.inventory.inventorySystem.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReturnService {

    private final ProductRepository productRepository;
    private final ReturnRepository returnRepository;
    private final SaleRepository saleRepository;
    private final ReturnMapper returnMapper;

    public ReturnResponseDto createReturn(ReturnCreateDto dto) {
        SaleEntity sale = saleRepository.findById(dto.saleId())
                .orElseThrow(() -> new ReturnNotFoundException(dto.saleId()));

        String productArticle = sale.getProduct().getArticle();
        int returnQuantity = sale.getQuantity();

        ProductEntity product = productRepository.findByArticle(productArticle)
                .orElseThrow(() -> new ProductNotFoundException(productArticle));

        product.setQuantity(product.getQuantity() + returnQuantity);
        productRepository.save(product);

        saleRepository.delete(sale);

        ReturnEntity returnEntity = new ReturnEntity();
        returnEntity.setProduct(product);
        returnEntity.setQuantity(returnQuantity);
        returnEntity.setDate(LocalDateTime.now());
        returnEntity.setReason(dto.reason());

        return returnMapper.toDto(returnRepository.save(returnEntity));
    }

    @Transactional(readOnly = true)
    public List<ReturnResponseDto> findAll() {
        return returnRepository.findAll()
                .stream()
                .map(returnMapper::toDto)
                .collect(Collectors.toList());
    }
}