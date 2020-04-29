/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @Description: 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
 * @Table: BUDG_MAT_TYPE_INCOME_SHIP
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class BudgMatTypeIncomeShip implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	private Long at_type_idOld;
	private String subj_codeOld;

	public Long getAt_type_idOld() {
		return at_type_idOld;
	}

	public void setAt_type_idOld(Long at_type_idOld) {
		this.at_type_idOld = at_type_idOld;
	}

	public String getSubj_codeOld() {
		return subj_codeOld;
	}

	public void setSubj_codeOld(String subj_codeOld) {
		this.subj_codeOld = subj_codeOld;
	}

	/**
	 * 医院ID
	 */
	private Long hos_id;
	/**
	 * 预算年度
	 * */
	private String mat_type_code;

	public String getMat_type_code() {
		return mat_type_code;
	}

	public void setMat_type_code(String mat_type_code) {
		this.mat_type_code = mat_type_code;
	}

	private String budg_year;

	public String getBudg_year() {
		return budg_year;
	}

	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}

	/**
	 * 账套编码
	 */
	private String copy_code;

	/**
	 * 物资分类编码
	 */
	private Long mat_type_id;
	private Long mat_type_no;

	public Long getMat_type_id() {
		return mat_type_id;
	}

	public void setMat_type_id(Long mat_type_id) {
		this.mat_type_id = mat_type_id;
	}

	public Long getMat_type_no() {
		return mat_type_no;
	}

	public void setMat_type_no(Long mat_type_no) {
		this.mat_type_no = mat_type_no;
	}

	/**
	 * 科目编码
	 */
	private String subj_code;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 科目编码
	 * 
	 * @param value
	 */
	public void setSubj_code(String value) {
		this.subj_code = value;
	}

	/**
	 * 获取 科目编码
	 * 
	 * @return String
	 */
	public String getSubj_code() {
		return this.subj_code;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}