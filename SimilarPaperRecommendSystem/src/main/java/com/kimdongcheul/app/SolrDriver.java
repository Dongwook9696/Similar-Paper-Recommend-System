package com.kimdongcheul.app;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrDriver{
	public static String url = "http://164.125.35.25:8983/solr/abstract";  
    public static SolrClient solr = new HttpSolrClient(url); 
	
}