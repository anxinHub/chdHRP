/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 050117 资产卡片显示表
 * @Table:
 * ASS_CARD_SET_VIEW
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssCardSetView implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 资产性质
	 */
	private String ass_naturs;
	
	/**
	 * 卡片表名
	 */
	private String table_name;
	
	/**
	 * 列名英文
	 */
	private String col_code;
	
	/**
	 * 列名中文
	 */
	private String col_name;
	
	/**
	 * 控件类型
	 */
	private String type_code;
	
	/**
	 * 显示顺序
	 */
	private Integer seq_no;
	
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Integer is_view;
	
	private String re_url;
	
	private Integer is_change;
	
	private String naturs_name;
	
	private Integer column_width;
	
	private String text_align;
	
	private Integer field_area;
	
	private Integer field_width;
	
	private Integer is_tab_view;
	
	private Integer is_verify;
	
	private Integer is_pk;
	
	private Integer is_init_view;
	
	private Integer is_init_tab_view;
	
	private Integer is_insert_view;
	
	private Integer is_default;
	
	private String default_value;
	
	private String default_text;
	
	private Integer is_read;
	
	private Integer is_insert_read;
	
	private Integer is_init_read;
	
	
	
	public Integer getIs_read() {
		return is_read;
	}

	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}

	public Integer getIs_insert_read() {
		return is_insert_read;
	}

	public void setIs_insert_read(Integer is_insert_read) {
		this.is_insert_read = is_insert_read;
	}

	public Integer getIs_init_read() {
		return is_init_read;
	}

	public void setIs_init_read(Integer is_init_read) {
		this.is_init_read = is_init_read;
	}

	public String getDefault_text() {
		return default_text;
	}

	public void setDefault_text(String default_text) {
		this.default_text = default_text;
	}

	public Integer getIs_tab_view() {
		return is_tab_view;
	}

	public void setIs_tab_view(Integer is_tab_view) {
		this.is_tab_view = is_tab_view;
	}

	public Integer getIs_verify() {
		return is_verify;
	}

	public void setIs_verify(Integer is_verify) {
		this.is_verify = is_verify;
	}

	public Integer getIs_pk() {
		return is_pk;
	}

	public void setIs_pk(Integer is_pk) {
		this.is_pk = is_pk;
	}

	public Integer getIs_init_view() {
		return is_init_view;
	}

	public void setIs_init_view(Integer is_init_view) {
		this.is_init_view = is_init_view;
	}

	public Integer getIs_init_tab_view() {
		return is_init_tab_view;
	}

	public void setIs_init_tab_view(Integer is_init_tab_view) {
		this.is_init_tab_view = is_init_tab_view;
	}

	public Integer getIs_insert_view() {
		return is_insert_view;
	}

	public void setIs_insert_view(Integer is_insert_view) {
		this.is_insert_view = is_insert_view;
	}

	public Integer getIs_default() {
		return is_default;
	}

	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public Integer getField_area() {
		return field_area;
	}

	public void setField_area(Integer field_area) {
		this.field_area = field_area;
	}

	public Integer getField_width() {
		return field_width;
	}

	public void setField_width(Integer field_width) {
		this.field_width = field_width;
	}

	public Integer getColumn_width() {
		return column_width;
	}

	public void setColumn_width(Integer column_width) {
		this.column_width = column_width;
	}

	public String getText_align() {
		return text_align;
	}

	public void setText_align(String text_align) {
		this.text_align = text_align;
	}


	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}

	public Integer getIs_change() {
		return is_change;
	}

	public void setIs_change(Integer is_change) {
		this.is_change = is_change;
	}

	public String getRe_url() {
		return re_url;
	}

	public void setRe_url(String re_url) {
		this.re_url = re_url;
	}

	public Integer getIs_view() {
		return is_view;
	}

	public void setIs_view(Integer is_view) {
		this.is_view = is_view;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setAss_naturs(String value) {
		this.ass_naturs = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getAss_naturs() {
		return this.ass_naturs;
	}
	/**
	* 设置 卡片表名
	* @param value 
	*/
	public void setTable_name(String value) {
		this.table_name = value;
	}
	
	/**
	* 获取 卡片表名
	* @return String
	*/
	public String getTable_name() {
		return this.table_name;
	}
	/**
	* 设置 列名英文
	* @param value 
	*/
	public void setCol_code(String value) {
		this.col_code = value;
	}
	
	/**
	* 获取 列名英文
	* @return String
	*/
	public String getCol_code() {
		return this.col_code;
	}
	/**
	* 设置 列名中文
	* @param value 
	*/
	public void setCol_name(String value) {
		this.col_name = value;
	}
	
	/**
	* 获取 列名中文
	* @return String
	*/
	public String getCol_name() {
		return this.col_name;
	}
	/**
	* 设置 控件类型
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 控件类型
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 显示顺序
	* @param value 
	*/
	public void setSeq_no(Integer value) {
		this.seq_no = value;
	}
	
	/**
	* 获取 显示顺序
	* @return Integer
	*/
	public Integer getSeq_no() {
		return this.seq_no;
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