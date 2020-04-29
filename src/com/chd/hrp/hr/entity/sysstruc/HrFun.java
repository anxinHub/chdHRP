
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.entity.sysstruc;

import java.io.Serializable;
import java.sql.Blob;
/**
 * 
 * @Description:
 * 9908 绩效函数表
 * @Table:
 * PRM_FUN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class HrFun implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
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
	 * 函数编码
	 */
	private String fun_code;
	
	/**
	 * 函数名称
	 */
	private String fun_name;
	
	/**
	 * 函数分类编码
	 */
	private String type_code;
	
	/**
	 * 取值函数(中文)
	 */
	private String fun_method_chs;
	
	/**
	 * 取值函数(英文)
	 */
	private String fun_method_eng;
	
	/**
	 * 函数说明
	 */
	private String fun_note;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 0：否 1:是
	 */
	private Integer is_pre;
	
	/**
	 * 0：否 1:是
	 */
	private Integer is_rec;
	
	/**
	 * 0：否 1:是
	 */
	private Integer is_stop;
	
	
	private String type_name;
	
	private String  prm_fun_sql; //函数sql

	private String prc_name;
	
	private byte[] fun_sql;// 列值 clob类型
	
	private String pkg_name;
	
	
	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public String getPrc_name() {
		return prc_name;
	}

	public void setPrc_name(String prc_name) {
		this.prc_name = prc_name;
	}

	public byte[] getFun_sql() {
		return fun_sql;
	}

	public void setFun_sql(byte[] fun_sql) {
		this.fun_sql = fun_sql;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getPrm_fun_sql() {
		return prm_fun_sql;
	}

	public void setPrm_fun_sql(String prm_fun_sql) {
		this.prm_fun_sql = prm_fun_sql;
	}
/**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
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
	* 设置 函数编码
	* @param value 
	*/
	public void setFun_code(String value) {
		this.fun_code = value;
	}
	
	/**
	* 获取 函数编码
	* @return String
	*/
	public String getFun_code() {
		return this.fun_code;
	}
	/**
	* 设置 函数名称
	* @param value 
	*/
	public void setFun_name(String value) {
		this.fun_name = value;
	}
	
	/**
	* 获取 函数名称
	* @return String
	*/
	public String getFun_name() {
		return this.fun_name;
	}
	/**
	* 设置 函数分类编码
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 函数分类编码
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 取值函数(中文)
	* @param value 
	*/
	public void setFun_method_chs(String value) {
		this.fun_method_chs = value;
	}
	
	/**
	* 获取 取值函数(中文)
	* @return String
	*/
	public String getFun_method_chs() {
		return this.fun_method_chs;
	}
	/**
	* 设置 取值函数(英文)
	* @param value 
	*/
	public void setFun_method_eng(String value) {
		this.fun_method_eng = value;
	}
	
	/**
	* 获取 取值函数(英文)
	* @return String
	*/
	public String getFun_method_eng() {
		return this.fun_method_eng;
	}
	/**
	* 设置 函数说明
	* @param value 
	*/
	public void setFun_note(String value) {
		this.fun_note = value;
	}
	
	/**
	* 获取 函数说明
	* @return String
	*/
	public String getFun_note() {
		return this.fun_note;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
	}
	/**
	* 设置 0：否 1:是
	* @param value 
	*/
	public void setIs_pre(Integer value) {
		this.is_pre = value;
	}
	
	/**
	* 获取 0：否 1:是
	* @return Integer
	*/
	public Integer getIs_pre() {
		return this.is_pre;
	}
	/**
	* 设置 0：否 1:是
	* @param value 
	*/
	public void setIs_rec(Integer value) {
		this.is_rec = value;
	}
	
	/**
	* 获取 0：否 1:是
	* @return Integer
	*/
	public Integer getIs_rec() {
		return this.is_rec;
	}
	/**
	* 设置 0：否 1:是
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0：否 1:是
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
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