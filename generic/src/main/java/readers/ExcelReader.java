package readers;

import config.Config;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Arrays;

public class ExcelReader {
    protected XSSFSheet excelSheet;
    protected XSSFWorkbook excelWBook;
    protected XSSFCell Cell;
    protected XSSFRow row;

    public void setExcelFile(String path) throws Exception {

        try (FileInputStream inputStream = new FileInputStream(Config.testResource + path)) {
            excelWBook = new XSSFWorkbook(inputStream);
        }

    }

    @SuppressWarnings("null")
    public String[][] getExcelSheetData(String sheetName) throws Exception {
        return readeSheet(excelWBook.getSheet(sheetName));
    }

    public String[][] getExcelSheetData(int index) throws Exception {
        String[][] strings = readeSheet(excelWBook.getSheetAt(index));
        if (strings.length == 0) {
            throw new IllegalStateException("failed to get data from excel");
        }
        return strings;

    }


    private String[][] readeSheet(XSSFSheet sheet) {
        if (sheet == null) {
            throw new IllegalArgumentException("sheet does not exist!");
        }
        int rowCount = sheet.getLastRowNum() + 1;
        int colCount = sheet.getRow(0).getLastCellNum();
        String[][] data = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                XSSFCell cell = row.getCell(j);
                data[i][j] = cell.getStringCellValue();
            }
        }

        return data;
    }


}
