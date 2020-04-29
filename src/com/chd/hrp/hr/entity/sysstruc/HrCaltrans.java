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
 * @Table:
 * HR_CALTRANS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrCaltrans implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private String group_id;
	
	/**
	 * 
	 */
	private String hos_id;
	
	/**
	 * 
	 */
	//private String copy_code;
	
	/**
	 * 
	 */
	private Integer tran_id;
	
	/**
	 * 
	 */
	private String main_tab_code;
	
	/**
	 * 
	 */
	private String main_col_code;
	
	/**
	 * T:次 D:天 W:周 M:月 S:季 Y:年
	 */
	private String tran_freq;
	private String tran_name;
	
	/**
	 * 
	 */
	private String exec_time;
	
	/**
	 * 
	 */
	private String exec_func;
	
	/**
	 * 0:新建  1：启动 2：中止
	 */
	private Integer tran_state;
	private String state_name;
	
	/**
	 * 
	 */
	private String note;
	
	private Integer is_system;
	private Integer func_type;
	private String mod_code;
	

   

public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

public Integer getIs_system() {
		return is_system;
	}

	public void setIs_system(Integer is_system) {
		this.is_system = is_system;
	}

	public Integer getFunc_type() {
		return func_type;
	}

	public void setFunc_type(Integer func_type) {
		this.func_type = func_type;
	}

public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
/**
	 * 导入验证信息
	 */
	private String error_type;
	
	public String getGroup_id() {
	return group_id;
	}
	
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	public String getHos_id() {
		return hos_id;
	}
	
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
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
	public void setTran_id(Integer value) {
		this.tran_id = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getTran_id() {
		return this.tran_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMain_tab_code(String value) {
		this.main_tab_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getMain_tab_code() {
		return this.main_tab_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMain_col_code(String value) {
		this.main_col_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getMain_col_code() {
		return this.main_col_code;
	}
	/**
	* 设置 T:次 D:天 W:周 M:月 S:季 Y:年
	* @param value 
	*/
	public void setTran_freq(String value) {
		this.tran_freq = value;
	}
	
	/**
	* 获取 T:次 D:天 W:周 M:月 S:季 Y:年
	* @return String
	*/
	public String getTran_freq() {
		return this.tran_freq;
	}
	
	public String getTran_name() {
		return tran_name;
	}

	public void setTran_name(String tran_name) {
		this.tran_name = tran_name;
	}

	/**
	* 设置 
	* @param value 
	*/
	public void setExec_time(String value) {
		this.exec_time = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getExec_time() {
		return this.exec_time;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setExec_func(String value) {
		this.exec_func = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getExec_func() {
		return this.exec_func;
	}
	/**
	* 设置 0:新建  1：启动 2：中止
	* @param value 
	*/
	public void setTran_state(Integer value) {
		this.tran_state = value;
	}
	
	/**
	* 获取 0:新建  1：启动 2：中止
	* @return Integer
	*/
	public Integer getTran_state() {
		return this.tran_state;
	}
	
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
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