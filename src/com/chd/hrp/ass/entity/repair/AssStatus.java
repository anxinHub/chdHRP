/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.repair;

import java.io.Serializable;
import java.util.*;


public class AssStatus implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Integer status_id;
	
	private String status_name;
	
	private Long bdf_hour;
	private Long fdx_hour;
	private Long xdw_hour;
	
	
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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
	public Integer getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public Long getBdf_hour() {
		return bdf_hour;
	}
	public void setBdf_hour(Long bdf_hour) {
		this.bdf_hour = bdf_hour;
	}
	public Long getFdx_hour() {
		return fdx_hour;
	}
	public void setFdx_hour(Long fdx_hour) {
		this.fdx_hour = fdx_hour;
	}
	public Long getXdw_hour() {
		return xdw_hour;
	}
	public void setXdw_hour(Long xdw_hour) {
		this.xdw_hour = xdw_hour;
	}

	
	
}