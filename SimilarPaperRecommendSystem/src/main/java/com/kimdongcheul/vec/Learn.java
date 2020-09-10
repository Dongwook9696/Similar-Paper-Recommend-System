package com.kimdongcheul.vec;

import java.io.IOException;
import java.util.ArrayList;


import love.cq.util.MapCount;

public class Learn {

	public MapCount<String> readVocab(ArrayList<String> str) throws IOException {
		MapCount<String> mc = new MapCount<>();
		
		for (String string : str) {
			mc.add(string);
		}
		return mc;
	}

}
