package utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

//import parentClass.BaseInit;
import parentClass.BaseInit3;


public class UtilityMethods extends BaseInit3{

	//This method is to check the execution status for Test Suite
	public static boolean checkTestSuiteForExecution(String testSuiteName, XSSFWorkbook data){
		
		XSSFSheet ExcelWSheet = data.getSheet("TestSuite");
		int rows = ExcelWSheet.getLastRowNum();
		
		Row row1 = ExcelWSheet.getRow(0);       
	    
		//Get column's number where "TestSuiteID" column is located
		int colNum = -1;
	    for (int i = 0 ;i<=row1.getLastCellNum();i++){
	         Cell cell1 = row1.getCell(i);
	         String cellValue1 = cell1.getStringCellValue();
	         if ("TestSuiteID".equals(cellValue1)){
	              colNum = i ;
	              break;
	         }
        }
		
	    //Get column's number where "Execute" column is located
  		int colNum2 = -1;
  	    for (int i = 0 ;i<=row1.getLastCellNum();i++){
  	         Cell cell1 = row1.getCell(i);
  	         String cellValue1 = cell1.getStringCellValue();
  	         if ("Execute".equals(cellValue1)){
  	              colNum2 = i ;
  	              break;
  	         }
        }
		
		for(int row=1;row<=rows;row++){
			
			if((ExcelWSheet.getRow(row).getCell(colNum).getStringCellValue()).equalsIgnoreCase(testSuiteName)){
							
				if((ExcelWSheet.getRow(row).getCell(colNum2).getStringCellValue()).equalsIgnoreCase("Y"))
					
					return true;
				else
					return false;
			}
		}
		return false;
	}
	
	//This method is to check the execution status for Test Case
	public static boolean checkTestCaseForExecution(String testCaseName, XSSFWorkbook data){
		
		XSSFSheet ExcelWSheet = data.getSheet("TestScripts");
		int rows = ExcelWSheet.getLastRowNum();
		
		Row row1 = ExcelWSheet.getRow(0);       
	    
		//Get column's number where TestID column is located
		int colNum = -1;
	    for (int i = 0 ;i<=row1.getLastCellNum();i++){
	         Cell cell1 = row1.getCell(i);
	         String cellValue1 = cell1.getStringCellValue();
	         if ("TestID".equals(cellValue1)){
	             colNum = i ;
	              break;}
        }
		
    	//Get column's number where Execute column is located
  		int colNum2 = -1;
  	    for (int i = 0 ;i<=row1.getLastCellNum();i++){
  	         Cell cell1 = row1.getCell(i);
  	         String cellValue1 = cell1.getStringCellValue();
  	         if ("Execute".equals(cellValue1)){
  	              colNum2 = i ;
  	              break;}
        }
	
		for(int row=1;row<=rows;row++){
			
			if((ExcelWSheet.getRow(row).getCell(colNum).getStringCellValue()).equalsIgnoreCase(testCaseName)){
							
				if((ExcelWSheet.getRow(row).getCell(colNum2).getStringCellValue()).equalsIgnoreCase("y"))
					
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public static void writeResult(int rowNumber, int colNumber, String caseResult, XSSFWorkbook data, String sheetName, String fileName) throws IOException {
		
		try {
			XSSFSheet Sheet=data.getSheet(sheetName);
					
			XSSFRow ResultRow = Sheet.getRow(rowNumber);
		
			XSSFCell ResultCell = ResultRow.getCell(colNumber);
			
			if (ResultCell == null) {
		
				ResultCell = ResultRow.createCell(colNumber);
		
				ResultCell.setCellValue(caseResult);
		
				} else {
		
					ResultCell.setCellValue(caseResult);
				}
				FileOutputStream outFile =new FileOutputStream(new File(System.getProperty("user.dir")+"//src//myFiles//"+fileName+".xlsx"));
				data.write(outFile);
				outFile.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void commonOpenModuleMspEng(String mainMenu, String moduleName) {
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
       	verifyXpath("lnk_"+mainMenu+"_menu_mspeng").click();
       	try {
       		driver.findElement(By.linkText(moduleName)).click();
       	}
       	catch(NoSuchElementException ne) {
       		ne.printStackTrace();
       	}
	}
	
	public static void commonOpenModule(String mainMenu, String moduleName) {
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
       	verifyXpath("lnk_"+mainMenu+"_menu").click();
       	try {
       		driver.findElement(By.linkText(moduleName)).click();
       	}
       	catch(NoSuchElementException ne) {
       		ne.printStackTrace();
       	}
	}
}
