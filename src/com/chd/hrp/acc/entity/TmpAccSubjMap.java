package com.chd.hrp.acc.entity;
/**
 * 新旧制度衔接-新旧 科目关系表
 * @author yang
 *
 */
public class TmpAccSubjMap {

	private String subj_code_old;
	private String subj_code_new;
	
	private String subj_name_old;
	private String subj_name_new;
	
	private String subj_name_all_old;
	private String subj_name_all_new;
	
	private String is_last;
	private String is_last_name;
	
	public TmpAccSubjMap(){}

	public String getSubj_code_old() {
		return subj_code_old;
	}

	public void setSubj_code_old(String subj_code_old) {
		this.subj_code_old = subj_code_old;
	}

	public String getSubj_code_new() {
		return subj_code_new;
	}

	public void setSubj_code_new(String subj_code_new) {
		this.subj_code_new = subj_code_new;
	}

	public String getSubj_name_old() {
		return subj_name_old;
	}

	public void setSubj_name_old(String subj_name_old) {
		this.subj_name_old = subj_name_old;
	}

	public String getSubj_name_new() {
		return subj_name_new;
	}

	public void setSubj_name_new(String subj_name_new) {
		this.subj_name_new = subj_name_new;
	}

	public String getSubj_name_all_old() {
		return subj_name_all_old;
	}

	public void setSubj_name_all_old(String subj_name_all_old) {
		this.subj_name_all_old = subj_name_all_old;
	}

	public String getSubj_name_all_new() {
		return subj_name_all_new;
	}

	public void setSubj_name_all_new(String subj_name_all_new) {
		this.subj_name_all_new = subj_name_all_new;
	}

	public String getIs_last() {
		return is_last;
	}

	public void setIs_last(String is_last) {
		this.is_last = is_last;
	}

	public String getIs_last_name() {
		return is_last_name;
	}

	public void setIs_last_name(String is_last_name) {
		this.is_last_name = is_last_name;
	}
}
