/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

/**
 * 
 * @Description: 查询设计器
 * @Table: HR_TABLE_DESIGN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrTableDesign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2955301172763603494L;

	private Integer group_id;
	private Integer hos_id;
	private String design_code;
	private String design_name;
	private String super_code;
	private String super_name;
	private Integer design_level;
	private Integer is_last;
	private String design_query_col;
	private String design_query_sql;
	private String design_query_page;
	private Integer design_sort;
	private Integer is_stop;
	private String design_note;
	
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

	public String getDesign_code() {
		return design_code;
	}

	public void setDesign_code(String design_code) {
		this.design_code = design_code;
	}

	public String getDesign_name() {
		return design_name;
	}

	public void setDesign_name(String design_name) {
		this.design_name = design_name;
	}

	public String getSuper_code() {
		return super_code;
	}

	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}
	
	public String getSuper_name() {
		return super_name;
	}

	public void setSuper_name(String super_name) {
		this.super_name = super_name;
	}

	public Integer getDesign_level() {
		return design_level;
	}

	public void setDesign_level(Integer design_level) {
		this.design_level = design_level;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}

	public String getDesign_query_col() {
		return design_query_col;
	}

	public void setDesign_query_col(String design_query_col) {
		this.design_query_col = design_query_col;
	}
	
	public String getDesign_query_sql() {
		return design_query_sql;
	}

	public void setDesign_query_sql(String design_query_sql) {
		this.design_query_sql = design_query_sql;
	}


	public String getDesign_query_page() {
		return design_query_page;
	}

	public void setDesign_query_page(String design_query_page) {
		this.design_query_page = design_query_page;
	}

	public Integer getDesign_sort() {
		return design_sort;
	}

	public void setDesign_sort(Integer design_sort) {
		this.design_sort = design_sort;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getDesign_note() {
		return design_note;
	}

	public void setDesign_note(String design_note) {
		this.design_note = design_note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	

}