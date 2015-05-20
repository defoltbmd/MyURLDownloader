/**
 * 
 */
package ru.defolt;

import java.io.*;
import java.util.ArrayList;

/**
 * @author DNS
 *
 */
public class Grabber extends Thread{
	
	String fileName;
	String folderName;
	int fileLines;
	int threadsCount;
	
	public ArrayList<String> listOfFileStrings;
	
	public Grabber(String aFileName, String aFolderName) {
		this.fileName = aFileName;
		this.folderName = aFolderName;
		this.fileLines = this.getLinesCount(fileName);
		this.threadsCount = 1;
	}
	
	public Grabber(String aFileName, String aFolderName, int aThreadsCount) {
		this.fileName = aFileName;
		this.folderName = aFolderName;
		this.fileLines = this.getLinesCount(fileName);
		this.threadsCount = aThreadsCount;
	}
	
	private int getLinesCount(String fileName)
    {
        int i=0;
        BufferedReader bufferedReader = null;
        try{
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            while(bufferedReader.readLine()!=null)
                i++;
            bufferedReader.close();
        }catch(Exception e){}
        return i;
    }

	public void run() {
		// TODO Auto-generated method stub
		try
		{
			this.listOfFileStrings = new ArrayList<String>();
			FileReader fileReader = new FileReader(this.fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String fileString;
			int line = 0;
			
			ArrayList <DownloaderThread> dthrList = new ArrayList<DownloaderThread>();
			for(int i=0;i<this.threadsCount;i++)
			{
				DownloaderThread downloaderThread = new DownloaderThread(i, this.folderName, this.fileLines);	
				downloaderThread.start();
				dthrList.add(downloaderThread);
			}
			
			while((fileString = bufferedReader.readLine()) != null) {
				//TODO Melt threading!!!
				dthrList.get(line%this.threadsCount).addParams(fileString, line);;
				line++;
				System.out.println(fileString);
			}
			bufferedReader.close();
			System.out.println("MAIN THREAD DONE! WAITING FOR DOWNLOADS...");
			
			for(int i=0; i<this.threadsCount;i++)
			{
				//dthrList.get(i).Halt();
				//dthrList.remove(i);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}

}
