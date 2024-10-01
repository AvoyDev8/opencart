/**
 * 
 */
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Avoy kumar Roy
 *
 */
public class ExcelLibrary {

	public static String path = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\test.xlsx";

	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	private XSSFCellStyle cstyle = null;

	// constructor
	public ExcelLibrary() {
		// this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// constructor accepts arguments
	public ExcelLibrary(String path) {
		ExcelLibrary.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get row count
	public int getRowCount(String sheetName) {
		int sheetIndex = workbook.getSheetIndex(sheetName);
		if (sheetIndex <= -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(sheetIndex);
			int num = sheet.getLastRowNum() + 1;
			return num;
		}
	}

	// get column count
	public int getColumnCount(String sheetName) {
		int sheetIndex = workbook.getSheetIndex(sheetName);
		if (sheetIndex < -1)
			return 0;

		sheet = workbook.getSheetAt(sheetIndex);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();
	}

	// getCell value
	public String getCellData(String Sheetname, String colName, int rowNum) {

		try {
			if (rowNum < 0)
				return "";

			int sheetIndex = workbook.getSheetIndex(Sheetname);
			int colNum = -1;
			if (sheetIndex == -1)
				return "";
			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(0);

			// to grab the column number
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					colNum = i;
				}
			}

			if (colNum <= -1)
				return "";

			row = sheet.getRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (row == null)
				return "";

			// ---------------------to grab the cell value-------------------------//

			// for string type
			if (cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();

			// for numeric and calendar type
			else if (cell.getCellType().name().equals("NUMERIC") || cell.getCellType().name().equals("FORMULA")) {

				// if cell consist numeric value
				String cellText = String.valueOf(cell.getNumericCellValue());

				// ---------if cell consist Date-------------------//
				cstyle = cell.getCellStyle();
				String dformat = cstyle.getDataFormatString();
				boolean isDate = dformat.contains("d") || dformat.contains("m") || dformat.contains("y");
				if (isDate) {
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					cellText = df.format(cell.getDateCellValue());
				}
				return cellText;
			}
			// FOR BLANK TYPE
			else if (cell.getCellType().name().equals("BLANK")) {
				return "";
			}
			// FOR BOOLEAN TYPE
			else {
				return String.valueOf(cell.getBooleanCellValue());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	// RETURN CELL VALUE ----when input type of cell is int
	public String getCellData(String sheetname, int colNum, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}

			// Sheet index
			int sheetIndex = workbook.getSheetIndex(sheetname);
			if (sheetIndex == -1) {
				return "";
			}

			// To grab sheet
			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(rowNum);
			if (row == null) {
				return "";
			}

			// Check column number
			if (colNum < 0 || colNum >= row.getPhysicalNumberOfCells()) {
				return "";
			}

			cell = row.getCell(colNum);
			if (cell == null) {
				return "";
			}

			// ------------TO GRAB CELL VALUE-----------------------------//
			switch (cell.getCellType()) {
			
			case STRING:
				return cell.getStringCellValue();
				
			case NUMERIC:
				String cellText = String.valueOf(cell.getNumericCellValue());

				// Checking  if the cell contains a date
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					return df.format(cell.getDateCellValue());
				}
				return cellText;
				
			case BLANK:
				return "";
				
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
				
			case FORMULA:
				return cell.getCellFormula();
				
			default:
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist in xls";
		}
	}

	// TO SET CELL VALUE
	public boolean setCellData(String sheetname, int rowNum, int colNum, String data) throws IOException {
		try {
			File xlfile = new File(path);
			if (!xlfile.exists()) // if file not exist
			{
				workbook = new XSSFWorkbook();
				fos = new FileOutputStream(path);
				workbook.write(fos);
			}
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (workbook.getSheetIndex(sheetname) == -1) // if sheet not exist
				workbook.createSheet(sheetname);

			sheet = workbook.getSheet(sheetname);

			if (sheet.getRow(rowNum) == null)
				sheet.createRow(rowNum);
			row = sheet.getRow(rowNum);

			cell = row.createCell(colNum);
			cell.setCellValue(data);

			fos = new FileOutputStream(path);
			workbook.write(fos);
			workbook.close();
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Add sheet or create sheet
	public boolean addSheet(String sheetname) {
		try {
			workbook.createSheet(sheetname);
			fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Remove sheet or Delete the Sheet
	public boolean removeSheet(String sheetname) {
		try {
			int sheetIndex = workbook.getSheetIndex(sheetname);
			if (sheetIndex <= -1)
				return false;

			fos = new FileOutputStream(path);
			workbook.removeSheetAt(sheetIndex); // removing sheet
			workbook.write(fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Add column in the sheet
	public boolean addColumn(String sheetname, String colName) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int sheetIndex = workbook.getSheetIndex(sheetname);
			if (sheetIndex == -1)
				return false;
			// creating cell style here
			cstyle = workbook.createCellStyle();

			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			if (row.getLastCellNum() == -1) {
				cell = row.createCell(0);
				cell.setCellValue(colName);
			} else {
				int colNum = row.getLastCellNum();
				cell = row.createCell(colNum + 1);
				cell.setCellValue(colName);
				cell.setCellStyle(cstyle);
				fos = new FileOutputStream(path);
				workbook.write(fos);
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// Sheet Exist in the excel sheet or not
	public boolean isSheetExist(String sheetname) {
		int sheetIndex = workbook.getSheetIndex(sheetname);
		if (sheetIndex == -1) {
			sheetIndex = workbook.getSheetIndex(sheetname.toUpperCase());
			if (sheetIndex == -1)
				return false;
			else
				return true;
		}
		return true;
	}

	// Remove Column from the sheet
	public boolean removeColumn(String sheetname, int colNum) {
		try {
			// TO CHECK THE SHEET EXISTANCE
			if (!isSheetExist(sheetname))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			cstyle = workbook.createCellStyle();

			// delete column value one by one
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(cstyle); // setting cell style before deleting its value
						row.removeCell(cell);
					}
				}
			}

			// writing into the excel file
			fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// cell and row number
	public int getCellRowNum(String sheetname, String colName, String cellValue) {

		for (int i = 2; i < getRowCount(sheetname); i++) {
			if (getCellData(sheetname, colName, i).equalsIgnoreCase(cellValue))
				return i;
		}
		return -1;
	}
}
