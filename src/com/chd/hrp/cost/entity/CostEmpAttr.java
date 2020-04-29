/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_职工字典表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostEmpAttr implements Serializable {

	
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
	 * 职工ID
	 */
	private Long emp_id;
	/**
	 * 职工变更ID
	 */
	private Long emp_no;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 职称编码
	 */
	private String title_code;
	/**
	 * 分类编码
	 */
	private String emp_kind_code;
	/**
	 * 职工编码
	 */
	private String emp_code;
	/**
	 * 职工姓名
	 */
	private String emp_name;
	/**
	 * 备注
	 */
	private String note;
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
	
    private String title_name;
    
	private String emp_kind_name;
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 职工ID
	 */
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	/**
	 * 获取 职工ID
	 */	
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	 * 设置 职工变更ID
	 */
	public void setEmp_no(Long value) {
		this.emp_no = value;
	}
	/**
	 * 获取 职工变更ID
	 */	
	public Long getEmp_no() {
		return this.emp_no;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	 * 设置 职称编码
	 */
	public void setTitle_code(String value) {
		this.title_code = value;
	}
	/**
	 * 获取 职称编码
	 */	
	public String getTitle_code() {
		return this.title_code;
	}
	/**
	 * 设置 分类编码
	 */
	public void setEmp_kind_code(String value) {
		this.emp_kind_code = value;
	}
	/**
	 * 获取 分类编码
	 */	
	public String getEmp_kind_code() {
		return this.emp_kind_code;
	}
	/**
	 * 设置 职工编码
	 */
	public void setEmp_code(String value) {
		this.emp_code = value;
	}
	/**
	 * 获取 职工编码
	 */	
	public String getEmp_code() {
		return this.emp_code;
	}
	/**
	 * 设置 职工姓名
	 */
	public void setEmp_name(String value) {
		this.emp_name = value;
	}
	/**
	 * 获取 职工姓名
	 */	
	public String getEmp_name() {
		return this.emp_name;
	}
	/**
	 * 设置 备注
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 备注
	 */	
	public String getNote() {
		return this.note;
	}
	/**
	 * 设置 拼音码
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	/**
	 * 获取 拼音码
	 */	
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	/**
	 * 获取 五笔码
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
	public String getTitle_name() {
		return title_name;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public String getEmp_kind_name() {
		return emp_kind_name;
	}
	public void setEmp_kind_name(String emp_kind_name) {
		this.emp_kind_name = emp_kind_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}