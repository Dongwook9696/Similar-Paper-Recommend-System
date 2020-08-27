package com.kimdongcheul.app;

import java.io.File;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;


public class bringPaper {
	
	public static String url = "http://164.125.35.25:8983/solr/abstract";  
    public static SolrClient solr = new HttpSolrClient(url); 

    public static void AbstractExtraction() throws IOException {
    	File files[] = getFileList("C:\\Users\\chlgy\\Downloads\\0");
    	String filenames[] = getFileNameList("C:\\Users\\chlgy\\Downloads\\0");


    	int cnt = 0;
    	for(int i = 0; i < files.length; i++) {
			String content = Jsoup.parse(files[i], "UTF-8").toString();
			content = Jsoup.parse(content).wholeText();
			String a = AbstractText(content);
			System.out.println(filenames[i] + " : " + a);
			if(!a.equals("no_Abstract"))
				cnt++;
			
		}
		/*
    	int cnt = 0;
    	File f = new File("C://Users//chlgy//Downloads//0//10.1007_s13139-015-0373-x.html");
    	String content = Jsoup.parse(f, "UTF-8").toString();
    	content = Jsoup.parse(content).wholeText();
    	System.out.println(content);
		String a = AbstractText(content);
		System.out.println("-------------------------------------------------------");
		System.out.println(a);
		if(!a.equals("no_Abstract"))
			cnt++;
		*/
    	System.out.println(cnt);
	}
	
	public static void SolrPutData(String filename, String abs) {
		SolrInputDocument solrDoc = new SolrInputDocument();
        solrDoc.addField("id", filename);
        solrDoc.addField("abstract", abs);
        
        try {
			solr.add(solrDoc);
			solr.commit();
		} catch (SolrServerException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
       
	}
	
	public static boolean lengthCheck(String Abs) {
		if(Abs.length() < 80) return false;
		return true;
	}
	
	public static String AbstractText(String text) {
		String abs = "bstract";
		String ABS = "BSTRACT";
		
		String Abstract = "no_Abstract";
		
		for(int i=0; i < text.length()-9; i++) {
			if(lengthCheck(Abstract))
				return Abstract;
			
			String Abs_temp = "";
			if (text.charAt(i) == 'A') {
				int n=0;
				for(int j=i+1; j<i+8; j++) { 
					if(text.charAt(j) != abs.charAt(n) && text.charAt(j) != ABS.charAt(n))						
						break;
					else n+=1;
				}
				
				if(n == 7) {
					if(text.charAt(i + 8) =='\n' || text.charAt(i+8) == ' ') {
						for(int r=i+9; r<text.length(); r++) {	
							if(text.charAt(r) == '\n' || text.charAt(r) == ' ' || text.charAt(r) == '\t')
								i++;
							else break;
						}
						
						if(text.substring(i+9,i+19).equals("Background")) {
							Abstract = "";
							for(int r=i+9; r<text.length(); r++) {	
								if(r+13 == text.length()-1) {
									Abstract = "no_Abstract";
									return Abstract;
								}
								if(text.substring(r,r+12).equals("Introduction") || text.substring(r,r+12).equals("INTRODUCTION"))
									break;
								if(text.charAt(r) != '\n')
									Abstract += text.charAt(r);
							}
							return Abstract;
						}else if(text.substring(i+9,i+18).equals("Objective")) {
							Abstract = "";
							for(int r=i+9; r<text.length(); r++) {	
								if(r+13 == text.length()-1) {
									Abstract = "no_Abstract";
									return Abstract;
								}
								if(text.substring(r,r+12).equals("Introduction") || text.substring(r,r+12).equals("INTRODUCTION"))
									break;
								if(text.charAt(r) != '\n')
									Abstract += text.charAt(r);
							}
							return Abstract;
						}
						else if(text.substring(i+9,i+16).equals("Purpose")) {
							Abstract = "";
							for(int r=i+9; r<text.length(); r++) {	
								if(r+13 == text.length()-1) {
									Abstract = "no_Abstract";
									return Abstract;
								}
								if(text.substring(r,r+12).equals("Introduction") || text.substring(r,r+12).equals("INTRODUCTION"))
									break;
								if(text.charAt(r) != '\n')
									Abstract += text.charAt(r);
							}
							return Abstract;
						}

						
						
						for(int r=i+9; r<text.length(); r++) {	
							if(text.charAt(r) != '\n')
								Abs_temp += text.charAt(r);
							else break;
						}
						if(lengthCheck(Abs_temp)) {
							Abstract = Abs_temp;
						}
					}
					else if(text.substring(i-6, i).equals("Go to:")) {
						int pos = i + 8;
						for(int r = i+8; i<text.length(); i++) {
							if(text.substring(pos, pos + 6).equals("Go to:"))
								break;
							Abs_temp += text.charAt(r);
							pos += 1;
						}

						if(lengthCheck(Abs_temp)) {
							Abstract = Abs_temp;
						}
					}
				}
			}
		}
		return Abstract;
	}
	
	
	public static File[] getFileList(String path) {
		File dir = new File(path);
		File files[] = dir.listFiles();
		return files;
	}
	
	public static String[] getFileNameList(String path) {
		File dir = new File(path);
		String[] filenames = dir.list();
		return filenames;
	}
	//test
}