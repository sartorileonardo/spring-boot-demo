package com.jpa.demo.utils;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

@UtilityClass
public class ExcelUtils {

    private static final String DEFAULT_VALUE = "N/A";
    private static final Locale BRAZIL_LOCALE = new Locale("pt", "BR");

    /**
     * Cria uma linha de cabeçalho com títulos e estilos especificados.
     *
     * @param sheet    A planilha onde a linha de cabeçalho será criada.
     * @param headers  O array de títulos do cabeçalho.
     * @param workbook O workbook para criar os estilos.
     */
    public static void createHeaderRow(Sheet sheet, String[] headers, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();

        // Define a cor de fundo e o estilo da fonte
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
            headerCell.setCellStyle(headerStyle);
        }
    }

    /**
     * Define o tamanho de uma coluna.
     *
     * @param sheet       A planilha onde o tamanho da coluna será ajustado.
     * @param columnIndex O índice da coluna para ajustar o tamanho (baseado em 0).
     * @param width       O tamanho a ser definido, em caracteres.
     */
    public static void setColumnWidth(Sheet sheet, int columnIndex, int width) {
        // O tamanho é medido em 1/256 do tamanho de um caractere.
        // Multiplique o tamanho desejado por 256 para obter o valor correto.
        sheet.setColumnWidth(columnIndex, width * 256);
    }

    /**
     * Popula uma linha com valores especificados.
     *
     * @param sheet    A planilha onde a linha será criada.
     * @param rowIndex O índice da linha a ser populada.
     * @param values   O array de valores a serem populados.
     */
    public static void populateRow(Sheet sheet, int rowIndex, Object[] values) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(getTextValue(values[i]));
        }
    }

    /**
     * Cria uma nova planilha com um nome especificado.
     *
     * @param workbook  O workbook onde a planilha será criada.
     * @param sheetName O nome da planilha.
     * @return A planilha criada.
     */
    public static Sheet createSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }

    public static String getTextValue(Object value) {
        return valueIsEmpty(value) ? DEFAULT_VALUE : String.valueOf(value);
    }

    public static String getValorData(Object value) {
        if (valueIsEmpty(value)) {
            return DEFAULT_VALUE;
        }

        LocalDate parsedDate = null;
        try {
            parsedDate = LocalDate.parse(value.toString());
        } catch (Exception e) {
            try {
                parsedDate = DateTimeUtils.stringToLocalDate(value.toString());
            } catch (Exception ex) {
                return DEFAULT_VALUE;
            }
        }

        if (parsedDate != null) {
            return DateTimeUtils.localDateToString(parsedDate);
        }

        return DEFAULT_VALUE;
    }

    public static String getCurrencyValue(Object value) {
        if (valueIsEmpty(value)) {
            return DEFAULT_VALUE;
        }

        if (value instanceof Number) {
            return formatCurrency((Number) value);
        }

        if (value instanceof String) {
            Number parsedValue = parseNumberFromString((String) value);
            return formatCurrency(parsedValue);
        }

        return DEFAULT_VALUE;
    }

    private static String formatCurrency(Number value) {
        return NumberFormat.getCurrencyInstance(BRAZIL_LOCALE).format(value);
    }

    public static boolean valueIsEmpty(Object value) {
        return Objects.isNull(value) || String.valueOf(value).isEmpty();
    }

    private static Number parseNumberFromString(String textValue) {
        try {
            return Double.parseDouble(textValue);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
