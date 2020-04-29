/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.entity.card;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @Description: 资产卡片维护_房屋及建筑物
 * @Table: ASS_CARD_HOUSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
public class AssCardInitHouse implements Serializable {     

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
	 * 卡片编号
	 */
	private String ass_card_no;

	/**
	 * 原始卡片编号
	 */
	private String ass_ori_card_no;

	/**
	 * 资产ID
	 */
	private Long ass_id;

	/**
	 * 资产变更ID
	 */
	private Long ass_no;

	/**
	 * 国标码
	 */
	private String gb_code;
	
	/**
	 * 国标名称
	 */
	private String gb_code_name;
	
	public String getGb_code_name() {
		return gb_code_name;
	}

	public void setGb_code_name(String gb_code_name) {
		this.gb_code_name = gb_code_name;
	}

	/**
	 * 数量
	 */
	private Long ass_amount;

	/**
	 * 资产原值
	 */
	private Double price;

	/**
	 * 累计折旧
	 */
	private Double depre_money;

	/**
	 * 累计分摊
	 */
	private Double manage_depre_money;

	/**
	 * 资产净值
	 */
	private Double cur_money;

	/**
	 * 预留残值
	 */
	private Double fore_money;

	/**
	 * 业务类型
	 */
	private String buy_type;

	/**
	 * 计量单位
	 */
	private String unit_code;

	/**
	 * 使用状态
	 */
	private Long use_state;

	/**
	 * 是否折旧
	 */
	private Integer is_depr;

	/**
	 * 折旧方法
	 */
	private String depr_method;

	/**
	 * 折旧年限
	 */
	private Integer acc_depre_amount;

	/**
	 * 是否分摊
	 */
	private Integer is_manage_depre;

	/**
	 * 分摊方法
	 */
	private String manage_depr_method;

	/**
	 * 分摊年限
	 */
	private Integer manage_depre_amount;

	/**
	 * 开发商ID
	 */
	private Long ven_id;

	/**
	 * 开发商变更ID
	 */
	private Long ven_no;

	/**
	 * 合同ID
	 */
	private String pact_code;

	/**
	 * 建筑结构
	 */
	private String struct_code;

	/**
	 * 建筑面积
	 */
	private Double house_area;

	/**
	 * 使用面积
	 */
	private Double use_area;

	/**
	 * 地下面积
	 */
	private Double base_area;

	/**
	 * 竣工日期
	 */
	private Date accept_date;

	/**
	 * 权属证明
	 */
	private String cert_name;

	/**
	 * 权属证号
	 */
	private String cert_code;

	/**
	 * 发证日期
	 */
	private Date cert_date;

	/**
	 * 产权形式
	 */
	private String prop_code;

	/**
	 * 投入使用日期
	 */
	private Date run_date;

	/**
	 * 坐落位置
	 */
	private String location;

	/**
	 * 管理部门ID
	 */
	private Long dept_id;

	/**
	 * 管理部门变更ID
	 */
	private Long dept_no;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 入库ID
	 */
	private String ass_in_no;

	/**
	 * 入账日期
	 */
	private Date in_date;

	/**
	 * 是否期初
	 */
	private Integer is_init;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	private Integer dispose_type;

	private Double dispose_cost;

	private Double dispose_income;

	private Double dispose_tax;

	private String ass_id_code;
	
	private String ass_id_name;
	
	private String buy_type_name;
	
	private String unit_code_name;
	
	private String use_state_name;
	
	private String is_depr_name;
	
	private String depr_method_name;
	
	private String is_manage_depre_name;
	
	private String manage_depr_method_name;
	
	private String ven_id_name;
	
	private String ven_id_code;
	
	
	private String struct_code_name;
	
	private String prop_code_name;
	
	private String dept_id_code;
	
	private String dept_id_name;
	
	
	private String is_init_name;
	
	private String dispose_type_name;
	
	private Integer add_depre_month;
	
	private Integer add_manage_month;
	
	private Integer proc_store_id;
	
	private Integer proc_store_no;
	
	private String proc_store_id_code;
	
	private String proc_store_id_name;
	
	private String contract_code;
	
	
	
	public String getContract_code() {
		return contract_code;
	}

	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	

	public Integer getProc_store_id() {
		return proc_store_id;
	}

	public void setProc_store_id(Integer proc_store_id) {
		this.proc_store_id = proc_store_id;
	}

	public Integer getProc_store_no() {
		return proc_store_no;
	}

	public void setProc_store_no(Integer proc_store_no) {
		this.proc_store_no = proc_store_no;
	}

	public String getProc_store_id_code() {
		return proc_store_id_code;
	}

	public void setProc_store_id_code(String proc_store_id_code) {
		this.proc_store_id_code = proc_store_id_code;
	}

	public String getProc_store_id_name() {
		return proc_store_id_name;
	}

	public void setProc_store_id_name(String proc_store_id_name) {
		this.proc_store_id_name = proc_store_id_name;
	}


	public Integer getAdd_depre_month() {
		return add_depre_month;
	}

	public void setAdd_depre_month(Integer add_depre_month) {
		this.add_depre_month = add_depre_month;
	}

	public Integer getAdd_manage_month() {
		return add_manage_month;
	}

	public void setAdd_manage_month(Integer add_manage_month) {
		this.add_manage_month = add_manage_month;
	}


	public String getAss_id_code() {
		return ass_id_code;
	}

	public void setAss_id_code(String ass_id_code) {
		this.ass_id_code = ass_id_code;
	}

	public String getAss_id_name() {
		return ass_id_name;
	}

	public void setAss_id_name(String ass_id_name) {
		this.ass_id_name = ass_id_name;
	}

	public String getBuy_type_name() {
		return buy_type_name;
	}

	public void setBuy_type_name(String buy_type_name) {
		this.buy_type_name = buy_type_name;
	}

	public String getUnit_code_name() {
		return unit_code_name;
	}

	public void setUnit_code_name(String unit_code_name) {
		this.unit_code_name = unit_code_name;
	}

	public String getUse_state_name() {
		return use_state_name;
	}

	public void setUse_state_name(String use_state_name) {
		this.use_state_name = use_state_name;
	}

	public String getIs_depr_name() {
		return is_depr_name;
	}

	public void setIs_depr_name(String is_depr_name) {
		this.is_depr_name = is_depr_name;
	}

	public String getDepr_method_name() {
		return depr_method_name;
	}

	public void setDepr_method_name(String depr_method_name) {
		this.depr_method_name = depr_method_name;
	}

	public String getIs_manage_depre_name() {
		return is_manage_depre_name;
	}

	public void setIs_manage_depre_name(String is_manage_depre_name) {
		this.is_manage_depre_name = is_manage_depre_name;
	}

	public String getManage_depr_method_name() {
		return manage_depr_method_name;
	}

	public void setManage_depr_method_name(String manage_depr_method_name) {
		this.manage_depr_method_name = manage_depr_method_name;
	}

	public String getVen_id_name() {
		return ven_id_name;
	}

	public void setVen_id_name(String ven_id_name) {
		this.ven_id_name = ven_id_name;
	}

	public String getVen_id_code() {
		return ven_id_code;
	}

	public void setVen_id_code(String ven_id_code) {
		this.ven_id_code = ven_id_code;
	}

	

	public String getStruct_code_name() {
		return struct_code_name;
	}

	public void setStruct_code_name(String struct_code_name) {
		this.struct_code_name = struct_code_name;
	}

	public String getProp_code_name() {
		return prop_code_name;
	}

	public void setProp_code_name(String prop_code_name) {
		this.prop_code_name = prop_code_name;
	}

	public String getDept_id_code() {
		return dept_id_code;
	}

	public void setDept_id_code(String dept_id_code) {
		this.dept_id_code = dept_id_code;
	}

	public String getDept_id_name() {
		return dept_id_name;
	}

	public void setDept_id_name(String dept_id_name) {
		this.dept_id_name = dept_id_name;
	}


	public String getIs_init_name() {
		return is_init_name;
	}

	public void setIs_init_name(String is_init_name) {
		this.is_init_name = is_init_name;
	}

	public String getDispose_type_name() {
		return dispose_type_name;
	}

	public void setDispose_type_name(String dispose_type_name) {
		this.dispose_type_name = dispose_type_name;
	}

	public Integer getDispose_type() {
		return dispose_type;
	}

	public void setDispose_type(Integer dispose_type) {
		this.dispose_type = dispose_type;
	}

	public Double getDispose_cost() {
		return dispose_cost;
	}

	public void setDispose_cost(Double dispose_cost) {
		this.dispose_cost = dispose_cost;
	}

	public Double getDispose_income() {
		return dispose_income;
	}

	public void setDispose_income(Double dispose_income) {
		this.dispose_income = dispose_income;
	}

	public Double getDispose_tax() {
		return dispose_tax;
	}

	public void setDispose_tax(Double dispose_tax) {
		this.dispose_tax = dispose_tax;
	}

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 卡片编号
	 * 
	 * @param value
	 */
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}

	/**
	 * 获取 卡片编号
	 * 
	 * @return String
	 */
	public String getAss_card_no() {
		return this.ass_card_no;
	}

	/**
	 * 设置 原始卡片编号
	 * 
	 * @param value
	 */
	public void setAss_ori_card_no(String value) {
		this.ass_ori_card_no = value;
	}

	/**
	 * 获取 原始卡片编号
	 * 
	 * @return String
	 */
	public String getAss_ori_card_no() {
		return this.ass_ori_card_no;
	}

	/**
	 * 设置 资产ID
	 * 
	 * @param value
	 */
	public void setAss_id(Long value) {
		this.ass_id = value;
	}

	/**
	 * 获取 资产ID
	 * 
	 * @return Long
	 */
	public Long getAss_id() {
		return this.ass_id;
	}

	/**
	 * 设置 资产变更ID
	 * 
	 * @param value
	 */
	public void setAss_no(Long value) {
		this.ass_no = value;
	}

	/**
	 * 获取 资产变更ID
	 * 
	 * @return Long
	 */
	public Long getAss_no() {
		return this.ass_no;
	}

	/**
	 * 设置 国标码
	 * 
	 * @param value
	 */
	public void setGb_code(String value) {
		this.gb_code = value;
	}

	/**
	 * 获取 国标码
	 * 
	 * @return String
	 */
	public String getGb_code() {
		return this.gb_code;
	}

	/**
	 * 设置 数量
	 * 
	 * @param value
	 */
	public void setAss_amount(Long value) {
		this.ass_amount = value;
	}

	/**
	 * 获取 数量
	 * 
	 * @return Long
	 */
	public Long getAss_amount() {
		return this.ass_amount;
	}

	/**
	 * 设置 资产原值
	 * 
	 * @param value
	 */
	public void setPrice(Double value) {
		this.price = value;
	}

	/**
	 * 获取 资产原值
	 * 
	 * @return Double
	 */
	public Double getPrice() {
		return this.price;
	}

	/**
	 * 设置 累计折旧
	 * 
	 * @param value
	 */
	public void setDepre_money(Double value) {
		this.depre_money = value;
	}

	/**
	 * 获取 累计折旧
	 * 
	 * @return Double
	 */
	public Double getDepre_money() {
		return this.depre_money;
	}

	/**
	 * 设置 累计分摊
	 * 
	 * @param value
	 */
	public void setManage_depre_money(Double value) {
		this.manage_depre_money = value;
	}

	/**
	 * 获取 累计分摊
	 * 
	 * @return Double
	 */
	public Double getManage_depre_money() {
		return this.manage_depre_money;
	}

	/**
	 * 设置 资产净值
	 * 
	 * @param value
	 */
	public void setCur_money(Double value) {
		this.cur_money = value;
	}

	/**
	 * 获取 资产净值
	 * 
	 * @return Double
	 */
	public Double getCur_money() {
		return this.cur_money;
	}

	/**
	 * 设置 预留残值
	 * 
	 * @param value
	 */
	public void setFore_money(Double value) {
		this.fore_money = value;
	}

	/**
	 * 获取 预留残值
	 * 
	 * @return Double
	 */
	public Double getFore_money() {
		return this.fore_money;
	}

	/**
	 * 设置 业务类型
	 * 
	 * @param value
	 */
	public void setBuy_type(String value) {
		this.buy_type = value;
	}

	/**
	 * 获取 业务类型
	 * 
	 * @return String
	 */
	public String getBuy_type() {
		return this.buy_type;
	}

	/**
	 * 设置 计量单位
	 * 
	 * @param value
	 */
	public void setUnit_code(String value) {
		this.unit_code = value;
	}

	/**
	 * 获取 计量单位
	 * 
	 * @return String
	 */
	public String getUnit_code() {
		return this.unit_code;
	}

	/**
	 * 设置 使用状态
	 * 
	 * @param value
	 */
	public void setUse_state(Long value) {
		this.use_state = value;
	}

	/**
	 * 获取 使用状态
	 * 
	 * @return Long
	 */
	public Long getUse_state() {
		return this.use_state;
	}

	/**
	 * 设置 是否折旧
	 * 
	 * @param value
	 */
	public void setIs_depr(Integer value) {
		this.is_depr = value;
	}

	/**
	 * 获取 是否折旧
	 * 
	 * @return Integer
	 */
	public Integer getIs_depr() {
		return this.is_depr;
	}

	/**
	 * 设置 折旧方法
	 * 
	 * @param value
	 */
	public void setDepr_method(String value) {
		this.depr_method = value;
	}

	/**
	 * 获取 折旧方法
	 * 
	 * @return String
	 */
	public String getDepr_method() {
		return this.depr_method;
	}

	/**
	 * 设置 折旧年限
	 * 
	 * @param value
	 */
	public void setAcc_depre_amount(Integer value) {
		this.acc_depre_amount = value;
	}

	/**
	 * 获取 折旧年限
	 * 
	 * @return Integer
	 */
	public Integer getAcc_depre_amount() {
		return this.acc_depre_amount;
	}

	/**
	 * 设置 是否分摊
	 * 
	 * @param value
	 */
	public void setIs_manage_depre(Integer value) {
		this.is_manage_depre = value;
	}

	/**
	 * 获取 是否分摊
	 * 
	 * @return Integer
	 */
	public Integer getIs_manage_depre() {
		return this.is_manage_depre;
	}

	/**
	 * 设置 分摊方法
	 * 
	 * @param value
	 */
	public void setManage_depr_method(String value) {
		this.manage_depr_method = value;
	}

	/**
	 * 获取 分摊方法
	 * 
	 * @return String
	 */
	public String getManage_depr_method() {
		return this.manage_depr_method;
	}

	/**
	 * 设置 分摊年限
	 * 
	 * @param value
	 */
	public void setManage_depre_amount(Integer value) {
		this.manage_depre_amount = value;
	}

	/**
	 * 获取 分摊年限
	 * 
	 * @return Integer
	 */
	public Integer getManage_depre_amount() {
		return this.manage_depre_amount;
	}

	/**
	 * 设置 开发商ID
	 * 
	 * @param value
	 */
	public void setVen_id(Long value) {
		this.ven_id = value;
	}

	/**
	 * 获取 开发商ID
	 * 
	 * @return Long
	 */
	public Long getVen_id() {
		return this.ven_id;
	}

	/**
	 * 设置 开发商变更ID
	 * 
	 * @param value
	 */
	public void setVen_no(Long value) {
		this.ven_no = value;
	}

	/**
	 * 获取 开发商变更ID
	 * 
	 * @return Long
	 */
	public Long getVen_no() {
		return this.ven_no;
	}

	

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	/**
	 * 设置 建筑结构
	 * 
	 * @param value
	 */
	public void setStruct_code(String value) {
		this.struct_code = value;
	}

	/**
	 * 获取 建筑结构
	 * 
	 * @return String
	 */
	public String getStruct_code() {
		return this.struct_code;
	}

	/**
	 * 设置 建筑面积
	 * 
	 * @param value
	 */
	public void setHouse_area(Double value) {
		this.house_area = value;
	}

	/**
	 * 获取 建筑面积
	 * 
	 * @return Double
	 */
	public Double getHouse_area() {
		return this.house_area;
	}

	/**
	 * 设置 使用面积
	 * 
	 * @param value
	 */
	public void setUse_area(Double value) {
		this.use_area = value;
	}

	/**
	 * 获取 使用面积
	 * 
	 * @return Double
	 */
	public Double getUse_area() {
		return this.use_area;
	}

	/**
	 * 设置 地下面积
	 * 
	 * @param value
	 */
	public void setBase_area(Double value) {
		this.base_area = value;
	}

	/**
	 * 获取 地下面积
	 * 
	 * @return Double
	 */
	public Double getBase_area() {
		return this.base_area;
	}

	/**
	 * 设置 竣工日期
	 * 
	 * @param value
	 */
	public void setAccept_date(Date value) {
		this.accept_date = value;
	}

	/**
	 * 获取 竣工日期
	 * 
	 * @return Date
	 */
	public Date getAccept_date() {
		return this.accept_date;
	}

	/**
	 * 设置 权属证明
	 * 
	 * @param value
	 */
	public void setCert_name(String value) {
		this.cert_name = value;
	}

	/**
	 * 获取 权属证明
	 * 
	 * @return String
	 */
	public String getCert_name() {
		return this.cert_name;
	}

	/**
	 * 设置 权属证号
	 * 
	 * @param value
	 */
	public void setCert_code(String value) {
		this.cert_code = value;
	}

	/**
	 * 获取 权属证号
	 * 
	 * @return String
	 */
	public String getCert_code() {
		return this.cert_code;
	}

	/**
	 * 设置 发证日期
	 * 
	 * @param value
	 */
	public void setCert_date(Date value) {
		this.cert_date = value;
	}

	/**
	 * 获取 发证日期
	 * 
	 * @return Date
	 */
	public Date getCert_date() {
		return this.cert_date;
	}

	/**
	 * 设置 产权形式
	 * 
	 * @param value
	 */
	public void setProp_code(String value) {
		this.prop_code = value;
	}

	/**
	 * 获取 产权形式
	 * 
	 * @return String
	 */
	public String getProp_code() {
		return this.prop_code;
	}

	/**
	 * 设置 投入使用日期
	 * 
	 * @param value
	 */
	public void setRun_date(Date value) {
		this.run_date = value;
	}

	/**
	 * 获取 投入使用日期
	 * 
	 * @return Date
	 */
	public Date getRun_date() {
		return this.run_date;
	}

	/**
	 * 设置 坐落位置
	 * 
	 * @param value
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * 获取 坐落位置
	 * 
	 * @return String
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * 设置 管理部门ID
	 * 
	 * @param value
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}

	/**
	 * 获取 管理部门ID
	 * 
	 * @return Long
	 */
	public Long getDept_id() {
		return this.dept_id;
	}

	/**
	 * 设置 管理部门变更ID
	 * 
	 * @param value
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}

	/**
	 * 获取 管理部门变更ID
	 * 
	 * @return Long
	 */
	public Long getDept_no() {
		return this.dept_no;
	}

	/**
	 * 设置 备注
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取 备注
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * 设置 入库ID
	 * 
	 * @param value
	 */
	public void setAss_in_no(String value) {
		this.ass_in_no = value;
	}

	/**
	 * 获取 入库ID
	 * 
	 * @return Long
	 */
	public String getAss_in_no() {
		return this.ass_in_no;
	}

	/**
	 * 设置 入账日期
	 * 
	 * @param value
	 */
	public void setIn_date(Date value) {
		this.in_date = value;
	}

	/**
	 * 获取 入账日期
	 * 
	 * @return Date
	 */
	public Date getIn_date() {
		return this.in_date;
	}

	/**
	 * 设置 是否期初
	 * 
	 * @param value
	 */
	public void setIs_init(Integer value) {
		this.is_init = value;
	}

	/**
	 * 获取 是否期初
	 * 
	 * @return Integer
	 */
	public Integer getIs_init() {
		return this.is_init;
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