package com.chd.hrp.hpm.entity;

import java.io.Serializable;
/** 
* 2015-2-5 
* IncomeItem.java 
* author:alfred
*/ 
public class AphiIncomeitemPoint implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	
	
	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	
	private String copy_code;
	
	private String income_item_code;
	
	private String income_item_name;
	
	private Integer is_acc;
	
	private Long imcome_point;
	
	private String error_type;
	
    public String getCopy_code() {
    	return copy_code;
    }

	
    public void setCopy_code(String copy_code) {
    	this.copy_code = copy_code;
    }

	
    public String getIncome_item_code() {
    	return income_item_code;
    }

	
    public void setIncome_item_code(String income_item_code) {
    	this.income_item_code = income_item_code;
    }

	
    public Long getImcome_point() {
    	return imcome_point;
    }

	
    public void setImcome_point(Long imcome_point) {
    	this.imcome_point = imcome_point;
    }

	
    public static long getSerialversionuid() {
    	return serialVersionUID;
    }


	
    public Integer getIs_acc() {
    	return is_acc;
    }


	
    public void setIs_acc(Integer is_acc) {
    	this.is_acc = is_acc;
    }


	
    public String getIncome_item_name() {
    	return income_item_name;
    }


	
    public void setIncome_item_name(String income_item_name) {
    	this.income_item_name = income_item_name;
    }


	public String getError_type() {
		return error_type;
	}


	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	
	
}