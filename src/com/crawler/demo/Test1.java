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
			//��ȡ��Դ��վ
			document = (Document)Jsoup.connect(networkUrl).get();
			//��ȡ��վ��ԴͼƬ
			elements = document.select("img[src]");
			for(Element e:elements){
				String outImage = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
//				String name = e.attr("alt")+".jpg";
				String name = e.attr("src").substring(e.attr("src").lastIndexOf("/")+1);
				//��������
				URL  imgUrl = new URL(e.attr("src"));
				//��ȡ������
				inputStream = imgUrl.openConnection().getInputStream();
				//����������Ϣ���뻺����������д�ٶ�
				bis = new BufferedInputStream(inputStream);
				//��ȡ�ֽ���
				byte[] buf = new byte[1024];
				
				//�����ļ�
				outputStream = new FileOutputStream(outPath+name);
				int size = 0;
				while((size = bis.read(buf)) != -1){
					outputStream.write(buf,0,size);
				}
				//ˢ���ļ���
				outputStream.flush();
				num++;
			}
		} catch (IOException e) {
			e.printStackTrace();			
		}finally {
			//�ͷ���Դ
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
		System.out.println("����� "+num+" ��ͼƬ\n---end---");
	}

}
