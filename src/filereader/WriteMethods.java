package filereader;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteMethods {
	
	// methods
	public void excelWriterIMEI(String filePath, List<List<String>> imeiList) { // 파일경로와 리스트를 받는다
		
		XSSFWorkbook workbook = null; // file
		XSSFSheet sheet = null; // sheet
		XSSFRow row = null; // row 행 123 세로
		XSSFCell cell = null; // cell 열 abc 가로
		
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("sheet1");
			
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("IMEI"); // 0.0에 셀 객체를 생성
			
			cell = row.createCell(1);
			cell.setCellValue("商品コード");
			
			cell = row.createCell(2);
			cell.setCellValue("発注申込番号");

			cell = row.createCell(3);
			cell.setCellValue("発注申込番号枝番");
			
			cell = row.createCell(4);
			cell.setCellValue("出荷先拠点コード");

			cell = row.createCell(5);
			cell.setCellValue("出荷日");
			
			// 반복문으로 iemi의 사이즈만큼 배열 값을 넣는다
			for(int i = 0; i < imeiList.size(); i++) { // 행
				List<String> arr = imeiList.get(i); // list in list
				row = sheet.createRow(i+1); // index 1부터 입력
				// 행을 다 채웠다면 다음 열로 이동하여 행을 읽어온다
				for(int j = 0; j < arr.size(); j++) { // 열의 값만큼 돈다
					cell = row.createCell(j);
					cell.setCellValue(arr.get(j));
				}
			}
			
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
		
			if(fos != null) // 값을 다 채웠으면 close
				fos.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excelWriterICCID(String filePath, List<List<String>> iccidList) {
		
		XSSFWorkbook workbook = null; // file
		XSSFSheet sheet = null; // sheet
		XSSFRow row = null; // row 행 123 세로
		XSSFCell cell = null; // cell 열 abc 가로
		
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("sheet1");
			
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("ICCID");

			cell = row.createCell(1);
			cell.setCellValue("IMSI");
			
			cell = row.createCell(2);
			cell.setCellValue("MSN");
			
			cell = row.createCell(3);
			cell.setCellValue("PUK CODE");
			
			cell = row.createCell(4);
			cell.setCellValue("商品コード");
			
			cell = row.createCell(5);
			cell.setCellValue("出荷先拠点コード");

			cell = row.createCell(6);
			cell.setCellValue("出荷日");
			
			for(int i = 0; i < iccidList.size(); i++) {
				List<String> arr = iccidList.get(i);
				row = sheet.createRow(i+1);

				for(int j = 0; j < arr.size(); j++) {
					cell = row.createCell(j);
					cell.setCellValue(arr.get(j));
				}
			}
			
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
		
			if(fos != null) // 값을 다 채웠으면 close
				fos.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excelWriterDATA(String filePath, List<List<String>> dataList) {
		
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		XSSFRow row = null; // 세로
		XSSFCell cell = null; // 가로
		
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("sheet1");
			
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("対象詳細日付");

			cell = row.createCell(1);
			cell.setCellValue("加入者コード");
			
			cell = row.createCell(2);
			cell.setCellValue("電話番号");
			
			cell = row.createCell(3);
			cell.setCellValue("上りオクテット数");
			
			cell = row.createCell(4);
			cell.setCellValue("下りオクテット数");
			
			cell = row.createCell(5);
			cell.setCellValue("通信種別");

			cell = row.createCell(6);
			cell.setCellValue("料金プラン");
			
			for(int i = 0; i < dataList.size(); i++) {
				List<String> arr = dataList.get(i);
				row = sheet.createRow(i+1);

				for(int j = 0; j < arr.size(); j++) {
					cell = row.createCell(j);
					cell.setCellValue(arr.get(j));
				}
			}
			
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
		
			if(fos != null) // 값을 다 채웠으면 close
				fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
