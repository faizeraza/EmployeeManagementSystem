package com.remiges.remigesdb.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static <T> byte[] generateExcelFile(List<T> dataList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (dataList.isEmpty()) {
                throw new IllegalArgumentException("The data list cannot be empty.");
            }

            Sheet sheet = workbook.createSheet("Data Sheet");
            T sampleObject = dataList.get(0);

            // Get all fields of the object's class for column headers
            Field[] fields = sampleObject.getClass().getDeclaredFields();

            // Create the header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fields[i].getName());
            }

            // Create the data rows
            int rowIndex = 1;
            for (T dataObject : dataList) {
                Row dataRow = sheet.createRow(rowIndex++);
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(dataObject);
                    Cell cell = dataRow.createCell(i);

                    if (value != null) {
                        switch (value) {
                            case Number number ->
                                cell.setCellValue(number.doubleValue());
                            case Date date -> {
                                cell.setCellValue(date);
                                CellStyle cellStyle = workbook.createCellStyle();
                                cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy-mm-dd"));
                                cell.setCellStyle(cellStyle);
                            }
                            default ->
                                cell.setCellValue(value.toString());
                        }
                    }
                }
            }

            // Autosize the columns
            for (int i = 0; i < fields.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the workbook to the output stream
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }
}
