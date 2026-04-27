package com.inventory.inventorySystem.service;

import com.inventory.inventorySystem.dto.SupplyCreateDto;
import com.inventory.inventorySystem.dto.SupplyResponseDto;
import com.inventory.inventorySystem.exception.InvalidQuantityException;
import com.inventory.inventorySystem.exception.ProductNotFoundException;
import com.inventory.inventorySystem.mapper.SupplyMapper;
import com.inventory.inventorySystem.model.ProductEntity;
import com.inventory.inventorySystem.model.SupplyEntity;
import com.inventory.inventorySystem.repository.ProductRepository;
import com.inventory.inventorySystem.repository.SupplyRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyService {

    private final ProductRepository productRepository;
    private final SupplyRepository supplyRepository;
    private final SupplyMapper supplyMapper;

    public SupplyResponseDto createSupply(SupplyCreateDto dto) {

        if (dto.quantity() == null || dto.quantity() <= 0) {
            throw new InvalidQuantityException();
        }

        ProductEntity product = productRepository.findByArticle(dto.productArticle())
                .orElseThrow(() -> new ProductNotFoundException(dto.productArticle()));

        product.setQuantity(product.getQuantity() + dto.quantity());

        SupplyEntity supply = new SupplyEntity();
        supply.setProduct(product);
        supply.setQuantity(dto.quantity());
        supply.setDate(LocalDateTime.now());
        supply.setComment(dto.comment());

        SupplyEntity saved = supplyRepository.save(supply);

        return supplyMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public List<SupplyResponseDto> findAll() {
        return supplyRepository.findAll()
                .stream()
                .map(supplyMapper::toDto)
                .collect(Collectors.toList());
    }
}
