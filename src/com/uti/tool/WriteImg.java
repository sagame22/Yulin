package com.uti.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WriteImg {
	
	
	public static void writeImg(InputStream is,File file) {
	try {
		if(null!=is && 0!=is.available()){
			//把上傳的圖檔寫入剛創好的file中
		    try(FileOutputStream fos = new FileOutputStream(file)){
		        byte b[] = new byte[1024 * 1024];
		        int length = 0;
		        //把資料讀進b[],寫入
		        while (-1 != (length = is.read(b))) {
		            fos.write(b, 0, length);
		        }
		        fos.flush();
		      	
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		}
	} catch (IOException e) {
	
		e.printStackTrace();
	}	
	
}
}