package com.chd.task.ass.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.View;

@View("v_mobile_ass_card")
public class AssCard {
 
	@Column("group_id")
	private String group_id;
	
	@Column("hos_id")
	private String hos_id;
	
	@Column("copy_code")
	private String copy_code;
	
	@Column("ass_card_no")
	private String ass_card_no;

	@Column("ass_ori_card_no")
	private String ass_ori_card_no;

	@Column("ass_id")
	private String ass_id;

	@Column("ass_no")
	private String ass_no;

	@Column("ass_code")
	private String ass_code;

	@Column("ass_name")
	private String ass_name;

	@Column("dept_id")
	private String dept_id;

	@Column("dept_no")
	private String dept_no;

	@Column("dept_code")
	private String dept_code;

	@Column("dept_name")
	private String dept_name;

	@Column("use_state")
	private String ass_state;

	@Column("bar_code")
	private String bar_code;
	
	@Column("ass_spec")
	private String ass_spec;
	
	@Column("ass_mondl")
	private String ass_mondl;
	
	@Column("ass_nature")
	private String ass_nature;

	public String getAss_card_no() {
		return ass_card_no;
	}

	public void setAss_card_no(String ass_card_no) {
		this.ass_card_no = ass_card_no;
	}

	public String getAss_ori_card_no() {
		return ass_ori_card_no;
	}

	public void setAss_ori_card_no(String ass_ori_card_no) {
		this.ass_ori_card_no = ass_ori_card_no;
	}

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_no() {
		return ass_no;
	}

	public void setAss_no(String ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getAss_state() {
		return ass_state;
	}

	public void setAss_state(String ass_state) {
		this.ass_state = ass_state;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	
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

	
    public String getAss_spec() {
    	return ass_spec;
    }

	
    public void setAss_spec(String ass_spec) {
    	this.ass_spec = ass_spec;
    }

	
    public String getAss_mondl() {
    	return ass_mondl;
    }

	
    public void setAss_mondl(String ass_mondl) {
    	this.ass_mondl = ass_mondl;
    }

	/**
	 * @return the ass_naturs
	 */
	public String getAss_nature() {
		return ass_nature;
	}

	/**
	 * @param ass_naturs the ass_naturs to set
	 */
	public void setAss_nature(String ass_nature) {
		this.ass_nature = ass_nature;
	}

}
