package ru.defolt;

public class Main {

	public static void main(String[] args) {
		
		//String fileName = "C:\\Server\\MyURLDownloader\\MyURLDownloader\\dist\\LinkList.txt";
		//String folderName = "C:\\Server\\MyURLDownloader\\MyURLDownloader\\dist\\pics";
		String fileName = "LinkList.txt";
		String folderName = "pics";
		int threadsCount = 15;
		
		Grabber grabber = new Grabber(fileName, folderName, threadsCount);
		grabber.start();
		
		/*for(int i=0;i<12;i++)
		{
			if((i%3)+1==3)
			System.out.println(i);
		}
		*/
	}

}
