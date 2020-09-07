package com.kimdongcheul.app;

import java.io.IOException;
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


    public static void extractNoun(String abs) throws SolrServerException, IOException {
		EnPosta posta = new EnPosta();
		posta.load("C:\\Users\\chlgy\\Downloads\\model");
		posta.buildFailLink();
		
		List<String> res = posta.analyze(abs);
		
		for(String result : res) {
			System.out.println(result);
		}
		System.out.println("-----------------------------------------------------------------");
	}
    
    public static void getAbstract() throws SolrServerException, IOException {
    	SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setRows(Integer.MAX_VALUE);
		
		QueryResponse rsp = solr.query(query);
		SolrDocumentList docs = rsp.getResults();
		
		for(int i=0 ; i<10;i++)
		{
			String abs = String.valueOf(docs.get(i).getFieldValue("abstract")) ;
			extractNoun(abs);
		}
		
    }

}
