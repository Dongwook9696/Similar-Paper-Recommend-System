package com.kimdongcheul.app.solr.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kimdongcheul.app.solr.db.SolrDTO;
import com.kimdongcheul.app.solr.service.SolrService;
import com.kimdongcheul.vec.getCoineSimilarity;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private final SolrService solrService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	
	@Inject
	public HomeController(SolrService solrService) {
		this.solrService = solrService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(SolrDTO param,Locale locale, Model model) throws Exception {
		
		String str = "";
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
	    System.out.println("asdasd");

		model.addAttribute("serverTime", formattedDate );
		
//		db에 solr 추가
//		solrService.insertPaper(param);
		//리스트 만들자
//		modelMap.addAttribute("listview", docs.get(0));
		
		//getCoineSimilarity.getCosineSimilarity();
		
		
		//ExtractAbstract.AbstractExtraction();
		
//		ExtractNoun.getAbstract();
		return "home";
	}
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public String AbstractList(SolrDTO param, RedirectAttributes redirectAttributes) throws Exception {

        solrService.insertPaper(param);
	    
		return "redirect:home";
	}

	
}
