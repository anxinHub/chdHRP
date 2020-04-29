/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_CHECK_Rela
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatAffiCheckRela implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	/**
	 * 
	 */
	private Long check_id;
	
	/**
	 * 
	 */
	private String check_code;
	
	/**
	 * 
	 */
	private Long out_id;
	
	/**
	 * 
	 */
	private String out_no;
	
	/**
	 * 
	 */
	private Long in_id;
	
	/**
	 * 
	 */
	private String in_no;
	
	
	
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	
	/**
	* 设置 
	* @param value 
	*/
	public void setCheck_id(Long value) {
		this.check_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getCheck_id() {
		return this.check_id;
	}
	
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_id(Long value) {
		this.in_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getIn_id() {
		return this.in_id;
	}
	
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_id(Long value) {
		this.out_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_id() {
		return this.out_id;
	}

	public String getCheck_code() {
		return check_code;
	}

	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}

	public String getOut_no() {
		return out_no;
	}

	public void setOut_no(String out_no) {
		this.out_no = out_no;
	}

	public String getIn_no() {
		return in_no;
	}

	public void setIn_no(String in_no) {
		this.in_no = in_no;
	}
	
}