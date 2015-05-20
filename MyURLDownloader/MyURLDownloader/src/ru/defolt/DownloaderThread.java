package ru.defolt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloaderThread extends Thread {
	
	private int threadNumber;
	private int fileLines;
	private String folderName;
	
	private ArrayList<String> URLList;
	private ArrayList<Integer> LineList;
	private boolean stoped;
	
	public DownloaderThread(int threadNumber, String folderName, int fileLines)
	{
		this.threadNumber = threadNumber;
		this.fileLines = fileLines;
		this.folderName = folderName;	
		this.stoped = false;
		URLList = new ArrayList<String>();
		LineList = new ArrayList<Integer>();
	}
	
	private void DownloadFile(String strURL, String strPath) {
        try {
        	int buffSize = 1024;
            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            //??
            urlconn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlconn.setRequestProperty("Accept","*/*");
            //??
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = null;
            in = urlconn.getInputStream();
            OutputStream writer = new FileOutputStream(strPath);
            byte buffer[] = new byte[buffSize];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
	
	public void run()
	{
		while(!stoped)
		{
			if(URLList.size()>0)
			{
				String fileString = this.URLList.get(0);
				int line = this.LineList.get(0);
				this.URLList.remove(0);
				this.LineList.remove(0);
				
				String strPath = folderName+"/"+fileString.substring(fileString.lastIndexOf("/")+1);  //+1???
				this.DownloadFile(fileString, strPath);
				//System.out.println();
				System.out.println("Downloaded "+line+"/"+this.fileLines+": "+strPath+" Thread#"+this.threadNumber);
				
				if (line+this.threadNumber+1 >= this.fileLines)
					this.Halt();
			}
		}
	}

	public void addParams(String fileString, int line) {
		// TODO Auto-generated method stub
		this.URLList.add(fileString);
		this.LineList.add(line);
		
	}
	
	public void Halt()
	{
		this.stoped = true;
	}
}
