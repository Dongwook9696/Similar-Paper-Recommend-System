package com.kimdongcheul.app;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static String url = "http://164.125.35.25:8983/solr/abstract";  
    private static SolrClient solr = new HttpSolrClient(url); 
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws IOException, SolrServerException {
		
		String str = "";
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//ExtractAbstract.AbstractExtraction();
		
//		ExtractNoun.getAbstract();
		return "home";
	}
	
	@RequestMapping(value = "/home")
	public String AbstractList(HttpServletRequest request, ModelMap modelMap) throws IOException {
		
		SolrQuery query =new SolrQuery();
	    query.setQuery("*:*");
	    query.setRows(Integer.MAX_VALUE);
	    query.addSort("id", ORDER.desc);
	    try {
	        QueryResponse rsp = solr.query(query);
	        SolrDocumentList docs=rsp.getResults(); 
	        System.out.println(docs.get(0) + "asdasd");
	        modelMap.addAttribute("listview", docs.get(0));
	    }catch (SolrServerException | IOException e) {
	        logger.error("list error");
	    }
	    
		return "home";
	}

	
}
