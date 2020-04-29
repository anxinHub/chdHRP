package com.chd.hrp.acc.entity.report;

import java.io.Serializable;

/**
 * 报表元素参数
 * @author ADMINISTRATOR
 *
 */
public class RepDefinePara implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072765849173771789L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String ele_code;//指字段名、函数名、存储过程名
	private String para_code;
	private String para_name;
	private int para_type;
	private String para_type_name;
	private String para_json;
	private String dict_code;
	private String dict_name;
	private Long sort_code;
	private int is_stop;
	private String is_stop_name; 
	private String para_value;
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
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getEle_code() {
		return ele_code;
	}
	public void setEle_code(String ele_code) {
		this.ele_code = ele_code;
	}
	
	public String getPara_value() {
		return para_value;
	}
	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}
	public String getPara_code() {
		return para_code;
	}
	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}
	public int getPara_type() {
		return para_type;
	}
	public void setPara_type(int para_type) {
		this.para_type = para_type;
	}
	public String getPara_json() {
		return para_json;
	}
	public void setPara_json(String para_json) {
		this.para_json = para_json;
	}
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	public String getDict_code() {
		return dict_code;
	}
	public void setDict_code(String dict_code) {
		this.dict_code = dict_code;
	}
	public Long getSort_code() {
		return sort_code;
	}
	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}
	public String getPara_type_name() {
		return para_type_name;
	}
	public void setPara_type_name(String para_type_name) {
		this.para_type_name = para_type_name;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public int getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
	public String getIs_stop_name() {
		return is_stop_name;
	}
	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
	}
	
}
