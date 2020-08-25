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
	// TODO Auto-generated method stub
	public static String url = "http://164.125.35.25:8983/solr/abstract";
    public static SolrClient solr = new HttpSolrClient(url); 

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
	
	public static void SolrPutData(String a) {
		SolrInputDocument solrDoc = new SolrInputDocument();
		String s = "10.1016_j.phrp.2015.12.006.html";
        solrDoc.addField("id", s);
        solrDoc.addField("title", a);
        
        try {
			solr.add(solrDoc);
			solr.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	// Abstract 추출 함수
	public static String AbstractText(String text) {
		String Abs = "bstract";
		int checker = 0;
		int cnt = 0;
		
		for(int i=0; i<text.length(); i++) {
			if (text.charAt(i) == 'A') {

				int n=0;
				for(int j=i+1; j<i+8; j++) { // A 뒤의 문자가 bstract인지 검사
					if(text.charAt(j) != Abs.charAt(n))						
						break;
					else
						n+=1;

				}
				
				if(n == 7) { // Abstract 문자열 찾음

					if(text.charAt(i + 8) =='\n') {
						String s = "";
						for(int r=i+9; r<text.length(); r++) {	// Abstract 밑에 개형 등 공백이 있고 그 다음 내용이 올 수 있기 때문에 공백제거
							if(text.charAt(r) == '\n' || text.charAt(r) == ' ' || text.charAt(r) == '\t')
								i +=1;
							else
								break;
						
							// 예외사항 추가해야함
						}
						
						for(int r=i+9; r<text.length(); r++) {	// 문자열 s에 요약문 다음 개행이 올때지 요약문 저장
							if(text.charAt(r) != '\n')
								s += text.charAt(r);
							else
								break;
						}
						Abs = s;
					}
				}
			}
			
			// else if 예외 사항 추가해야함
			
			if(!Abs.equals("bstract")) {	// Abs에 요약문이라고 생각되는 문자열이 담겨있음
//				if(Abs.length()<15) {	// 논문 중 요약문 자체가 backgroun, Objective, Results 등의 구성으로 이루어져 예외가 발생할 수 있음
//					int cnt2 = cnt +9;
//					Abs = "";
//					for(int r = cnt+9; r<text.length(); r++) {
//						if(text.substring(cnt2, cnt2).equals("Introduction"))
//							break;
//						
//						Abs += text.charAt(r);
//						cnt2+=1;
//					}
//				}
				
				// Abs를 DB에 저장해야함 
				return Abs; // -> 요약문 뽑아낸것을 반환
//				System.out.print(Abs);
//				break;
			}
			
		}
//		cnt += 1;
		return "aa";
	}
	public static String crawling() throws IOException {
					  	
		String content = Jsoup.parse(new File("C:\\gp\\Similar-Paper-Recommend-System\\SimilarPaperRecommendSystem\\target\\classes\\com\\kimdongcheul\\app\\0\\10.1016_j.phrp.2015.12.006.html"), "UTF-8").toString();
		content = Jsoup.parse(content).wholeText();
//		System.out.print(content);
		
		return content;
	}

}