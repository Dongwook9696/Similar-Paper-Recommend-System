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


public class ExtractAbstract {
	
	public static String url = "http://164.125.35.25:8983/solr/abstract";  
    public static SolrClient solr = new HttpSolrClient(url); 

    public static void AbstractExtraction() throws IOException {
    	File files[] = getFileList("C:\\Users\\hdw96\\Downloads\\12");
    	String filenames[] = getFileNameList("C:\\Users\\hdw96\\Downloads\\12");


    	int cnt = 0;
    	for(int i = 0; i < files.length; i++) {
			String content = Jsoup.parse(files[i], "UTF-8").toString();
			content = Jsoup.parse(content).wholeText();
			String abs = AbstractText(content);
//			System.out.println(filenames[i] + " : " + abs);
			if(!abs.equals("no_Abstract"))
				SolrPutData(filenames[i], abs);
		}
    	
    	/*
    	int cnt = 0;
    	File f = new File("C:\\Users\\hdw96\\Downloads\\10\\10.1155_2015_725989.html");
    	String content = Jsoup.parse(f, "UTF-8").toString();
    	content = Jsoup.parse(content).wholeText();
		String a = AbstractText(content);
		System.out.println(a);
		if(!a.equals("no_Abstract"))
			cnt++;
		*/
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
		
		String sum = "ummary";
		
		String Abstract = "no_Abstract";

		for(int i=0; i < text.length()-9; i++) {
			if(lengthCheck(Abstract))
				return Abstract;
			
			String Abs_temp = "";
			
			if (text.charAt(i) == 'S') {
				int n=0;
				for(int j=i+1; j<i+7; j++) { 
					if(text.charAt(j) != sum.charAt(n))
						break;
					else n+=1;
				}
				if(n == 6) {
					if(text.charAt(i + 7) =='\n' || text.charAt(i+7) == ' ') {
						for(int r=i+8; r<text.length(); r++) {	
							if(text.charAt(r) == '\n' || text.charAt(r) == ' ' || text.charAt(r) == '\t')
								i++;
							else break;
						}
						for(int r=i+8; r<text.length(); r++) {	
							if(text.charAt(r) != '\n')
								Abs_temp += text.charAt(r);
							else break;
						}
						if(lengthCheck(Abs_temp)) {
							Abstract = Abs_temp;
						}
					}
				}
			}
			
			if (text.charAt(i) == 'A') {
				int n=0;
				for(int j=i+1; j<i+8; j++) { 
					if(text.charAt(j) != abs.charAt(n) && text.charAt(j) != ABS.charAt(n))
						break;
					else n+=1;
				}
				
				if(n == 7) {
//					System.out.println(text.substring(i, i+100));

					//for 'Go to:' paper 
					int pre;
					for(pre = i-1; pre > i-100; pre--) {
						if(pre<=0) break;
						if(text.charAt(pre) == '\n' || text.charAt(pre) == ' ' || text.charAt(pre) == '\t')
							continue;
						pre++;
						break;
					}
					
					if(pre>6 && text.substring(pre-6, pre).equals("Go to:")) {
						for(int pos = i+8; pos<text.length(); pos++) {
							if(text.substring(pos, pos + 6).equals("Go to:"))
								break;
							if(text.charAt(pos) != '\n' && text.charAt(pos) != '\t')
								Abs_temp += text.charAt(pos);
						}

						if(lengthCheck(Abs_temp)) {
							Abstract = Abs_temp;
						}
						return Abstract;
					}
					
					if(text.charAt(i + 8) =='\n' || text.charAt(i+8) == ' '|| text.charAt(i+8) == '\t') {

						for(int r=i+9; r<text.length(); r++) {	
							if(text.charAt(r) == '\n' || text.charAt(r) == ' ' || text.charAt(r) == '\t')
								i++;
							else break;
						}
						if(text.substring(i+9,i+19).equals("Background") || text.substring(i+9, i+19).equals("BACKGROUND")) {
//							System.out.println(1);
							Abstract = "";
							if(text.substring(i+19+25,i+19+9+25).equals("MATERIALS") || text.substring(i+19+25,i+19+7+25).equals("METHODS")) {
								continue;
							}
							for(int r=i+9; r<text.length(); r++) {	
								if(r+13 == text.length()-1) {
									Abstract = "no_Abstract";
									return Abstract;
								}
								if(text.substring(r,r+12).equals("Introduction") || text.substring(r,r+12).equals("INTRODUCTION") || text.substring(r,r+7).equals("Methods"))
									break;
								if(text.charAt(r) != '\n')
									Abstract += text.charAt(r);
							}
							if(!lengthCheck(Abstract)) {
								Abstract = "no_Abstract";
							}
							return Abstract;
						}
						else if(text.substring(i+9,i+18).equals("Objective") || text.substring(i+9,i+18).equals("OBJECTIVE")) {
//							System.out.println(2);
							Abstract = "";
							if(text.substring(i+18+25,i+18+9+25).equals("MATERIALS") || text.substring(i+18+25,i+18+7+25).equals("METHODS")) {
								continue;
							}
							
							if(text.charAt(i+19) < 65 || text.charAt(i+19) > 122) {
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
								if(!lengthCheck(Abstract)) {
									
									Abstract = "no_Abstract";
								}
								return Abstract;
							}
							
						}
						else if(text.substring(i+9,i+16).equals("Purpose") || text.substring(i+9,i+16).equals("PURPOSE")) {
//							System.out.println(3);
							Abstract = "";
							if(text.substring(i+16+25,i+16+9+25).equals("MATERIALS") || text.substring(i+16+25,i+16+7+25).equals("METHODS")) {
								continue;
							}
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
							if(!lengthCheck(Abstract)) {
								Abstract = "no_Abstract";
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
}