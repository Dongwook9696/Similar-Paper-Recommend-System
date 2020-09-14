package com.kimdongcheul.vec;

import java.io.IOException;
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
    
    static double similarity = 0.7;
	DocVector[] docVector;
    static Map<String,Integer> allterms = new HashMap<String,Integer>();
    
    static MapCount<String> map_i = new MapCount<>();
    static MapCount<String> map_j = new MapCount<>();
	
	public static void getCosineSimilarity() throws IOException, SolrServerException
    {

		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setRows(Integer.MAX_VALUE);
		
		QueryResponse rsp = solr.query(query);
		SolrDocumentList docs = rsp.getResults();
		
       try
       {
    	   long document_cnt = docs.getNumFound();
           for (int idocId = 0; idocId < document_cnt; idocId++)
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
       				if(next.getValue() == 1) continue;
       				System.out.print(next.getKey() + " ");

       				arrTerms.put(next.getKey(), pos_i++);
       			}
   				System.out.println();

       			str.clear();
	       		
        	   
               for (int jdocId = idocId ; jdocId < document_cnt; jdocId++)
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
              				if(next.getValue() == 1) continue;
              				System.out.print(next.getKey() + " ");
              				
              				arrTerms2.put(next.getKey(), pos_j++);
              				
              			}
              		    System.out.println();
            		    allterms = arrTerms2;
            		   
	                   DocVector docVectori = iGetDocumentVector(String.valueOf(docs.get(idocId).getFieldValue("id")));        
	            	   DocVector docVectorj = jGetDocumentVector(String.valueOf(docs.get(jdocId).getFieldValue("id")));
	            	   
//	            	   double cosineSimilarity = cosineSimilarity(docVectori, docVectorj);
	            	   double TS_SS_Similarity = Vector_Similarity.TS_SS(docVectori, docVectorj);
	            	   double TS_SS_Similarity1 = Vector_Similarity.Cosine(docVectori, docVectorj);
	            	   double TS_SS_Similarity2 = Vector_Similarity.Euclidean(docVectori, docVectorj);

//	            	   if(cosineSimilarity>similarity && cosineSimilarity<1.0)
//		    		   {
	    			   System.out.println(idocId  +" 번째 눈문과 " + jdocId +" 번째 논문의 TS_SS 유사도 : " + TS_SS_Similarity);
	    			   System.out.println(idocId  +" 번째 눈문과 " + jdocId +" 번째 논문의 COS 유사도 : " + TS_SS_Similarity1);
	    			   System.out.println(idocId  +" 번째 눈문과 " + jdocId +" 번째 논문의 ED 유사도 : " + TS_SS_Similarity2);

//		    		   }

		    		   docVectorj = null ;
		    		   docVectori = null ;
		    		   str.clear();
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
