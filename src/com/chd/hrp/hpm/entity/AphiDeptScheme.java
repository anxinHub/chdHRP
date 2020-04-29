package com.chd.hrp.hpm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * alfred
 */

public class AphiDeptScheme implements Serializable {

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

	private Long dept_no;

	private Long dept_id;

	private String dept_code;

	private String dept_kind_code;

	private String dept_kind_name;

	public String getDept_kind_code() {
		return dept_kind_code;
	}

	public void setDept_kind_code(String dept_kind_code) {
		this.dept_kind_code = dept_kind_code;
	}

	public String getDept_kind_name() {
		return dept_kind_name;
	}

	public void setDept_kind_name(String dept_kind_name) {
		this.dept_kind_name = dept_kind_name;
	}

	private String method_code;

	private String formula_code;

	private String fun_code;

	private String dept_name;

	private String item_name;

	private String item_note;

	private String method_name;

	private String formula_name;
	private String formula_method_chs;

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

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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

	
	
	

	

//	public void setFormula_method_chs(String formula_method_chs) {
//		this.formula_method_chs = formula_method_chs;
//	}

//	public String getFormula_method_chs() {
//		String str = null;
//		
//		try {
//			
//			str = new String(formula_method_chs,"UTF-8");
//			
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return str;
//	}
//
//	public void setFormula_method_chs(byte[] formula_method_chs) {
//		this.formula_method_chs = formula_method_chs;
//	}

	public String getFormula_method_chs() {
		return formula_method_chs;
	}

	public void setFormula_method_chs(String formula_method_chs) {
		this.formula_method_chs = formula_method_chs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

}