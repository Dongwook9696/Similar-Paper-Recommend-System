package com.kimdongcheul.app;

public class SolrDTO {
	private String id;
	private String abstract_;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbstract_() {
		return abstract_;
	}
	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}
	

	@Override
	public String toString() {
		return "SolrDTO [id=" + id + ", abstract_=" + abstract_ + "]";
	}
}
