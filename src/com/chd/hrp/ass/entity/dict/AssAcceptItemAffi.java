
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050107 验收项目字典
 * @Table:
 * ASS_ACCEPT_ITEM_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssAcceptItemAffi implements Serializable {

	
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
	 * 验收项目编码
	 */
	private String accept_item_code;
	
	/**
	 * 验收项目名称
	 */
	private String accept_item_name;
	
	/**
	 * 是否停用
	 */
	private Integer ass_id;
	
	
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
	* 设置 验收项目编码
	* @param value 
	*/
	public void setAccept_item_code(String value) {
		this.accept_item_code = value;
	}
	
	/**
	* 获取 验收项目编码
	* @return String
	*/
	public String getAccept_item_code() {
		return this.accept_item_code;
	}
	/**
	* 设置 验收项目名称
	* @param value 
	*/
	public void setAccept_item_name(String value) {
		this.accept_item_name = value;
	}
	
	/**
	* 获取 验收项目名称
	* @return String
	*/
	public String getAccept_item_name() {
		return this.accept_item_name;
	}

	public Integer getAss_id() {
		return ass_id;
	}

	public void setAss_id(Integer ass_id) {
		this.ass_id = ass_id;
	}

}