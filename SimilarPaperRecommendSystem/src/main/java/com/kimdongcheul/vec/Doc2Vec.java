package com.kimdongcheul.vec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrServerException;

import com.kimdongcheul.app.ExtractNoun;
import com.kimdongcheul.vec.Learn;

public class Doc2Vec {
	public static HashMap<String, Integer> hm = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException, SolrServerException {
		
		getCoineSimilarity.getCosineSimilarity();	

	}
}
