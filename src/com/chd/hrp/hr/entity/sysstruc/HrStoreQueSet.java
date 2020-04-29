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
 * 人员档案库查询条件设置
 * @Table:
 * HR_STORE_QUE_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrStoreQueSet implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	
	/**
	 * 
	 */
	private String store_type_code;
	
	/**
	 * 
	 */
	private String tab_code;
	
	/**
	 * 
	 */
	private String col_code;
	
	private String col_name;
	
	/**
	 * 
	 */
	private String col_name_show;
	
	/**
	 * 
	 */
	private String com_type_code;
	
	private String com_type_name;
	
	/**
	 * 
	 */
	private Integer seq_no;
	
	/**
	 * 
	 */
	private Integer is_view;
	
	private String is_view_text;
	
	/**
	 * left:左对齐  right:右对齐 center:居中对齐
	 */
	private String text_align;
	
	private String text_align_text;
	
	/**
	 * 最小为1(占一列)，最大为4（占一行）

	 */
	private Integer field_width;
	
	private String field_width_text;
	
	/**
	 * 
	 */
	private String con_sign_code;
	
	private String con_sign_name;
	
	/**
	 * 
	 */
	private Integer is_section;
	
	private String is_section_text;
	
	/**
	 * 
	 */
	private Integer is_change;
	
	private String is_change_text;
	
	/**
	 * 
	 */
	private String change_col_code;
	
	/**
	 * 
	 */
	private Integer is_default;
	
	private String is_default_text;
	
	/**
	 * 
	 */
	private String default_value;
	
	/**
	 * 
	 */
	private String default_text;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	* 设置 
	* @param value 
	*/
	public void setStore_type_code(String value) {
		this.store_type_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getStore_type_code() {
		return this.store_type_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTab_code(String value) {
		this.tab_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTab_code() {
		return this.tab_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCol_code(String value) {
		this.col_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCol_code() {
		return this.col_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCol_name_show(String value) {
		this.col_name_show = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCol_name_show() {
		return this.col_name_show;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCom_type_code(String value) {
		this.com_type_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCom_type_code() {
		return this.com_type_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSeq_no(Integer value) {
		this.seq_no = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getSeq_no() {
		return this.seq_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_view(Integer value) {
		this.is_view = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_view() {
		return this.is_view;
	}
	/**
	* 设置 left:左对齐  right:右对齐 center:居中对齐
	* @param value 
	*/
	public void setText_align(String value) {
		this.text_align = value;
	}
	
	/**
	* 获取 left:左对齐  right:右对齐 center:居中对齐
	* @return String
	*/
	public String getText_align() {
		return this.text_align;
	}
	/**
	* 设置 最小为1(占一列)，最大为4（占一行）

	* @param value 
	*/
	public void setField_width(Integer value) {
		this.field_width = value;
	}
	
	/**
	* 获取 最小为1(占一列)，最大为4（占一行）

	* @return Long
	*/
	public Integer getField_width() {
		return this.field_width;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCon_sign_code(String value) {
		this.con_sign_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getCon_sign_code() {
		return this.con_sign_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_section(Integer value) {
		this.is_section = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_section() {
		return this.is_section;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_change(Integer value) {
		this.is_change = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_change() {
		return this.is_change;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setChange_col_code(String value) {
		this.change_col_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getChange_col_code() {
		return this.change_col_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_default(Integer value) {
		this.is_default = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_default() {
		return this.is_default;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDefault_value(String value) {
		this.default_value = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getDefault_value() {
		return this.default_value;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDefault_text(String value) {
		this.default_text = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getDefault_text() {
		return this.default_text;
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

	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	public String getCom_type_name() {
		return com_type_name;
	}

	public void setCom_type_name(String com_type_name) {
		this.com_type_name = com_type_name;
	}

	public String getCon_sign_name() {
		return con_sign_name;
	}

	public void setCon_sign_name(String con_sign_name) {
		this.con_sign_name = con_sign_name;
	}

	public void setIs_view_text(String is_view_text) {
		this.is_view_text = is_view_text;
	}

	public void setIs_section_text(String is_section_text) {
		this.is_section_text = is_section_text;
	}

	public void setIs_change_text(String is_change_text) {
		this.is_change_text = is_change_text;
	}

	public void setIs_default_text(String is_default_text) {
		this.is_default_text = is_default_text;
	}

	public String getIs_view_text() {
		return is_view != null && is_view == 1 ? "是" : "否";
	}

	public String getIs_section_text() {
		return is_section != null && is_section == 1 ? "是" : "否";
	}

	public String getIs_change_text() {
		return is_change != null && is_change == 1 ? "是" : "否";
	}

	public String getIs_default_text() {
		return is_default != null && is_default == 1 ? "是" : "否";
	}
	
	public String getText_align_text() {
		if(text_align != null && "left".equals(text_align)){
			return "左对齐";
		}else if(text_align != null && "right".equals(text_align)){
			return "右对齐";
		}else if(text_align != null && "center".equals(text_align)){
			return "居中对齐";
		}
		return text_align_text;
	}

	public void setText_align_text(String text_align_text) {
		this.text_align_text = text_align_text;
	}

	public String getField_width_text() {
		if(field_width != null){
			switch (field_width) {
			case 1:
				field_width_text = "1列";
				break;
			case 2:
				field_width_text = "2列";
				break;
			case 3:
				field_width_text = "3列";
				break;
			case 4:
				field_width_text = "4列";
				break;
			}
		}
		return field_width_text;
		
	}

	public void setField_width_text(String field_width_text) {
		this.field_width_text = field_width_text;
	}
	
	
}