package com.kimdongcheul.vec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.kimdongcheul.app.ExtractNoun;

import love.cq.util.MapCount;

public class getCoineSimilarity {
	public static String url = "http://164.125.35.25:8983/solr/abstract";  
    public static SolrClient solr = new HttpSolrClient(url);
    
    double similarity = 0.5;
	DocVector[] docVector;
    static Map<String,Integer> allterms = new HashMap<String,Integer>();
    
    static MapCount<String> map_i = new MapCount<>();
    static MapCount<String> map_j = new MapCount<>();
	
    static int [][]similars = new int[500][500];
    
    

    
	public static int [][] getCosineSimilarity() throws IOException, SolrServerException
    {

		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setRows(Integer.MAX_VALUE);
		
		QueryResponse rsp = solr.query(query);
		SolrDocumentList docs = rsp.getResults();
		
       try
       {
    	  OutputStream file = new FileOutputStream("C:\\Users\\chlgy\\Downloads\\similarity");
	         

	        
	        long document_cnt = docs.getNumFound();
    	   
          for (int idocId = 3; idocId < 500; idocId++)
           { 
        	   HashMap<String,Integer> arrTerms = new HashMap<String,Integer>();
        	   int pos_i = 0;
	           
        	    ArrayList<String> str = new ArrayList<String>();
	       		
       			str = ExtractNoun.getAbstract(idocId);
       			
       			map_i = readVocab(str);
       			
       			Iterator<Entry<String, Integer>> iterator = map_i.get().entrySet().iterator();
       			Entry<String, Integer> next = null ;

       			while(iterator.hasNext()){
       				next = iterator.next();
       				arrTerms.put(next.getKey(), pos_i++);
       			}
       			str.clear();
	       		
       			int cnt = 0;
               for (int jdocId = 346; jdocId < 500; jdocId++)
               {
            	   int pos_j = 0;
            	   
            	   try
            	   {
	            	   HashMap<String,Integer> arrTerms2 = new HashMap<String,Integer>();
            		   arrTerms2 = (HashMap) arrTerms.clone();
            		   
            		   str = ExtractNoun.getAbstract(jdocId);
            		   
            		   map_j = readVocab(str);
              			iterator = map_j.get().entrySet().iterator();
              			next = null ;

              			while(iterator.hasNext()){
              				next = iterator.next() ;
              				arrTerms2.put(next.getKey(), pos_j++);
              			}

            		   allterms = arrTerms2;
            		   
	                   DocVector docVectori = iGetDocumentVector(String.valueOf(docs.get(idocId).getFieldValue("id")));        
	            	   DocVector docVectorj = jGetDocumentVector(String.valueOf(docs.get(jdocId).getFieldValue("id")));
	            	   
	            	   double cosineSimilarity = cosineSimilarity(docVectori, docVectorj);
	            	   System.out.println("i : " + idocId + ", j : " + jdocId + " = " + cosineSimilarity);
		    		   if(cosineSimilarity > 0.75 && cosineSimilarity != 1.0)
		    		   {
		    			   //similars[idocId][jdocId] = 1;
		    			   //similars[jdocId][idocId] = 1;
		    			   
		    			   String s =" " + idocId + "," + jdocId;
		    			   byte[] by = s.getBytes();
		    			   file.write(by);
		    			   cnt++;
		    		   }

		    		   docVectorj = null ;
		    		   docVectori = null ;
		    		   
		    		   str.clear();
		    		   if(cnt > 10) break;
            	   }
            	   catch(Exception e)
            	   {
            		   System.out.println("FindPaperSimilarity consinesimilarity:"+e.toString()); 
            	   }
               }
           }
           
       }
       catch(Exception e)
       {
		   System.out.println("FindPaperSimilarity consinesimilarity:"+e.toString());
       }
       return similars;

    }   
    public static double cosineSimilarity(DocVector d1,DocVector d2) 
    {
        double cosinesimilarity;
        try {
            cosinesimilarity = (d1.vector.dotProduct(d2.vector))
                    / (d1.vector.getNorm() * d2.vector.getNorm());
        } catch (Exception e) {
            return 0.0;
        }
        return cosinesimilarity;
    }
    
    public static DocVector iGetDocumentVector(String docId) throws IOException 
    {
    	DocVector docvect = new DocVector(allterms);  
    	
    	
    	Iterator<Entry<String, Integer>> iterator = map_i.get().entrySet().iterator();
		Entry<String, Integer> next = null ;

		while(iterator.hasNext()){
			next = iterator.next();
			docvect.setEntry(next.getKey(), next.getValue());
            docvect.setDocId(docId);
		}
		
        docvect.normalize();

        return docvect ;
    }
    
    public static DocVector jGetDocumentVector(String docId) throws IOException 
    {

    	DocVector docvect = new DocVector(allterms);  

    	Iterator<Entry<String, Integer>> iterator = map_j.get().entrySet().iterator();
		Entry<String, Integer> next = null ;

		while(iterator.hasNext()){
			next = iterator.next();
			docvect.setEntry(next.getKey(), next.getValue());
            docvect.setDocId(docId);
		}

        docvect.normalize();

        return docvect ;
    }
    
    public static MapCount<String> readVocab(ArrayList<String> str) throws IOException {
		MapCount<String> mc = new MapCount<>();
		
		for (String string : str) {
			mc.add(string);
		}
		return mc;
	}
}
