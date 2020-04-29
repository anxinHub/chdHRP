package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * alfred
 */

public class AphiCompanyScheme implements Serializable {

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

	private String item_code;

	private String method_code;

	private String formula_code;

	private String fun_code;

	private String item_name;

	private String item_note;

	private String method_name;

	private String formula_name;

	private String fun_name;
	
	private String error_type;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setItem_code(String value) {
		this.item_code = value;
	}

	public String getItem_code() {
		return this.item_code;
	}

	public void setMethod_code(String value) {
		this.method_code = value;
	}

	public String getMethod_code() {
		return this.method_code;
	}

	public void setFormula_code(String value) {
		this.formula_code = value;
	}

	public String getFormula_code() {
		return this.formula_code;
	}

	public void setFun_code(String value) {
		this.fun_code = value;
	}

	public String getFun_code() {
		return this.fun_code;
	}

	
    public String getItem_name() {
    	return item_name;
    }

	
    public void setItem_name(String item_name) {
    	this.item_name = item_name;
    }

	
    public String getItem_note() {
    	return item_note;
    }

	
    public void setItem_note(String item_note) {
    	this.item_note = item_note;
    }

	
    public String getMethod_name() {
    	return method_name;
    }

	
    public void setMethod_name(String method_name) {
    	this.method_name = method_name;
    }

	
    public String getFormula_name() {
    	return formula_name;
    }

	
    public void setFormula_name(String formula_name) {
    	this.formula_name = formula_name;
    }

	
    public String getFun_name() {
    	return fun_name;
    }

	
    public void setFun_name(String fun_name) {
    	this.fun_name = fun_name;
    }

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	
}