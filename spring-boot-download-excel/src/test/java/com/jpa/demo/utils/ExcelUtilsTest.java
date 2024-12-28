package com.jpa.demo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilsTest {

    private Workbook workbook;
    private Sheet sheet;

    @BeforeEach
    public void setup() {
        // Create a new workbook and sheet before each test
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Test Sheet");
    }

    @Test
    @DisplayName("Should create a header row with the correct style and values")
    void createHeaderRowTest() {
        // Given
        String[] headers = {"ID", "Name", "Price"};

        // When
        ExcelUtils.createHeaderRow(sheet, headers, workbook);

        // Then
        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        assertEquals("ID", headerRow.getCell(0).getStringCellValue());
        assertEquals("Name", headerRow.getCell(1).getStringCellValue());
        assertEquals("Price", headerRow.getCell(2).getStringCellValue());

        // Check if the style has been applied (using the color of the first cell)
        CellStyle headerStyle = headerRow.getCell(0).getCellStyle();
        assertNotNull(headerStyle);
        assertEquals(IndexedColors.GREY_25_PERCENT.getIndex(), headerStyle.getFillForegroundColor());
    }

    @Test
    @DisplayName("Should set column width correctly")
    void setColumnWidthTest() {
        // Given
        int columnIndex = 1;
        int width = 20;

        // When
        ExcelUtils.setColumnWidth(sheet, columnIndex, width);

        // Then
        assertEquals(20 * 256, sheet.getColumnWidth(columnIndex));
    }

    @Test
    @DisplayName("Should populate a row with the correct values")
    void populateRowTest() {
        // Given
        Object[] values = {"1", "Book Name", new BigDecimal("29.90")};

        // When
        ExcelUtils.populateRow(sheet, 1, values);

        // Then
        Row row = sheet.getRow(1);
        assertNotNull(row);
        assertEquals("1", row.getCell(0).getStringCellValue());
        assertEquals("Book Name", row.getCell(1).getStringCellValue());
        assertTrue( row.getCell(2).getStringCellValue().contains("29.90"));
    }

    @Test
    @DisplayName("Should return the correct text value, defaulting to 'N/A' for null/empty")
    void getTextValueTest() {
        // When/Then
        assertEquals("Hello", ExcelUtils.getTextValue("Hello"));
        assertEquals("N/A", ExcelUtils.getTextValue(null));
        assertEquals("N/A", ExcelUtils.getTextValue(""));
    }

    @Test
    @DisplayName("Should return the formatted date string or 'N/A' if invalid")
    void getValorDataTest() {
        // Given
        String validDate = "2024-12-28";
        String invalidDate = "Invalid Date";

        // When
        String formattedDate = ExcelUtils.getValorData(validDate);
        String invalidFormattedDate = ExcelUtils.getValorData(invalidDate);

        // Then
        assertEquals("28/12/2024", formattedDate);  // Assuming the DateTimeUtils converts the date correctly
        assertEquals("N/A", invalidFormattedDate);
    }

    @Test
    @DisplayName("Should return formatted currency or 'N/A' if not a valid number")
    void getCurrencyValueTest() {
        // Given
        Object validNumber = new BigDecimal("99.99");
        Object invalidNumber = "Invalid Number";

        // When
        String formattedCurrency = ExcelUtils.getCurrencyValue(validNumber);
        String invalidFormattedCurrency = ExcelUtils.getCurrencyValue(invalidNumber);

        // Then
        assertTrue( formattedCurrency.contains("R$") && formattedCurrency.contains("99,99"));
        assertTrue(invalidFormattedCurrency.contains("R$") && invalidFormattedCurrency.contains("0,00"));
    }

    @Test
    @DisplayName("Should return 'N/A' for empty or null values in currency")
    void getCurrencyValueEmptyTest() {
        // Given
        Object nullValue = null;
        Object emptyString = "";
        Object zeroValue = "0";

        // When
        String nullCurrency = ExcelUtils.getCurrencyValue(nullValue);
        String emptyStringCurrency = ExcelUtils.getCurrencyValue(emptyString);
        String zeroCurrency = ExcelUtils.getCurrencyValue(zeroValue);

        // Then
        assertEquals("N/A", nullCurrency);
        assertEquals("N/A", emptyStringCurrency);
        assertTrue( zeroCurrency.contains("R$") && zeroCurrency.contains("0,00"));  // Zero value should be formatted as currency
    }

    @Test
    @DisplayName("Should return true for empty values and false for non-empty values")
    void valueIsEmptyTest() {
        // Given
        Object nullValue = null;
        Object emptyString = "";
        Object nonEmptyString = "Test";

        // When/Then
        assertTrue(ExcelUtils.valueIsEmpty(nullValue));
        assertTrue(ExcelUtils.valueIsEmpty(emptyString));
        assertFalse(ExcelUtils.valueIsEmpty(nonEmptyString));
    }
}

