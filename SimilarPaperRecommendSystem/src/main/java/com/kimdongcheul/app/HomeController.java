package com.kimdongcheul.app;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kimdongcheul.vec.Pretreatment;
import com.kimdongcheul.vec.getCoineSimilarity;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static String url = "http://164.125.35.25:8983/solr/abstract";  
    private static SolrClient solr = new HttpSolrClient(url); 
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	static int [][]similar = new int[501][501];
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws IOException, SolrServerException {
	
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		similar = Pretreatment.similar();
		//similar = getCoineSimilarity.getCosineSimilarity();
		//ExtractAbstract.AbstractExtraction();
		
//		ExtractNoun.getAbstract();
		return "home";
	}
	
	@RequestMapping(value = "/abstractList")
	public String AbstractList(HttpServletRequest request, ModelMap modelMap, String paper_id) throws IOException, SolrServerException {
		
		List<String> id_list = new ArrayList<String>();
		
	    SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.setRows(Integer.MAX_VALUE);
		
		QueryResponse rsp = solr.query(query);
		SolrDocumentList docs = rsp.getResults();
		
		System.out.println( paper_id);
		
		int paper_num = -1;
		for(int i = 0; i < 500; i++) {
			String id = String.valueOf(docs.get(i).getFieldValue("id")) ;
			//System.out.println("id : " + id);
			if(id.equals(paper_id)) {
				paper_num = i;
				break;
			}
		}
		
		System.out.println(paper_num);

		if(paper_num != -1) {
			for(int i = 0; i < 500; i++) {
				if(paper_num != i && similar[paper_num][i] == 1) {
					String id = String.valueOf(docs.get(i).getFieldValue("id")) ;
					id_list.add(id);
					System.out.println("유사 논문 : " + id);
				}
			}
		}
		
		modelMap.addAttribute("listview", id_list);
		
		
		return "home";
	}
	
	
	@RequestMapping(value = "/showAbstract")
	public String show(HttpServletRequest request, RedirectAttributes redirectAttributes, String id, String paper) throws IOException {
		String filepath = "C:\\Users\\chlgy\\Downloads\\11\\" + id;
		System.out.println(paper);
		Desktop.getDesktop().open(new File(filepath));
		
		String referer = request.getHeader("Referer");
		return "home";
	}
	
}
