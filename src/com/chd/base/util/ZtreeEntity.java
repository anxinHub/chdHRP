package com.chd.base.util;

import java.util.List;
import java.util.Map;

public class ZtreeEntity{
	
	//用于查找数据
	private List<Map<String,Object>> data;
	private int curr=-1;
	
	public Map<String,Object> getMap(){
		return data.get(curr);
	}
	
	public Object getData(String name){
		return data.get(curr).get(name);
	}
	
	public boolean next(){
		if(curr<(data.size()-1)){
			curr++;
			return true;
		}
		return false;
	}

	
	public List<Map<String, Object>> getData() {
		return data;
	}

	
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
		
	}

	
}
