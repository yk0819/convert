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

public class DataUsageFile {
	
	WriteMethods writeMethods = new WriteMethods();
	
	public void dataConvert (File dir) throws IOException {

		String directory = "C:/dev/file/01_H01_通信明細/";
		
		String recodeType; // レコードタイプ
		String recodeCode; // レコード区分
		String detailIdentificationCode; // 明細識別コード
		String billingCode ; // 請求先コード
		String usageDate; // 対象明細日付
		
		String subscriberCode; // 加入者コード
		String msn; // 電話番号
		String NWResourceID; // 発NWリソースID
		String domeinCode; // ドメインコード
		String dataEndDate; // 通信終了日
		
		String dataEndTime; // 通信終了時刻
		String chargingContractor; // 課金電話契約事業者
		String chargingTime; // 課金通信時間
		String dataCount; // 通信回数
		String upStreamSpeed ; // 上りオクテット数
		
		String downStreamSpeed ; // 下りオクテット数
		String NWResourceIDCode; // 発NWリソースID種別
		String outgoingServiceProvider; // 発信役務提供事業者
		String callDetailType; // 通話明細種類
		String callType; // 通信種別 20まで
		
		String callTypeName; // 通信種別名称
		String dataTransmissionSpeedCode ; // 回線速度区分
		String dataTransmissionSpeedCodeName; // 回線速度区分名称
		String contactsCode; // 通話先区分
		String contactsCodeName; // 通話先区分（料金区分）名称
		
		String internationalRoamingCode; // 国際ローミング区分
		String useArea; // ご利用地域
		String useAreaName; // ご利用地域名称
		String connectionState; // 接続状態区分（タリフ区分）
		String rateCalculationCount; // 料金計算回数

		String networkCode; // ネットワーク区分		
		String ratePlan ; // 料金プラン
		String memberServiceInfo; // タリフィングお客様情報
		String branchOfficeCode; // 支社コード
		String branchOfficeName; // 支社名
		
		String paymentGroup; // 支払群
		String demoCode; // デモ機区分
		String MVNOCode; // MVNO識別
		String reserve; // 予備
		String lineCode; // 改行コード
		
		dir = new File(directory);
		String[] fileList = dir.list();	// all list
		String[] fileFilterList; // filter list
	
		// 경로 안에 있는 파일 이름의 리스트를 반환
		for(int i = 0; i < fileList.length; i++) {
	
			List<List<String>> dataList = new ArrayList<List<String>>();

			if(!fileList[i].endsWith("_1.atm") && !fileList[i].endsWith("_data.xlsx")) { // 아직 처리되지 않은 파일
				
				fileFilterList = fileList;
				System.out.println("file: " + fileFilterList[i]); // 지역으로 인식됨
				
				File file = new File(directory + fileFilterList[i]);
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				
				String line = "";
				
				while((line = br.readLine()) != null) { // 한줄씩 읽어옴, 라인만큼 루프한다
					
					List<String> list = new ArrayList<String>();

					// str -> byte
					byte[] bytes = line.getBytes();
	                 
		               // byte 배열로 만들어서 잘라내기
		               byte[] usageDateArr = new byte[8]; // 자를려고 하는 크기
		               byte[] subscriberCodeArr = new byte[8]; 
		               byte[] msnArr = new byte[21];
		               byte[] upStreamSpeedArr = new byte[15]; // 上りオクテット数
		               byte[] downStreamSpeedArr = new byte[15]; // 上りオクテット数
		               byte[] callTypeArr = new byte[2]; // 通信種別
		               byte[] ratePlanArr = new byte[4]; // 料金プラン

		               System.arraycopy(bytes, 12, usageDateArr, 0, 8); // 원본 바이트배열, 지정한 인덱스 값부터 가져온다, 복사하는 배열, 0번부터 8까지 채운다
		               System.arraycopy(bytes, 20, subscriberCodeArr, 0, 8);
		               System.arraycopy(bytes, 28, msnArr, 0, 21);
		               System.arraycopy(bytes, 118, upStreamSpeedArr, 0, 15);
		               System.arraycopy(bytes, 133, downStreamSpeedArr, 0, 15);
		               System.arraycopy(bytes, 156, callTypeArr, 0, 2);
//		               System.arraycopy(bytes, 259, ratePlanArr, 0, 4); byte로 하면 이거...
		               ratePlan = line.substring(230, 234); // sub로 하면 이거..
		               
		               usageDate = new String(usageDateArr);
		               subscriberCode = new String(subscriberCodeArr);
		               msn = new String(msnArr);
		               upStreamSpeed = new String(upStreamSpeedArr);
		               downStreamSpeed = new String(downStreamSpeedArr);
		               callType = new String(callTypeArr, "SHIFT-JIS");
//		               ratePlan = new String(ratePlanArr);

		               // msn index 21 공백이 있는 경우 잘라내기 trim
		               String phoneNumber = msn.trim();

		               list.add(usageDate);
		               list.add(subscriberCode);
		               list.add(phoneNumber);
		               list.add(upStreamSpeed);
		               list.add(downStreamSpeed);
		               list.add(callType);
		               list.add(ratePlan);
		               dataList.add(list);

				} // 한줄 루프 끝, 다시 올라가서 다음 라인 읽어오기
				// 라인을 전부 다 읽어오면 하나의 파일을 처리한 것으로 밑에 로직이 실행된다

				String filename = fileFilterList[i].substring(0, 17); // 확장자명을 제외한 파일명
				writeMethods.excelWriterDATA(directory + filename + "_data.xlsx", dataList);
	
				reader.close();
				
				// Files.move
				try {
					Path source = Paths.get(directory + fileFilterList[i]); // 기존 파일, 18_20210521183000.atm
					Files.move(source, source.resolveSibling(directory + filename + "_1.atm"));
				}catch(Exception e) {
					System.out.println(e);
				}
			} // if end
		}
	}

}
