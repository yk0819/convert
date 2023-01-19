package filereader;

import java.io.File;
import java.io.IOException;

public class FileReaderTest {
	
	public static void main(String[] args) throws IOException {

		// 생성자를 만들어서 안에 있는 메서드를 사용할수 있게 한다.
		UQStorageFile uqStorageFile = new UQStorageFile();
		EntrustStorageFile entrustStorageFile = new EntrustStorageFile();
		DataUsageFile dataUsageFile = new DataUsageFile();

		String directory = "C:/dev/file/test/";
		File dir = new File(directory);
		
		uqStorageFile.UQStorageFileProcessed(dir);
		entrustStorageFile.EntrustStorageFileProcessed(dir);
		dataUsageFile.dataConvert(dir);
		
	}
}