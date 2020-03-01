package readers;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {
    protected XSSFSheet ExcelWSheet;
    protected XSSFWorkbook ExcelWBook;
    protected XSSFCell Cell;
    protected XSSFRow Row;

    public void setExcelFile(String path) throws Exception {

        try(FileInputStream inputStream = new FileInputStream(path)) {
            ExcelWBook = new XSSFWorkbook(inputStream);
        }

    }

    @SuppressWarnings("null")
    public String[][] getExcelSheetData(String sheetName) throws Exception {
        String[][] data = null;
        String[][] mydata = null;

        if (ExcelWBook != null) {
            XSSFSheet sheet = ExcelWBook.getSheet(sheetName);
            if (sheet != null) {
                int rows = sheet.getLastRowNum();
                int cols = sheet.getRow(sheet.getLastRowNum()).getPhysicalNumberOfCells();
                int arrayrow = rows+1;
                data = new String[arrayrow][cols];
                mydata = new String[rows][cols];


                for (int i = 0; i < arrayrow; i++) {
                    for (int j = 0; j < cols; j++) {
                        org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(i).getCell(j);
                        String cellData = cell.getStringCellValue();
                        data[i][j] = cellData;
                    }
                }

                for(int m=0; m<rows; m++){
                    for (int n=0; n<cols; n++){
                        mydata[m][n] = data[m+1][n];

                    }
                }

            }
        }

        return mydata;
    }

}
