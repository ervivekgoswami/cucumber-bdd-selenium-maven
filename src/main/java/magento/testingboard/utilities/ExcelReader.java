package magento.testingboard.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ExcelReader {

    public static FileInputStream fileInput;
    public static FileOutputStream fileOutPut;
    public static XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public String filePath;

    public ExcelReader(String filePath) {
        try {
            this.filePath = filePath;
            fileInput = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCellValue(Cell cell) {
        String cellStringValue = "";
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            cellStringValue = String.valueOf(cell.getNumericCellValue());
        } else if (cellType == CellType.STRING) {
            cellStringValue = cell.getStringCellValue();
        } else if (cellType == CellType.FORMULA) {
            cellStringValue = cell.getStringCellValue();
        } else if (cellType == CellType.BOOLEAN) {
            cellStringValue = String.valueOf(cell.getNumericCellValue());
        }
        return cellStringValue;
    }

    public void setCellValue(Cell cell, String value) {
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            cell.setCellValue(value);
        } else if (cellType == CellType.STRING) {
            cell.setCellValue(value);
        } else if (cellType == CellType.FORMULA) {
            cell.setCellValue(value);
        } else if (cellType == CellType.BOOLEAN) {
            cell.setCellValue(value);
        }
    }

    public XSSFSheet getWorkBookSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet '" + sheetName + "' not found.");
        }
        return sheet;
    }

    public String readScenarioData(String sheetName, String scenarioName, String headers) {
        sheet = getWorkBookSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        Cell scCell;
        String scName;
        Cell headerCell;
        String headerValue;
        Cell resultCell;
        String resultValue = "";
        int scFound = 0;
        int headerFound = 0;
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            scCell = sheet.getRow(i).getCell(0);
            scName = getCellValue(scCell);
            if (scName.equalsIgnoreCase((scenarioName))) {
                scFound++;
                for (int j = 1; j < headerRow.getLastCellNum(); j++) {
                    headerCell = sheet.getRow(0).getCell(j);
                    headerValue = getCellValue(headerCell);
                    if (headerValue.equals(headers)) {
                        headerFound++;
                        resultCell = sheet.getRow(i).getCell(j);
                        resultValue = getCellValue(resultCell);
                        System.out.println("Scenario: " + scName + " Header " + headers + " value is: " + resultValue);
                        break;
                    }
                }
                break;
            }
        }
        if (scFound == 0) {
            throw new IllegalArgumentException("Scenario with name " + scenarioName + " not found in excel sheet " + sheetName);
        } else if (headerFound == 0) {
            throw new IllegalArgumentException("Header with name " + headers + " not found in excel sheet " + sheetName);
        }
        return resultValue;
    }


    public void writeToExcelByHeader(String sheetName, String header) {
        sheet = getWorkBookSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        Cell headerCell;
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headerCell = headerRow.getCell(i);
            String headerName = getCellValue(headerCell);
            if (headerName.equalsIgnoreCase(header)) {
                System.out.println("Header: "+ headerName);
            }
        }
        try {
            fileInput.close();
            fileOutPut = new FileOutputStream(filePath);
            workbook.write(fileOutPut);
            fileOutPut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Function to get a list of features to execute
     * @param sheetName the Excel sheet name
     * @return ArrayList
     * @author Vivek K
     */
    public ArrayList<String> featureToExecute(String sheetName) {
        XSSFRow row;
        String excelSheetScenarioName = "";
        ArrayList<String> featuresToExecute = new ArrayList<>();
        try {
            sheet = getWorkBookSheet(sheetName);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                try {
                    row = sheet.getRow(i);
                    String ifExcelSheetExecution = row.getCell(1).toString();
                    if (ifExcelSheetExecution.equalsIgnoreCase("Y"))
                        excelSheetScenarioName = sheet.getRow(i).getCell(0).toString().trim();
                    featuresToExecute.add(excelSheetScenarioName);
                } catch (NullPointerException n) {
                    System.out.println("Error Reading Excel Data: " + n.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error Reading " + sheetName + " Sheet Data: " + e.getMessage());
        }
        return featuresToExecute;
    }

    public void closeExcelFile() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to fetch the below details from datasheet with, features and scenario(s) to Execute and testData for those scenario(s)
     * @param sheetName the Excel sheet name
     * @return HashMap
     * @author Vivek K
     */
    public HashMap<String, ArrayList<HashMap<String, String>>> masterExecutionData(String sheetName) {
        HashMap<String, ArrayList<HashMap<String, String>>> allExecutingScenariosTestData = new LinkedHashMap<>();
        try {
            XSSFRow row;

            //get a list of features to be executed
            ArrayList<String> featuresToExecute = featureToExecute(sheetName);

            //TestData collection based on a feature list
            for (String featureName : featuresToExecute) {
                try {
                    sheet = getWorkBookSheet(featureName);
                    int columnCount = sheet.getRow(0).getLastCellNum();
                    for (int i = 1; i < sheet.getLastRowNum(); i++) {
                        ArrayList<HashMap<String, String>> tempList = new ArrayList<>();
                        HashMap<String, String> singleDataSet = new LinkedHashMap<>();
                        row = sheet.getRow(i);
                        //Dataset from the specific sheet
                        if (row.getCell(1).toString().equalsIgnoreCase("Y")) {
                            singleDataSet.put("RowNumber", "" + i);
                            singleDataSet.put("FeatureName", featureName);
                            for (int j = 0; j < columnCount; j++) {
                                try {
                                    XSSFCell cell = row.getCell(j);
                                    cell.setCellType(CellType.STRING);
                                    singleDataSet.put(sheet.getRow(0).getCell(j).toString().trim(), cell.toString().trim());
                                } catch (NullPointerException n) {
                                    System.out.println(n.getMessage());
                                }
                            }
                            if (allExecutingScenariosTestData.containsKey(singleDataSet.get("Scenario"))) {
                                tempList = allExecutingScenariosTestData.get(singleDataSet.get("Scenario"));
                            }
                            tempList.add(singleDataSet);
                            allExecutingScenariosTestData.put(singleDataSet.get("Scenario"), tempList);
                        }
                    }
                } catch (NullPointerException n) {
                    System.out.println(n.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allExecutingScenariosTestData;
    }
}
