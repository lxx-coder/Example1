package com.crawler.demo;

import java.io.*;
import java.net.URL;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test1 {
	private static int num = 0;
	
	public static void getNetworkImage(String networkUrl,String outPath) throws IOException{
		FileOutputStream outputStream = null;
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		Document document;
		Elements elements;
		
		File file = new File(outPath);
		if(!file.exists())
			file.mkdirs();
		
		try {
			//获取资源网站
			document = (Document)Jsoup.connect(networkUrl).get();
			//获取网站资源图片
			elements = document.select("img[src]");
			for(Element e:elements){
				String outImage = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
//				String name = e.attr("alt")+".jpg";
				String name = e.attr("src").substring(e.attr("src").lastIndexOf("/")+1);
				//创建连接
				URL  imgUrl = new URL(e.attr("src"));
				//获取输入流
				inputStream = imgUrl.openConnection().getInputStream();
				//将输入流信息放入缓冲流提升读写速度
				bis = new BufferedInputStream(inputStream);
				//读取字节类
				byte[] buf = new byte[1024];
				
				//生成文件
				outputStream = new FileOutputStream(outPath+name);
				int size = 0;
				while((size = bis.read(buf)) != -1){
					outputStream.write(buf,0,size);
				}
				//刷新文件流
				outputStream.flush();
				num++;
			}
		} catch (IOException e) {
			e.printStackTrace();			
		}finally {
			//释放资源
			if(outputStream != null)
				outputStream.close();
			if(bis != null)
				bis.close();
			if(inputStream!= null)
				inputStream.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("---start---");
		getNetworkImage("http://www.tooopen.com/img/87.aspx", "D:\\test\\");
		System.out.println("共获得 "+num+" 张图片\n---end---");
	}

}
