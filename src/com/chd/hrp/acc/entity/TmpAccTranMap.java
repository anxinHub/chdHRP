package com.chd.hrp.acc.entity;
/**
 * 新旧科目映射关系
 * @author yang
 *
 */
public class TmpAccTranMap {

	private String subj_code_old;// 旧科目
	private String subj_name_old;
	private String subj_name_all_old;
	
	private String subj_code_new;// 账务新科目
	private String subj_name_new;
	private String subj_name_all_new;
	
	private String subj_code_new_b;// 预算新科目
	private String subj_name_new_b;
	private String subj_name_all_new_b;
	
	public TmpAccTranMap(){}

	public String getSubj_code_old() {
		return subj_code_old;
	}

	public void setSubj_code_old(String subj_code_old) {
		this.subj_code_old = subj_code_old;
	}

	public String getSubj_name_old() {
		return subj_name_old;
	}

	public void setSubj_name_old(String subj_name_old) {
		this.subj_name_old = subj_name_old;
	}

	public String getSubj_name_all_old() {
		return subj_name_all_old;
	}

	public void setSubj_name_all_old(String subj_name_all_old) {
		this.subj_name_all_old = subj_name_all_old;
	}

	public String getSubj_code_new() {
		return subj_code_new;
	}

	public void setSubj_code_new(String subj_code_new) {
		this.subj_code_new = subj_code_new;
	}

	public String getSubj_name_new() {
		return subj_name_new;
	}

	public void setSubj_name_new(String subj_name_new) {
		this.subj_name_new = subj_name_new;
	}

	public String getSubj_name_all_new() {
		return subj_name_all_new;
	}

	public void setSubj_name_all_new(String subj_name_all_new) {
		this.subj_name_all_new = subj_name_all_new;
	}

	public String getSubj_code_new_b() {
		return subj_code_new_b;
	}

	public void setSubj_code_new_b(String subj_code_new_b) {
		this.subj_code_new_b = subj_code_new_b;
	}

	public String getSubj_name_new_b() {
		return subj_name_new_b;
	}

	public void setSubj_name_new_b(String subj_name_new_b) {
		this.subj_name_new_b = subj_name_new_b;
	}

	public String getSubj_name_all_new_b() {
		return subj_name_all_new_b;
	}

	public void setSubj_name_all_new_b(String subj_name_all_new_b) {
		this.subj_name_all_new_b = subj_name_all_new_b;
	}
	
}
