/*package dlmu.oa.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;


public class TestFile {
	
	@Test
	public void TestFileRenameTo(){
		File file = new File("C:\\Users\\CGK\\Desktop\\JVM面试题.docx");
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_file");
		String subPath = sdf.format(new Date());
		File dir = new File(basePath + subPath);
		if(!dir.exists()){
			dir.mkdirs(); //递归创建所有不存在的文件夹
		}
		String path = basePath + subPath + UUID.randomUUID().toString();
		
		dir.renameTo(new File(path)); //移动文件从临时区到指定目录下
		if(file.exists())
		{
			
		}
	}

}
*/