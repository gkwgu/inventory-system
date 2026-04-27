package com.inventory.inventorySystem.importer;

import com.inventory.inventorySystem.model.BigCategoryEntity;
import com.inventory.inventorySystem.model.CategoryEntity;
import com.inventory.inventorySystem.model.ProductEntity;
import com.inventory.inventorySystem.repository.BigCategoryRepository;
import com.inventory.inventorySystem.repository.CategoryRepository;
import com.inventory.inventorySystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ExcelImportService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BigCategoryRepository bigCategoryRepository;

    @Value("${import.excel.max-rows:1000}")
    private int maxRows;

    public void importProducts(InputStream inputStream) {

        BigCategoryEntity currentBigCategory = null;
        CategoryEntity currentSmallCategory = null;
        boolean lastWasCategory = false;

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 6; i <= sheet.getLastRowNum() && i < maxRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell articleCell = row.getCell(0);
                String article = getString(articleCell);

                if (article == null || article.isBlank()) {
                    Cell nameCell = row.getCell(1);
                    if (nameCell == null || nameCell.getCellType() != CellType.STRING) continue;

                    String rawValue = nameCell.getStringCellValue();
                    if (rawValue == null || rawValue.isBlank()) continue;

                    String categoryName = rawValue.trim();

                    if (lastWasCategory) {
                        currentSmallCategory = findOrCreateCategory(categoryName, currentBigCategory);
                        lastWasCategory = false;
                        log.info("Подкатегория: {}", categoryName);
                    } else {
                        currentBigCategory = findOrCreateBigCategory(categoryName);
                        currentSmallCategory = null;
                        lastWasCategory = true;
                        log.info("Категория: {}", categoryName);
                    }
                    continue;
                }
                if (lastWasCategory) {
                    currentSmallCategory = findOrCreateCategory(
                            currentBigCategory.getName(), currentBigCategory
                    );
                    lastWasCategory = false;
                }

                if (currentSmallCategory == null) {
                    log.warn("Товар без категории, строка {}", i + 1);
                    continue;
                }

                ProductInfo info = parseProductRow(row);
                if (info == null) continue;

                if (productRepository.existsByArticle(info.article)) {
                    continue;
                }

                ProductEntity product = new ProductEntity();
                product.setArticle(info.article);
                product.setName(info.name);
                product.setPrice(info.price);
                product.setCategory(currentSmallCategory);
                product.setQuantity(0);

                productRepository.save(product);
                log.info("Товар добавлен: {}", info.article);
            }

        } catch (Exception e) {
            log.error("Ошибка импорта Excel", e);
            throw new RuntimeException(e);
        }
    }

    private BigCategoryEntity findOrCreateBigCategory(String name) {
        return bigCategoryRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    BigCategoryEntity c = new BigCategoryEntity();
                    c.setName(name);
                    return bigCategoryRepository.save(c);
                });
    }

    private CategoryEntity findOrCreateCategory(String name, BigCategoryEntity bigCategory) {
        return categoryRepository.findByNameIgnoreCaseAndBigCategory(name, bigCategory)
                .orElseGet(() -> {
                    CategoryEntity c = new CategoryEntity();
                    c.setName(name);
                    c.setBigCategory(bigCategory);
                    return categoryRepository.save(c);
                });
    }

    private ProductInfo parseProductRow(Row row) {
        try {
            String article = getString(row.getCell(0));
            String name = getString(row.getCell(1));
            BigDecimal price = extractPrice(row.getCell(2));

            if (article == null || name == null || price == null) {
                return null;
            }

            return new ProductInfo(article, name, price);
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal extractPrice(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
            if (cell.getCellType() == CellType.STRING) {
                String v = cell.getStringCellValue()
                        .replace(" ", "")
                        .replace(",", ".");
                return new BigDecimal(v);
            }
        } catch (Exception ignored) {}
        return null;
    }

    private String getString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default -> null;
        };
    }

    private record ProductInfo(String article, String name, BigDecimal price) {}
}