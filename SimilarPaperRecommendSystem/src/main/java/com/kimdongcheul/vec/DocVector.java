package com.kimdongcheul.vec;

import java.util.Map;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVectorFormat;

public class DocVector {
	
	public Map<String,Integer> terms;
    public OpenMapRealVector vector;
    public Map<Integer,String> mapid ;
    public String sdid = "";
    
    public DocVector(Map terms) {
        this.terms = terms;
        this.vector = new OpenMapRealVector(terms.size());        
    }

    public void setEntry(String term, int freq) {
        if (terms.containsKey(term)) {
            int pos = terms.get(term);
            vector.setEntry(pos, (double) freq);
        }
    }
    public void setDocId(String mid)
    {
    	mid=mid.substring(mid.lastIndexOf("/")+1,mid.lastIndexOf("."));
    	this.sdid = mid ;
    }

    public void normalize() {
        double sum = vector.getL1Norm();
        vector = (OpenMapRealVector) vector.mapDivide(sum);
    }

    @Override
    public String toString() {
        RealVectorFormat formatter = new RealVectorFormat();
        return formatter.format(vector);
    }
}
