package com.inventory.inventorySystem.service;

import com.inventory.inventorySystem.dto.InventoryItemCreateDto;
import com.inventory.inventorySystem.dto.InventoryItemResponseDto;
import com.inventory.inventorySystem.dto.InventoryResponseDto;
import com.inventory.inventorySystem.exception.InventoryAlreadyCompletedException;
import com.inventory.inventorySystem.exception.InventoryItemAlreadyExistsException;
import com.inventory.inventorySystem.exception.InventoryNotFoundException;
import com.inventory.inventorySystem.exception.ProductNotFoundException;
import com.inventory.inventorySystem.mapper.InventoryItemMapper;
import com.inventory.inventorySystem.model.InventoryEntity;
import com.inventory.inventorySystem.model.InventoryItemEntity;
import com.inventory.inventorySystem.model.ProductEntity;
import com.inventory.inventorySystem.repository.InventoryItemRepository;
import com.inventory.inventorySystem.repository.InventoryRepository;
import com.inventory.inventorySystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;
    private final InventoryItemMapper mapper;

    public Long createInventory() {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setDate(LocalDateTime.now());
        inventory.setStatus(InventoryEntity.InventoryStatus.DRAFT);

        return inventoryRepository.save(inventory).getId();
    }

    public InventoryItemResponseDto addItem(Long inventoryId, InventoryItemCreateDto dto) {

        InventoryEntity inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException(inventoryId));

        if (inventory.getStatus() == InventoryEntity.InventoryStatus.COMPLETED) {
            throw new InventoryAlreadyCompletedException(inventoryId);
        }

        ProductEntity product = productRepository.findByArticle(dto.productArticle())
                .orElseThrow(() -> new ProductNotFoundException(dto.productArticle()));

        if (inventoryItemRepository.existsByInventory_IdAndProduct_Id(
                inventoryId, product.getId())) {
            throw new InventoryItemAlreadyExistsException(dto.productArticle());
        }

        int expected = product.getQuantity();
        int actual = dto.actualQuantity();
        int difference = actual - expected;

        InventoryItemEntity item = new InventoryItemEntity();
        item.setInventory(inventory);
        item.setProduct(product);
        item.setExpectedQuantity(expected);
        item.setActualQuantity(actual);
        item.setDifference(difference);

        return mapper.toDto(inventoryItemRepository.save(item));
    }

    public void completeInventory(Long inventoryId) {

        InventoryEntity inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException(inventoryId));

        if (inventory.getStatus() == InventoryEntity.InventoryStatus.COMPLETED) {
            return;
        }

        List<InventoryItemEntity> items =
                inventoryItemRepository.findAllByInventory_Id(inventoryId);

        for (InventoryItemEntity item : items) {
            ProductEntity product = item.getProduct();
            product.setQuantity(item.getActualQuantity());
        }

        inventory.setStatus(InventoryEntity.InventoryStatus.COMPLETED);
    }
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> findAll() {
        return inventoryRepository.findAllByOrderByDateDesc()
                .stream()
                .map(inv -> {
                    List<InventoryItemResponseDto> items = inventoryItemRepository
                            .findAllByInventory_Id(inv.getId())
                            .stream()
                            .map(mapper::toDto)
                            .collect(Collectors.toList());
                    return new InventoryResponseDto(
                            inv.getId(),
                            inv.getDate(),
                            inv.getStatus().name(),
                            items
                    );
                })
                .collect(Collectors.toList());
    }
}
