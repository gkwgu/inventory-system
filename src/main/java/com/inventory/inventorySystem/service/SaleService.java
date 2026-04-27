package com.inventory.inventorySystem.service;

import com.inventory.inventorySystem.dto.SaleCreateDto;
import com.inventory.inventorySystem.dto.SaleResponseDto;
import com.inventory.inventorySystem.exception.InsufficientStockException;
import com.inventory.inventorySystem.exception.InvalidQuantityException;
import com.inventory.inventorySystem.exception.ProductNotFoundException;
import com.inventory.inventorySystem.mapper.SaleMapper;
import com.inventory.inventorySystem.model.ProductEntity;
import com.inventory.inventorySystem.model.SaleEntity;
import com.inventory.inventorySystem.repository.ProductRepository;
import com.inventory.inventorySystem.repository.SaleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public SaleResponseDto createSale(SaleCreateDto dto) {

        if (dto.quantity() == null || dto.quantity() <= 0) {
            throw new InvalidQuantityException();
        }

        ProductEntity product = productRepository.findByArticle(dto.productArticle())
                .orElseThrow(() -> new ProductNotFoundException(dto.productArticle()));

        if (product.getQuantity() < dto.quantity()) {
            throw new InsufficientStockException(dto.productArticle());
        }

        product.setQuantity(product.getQuantity() - dto.quantity());

        SaleEntity sale = new SaleEntity();
        sale.setProduct(product);
        sale.setQuantity(dto.quantity());
        sale.setDate(LocalDateTime.now());

        SaleEntity saved = saleRepository.save(sale);

        return saleMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public List<SaleResponseDto> findAll() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<SaleResponseDto> findByDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        return saleRepository.findByDateBetween(start, end)
                .stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }
}
