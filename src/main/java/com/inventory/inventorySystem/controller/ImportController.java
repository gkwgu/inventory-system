package com.inventory.inventorySystem.controller;

import com.inventory.inventorySystem.importer.ExcelImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
@Transactional
public class ImportController {

    private final ExcelImportService excelImportService;

    @PostMapping("/excel")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        excelImportService.importProducts(file.getInputStream());
        return ResponseEntity.ok("Импорт выполнен успешно");
    }
}