package util;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 *
 */
public class ExcelUtil {

    /**
     * 读取Excel指定行和列的数据
     *
     * @param excelPath     excel路径
     * @param sheetIndexStr sheet下标，0开始
     * @param startRowStr   开始行，1开始
     * @param endRowStr     结束行
     * @param startCellStr  开始列，1开始
     * @param endCellStr    结束列
     * @return data对象
     */
    public static Object[][] readExcel(String excelPath, String sheetIndexStr, String startRowStr, String endRowStr,
                                       String startCellStr, String endCellStr) {

        int sheetIndex = Integer.parseInt(sheetIndexStr);
        int startRow = Integer.parseInt(startRowStr);
        int endRow = Integer.parseInt(endRowStr);
        int startCell = Integer.parseInt(startCellStr);
        int endCell = Integer.parseInt(endCellStr);

        return readExcel(excelPath, sheetIndex, startRow, endRow, startCell, endCell);
    }

    public static Object[][] readExcel(String excelPath, int sheetIndex, int startRow, int endRow, int startCell,
                                       int endCell) {
        Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 1];
        Workbook workbook;
        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        try {
            workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            for (int i = startRow; i <= endRow; i++) {
                Row row = sheet.getRow(i - 1);
                for (int j = startCell; j <= endCell; j++) {
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    datas[i - startRow][j - startCell] = cellValue;
                }
            }
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * @param excelPath     文件存放绝对路径
     * @param sheetIndexStr sheet下标，0开始
     * @param startRowStr   开始行，1开始
     * @param endRowStr     结束行
     * @param startCellStr  开始列，1开始
     * @param endCellStr    结束列
     * @return data数据对象
     */

    public static Object[][] readExcelLocal(String excelPath, String sheetIndexStr, String startRowStr, String endRowStr,
                                            String startCellStr, String endCellStr) {

        int sheetIndex = Integer.parseInt(sheetIndexStr);
        int startRow = Integer.parseInt(startRowStr);
        int endRow = Integer.parseInt(endRowStr);
        int startCell = Integer.parseInt(startCellStr);
        int endCell = Integer.parseInt(endCellStr);

        return readExcelLocal(excelPath, sheetIndex, startRow, endRow, startCell, endCell);
    }

    public static Object[][] readExcelLocal(String excelPath, int sheetIndex, int startRow, int endRow, int startCell,
                                            int endCell) {
        Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 1];
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(excelPath)));
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(bis);
            Sheet sheet = xssfWorkbook.getSheetAt(sheetIndex);
            for (int i = startRow; i <= endRow; i++) {
                Row row = sheet.getRow(i - 1);
                for (int j = startCell; j <= endCell; j++) {
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    datas[i - startRow][j - startCell] = cellValue;
                }
            }
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Downloads\\t_plm_department.xls";
        Object[][] datas = ExcelUtil.readExcelLocal(path, "0", "0", "12", "2", "14");
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.print("[" + object.toString() + "]");
            }
            System.out.println();
        }
    }
}
