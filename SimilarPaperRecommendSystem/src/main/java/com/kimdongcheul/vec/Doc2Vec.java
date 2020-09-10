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
		ArrayList<String> str = new ArrayList<String>();
		for(int i=0; i<1; i++) {
			str = ExtractNoun.getAbstract(i);
			
			Learn learn = new Learn();
			
			learn.readVocab(str).get().entrySet().iterator();
			Iterator<Entry<String, Integer>> iterator = learn.readVocab(str).get().entrySet().iterator();
			Entry<String, Integer> next = null ;
			System.out.println();
			while(iterator.hasNext()){
				next = iterator.next() ;
				System.out.print(next.getKey() + " ");
				System.out.println();
				System.out.print(next.getValue() + " ");
				
			}
			str.clear();
		}
			

	}
}
