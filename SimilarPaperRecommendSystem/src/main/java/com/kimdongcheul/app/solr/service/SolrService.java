package com.kimdongcheul.app.solr.service;

import com.kimdongcheul.app.solr.db.SolrDAO;
import com.kimdongcheul.app.solr.db.SolrDTO;

public interface SolrService {
	void insertPaper(SolrDTO param) throws Exception;

}
