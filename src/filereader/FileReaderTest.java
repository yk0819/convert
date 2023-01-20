package filereader;

import java.io.File;
import java.io.IOException;

public class FileReaderTest {
	
	public static void main(String[] args) {

		// 생성자를 만들어서 안에 있는 메서드를 사용할수 있게 한다.
		UQStorageFile uqStorageFile = new UQStorageFile();
		EntrustStorageFile entrustStorageFile = new EntrustStorageFile();
		DataUsageFile dataUsageFile = new DataUsageFile();

		// 파일 생성할때는 안에 경로를 넣어주어야 한다
		// 다만 메서드 안에서 경로를 지정을 해주면 메서드 안의 경로로 실행이 됨
		String directory = "C:/dev/file/test/";
		File dir = new File(directory);
		
		try {
			uqStorageFile.UQStorageFileProcessed(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("출하 리스트를 업로드 할 수 없습니다");
			e.printStackTrace();
		}
		try {
			entrustStorageFile.EntrustStorageFileProcessed(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("출하 리스트를 업로드 할 수 없습니다");
			e.printStackTrace();
		}
		try {
			dataUsageFile.dataConvert(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("데이터 사용량 파일을 업로드 할 수 없습니다");
			e.printStackTrace();
		}
		
	}
}