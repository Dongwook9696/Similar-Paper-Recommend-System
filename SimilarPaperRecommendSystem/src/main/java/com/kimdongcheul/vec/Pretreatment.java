package com.kimdongcheul.vec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Pretreatment {
	@SuppressWarnings("resource")
	public static int[][] similar(){
		
		int[][] s = new int[501][501];
		
		try {
			String filepath = "C:\\Users\\chlgy\\Downloads\\similarity.txt";
			FileInputStream fileStream = null; // 파일 스트림
		        
	        fileStream = new FileInputStream( filepath );// 파일 스트림 생성
	      
	        byte[ ] readBuffer = new byte[fileStream.available()];
	        while (fileStream.read( readBuffer ) != -1){}
	        
	        String str = new String(readBuffer); //출력
			
	        String temp = "";
	        int a = -1, b = -1;
	        for(int i = 1; i < str.length(); i++) {
        		if(str.charAt(i) == ',') {
        			a = Integer.parseInt(temp);
        			temp = "";
        		}
        		else if(str.charAt(i) == ' ') {
        			b = Integer.parseInt(temp);
        			System.out.println(a + " " + b);
        			temp = "";
        			
        			s[a][b] = 1;
        		}
        		else {
            		temp += Character.toString(str.charAt(i));
        		}
	        }
		}catch (Exception e) {
	        e.getStackTrace();
	    }
		
		return s;
	}

	
}
