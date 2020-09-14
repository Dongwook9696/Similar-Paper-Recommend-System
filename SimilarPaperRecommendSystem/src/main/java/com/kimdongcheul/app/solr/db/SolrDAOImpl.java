package com.kimdongcheul.app.solr.db;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class SolrDAOImpl implements SolrDAO{

	private static final String NAMESPACE = "com.kimdongcheul.app.sql.solr";
	private final SqlSession sqlSession;
	
	@Inject
	public SolrDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertPaper(SolrDTO param) throws Exception {
		sqlSession.insert(NAMESPACE + ".create",param);
	}

}
