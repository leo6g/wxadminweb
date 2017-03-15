package com.lfc.wxadminweb.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileDownLoadUtil {
	
	
	private static final Logger logger = LoggerFactory.getLogger(FileDownLoadUtil.class);
	
	
	
	/**
	 * 访问外链URL地址，下载文件到本地
	 * @param url 外链URL
	 * @param fileDownLoadPath 文件下载地址
	 * @return 下载成功标识
	 */  
    public static boolean downLoadFile(String url, String fileDownLoadPath) {  
        try {  
            HttpClient client = new DefaultHttpClient();  
            HttpGet httpget = new HttpGet(url);  
            HttpResponse response = client.execute(httpget);  
  
            HttpEntity entity = response.getEntity();  
            InputStream is = entity.getContent();  
            File file = new File(fileDownLoadPath);  
            file.getParentFile().mkdirs();  
            FileOutputStream fileout = new FileOutputStream(file);  
            /** 
             * 根据实际运行效果 设置缓冲区大小 
             */  
            byte[] buffer=new byte[10 * 1024];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                fileout.write(buffer,0,ch);  
            }  
            is.close();  
            fileout.flush();  
            fileout.close();  
            return true;
        } catch (Exception e) {  
            e.printStackTrace(); 
            logger.error("文件下载异常！", e);
            return false;
        }  
    }
	
	
	public static void main(String[] args) {
		System.out.println("处理结果---"+downLoadFile("http://bpic.588ku.com/element_origin_min_pic/16/11/08/45d323a32ea4f2abad8d449d9b25369f.jpg", "E:/some-img/test-01-11.png"));
	}
	
	
}
