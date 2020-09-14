package com.kimdongcheul.app.solr.service;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import com.kimdongcheul.app.SolrDriver;
import com.kimdongcheul.app.solr.db.SolrDAO;
import com.kimdongcheul.app.solr.db.SolrDTO;

@Service
public class SolrServiceImpl implements SolrService{
	
	private final SolrDAO solrDAO;
	
	@Inject
	public SolrServiceImpl(SolrDAO solrDAO) {
		this.solrDAO = solrDAO;
	}
	@Override
	public void insertPaper(SolrDTO param) throws Exception {
		
		SolrQuery query =new SolrQuery();
	    query.setQuery("*:*");
	    query.setRows(Integer.MAX_VALUE);
	    try {
	        QueryResponse rsp = SolrDriver.solr.query(query);
	        SolrDocumentList docs=rsp.getResults(); 
			for(int i=0 ; i<docs.getNumFound();i++)
			{
				param.setId(String.valueOf(docs.get(i).getFieldValue("id"))) ;
				param.setAbstract_(String.valueOf(docs.get(i).getFieldValue("abstract"))); 
				solrDAO.insertPaper(param);
			}
	    }catch (SolrServerException | IOException e) {
	        
	    }
		
	}

}
