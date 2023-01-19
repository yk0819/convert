package filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EntrustStorageFile {
	
	WriteMethods writeMethods = new WriteMethods();

	public void EntrustStorageFileProcessed(File dir) throws IOException{
		
		String directory = "C:/dev/file/06_委託倉庫出荷実績通知情報/";
		
		String recodeCode; // レコード区分
		String iccid; // ICCID
		String imsi; // IMSI
		String msn; // 電話番号
		String pukCode; // PUKコード
		String imei; // IMEI
		String productCode; // 商品コード
		String orderNumber; // 発注申込番号
		String orderBranchNumber;// 発注申込番号枝番
		String destinationCode; // 出荷先拠点コード
		String shipmentDate; // 出荷日
		String shippingCode; // 運送会社コード
		String trackingNumber; // 配送番号
		String reserve; // 予備
		String lineCode; // 改行コード
		
		dir = new File(directory);
		String[] fileList = dir.list();	// all list
		String[] fileFilterList; // filter list

		// 경로 안에 있는 파일 이름의 리스트를 반환
		for(int i = 0; i < fileList.length; i++) {

			List<List<String>> iccidList = new ArrayList<List<String>>();
			List<List<String>> imeiList = new ArrayList<List<String>>();
			
			if(!fileList[i].endsWith("_1.nst")) { // 아직 처리되지 않은 파일
				fileFilterList = fileList;
				System.out.println("file: " + fileFilterList[i]); // 지역으로 인식됨
				
				File file = new File(directory + fileFilterList[i]);
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				
				String line = "";
				
				while((line = br.readLine()) != null) { // 한줄씩 읽어옴, 라인만큼 루프한다
					
					List<String> inIccidList = new ArrayList<String>();
					List<String> inImeiList = new ArrayList<String>();

					recodeCode = line.substring(0,1);
					iccid = line.substring(1,20); // 1~20 미만
					imsi = line.substring(20,35);
					msn = line.substring(35,46);
					pukCode = line.substring(46,54);
					imei = line.substring(54,69);
					productCode = line.substring(69,77);
					orderNumber = line.substring(77,86);
					orderBranchNumber = line.substring(86,88);
					destinationCode = line.substring(88,95);
					shipmentDate = line.substring(95,103);

					// 루프를 돌때마다 해당 값을 찾아내서 저장
					if(iccid.replace(" ", "").length() == 0) { // " "를 ""으로 없앤다. 공백을 아예 없앤뒤 iccid의 길이가 0과 같다면
						inImeiList.add(imei); // iccid는 존재하지 않으므로 imei 리스트에 넣는다
						inImeiList.add(productCode);
						inImeiList.add(orderNumber); // 공백
						inImeiList.add(orderBranchNumber);
						inImeiList.add(destinationCode);
						inImeiList.add(shipmentDate);
						imeiList.add(inImeiList); // 확인한 후 반복문 안에 넣어주어야 빈배열이 생기지 않음..
					} else {

						String check = msn.substring(0,3); // 0200인 경우, 앞에 020이 짤리고 012가 된다
						if(!check.equals("090") && !check.equals("080") && !check.equals("070")) {
							msn = "020" + msn; // 01293039160 -> 02001293039160
						}
						inIccidList.add(iccid);
						inIccidList.add(imsi);
						inIccidList.add(msn);
						inIccidList.add(pukCode);
						inIccidList.add(productCode);
						inIccidList.add(destinationCode);
						inIccidList.add(shipmentDate);
						iccidList.add(inIccidList);
					}
					
				} // 한줄 루프 끝, 다시 올라가서 다음 라인 읽어오기
				// 라인을 전부 다 읽어오면 하나의 파일을 처리한 것으로 밑에 로직이 실행된다

				// iemi, iccid list created
				String filename = fileFilterList[i].substring(0, 17); // 확장자명을 제외한 파일명
				
				writeMethods.excelWriterIMEI(directory + filename + "_imei.xlsx", imeiList);
				writeMethods.excelWriterICCID(directory + filename + "_iccid.xlsx", iccidList);
				reader.close();
				
				// Files.move
				try {
					Path source = Paths.get(directory + fileFilterList[i]); // 기존 파일, 18_20210521074551.nst
					Files.move(source, source.resolveSibling(directory + filename + "_1.nst"));
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}	
}
