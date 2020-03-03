package readers;

import config.Config;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {
    protected XSSFSheet excelSheet;
    protected XSSFWorkbook excelWBook;
    protected XSSFCell Cell;
    protected XSSFRow Row;

    public void  setExcelFile(String path) throws Exception {

        try (FileInputStream inputStream = new FileInputStream(Config.testResource + path)) {
            excelWBook = new XSSFWorkbook(inputStream);
        }

    }

    @SuppressWarnings("null")
    public String[][] getExcelSheetData(String sheetName) throws Exception {
        return readeSheet(excelWBook.getSheet(sheetName));
    }
    public String[][] getExcelSheetData(int index) throws Exception {
        return readeSheet(excelWBook.getSheetAt(index));
    }


    private String[][] readeSheet(XSSFSheet sheet) {
        if (sheet == null) {
            throw new IllegalArgumentException("sheet does not exist!");
        }
        String[][] data = null;
        String[][] mydata = null;
        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(sheet.getLastRowNum()).getPhysicalNumberOfCells();
        int arrayrow = rows + 1;
        data = new String[rows + 1][cols];
        mydata = new String[rows][cols];


        for (int i = 0; i < arrayrow; i++) {
            for (int j = 0; j < cols; j++) {
                org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(i).getCell(j);
                cell.setCellType(CellType.STRING);
                String cellData = cell.getStringCellValue();
                data[i][j] = cellData;
            }
        }

        for (int m = 0; m < rows; m++) {
            for (int n = 0; n < cols; n++) {
                mydata[m][n] = data[m + 1][n];

            }
        }

        return mydata;


    }


}
