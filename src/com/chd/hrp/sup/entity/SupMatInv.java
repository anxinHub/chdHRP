/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.sup.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 100003 物资材料表
 * @Table:
 * SUP_MAT_INV
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class SupMatInv implements Serializable {

	
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
	 * 物资材料编码
	 */
	private Long inv_id;
	/**
	 * 物资材料编码
	 */
	private String inv_code;
	
	/**
	 * 物资材料名称
	 */
	private String inv_name;
	
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 物资类别ID
	 */
	private Long mat_type_id;
	
	/**
	 * 物资类别ID
	 */
	private Long mat_type_no;
	
	/**
	 * 物资类别编码
	 */
	private String mat_type_code;
	/* 物资类别名称
	*/
	private String mat_type_name;
	
	/**
	 * 规格型号
	 */
	private String inv_model;
	
	/**
	 * 计量单位
	 */
	private String unit_code;
	/**
	 * 计量单位
	 */
	private String unit_name;
	
	/**
	 * 生产厂商ID
	 */
	private Long fac_id;
	/**
	 * 生产厂商ID
	 */
	private String fac_code;
	/**
	 * 生产厂商ID
	 */
	private String fac_name;
	
	/**
	 * 计划价
	 */
	private Double plan_price;
	
	/**
	 * 加价率
	 */
	private Double price_rate;
	
	/**
	 * 零售价
	 */
	private Double sell_price;
	
	/**
	 * 品牌
	 */
	private String brand_name;
	
	/**
	 * 代理商
	 */
	private String agent_name;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 审核状态
	 */
	private Integer state;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	/**
	 * 审核人
	 */
	private String checker_name;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	/**
	 * 供应商编码
	 */
	private String sup_code;
	
	/**
	 * 供应商名称
	 */
	private String sup_name;

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Long hrp_inv_id;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
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
	* 设置 物资材料编码
	* @param value 
	*/
	public void setInv_code(String value) {
		this.inv_code = value;
	}
	
	/**
	* 获取 物资材料编码
	* @return String
	*/
	public String getInv_code() {
		return this.inv_code;
	}
	/**
	* 设置 物资材料名称
	* @param value 
	*/
	public void setInv_name(String value) {
		this.inv_name = value;
	}
	
	/**
	* 获取 物资材料名称
	* @return String
	*/
	public String getInv_name() {
		return this.inv_name;
	}
	/**
	* 设置 别名
	* @param value 
	*/
	public void setAlias(String value) {
		this.alias = value;
	}
	
	/**
	* 获取 别名
	* @return String
	*/
	public String getAlias() {
		return this.alias;
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
	* 设置 物资类别ID
	* @param value 
	*/
	public void setMat_type_id(Long value) {
		this.mat_type_id = value;
	}
	
	/**
	* 获取 物资类别ID
	* @return Long
	*/
	public Long getMat_type_id() {
		return this.mat_type_id;
	}
	/**
	* 设置 规格型号
	* @param value 
	*/
	public void setInv_model(String value) {
		this.inv_model = value;
	}
	
	/**
	* 获取 规格型号
	* @return String
	*/
	public String getInv_model() {
		return this.inv_model;
	}
	/**
	* 设置 计量单位
	* @param value 
	*/
	public void setUnit_code(String value) {
		this.unit_code = value;
	}
	
	/**
	* 获取 计量单位
	* @return String
	*/
	public String getUnit_code() {
		return this.unit_code;
	}
	/**
	* 设置 生产厂商ID
	* @param value 
	*/
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂商ID
	* @return Long
	*/
	public Long getFac_id() {
		return this.fac_id;
	}
	/**
	* 设置 计划价
	* @param value 
	*/
	public void setPlan_price(Double value) {
		this.plan_price = value;
	}
	
	/**
	* 获取 计划价
	* @return Double
	*/
	public Double getPlan_price() {
		return this.plan_price;
	}
	/**
	* 设置 加价率
	* @param value 
	*/
	public void setPrice_rate(Double value) {
		this.price_rate = value;
	}
	
	/**
	* 获取 加价率
	* @return Double
	*/
	public Double getPrice_rate() {
		return this.price_rate;
	}
	/**
	* 设置 零售价
	* @param value 
	*/
	public void setSell_price(Double value) {
		this.sell_price = value;
	}
	
	/**
	* 获取 零售价
	* @return Double
	*/
	public Double getSell_price() {
		return this.sell_price;
	}
	/**
	* 设置 品牌
	* @param value 
	*/
	public void setBrand_name(String value) {
		this.brand_name = value;
	}
	
	/**
	* 获取 品牌
	* @return String
	*/
	public String getBrand_name() {
		return this.brand_name;
	}
	/**
	* 设置 代理商
	* @param value 
	*/
	public void setAgent_name(String value) {
		this.agent_name = value;
	}
	
	/**
	* 获取 代理商
	* @return String
	*/
	public String getAgent_name() {
		return this.agent_name;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	/**
	* 设置 审核状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 审核状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getSup_id() {
		return this.sup_id;
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

	
    public Long getInv_id() {
    	return inv_id;
    }

	
    public void setInv_id(Long inv_id) {
    	this.inv_id = inv_id;
    }

	
    public String getMat_type_name() {
    	return mat_type_name;
    }

	
    public void setMat_type_name(String mat_type_name) {
    	this.mat_type_name = mat_type_name;
    }

	
    public String getUnit_name() {
    	return unit_name;
    }

	
    public void setUnit_name(String unit_name) {
    	this.unit_name = unit_name;
    }

	
    public String getFac_name() {
    	return fac_name;
    }

	
    public void setFac_name(String fac_name) {
    	this.fac_name = fac_name;
    }

	
    public String getMat_type_code() {
    	return mat_type_code;
    }

	
    public void setMat_type_code(String mat_type_code) {
    	this.mat_type_code = mat_type_code;
    }

	
    public String getFac_code() {
    	return fac_code;
    }

	
    public void setFac_code(String fac_code) {
    	this.fac_code = fac_code;
    }

	
    public String getSup_code() {
    	return sup_code;
    }

	
    public void setSup_code(String sup_code) {
    	this.sup_code = sup_code;
    }

	
    public String getSup_name() {
    	return sup_name;
    }

	
    public void setSup_name(String sup_name) {
    	this.sup_name = sup_name;
    }

	
    public Long getMat_type_no() {
    	return mat_type_no;
    }

	
    public void setMat_type_no(Long mat_type_no) {
    	this.mat_type_no = mat_type_no;
    }

	
    public Long getHrp_inv_id() {
    	return hrp_inv_id;
    }

	
    public void setHrp_inv_id(Long hrp_inv_id) {
    	this.hrp_inv_id = hrp_inv_id;
    }

	
    public String getChecker_name() {
    	return checker_name;
    }

	
    public void setChecker_name(String checker_name) {
    	this.checker_name = checker_name;
    }
    
}