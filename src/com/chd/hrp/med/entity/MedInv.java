/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 08105 药品材料表
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public class MedInv implements Serializable {

	
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
	 * 药品材料ID
	 */
	private Long inv_id;
	
	/**
	 * 药品材料编码
	 */
	private String inv_code;
	
	/**
	 * 药品材料名称
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
	 * 药品类别ID
	 */
	private Long med_type_id;
	
	
	/**
	 * 药品类别编码
	 */
	private String med_type_code;
	/**
	 * 药品类别名称
	 */
	private String med_type_name;
	
	/**
	 * 规格型号
	 */
	private String inv_model;
	
	/**
	 * 计量单位
	 */
	private String unit_code;
	
	/**
	 * 计量单位名称
	 */
	private String unit_name;
	
	/**
	 * 摊销方式
	 */
	private Integer amortize_type;
	
	/**
	 * 计价方法
	 */
	private Integer price_type;
	
	/**
	 * 生产厂商ID
	 */
	private Long fac_id;
	
	
	/**
	 * 生产厂商编码
	 */
	private String fac_code;
	

	/**
	 * 生产厂商名称
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
	 * 是否批次管理
	 */
	private Integer is_batch;
	
	/**
	 * 是否保质期管理
	 */
	private Integer is_quality;
	
	/**
	 * 是否为耐用品
	 */
	private Integer is_dura;
	
	/**
	 * 是否作二级库管理
	 */
	private Integer is_sec_whg;
	
	/**
	 * 是否自制品
	 */
	private Integer is_shel_make;
	
	/**
	 * 是否为加价销售
	 */
	private Integer is_add_sale;
	
	/**
	 * 是否条码管理
	 */
	private Integer is_bar;
	
	/**
	 * 品种条码
	 */
	private String bar_code_new;
	
	/**
	 * 是否证件管理
	 */
	private Integer is_cert;
	
	/**
	 * 是否高值
	 */
	private Integer is_highvalue;
	
	/**
	 * 是否专购
	 */
	private Integer is_com;
	
	/**
	 * 是否收费
	 */
	private Integer is_charge;
	
	/**
	 * 是否灭菌材料
	 */
	private Integer is_disinfect;
	
	/**
	 * 是否个体码
	 */
	private Integer is_per_bar;
	
	/**
	 * 是否唯一供应商
	 */
	private Integer is_single_ven;
	
	/**
	 * 启用日期
	 */
	private Date sdate;
	
	/**
	 * 停用日期
	 */
	private Date edate;
	
	/**
	 * 单位重量
	 */
	private Double per_weight;
	
	/**
	 * 单位体积
	 */
	private Double per_volum;
	
	/**
	 * 品牌
	 */
	private String brand_name;
	
	
	/**
	 * ABC分类
	 */
	private String abc_type;
	/**
	 * 代理商
	 */
	private String agent_name;
	
	/**
	 * 材料结构
	 */
	private String inv_structure;
	
	/**
	 * 材料用途
	 */
	private String inv_usage;
	
	/**
	 * 使用状态
	 */
	private Integer use_state;
	
	/**
	 * 修改备注
	 */
	private String note;
	
	/**
	 * 是否中标
	 */
	private Integer is_bid;
	
	/**
	 * 中标日期
	 */
	private Date bid_date;
	
	/**
	 * 项目中标编码
	 */
	private String bid_code;
	
	/**
	 * 是否介入
	 */
	private Integer is_involved;
	
	/**
	 * 是否植入
	 */
	private Integer is_implant;
	
	/**
	 * 储运条件
	 */
	private String stora_tran_cond;
	
	/**
	 * 是否零库存管理
	 */
	private Integer is_zero_store;
	
	/**
	 * 管理类别
	 */
	private String manage_type;

	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Integer is_inv_bar;
	
	/**
	 * 药品剂型ID
	 */
	private Integer med_jx_id;
	

	/**
	 * 药品属性ID
	 */
	private Integer med_sx_id;
	
	/**
	 * 病区拆零系数
	 */
	private Integer bqplxs;
	
	/**
	 * 门诊拆零系数
	 */
	private Integer mzplxs;
	
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
	* 设置 药品材料ID
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 药品材料ID
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 药品材料编码
	* @param value 
	*/
	public void setInv_code(String value) {
		this.inv_code = value;
	}
	
	/**
	* 获取 药品材料编码
	* @return String
	*/
	public String getInv_code() {
		return this.inv_code;
	}
	/**
	* 设置 药品材料名称
	* @param value 
	*/
	public void setInv_name(String value) {
		this.inv_name = value;
	}
	
	/**
	* 获取 药品材料名称
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
	* 设置 药品类别ID
	* @param value 
	*/
	public void setMed_type_id(Long value) {
		this.med_type_id = value;
	}
	
	/**
	* 获取 药品类别ID
	* @return Long
	*/
	public Long getMed_type_id() {
		return this.med_type_id;
	}
	
	/**
	* 设置 药品类别名称
	* @param value 
	*/
	public void setMed_type_name(String value) {
		this.med_type_name = value;
	}
	
	/**
	 * 药品类别编码
	 */
	
	public String getMed_type_code() {
		return med_type_code;
	}

	public void setMed_type_code(String med_type_code) {
		this.med_type_code = med_type_code;
	}

	/**
	* 获取 药品类别名称
	* @return String
	*/
	public String getMed_type_name() {
		return this.med_type_name;
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
	* 设置 计量单位名称
	* @param value 
	*/
	public void setUnit_name(String value) {
		this.unit_name = value;
	}
	
	/**
	* 获取 计量单位名称
	* @return String
	*/
	public String getUnit_name() {
		return this.unit_name;
	}
	/**
	* 设置 摊销方式
	* @param value 
	*/
	public void setAmortize_type(Integer value) {
		this.amortize_type = value;
	}
	
	/**
	* 获取 摊销方式
	* @return Integer
	*/
	public Integer getAmortize_type() {
		return this.amortize_type;
	}
	/**
	* 设置 计价方法
	* @param value 
	*/
	public void setPrice_type(Integer value) {
		this.price_type = value;
	}
	
	/**
	* 获取 计价方法
	* @return Integer
	*/
	public Integer getPrice_type() {
		return this.price_type;
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
	* 设置 生产厂商编码
	* @param value 
	*/
	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	/**
	* 设置 生产厂商名称
	* @param value 
	*/
	public void setFac_name(String value) {
		this.fac_name = value;
	}
	
	/**
	* 获取 生产厂商名称
	* @return String
	*/
	public String getFac_name() {
		return this.fac_name;
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
	* 设置 是否批次管理
	* @param value 
	*/
	public void setIs_batch(Integer value) {
		this.is_batch = value;
	}
	
	/**
	* 获取 是否批次管理
	* @return Integer
	*/
	public Integer getIs_batch() {
		return this.is_batch;
	}
	/**
	* 设置 是否保质期管理
	* @param value 
	*/
	public void setIs_quality(Integer value) {
		this.is_quality = value;
	}
	
	/**
	* 获取 是否保质期管理
	* @return Integer
	*/
	public Integer getIs_quality() {
		return this.is_quality;
	}
	/**
	* 设置 是否为耐用品
	* @param value 
	*/
	public void setIs_dura(Integer value) {
		this.is_dura = value;
	}
	
	/**
	* 获取 是否为耐用品
	* @return Integer
	*/
	public Integer getIs_dura() {
		return this.is_dura;
	}
	/**
	* 设置 是否作二级库管理
	* @param value 
	*/
	public void setIs_sec_whg(Integer value) {
		this.is_sec_whg = value;
	}
	
	/**
	* 获取 是否作二级库管理
	* @return Integer
	*/
	public Integer getIs_sec_whg() {
		return this.is_sec_whg;
	}
	/**
	* 设置 是否自制品
	* @param value 
	*/
	public void setIs_shel_make(Integer value) {
		this.is_shel_make = value;
	}
	
	/**
	* 获取 是否自制品
	* @return Integer
	*/
	public Integer getIs_shel_make() {
		return this.is_shel_make;
	}
	/**
	* 设置 是否为加价销售
	* @param value 
	*/
	public void setIs_add_sale(Integer value) {
		this.is_add_sale = value;
	}
	
	/**
	* 获取 是否为加价销售
	* @return Integer
	*/
	public Integer getIs_add_sale() {
		return this.is_add_sale;
	}
	/**
	* 设置 是否条码管理
	* @param value 
	*/
	public void setIs_bar(Integer value) {
		this.is_bar = value;
	}
	
	/**
	* 获取 是否条码管理
	* @return Integer
	*/
	public Integer getIs_bar() {
		return this.is_bar;
	}
	/**
	* 设置 品种条码
	* @param value 
	*/
	public void setBar_code_new(String value) {
		this.bar_code_new = value;
	}
	
	/**
	* 获取 品种条码
	* @return String
	*/
	public String getBar_code_new() {
		return this.bar_code_new;
	}
	/**
	* 设置 是否证件管理
	* @param value 
	*/
	public void setIs_cert(Integer value) {
		this.is_cert = value;
	}
	
	/**
	* 获取 是否证件管理
	* @return Integer
	*/
	public Integer getIs_cert() {
		return this.is_cert;
	}
	/**
	* 设置 是否高值
	* @param value 
	*/
	public void setIs_highvalue(Integer value) {
		this.is_highvalue = value;
	}
	
	/**
	* 获取 是否高值
	* @return Integer
	*/
	public Integer getIs_highvalue() {
		return this.is_highvalue;
	}
	/**
	* 设置 是否专购
	* @param value 
	*/
	public void setIs_com(Integer value) {
		this.is_com = value;
	}
	
	/**
	* 获取 是否专购
	* @return Integer
	*/
	public Integer getIs_com() {
		return this.is_com;
	}
	/**
	* 设置 是否收费
	* @param value 
	*/
	public void setIs_charge(Integer value) {
		this.is_charge = value;
	}
	/**
	* 获取 是否收费
	* @return Integer
	*/
	public Integer getIs_charge() {
		return this.is_charge;
	}
	
	
	public String getAbc_type() {
		return abc_type;
	}

	public void setAbc_type(String abc_type) {
		this.abc_type = abc_type;
	}

	/**
	* 设置 是否灭菌材料
	* @param value 
	*/
	public void setIs_disinfect(Integer value) {
		this.is_disinfect = value;
	}
	/**
	* 获取 是否灭菌材料
	* @return Integer
	*/
	public Integer getIs_disinfect() {
		return this.is_disinfect;
	}
	/**
	* 设置 是否个体码
	* @param value 
	*/
	public void setIs_per_bar(Integer value) {
		this.is_per_bar = value;
	}
	/**
	* 获取 是否个体码
	* @return Integer
	*/
	public Integer getIs_per_bar() {
		return this.is_per_bar;
	}
	/**
	* 设置 是否唯一供应商
	* @param value 
	*/
	public void setIs_single_ven(Integer value) {
		this.is_single_ven = value;
	}
	/**
	* 获取 是否唯一供应商
	* @return Integer
	*/
	public Integer getIs_single_ven() {
		return this.is_single_ven;
	}
	/**
	* 设置 启用日期
	* @param value 
	*/
	public void setSdate(Date value) {
		this.sdate = value;
	}
	
	/**
	* 获取 启用日期
	* @return Date
	*/
	public Date getSdate() {
		return this.sdate;
	}
	/**
	* 设置 停用日期
	* @param value 
	*/
	public void setEdate(Date value) {
		this.edate = value;
	}
	
	/**
	* 获取 停用日期
	* @return Date
	*/
	public Date getEdate() {
		return this.edate;
	}
	/**
	* 设置 单位重量
	* @param value 
	*/
	public void setPer_weight(Double value) {
		this.per_weight = value;
	}
	
	/**
	* 获取 单位重量
	* @return Double
	*/
	public Double getPer_weight() {
		return this.per_weight;
	}
	/**
	* 设置 单位体积
	* @param value 
	*/
	public void setPer_volum(Double value) {
		this.per_volum = value;
	}
	
	/**
	* 获取 单位体积
	* @return Double
	*/
	public Double getPer_volum() {
		return this.per_volum;
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
	* 设置 材料结构
	* @param value 
	*/
	public void setInv_structure(String value) {
		this.inv_structure = value;
	}
	
	/**
	* 获取 材料结构
	* @return String
	*/
	public String getInv_structure() {
		return this.inv_structure;
	}
	/**
	* 设置 材料用途
	* @param value 
	*/
	public void setInv_usage(String value) {
		this.inv_usage = value;
	}
	
	/**
	* 获取 材料用途
	* @return String
	*/
	public String getInv_usage() {
		return this.inv_usage;
	}
	/**
	* 设置 使用状态
	* @param value 
	*/
	public void setUse_state(Integer value) {
		this.use_state = value;
	}
	
	/**
	* 获取 使用状态
	* @return Integer
	*/
	public Integer getUse_state() {
		return this.use_state;
	}
	/**
	* 设置 修改备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 修改备注
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

	public Integer getIs_bid() {
		return is_bid;
	}

	public void setIs_bid(Integer is_bid) {
		this.is_bid = is_bid;
	}

	public Date getBid_date() {
		return bid_date;
	}

	public void setBid_date(Date bid_date) {
		this.bid_date = bid_date;
	}

	public String getBid_code() {
		return bid_code;
	}

	public void setBid_code(String bid_code) {
		this.bid_code = bid_code;
	}

	public Integer getIs_involved() {
		return is_involved;
	}

	public void setIs_involved(Integer is_involved) {
		this.is_involved = is_involved;
	}

	public Integer getIs_implant() {
		return is_implant;
	}

	public void setIs_implant(Integer is_implant) {
		this.is_implant = is_implant;
	}

	public String getStora_tran_cond() {
		return stora_tran_cond;
	}

	public void setStora_tran_cond(String stora_tran_cond) {
		this.stora_tran_cond = stora_tran_cond;
	}

	public Integer getIs_zero_store() {
		return is_zero_store;
	}

	public void setIs_zero_store(Integer is_zero_store) {
		this.is_zero_store = is_zero_store;
	}
	
	public String getManage_type() {
		return manage_type;
	}
	
	public void setManage_type(String manage_type) {
		this.manage_type = manage_type;
	}

	/**
	 * @return the is_inv_bar
	 */
	public Integer getIs_inv_bar() {
		return is_inv_bar;
	}

	/**
	 * @param is_inv_bar the is_inv_bar to set
	 */
	public void setIs_inv_bar(Integer is_inv_bar) {
		this.is_inv_bar = is_inv_bar;
	}
	
	/**
	 * @return the med_jx_id
	 */
	public Integer getMed_jx_id() {
		return med_jx_id;
	}

	/**
	 * @param med_jx_id the med_jx_id to set
	 */
	public void setMed_jx_id(Integer med_jx_id) {
		this.med_jx_id = med_jx_id;
	}

	/**
	 * @return the med_sx_id
	 */
	public Integer getMed_sx_id() {
		return med_sx_id;
	}

	/**
	 * @param med_sx_id the med_sx_id to set
	 */
	public void setMed_sx_id(Integer med_sx_id) {
		this.med_sx_id = med_sx_id;
	}

	/**
	 * @return the bqplxs
	 */
	public Integer getBqplxs() {
		return bqplxs;
	}

	/**
	 * @param bqplxs the bqplxs to set
	 */
	public void setBqplxs(Integer bqplxs) {
		this.bqplxs = bqplxs;
	}

	/**
	 * @return the mzplxs
	 */
	public Integer getMzplxs() {
		return mzplxs;
	}

	/**
	 * @param mzplxs the mzplxs to set
	 */
	public void setMzplxs(Integer mzplxs) {
		this.mzplxs = mzplxs;
	}
	
	
}