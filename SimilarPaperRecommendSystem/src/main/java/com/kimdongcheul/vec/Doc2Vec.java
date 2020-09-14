package com.kimdongcheul.vec;

import java.io.IOException;
import java.util.HashMap;

import org.apache.solr.client.solrj.SolrServerException;


public class Doc2Vec {
	public static HashMap<String, Integer> hm = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException, SolrServerException {
		
		getCoineSimilarity.getCosineSimilarity();	

	}
}
