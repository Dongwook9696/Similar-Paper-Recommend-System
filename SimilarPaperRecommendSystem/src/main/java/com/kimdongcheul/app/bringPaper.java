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
    	File files[] = getFileList("C:\\Users\\hdw96\\Downloads\\0");
    	String filenames[] = getFileNameList("C:\\Users\\hdw96\\Downloads\\0");

    	for(int i = 0; i < files.length; i++) {
			
			String content = Jsoup.parse(files[i], "UTF-8").toString();
			content = Jsoup.parse(content).wholeText();
			String a = AbstractText(content);
			
			if(!a.equals("aa"))
				SolrPutData(filenames[i], a);
			
		}
	}

	public static void SolrQueryData() throws SolrServerException, IOException {
    	SolrQuery query = new SolrQuery();
	    query.setQuery("*:*");
	    query.setRows(100);
	
	    QueryResponse rsp = solr.query(query);
	    SolrDocumentList docs=rsp.getResults(); 
	    for(int i=0;i<docs.getNumFound();i++){
	        System.out.println(docs.get(i));
	    
		}
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
	
	
	public static String AbstractText(String text) {
		String Abs = "bstract";
		
		for(int i=0; i<text.length(); i++) {
			if (text.charAt(i) == 'A') {

				int n=0;
				for(int j=i+1; j<i+8; j++) { 
					if(text.charAt(j) != Abs.charAt(n))						
						break;
					else
						n+=1;

				}
				
				if(n == 7) { 

					if(text.charAt(i + 8) =='\n') {
						String s = "";
						for(int r=i+9; r<text.length(); r++) {	
							if(text.charAt(r) == '\n' || text.charAt(r) == ' ' || text.charAt(r) == '\t')
								i +=1;
							else
								break;
						
							
						}
						
						for(int r=i+9; r<text.length(); r++) {	
							if(text.charAt(r) != '\n')
								s += text.charAt(r);
							else
								break;
						}
						Abs = s;
					}
					
//					else if(text.substring(i-6, i).equals("Go to:")) {
//						int cnt2 = i + 8;
//						Abs = "";
//						for(int r = i+8; i<text.length(); i++) {
//							if(text.substring(cnt2, cnt2 + 6).equals("Go to:"))
//								break;
//							Abs += text.charAt(r);
//							cnt2 += 1;
//						}
//						System.out.println(Abs);
//					}
					
//					else if(text.charAt(i-1) == '\n' && text.charAt(i+9) != '\n' && text.charAt(i+9) != ' ' && text.charAt(i+9) != '\t') {
//						String s = "";
//						for(int r = i+9; r<text.length(); i++) {
//							if(text.charAt(r) != '\n')
//								s += text.charAt(r);
//							else
//								break;
//						}
//						Abs = s;
//						System.out.println(Abs);
//					}
				}
			}
			
			
			
			if(!Abs.equals("bstract")) {	
				if(Abs.length()<30) {	
					Abs = "aa";
				}
				
				 
				return Abs; 
//				System.out.print(Abs);
//				break;
			}
			
		}
//		cnt += 1;
		return "aa";
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