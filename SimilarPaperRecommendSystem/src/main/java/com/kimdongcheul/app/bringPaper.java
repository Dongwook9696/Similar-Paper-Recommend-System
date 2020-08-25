package com.kimdongcheul.app;

import java.io.File;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;


public class bringPaper {
	// TODO Auto-generated method stub
	public static String url = "http://164.125.35.25:8983/solr/abstract";
    public static SolrClient solr = new HttpSolrClient(url); 

    public static void AbstractExtraction() throws IOException {
		
    	File files[] = getFileList("/Users/gukcheolchoi/Downloads/0");
    	String filenames[] = getFileNameList("/Users/gukcheolchoi/Downloads/0");
    	
		for(int i = 0; i < files.length; i++) {
			String content = Jsoup.parse(files[i], "UTF-8").toString();
			content = Jsoup.parse(content).wholeText();
			String a = AbstractText(content);
			SolrPutData(filenames[i], a);
		}
	}

	public static void SolrQueryData() throws SolrServerException, IOException {
    	SolrQuery query = new SolrQuery();
	    query.setQuery("*:*");
	    query.setRows(100);
	
	    QueryResponse rsp = solr.query(query);
	    SolrDocumentList docs=rsp.getResults(); 
	    for(int i=0;i<docs.getNumFound();i++){
	        System.out.println(docs.get(i));
	    
		}
	}
	
	public static void SolrPutData(String filename, String abs) {
		SolrInputDocument solrDoc = new SolrInputDocument();
        solrDoc.addField("id", filename);
        solrDoc.addField("title", abs);
        
        try {
			solr.add(solrDoc);
			solr.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	// Abstract ���� �Լ�
	public static String AbstractText(String text) {
		String Abs = "bstract";
		int checker = 0;
		int cnt = 0;
		
		for(int i=0; i<text.length(); i++) {
			if (text.charAt(i) == 'A') {

				int n=0;
				for(int j=i+1; j<i+8; j++) { // A ���� ���ڰ� bstract���� �˻�
					if(text.charAt(j) != Abs.charAt(n))						
						break;
					else
						n+=1;

				}
				
				if(n == 7) { // Abstract ���ڿ� ã��

					if(text.charAt(i + 8) =='\n') {
						String s = "";
						for(int r=i+9; r<text.length(); r++) {	// Abstract �ؿ� ���� �� ������ �ְ� �� ���� ������ �� �� �ֱ� ������ ��������
							if(text.charAt(r) == '\n' || text.charAt(r) == ' ' || text.charAt(r) == '\t')
								i +=1;
							else
								break;
						
							// ���ܻ��� �߰��ؾ���
						}
						
						for(int r=i+9; r<text.length(); r++) {	// ���ڿ� s�� ��๮ ���� ������ �ö��� ��๮ ����
							if(text.charAt(r) != '\n')
								s += text.charAt(r);
							else
								break;
						}
						Abs = s;
					}
				}
			}
			
			// else if ���� ���� �߰��ؾ���
			
			if(!Abs.equals("bstract")) {	// Abs�� ��๮�̶�� �����Ǵ� ���ڿ��� �������
//				if(Abs.length()<15) {	// �� �� ��๮ ��ü�� backgroun, Objective, Results ���� �������� �̷���� ���ܰ� �߻��� �� ����
//					int cnt2 = cnt +9;
//					Abs = "";
//					for(int r = cnt+9; r<text.length(); r++) {
//						if(text.substring(cnt2, cnt2).equals("Introduction"))
//							break;
//						
//						Abs += text.charAt(r);
//						cnt2+=1;
//					}
//				}
				
				// Abs�� DB�� �����ؾ��� 
				return Abs; // -> ��๮ �̾Ƴ����� ��ȯ
//				System.out.print(Abs);
//				break;
			}
			
		}
//		cnt += 1;
		return "aa";
	}
	
	
	public static File[] getFileList(String path) {
		File dir = new File(path);
		File files[] = dir.listFiles();
		return files;
	}
	
	public static String[] getFileNameList(String path) {
		File dir = new File(path);
		String[] filenames = dir.list();
		return filenames;
	}

}