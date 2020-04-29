
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 8804 科室字典变更表
 * @Table:
 * APHI_DEPT_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AphiDeptDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 变更ID
	 */
	private Long dept_no;
	
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
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 部门编码
	 */
	private String dept_code;
	
	/**
	 * 部门名称
	 */
	private String dept_name;
	
	/**
	 * 科室描述
	 */
	private String dept_note;
	
	/**
	 * 科室分类
	 */
	private String dept_kind_code;
	
	/**
	 * 科室性质
	 */
	private String nature_code;
	
	/**
	 * 0:不参与 1:参与
	 */
	private Integer is_avg;
	
	/**
	 * 变更人
	 */
	private String user_code;
	
	/**
	 * 变更时间
	 */
	private Date create_date;
	
	/**
	 * 变更原因
	 */
	private String note;
	
	/**
	 * 0启用，1停用
	 */
	private Integer is_stop;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 变更ID
	* @return Double
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Double
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
	* @return Double
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
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Double
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 部门编码
	* @param value 
	*/
	public void setDept_code(String value) {
		this.dept_code = value;
	}
	
	/**
	* 获取 部门编码
	* @return String
	*/
	public String getDept_code() {
		return this.dept_code;
	}
	/**
	* 设置 部门名称
	* @param value 
	*/
	public void setDept_name(String value) {
		this.dept_name = value;
	}
	
	/**
	* 获取 部门名称
	* @return String
	*/
	public String getDept_name() {
		return this.dept_name;
	}
	/**
	* 设置 科室描述
	* @param value 
	*/
	public void setDept_note(String value) {
		this.dept_note = value;
	}
	
	/**
	* 获取 科室描述
	* @return String
	*/
	public String getDept_note() {
		return this.dept_note;
	}
	/**
	* 设置 科室分类
	* @param value 
	*/
	public void setDept_kind_code(String value) {
		this.dept_kind_code = value;
	}
	
	/**
	* 获取 科室分类
	* @return String
	*/
	public String getDept_kind_code() {
		return this.dept_kind_code;
	}
	/**
	* 设置 科室性质
	* @param value 
	*/
	public void setNature_code(String value) {
		this.nature_code = value;
	}
	
	/**
	* 获取 科室性质
	* @return String
	*/
	public String getNature_code() {
		return this.nature_code;
	}
	/**
	* 设置 0:不参与 1:参与
	* @param value 
	*/
	public void setIs_avg(Integer value) {
		this.is_avg = value;
	}
	
	/**
	* 获取 0:不参与 1:参与
	* @return Integer
	*/
	public Integer getIs_avg() {
		return this.is_avg;
	}
	/**
	* 设置 变更人
	* @param value 
	*/
	public void setUser_code(String value) {
		this.user_code = value;
	}
	
	/**
	* 获取 变更人
	* @return String
	*/
	public String getUser_code() {
		return this.user_code;
	}
	/**
	* 设置 变更时间
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 变更时间
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 变更原因
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 变更原因
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	/**
	* 设置 0启用，1停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0启用，1停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
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