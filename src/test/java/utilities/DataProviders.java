package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	/**
	 * @author Avoy kumar Roy
	 */
	
	@DataProvider(name="loginData")
	public String[][] loginDataCombination() {

		String path = ".//testData//Book1.xlsx";

		ExcelLibrary lib = new ExcelLibrary(path);
		int row = lib.getRowCount("Sheet1");
		int col = lib.getColumnCount("Sheet1");

		String data[][] = new String[row][col];

		for (int i = 1; i <row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.println(lib.getCellData("Sheet1", j, i));
				data[i][j]=lib.getCellData("Sheet1", j, i);
			}
		}

		return data;

	}

//	public static void main(String[] args) {
//		DataProvider dp = new DataProvider();
//		String[][] excelVal = dp.getData();
////		System.out.println();
////		for(String[] p:excelVal) {
////			for(String value: p) {
////				System.out.println(value+" ");
////			}
////		}X
////		Arrays.stream(excelVal)
////			.map(row-> String.join(" ", row))
////			.forEach(System.out::println);
//	}
}
