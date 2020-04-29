/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.entity.sysstruc;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 
 * @Table: HR_COL_STRUC
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrColStruc implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Integer group_id;

	/**
	 * 
	 */
	private Integer hos_id;

	/**
	 * 
	 */
	//private String copy_code;

	/**
	 * 归属表
	 */
	private String tab_code;

	/**
	 * 列名
	 */
	private String col_code;

	/**
	 * 列描述
	 */
	private String col_name;

	/**
	 * 数据类型
	 */
	private String data_type;

	private String data_type_name;

	/**
	 * 字段长度
	 */
	private Integer filed_length;

	/**
	 * 精度
	 */
	private Integer prec;

	/**
	 * 字段长度类型 0:filedname filedtype,1:filedname filedtype(length),2:filedname
	 * filedtype(length,prec)
	 */
	private Integer fileTypeLength;

	/**
	 * 是否主键
	 */
	private Integer is_pk;

	private String is_pk_text;

	/**
	 * 是否必填
	 */
	private Integer is_m;

	private String is_m_text;

	/**
	 * 是否内置
	 */
	private Integer is_innr;

	private String is_innr_text;
	/***
	 *  是否唯一
	 */
	private Integer is_unique;
	
	private String is_unique_text;

	/**
	 * 代码表
	 */
	private String field_tab_code;
	
	private String field_tab_name;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 排序（升序）
	 */
	private Integer sort;
	
	private HrStoreColSet hrStoreColSet;
	
	private HrStoreQueSet hrStoreQueSet;
	

	/**
	 * 导入验证信息
	 */
	private String error_type;

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	/*public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}*/

	public String getTab_code() {
		return tab_code;
	}

	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}

	public String getCol_code() {
		return col_code;
	}

	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}

	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getData_type_name() {
		return data_type_name;
	}

	public void setData_type_name(String data_type_name) {
		this.data_type_name = data_type_name;
	}

	public Integer getFiled_length() {
		return filed_length;
	}

	public void setFiled_length(Integer filed_length) {
		this.filed_length = filed_length;
	}

	public Integer getPrec() {
		return prec;
	}

	public void setPrec(Integer prec) {
		this.prec = prec;
	}

	public Integer getFileTypeLength() {
		return fileTypeLength;
	}

	public void setFileTypeLength(Integer fileTypeLength) {
		this.fileTypeLength = fileTypeLength;
	}

	public Integer getIs_pk() {
		return is_pk;
	}

	public void setIs_pk(Integer is_pk) {
		this.is_pk = is_pk;
	}

	public String getIs_pk_text() {
		return is_pk_text;
	}

	public void setIs_pk_text(String is_pk_text) {
		this.is_pk_text = is_pk_text;
	}

	public Integer getIs_m() {
		return is_m;
	}

	public void setIs_m(Integer is_m) {
		this.is_m = is_m;
	}

	public String getIs_m_text() {
		return is_m_text;
	}

	public void setIs_m_text(String is_m_text) {
		this.is_m_text = is_m_text;
	}

	public Integer getIs_innr() {
		return is_innr;
	}

	public void setIs_innr(Integer is_innr) {
		this.is_innr = is_innr;
	}

	public String getIs_innr_text() {
		return is_innr_text;
	}

	public void setIs_innr_text(String is_innr_text) {
		this.is_innr_text = is_innr_text;
	}

	public String getField_tab_code() {
		return field_tab_code;
	}

	public void setField_tab_code(String field_tab_code) {
		this.field_tab_code = field_tab_code;
	}
	
	public String getField_tab_name() {
		return field_tab_name;
	}

	public void setField_tab_name(String field_tab_name) {
		this.field_tab_name = field_tab_name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public HrStoreColSet getHrStoreColSet() {
		return hrStoreColSet;
	}

	public void setHrStoreColSet(HrStoreColSet hrStoreColSet) {
		this.hrStoreColSet = hrStoreColSet;
	}

	public HrStoreQueSet getHrStoreQueSet() {
		return hrStoreQueSet;
	}

	public void setHrStoreQueSet(HrStoreQueSet hrStoreQueSet) {
		this.hrStoreQueSet = hrStoreQueSet;
	}

	public Integer getIs_unique() {
		return is_unique;
	}

	public void setIs_unique(Integer is_unique) {
		this.is_unique = is_unique;
	}

	public String getIs_unique_text() {
		return is_unique_text;
	}

	public void setIs_unique_text(String is_unique_text) {
		this.is_unique_text = is_unique_text;
	}
	

}