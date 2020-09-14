package com.kimdongcheul.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import kr.co.shineware.nlp.posta.en.core.EnPosta;

public class ExtractNoun {
	
	public static String url = "http://164.125.35.25:8983/solr/abstract";  
    public static SolrClient solr = new HttpSolrClient(url); 

    public static ArrayList<String> extractNoun(String abs) throws SolrServerException, IOException {
		EnPosta posta = new EnPosta();
		posta.load("C:\\Users\\hdw96\\Downloads\\model_0.5");
		posta.buildFailLink();
		
		List<String> res = posta.analyze(abs);
		
		ArrayList<String> str = new ArrayList<String>();
		for(String result : res) {
			if(result.charAt(0) >= 48 && result.charAt(0) <=57)
				continue;
			else if(result.substring(result.length() - 3, result.length()).equals("NNS")) {
				if(result.subSequence(0, result.length() - 4).length() != 1) 
					str.add(result.substring(0, result.length() - 4));
			}
			else if(result.substring(result.length() - 2, result.length()).equals("NN")) {
				if(result.subSequence(0, result.length() - 3).length() != 1) 
					str.add(result.substring(0, result.length() - 3));
			}
		}
		System.out.println("---------------------------------------------");

//		for(int i=0 ; i<str.size();i++) {
//			System.out.print(str.get(i) + " ");
//		}
		return str;
		
	}
    
    public static ArrayList<String> getAbstract(int i) throws SolrServerException, IOException {
    	SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setRows(Integer.MAX_VALUE);
		
		QueryResponse rsp = solr.query(query);
		SolrDocumentList docs = rsp.getResults();
		
		String abs = String.valueOf(docs.get(i).getFieldValue("abstract")) ;
		
		return extractNoun(abs);
//		System.out.println(docs.getNumFound());
//		for(int i=0 ; i<10;i++)
//		{
//			String abs = String.valueOf(docs.get(i).getFieldValue("abstract")) ;
//			extractNoun(abs);
//			System.out.println("-----------------------------------------------------------------" + i);
//		}
		
    }

}
