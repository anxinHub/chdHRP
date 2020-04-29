/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description:
 * 
 * @Table: HR_TAB_STRUC
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrTableStruc implements Serializable {

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
	// private String copy_code;

	/**
	 * 
	 */
	private String tab_code;

	/**
	 * 
	 */
	private String tab_name;

	/**
	 * 
	 */
	private String type_tab_code;

	/**
	 * JSON存储数据表字段
	 */
	private String tab_col;

	/**
	 * JSON存储数据表SQL
	 */
	private String tab_sql;

	/**
	 * JSON数据列设置
	 */
	private String tab_view_col;

	/**
	 * JSON数据列设置
	 */
	private String tab_view_page;

	/**
	 * JSON查询设计器
	 */
	private String tab_query_col;

	/**
	 * JSON动态页面构建
	 */
	private String tab_query_page;

	/**
	 * 
	 */
	private Integer is_innr;

	private Integer sort;

	private Integer is_stop;;

	/**
	 * 
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setGroup_id(Integer value) {
		this.group_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Integer getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setHos_id(Integer value) {
		this.hos_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Integer getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置
	 * 
	 * @param value
	 */
	/*
	 * public void setCopy_code(String value) { this.copy_code = value; }
	 */

	/**
	 * 获取
	 * 
	 * @return String
	 */
	/*
	 * public String getCopy_code() { return this.copy_code; }
	 */
	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setTab_code(String value) {
		this.tab_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getTab_code() {
		return this.tab_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setTab_name(String value) {
		this.tab_name = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getTab_name() {
		return this.tab_name;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setType_tab_code(String value) {
		this.type_tab_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getType_tab_code() {
		return this.type_tab_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setIs_innr(Integer value) {
		this.is_innr = value;
	}

	/**
	 * 获取
	 * 
	 * @return Integer
	 */
	public Integer getIs_innr() {
		return this.is_innr;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
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

	public String getTab_col() {
		return tab_col;
	}

	public void setTab_col(String tab_col) {
		this.tab_col = tab_col;
	}

	public String getTab_sql() {
		return tab_sql;
	}

	public void setTab_sql(String tab_sql) {
		this.tab_sql = tab_sql;
	}

	public String getTab_view_col() {
		return tab_view_col;
	}

	public void setTab_view_col(String tab_view_col) {
		this.tab_view_col = tab_view_col;
	}

	public String getTab_view_page() {
		return tab_view_page;
	}

	public void setTab_view_page(String tab_view_page) {
		this.tab_view_page = tab_view_page;
	}

	public String getTab_query_col() {
		return tab_query_col;
	}

	public void setTab_query_col(String tab_query_col) {
		this.tab_query_col = tab_query_col;
	}

	public String getTab_query_page() {
		return tab_query_page;
	}

	public void setTab_query_page(String tab_query_page) {
		this.tab_query_page = tab_query_page;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	
	public List<HrTableColumn> getTableColumn(){
		if(isJsonArr(this.tab_col)){
			return JSONArray.parseArray(tab_col, HrTableColumn.class);
		}
		return new ArrayList<HrTableColumn>();
	}
	
	public HrTableColumnConfig getTableColumnConfig(){
		if(isJsonObj(this.tab_view_col)){
			return JSONObject.parseObject(tab_view_col, HrTableColumnConfig.class);
		}
		return new HrTableColumnConfig();
	}
	
	public List<HrTableSql> getTableSql(){
		if(isJsonArr(this.tab_sql)){
			return JSONArray.parseArray(tab_sql, HrTableSql.class);
		}
		return new ArrayList<HrTableSql>();
	}
		
	private boolean isJsonArr(String str){
		Object object = JSON.parse(str);
		return object instanceof JSONArray;
	}
	
	private boolean isJsonObj(String str){
		Object object = JSON.parse(str);
		return object instanceof JSONObject;
	}

}