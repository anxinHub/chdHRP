package com.chd.task.ass.bean;

import org.nutz.dao.entity.annotation.View;

@View("v_mobile_fault")
public class Fault {
//故障分类
	private String group_id;

	private String hos_id;

	private String copy_code;
	
	private String fau_code;
	private String fau_name;

	
    public String getGroup_id() {
    	return group_id;
    }

	
    public void setGroup_id(String group_id) {
    	this.group_id = group_id;
    }

    public String getHos_id() {
    	return hos_id;
    }

	
    public void setHos_id(String hos_id) {
    	this.hos_id = hos_id;
    }

    public String getCopy_code() {
    	return copy_code;
    }

	
    public void setCopy_code(String copy_code) {
    	this.copy_code = copy_code;
    }


	/**
	 * @return the fau_code
	 */
	public String getFau_code() {
		return fau_code;
	}


	/**
	 * @param fau_code the fau_code to set
	 */
	public void setFau_code(String fau_code) {
		this.fau_code = fau_code;
	}


	/**
	 * @return the fau_name
	 */
	public String getFau_name() {
		return fau_name;
	}


	/**
	 * @param fau_name the fau_name to set
	 */
	public void setFau_name(String fau_name) {
		this.fau_name = fau_name;
	}
    
}
