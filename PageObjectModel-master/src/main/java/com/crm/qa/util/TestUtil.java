package com.crm.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.crm.qa.base.TestBase;
import com.crm.qa.models.Column;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 60;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/src/main/java/com/crm/qa/testdata/FreeCrmTestData.xlsx";
	
	static Workbook book;
	static Sheet sheet;
	
	
	public void switchToFrame(){
		driver.switchTo().frame("mainpanel");
	}
	
	public static <T extends Object> Object[][] getTestData(String sheetName, Class<T> type) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, FieldType> annotationsMap = TestUtil.getAnnotations(type);
		
		sheet = book.getSheet(sheetName);
		String columnName;
		FieldType fieldt;
		Object[][] data = new Object[sheet.getLastRowNum()][1];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			T obj = null;
			try {
				obj = type.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				columnName = sheet.getRow(0).getCell(k).toString();
				fieldt = annotationsMap.get(columnName);
				if(fieldt != null) {
					try {
						
						type.getDeclaredField(fieldt.name).set(obj, TestUtil.parseValue(fieldt.type, sheet.getRow(i + 1).getCell(k)));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			
			data[i][0] = obj;
		}
		return data;
	}
	
	public static <T extends Object> HashMap<String, FieldType> getAnnotations(Class<T> clazz){
		HashMap<String, FieldType> annotationsMap = new HashMap<String, FieldType>();
	    for(Field field : clazz.getDeclaredFields()){
	    	Column annotation = field.getAnnotation(Column.class);
	    	if (annotation != null) {
		    	FieldType ft = new FieldType();
		        ft.type = field.getType();
		        ft.name = field.getName();
		        annotationsMap.put(annotation.name(), ft) ;
	    	}
	    }
	    
	    return annotationsMap;
	}
	
	public static Object parseValue (Class type, Cell cell) {
		 if (type == BigDecimal.class) {
		      return new BigDecimal(cell.getNumericCellValue());
		    } else if (type == BigInteger.class) {
		      return new BigInteger(cell.getStringCellValue());
		    } else if (type == Boolean.class) {
		      return cell.getBooleanCellValue();
		    } else if (type == Byte.class) {
		      return Byte.valueOf(cell.toString());
		    } else if (type == Date.class) {
		      return cell.getDateCellValue();
		    } else if (type == Double.class) {
		      return cell.getNumericCellValue();
		    } else if (type == Float.class) {
		      return Float.valueOf((float) cell.getNumericCellValue());
		    } else if (type == Integer.class || type == int.class) {
		      return Integer.valueOf((int) cell.getNumericCellValue());
		    }else if (type == Integer.class) {
			      return Integer.valueOf((int) cell.getNumericCellValue());
		    } else if (type == Long.class) {
		      return Long.valueOf((long) cell.getNumericCellValue());
		    } else if (type == Short.class) {
		      return Short.valueOf((short) cell.getNumericCellValue());
		    } else if (type == String.class) {
		      return cell.getStringCellValue();
		    }
		return cell;
	}
}
