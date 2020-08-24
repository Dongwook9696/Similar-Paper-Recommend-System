package com.kimdongcheul.app;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class bringPaper {
	// TODO Auto-generated method stub
	public static String url = "http://164.125.35.25:8983/solr/pubmedcontent";
    public static SolrClient solr = new HttpSolrClient(url); 

	public static void SolrQueryData() throws SolrServerException, IOException {
	    	SolrQuery query = new SolrQuery();
		    query.setQuery("*:*");
		    query.setRows(1000);
		
		    QueryResponse rsp = solr.query(query);
		    SolrDocumentList docs=rsp.getResults(); 
		    for(int i=0;i<docs.getNumFound();i++){
		        System.out.println(docs.get(i));
		    
		}
	}

}