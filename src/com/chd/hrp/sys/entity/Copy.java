/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class Copy implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String copy_name;
	private int is_stop;
	private String nature_code;
	private String nature_name;
	private String note;

	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
		
	public Long getHos_id() {
		return this.hos_id;
	}
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
		
	public String getCopy_code() {
		return this.copy_code;
	}
	public void setCopy_name(String value) {
		this.copy_name = value;
	}
		
	public String getCopy_name() {
		return this.copy_name;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}

	
	
	/**
	 * 获取 nature_code
	 * @return nature_code
	 */
	public String getNature_code() {
		return nature_code;
	}

	
	/**
	 * 设置 nature_code
	 * @param nature_code 
	 */
	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}

	/**
	 * 获取 nature_name
	 * @return nature_name
	 */
	public String getNature_name() {
		return nature_name;
	}

	
	/**
	 * 设置 nature_name
	 * @param nature_name 
	 */
	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	
}