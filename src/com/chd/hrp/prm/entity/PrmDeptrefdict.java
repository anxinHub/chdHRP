
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 8805 科室映射字典参数表
 * @Table:
 * APHI_DEPTREFDICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmDeptrefdict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 01:平行 02:拆分 C03:合并
	 */
	private String ref_code;
	
	/**
	 * 关系名称
	 */
	private String ref_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 01:平行 02:拆分 C03:合并
	* @param value 
	*/
	public void setRef_code(String value) {
		this.ref_code = value;
	}
	
	/**
	* 获取 01:平行 02:拆分 C03:合并
	* @return String
	*/
	public String getRef_code() {
		return this.ref_code;
	}
	/**
	* 设置 关系名称
	* @param value 
	*/
	public void setRef_name(String value) {
		this.ref_name = value;
	}
	
	/**
	* 获取 关系名称
	* @return String
	*/
	public String getRef_name() {
		return this.ref_name;
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