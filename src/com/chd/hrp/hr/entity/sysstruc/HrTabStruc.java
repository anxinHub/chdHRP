/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.sysstruc;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrTabStruc implements Serializable {

	
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
	 * 
	 */
	private Integer is_innr;
	
	/**
	 * 
	 */
	private String note;
	/**
	 * 
	 */
    private List<HrColStruc> hrColStrucList;
	
    /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Integer value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Integer value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	/*public void setCopy_code(String value) {
		this.copy_code = value;
	}*/
	
	/**
	* 获取 
	* @return String
	*/
	/*public String getCopy_code() {
		return this.copy_code;
	}*/
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
	public void setTab_name(String value) {
		this.tab_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTab_name() {
		return this.tab_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setType_tab_code(String value) {
		this.type_tab_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getType_tab_code() {
		return this.type_tab_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_innr(Integer value) {
		this.is_innr = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_innr() {
		return this.is_innr;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
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


	public List<HrColStruc> getHrColStrucList() {
		return hrColStrucList;
	}

	public void setHrColStrucList(List<HrColStruc> hrColStrucList) {
		this.hrColStrucList = hrColStrucList;
	}

}