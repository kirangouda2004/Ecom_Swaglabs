package ecom.bitm.swaglabs.utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtility(
            String filePath,
            String sheetName) throws IOException {

        FileInputStream fis =
                new FileInputStream(filePath);

        workbook = new XSSFWorkbook(fis);

        sheet = workbook.getSheet(sheetName);

        fis.close();

        if (sheet == null) {
            workbook.close();

            throw new IllegalArgumentException(
                    "Sheet not found: " + sheetName);
        }
    }

    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    public String getCellData(
            int rowNumber,
            int columnNumber) {

        Row row = sheet.getRow(rowNumber);

        if (row == null) {
            return "";
        }

        if (row.getCell(columnNumber) == null) {
            return "";
        }

        DataFormatter formatter =
                new DataFormatter();

        return formatter.formatCellValue(
                row.getCell(columnNumber));
    }

    public String getCellData(
            int rowNumber,
            String columnName) {

        Row headerRow = sheet.getRow(0);

        int columnNumber = -1;

        for (int i = 0;
             i < headerRow.getLastCellNum();
             i++) {

            String header =
                    headerRow.getCell(i)
                            .getStringCellValue()
                            .trim();

            if (header.equalsIgnoreCase(
                    columnName.trim())) {

                columnNumber = i;
                break;
            }
        }

        if (columnNumber == -1) {
            throw new IllegalArgumentException(
                    "Column not found: " + columnName);
        }

        return getCellData(
                rowNumber,
                columnNumber);
    }

    public int getRowNumber(
            String testCaseId) {

        for (int i = 1;
             i <= sheet.getLastRowNum();
             i++) {

            String id =
                    getCellData(i, 0).trim();

            if (id.equalsIgnoreCase(
                    testCaseId.trim())) {

                return i;
            }
        }

        throw new IllegalArgumentException(
                "Test Case ID not found: "
                + testCaseId);
    }

    public String getData(
            String testCaseId,
            String columnName) {

        int row =
                getRowNumber(testCaseId);

        return getCellData(
                row,
                columnName);
    }

    public void closeWorkbook()
            throws IOException {

        workbook.close();
    }
}