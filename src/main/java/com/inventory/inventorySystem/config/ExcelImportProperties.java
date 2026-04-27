package com.inventory.inventorySystem.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "import.excel")
public class ExcelImportProperties {

    private int maxRows = 100;
    private int sheetIndex = 0;

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }
}