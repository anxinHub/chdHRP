package com.chd.task.ass.bean;

import org.nutz.dao.entity.annotation.View;

@View("v_mobile_info")
public class Info {

	private String group_id;

	private String group_name;

	private String hos_id;

	private String hos_name;

	private String copy_code;

	private String copy_name;

	
    public String getGroup_id() {
    	return group_id;
    }

	
    public void setGroup_id(String group_id) {
    	this.group_id = group_id;
    }

	
    public String getGroup_name() {
    	return group_name;
    }

	
    public void setGroup_name(String group_name) {
    	this.group_name = group_name;
    }

	
    public String getHos_id() {
    	return hos_id;
    }

	
    public void setHos_id(String hos_id) {
    	this.hos_id = hos_id;
    }

	
    public String getHos_name() {
    	return hos_name;
    }

	
    public void setHos_name(String hos_name) {
    	this.hos_name = hos_name;
    }

	
    public String getCopy_code() {
    	return copy_code;
    }

	
    public void setCopy_code(String copy_code) {
    	this.copy_code = copy_code;
    }

	
    public String getCopy_name() {
    	return copy_name;
    }

	
    public void setCopy_name(String copy_name) {
    	this.copy_name = copy_name;
    }
	
}
