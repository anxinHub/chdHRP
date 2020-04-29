package com.chd.hrp.platform.entity;

import java.io.Serializable;
/**
 * 省平台对应关系实体类
 * @author Administrator
 *
 */
public class Zj implements Serializable {
 
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
	 * HRP材料编码
	 */
	private String inv_code;
	
	/**
	 * HRP材料名称
	 */
	private String inv_name;
	
	/**
	 * 省平台材料编码
	 */
	private String goodsid;
	
	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getInv_code() {
		return inv_code;
	}

	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}

	public String getInv_name() {
		return inv_name;
	}

	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * 省平台材料名称
	 */
	private String goodsname;
	
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	
}
