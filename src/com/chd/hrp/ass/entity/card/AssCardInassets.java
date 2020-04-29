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
 * @Description:
 * 资产卡片维护_无形资产
 * @Table:
 * ASS_CARD_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
  
 
public class AssCardInassets implements Serializable {      

	
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
	 * 原始卡片号
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
	
	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_mondl;
	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 数量
	 */
	private Long ass_amount;
	
	/**
	 * 计量单位
	 */
	private String unit_code;
	
	/**
	 * 生产厂商ID
	 */
	private Long fac_id;
	
	/**
	 * 生产厂商NO
	 */
	private Long fac_no;
	
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long ven_no;
	
	/**
	 * 在用状态：1：在用 0：在库
	 */
	private Integer is_dept;
	
	/**
	 * 管理科室ID
	 */
	private Long dept_id;
	
	/**
	 * 管理科室变更ID
	 */
	private Long dept_no;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 仓库变更ID
	 */
	private Long store_no;
	
	/**
	 * 资产原值
	 */
	private Double price;
	
	/**
	 * 累计摊销
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
	 * 使用状态
	 */
	private Long use_state;
	
	/**
	 * 是否摊销
	 */
	private Integer is_depr;
	
	/**
	 * 摊销方法
	 */
	private String depr_method;
	
	/**
	 * 计提年限
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
	 * 是否投放
	 */
	private Integer is_throw;
	
	/**
	 * 合同ID
	 */
	private String pact_code;
	
	/**
	 * 安装费用
	 */
	private Double ins_money;
	
	/**
	 * 安装日期
	 */
	private Date ins_date;
	
	/**
	 * 验收人
	 */
	private Long accept_emp;
	
	/**
	 * 验收日期
	 */
	private Date accept_date;
	
	/**
	 * 服务截止日期
	 */
	private Date service_date;
	
	/**
	 * 存放位置
	 */
	private String location;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 入库ID
	 */
	private String ass_in_no;
	
	/**
	 * 入库日期
	 */
	private Date in_date;
	
	/**
	 * 投入使用日期
	 */
	private Date run_date;
	
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
	
	private String unit_code_name;
	
	private String fac_id_code;
	
	private String fac_id_name;
	
	private String ven_id_code;
	
	private String ven_id_name;
	
	private String is_dept_name;
	
	private String dept_id_code;
	
	private String dept_id_name;
	
	private String store_id_code;
	
	private String store_id_name;
	
	private String buy_type_name;
	
	private String use_state_name;
	
	private String is_depr_name;
	
	private String depr_method_name;
	
	private String is_manage_depre_name;
	
	private String manage_depr_method_name;
	
	private String is_throw_name;
	
	
	private String is_init_name;
	
	private String dispose_type_name;
	
	private String accept_emp_name;
	
	private Integer proj_id;
	
	private Integer proj_no;
	
	private String proj_id_code;
	
	private String proj_id_name;
	
	private String ass_purpose;
	
	private String ass_purpose_name;
	
	private Date dispose_date;
	
	private Date apply_date;
	
	private Integer add_depre_month;
	
	private Integer add_manage_month;
	
	private String reg_no;
	
	private String gb_code_name;
	
	private String simple_name;
	
	private String reg_no_name;
	
	private Integer proc_store_id;
	
	private Integer proc_store_no;
	
	private String proc_store_id_code;
	
	private String proc_store_id_name;
	
	private String common_name;
	
	private String six_type_code;
	
	private String six_type_code_name;
	
	private String contract_code;
	
	
	
	public String getContract_code() {
		return contract_code;
	}

	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	

	public String getSix_type_code() {
		return six_type_code;
	}

	public void setSix_type_code(String six_type_code) {
		this.six_type_code = six_type_code;
	}


	public String getSix_type_code_name() {
		return six_type_code_name;
	}

	public void setSix_type_code_name(String six_type_code_name) {
		this.six_type_code_name = six_type_code_name;
	}
	

	public String getCommon_name() {
		return common_name;
	}

	public void setCommon_name(String common_name) {
		this.common_name = common_name;
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


	public String getReg_no_name() {
		return reg_no_name;
	}

	public void setReg_no_name(String reg_no_name) {
		this.reg_no_name = reg_no_name;
	}
	

	public final String getSimple_name() {
		return simple_name;
	}

	public final void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}

	public String getGb_code_name() {
		return gb_code_name;
	}

	public void setGb_code_name(String gb_code_name) {
		this.gb_code_name = gb_code_name;
	}
	

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	

	public Date getDispose_date() {
		return dispose_date;
	}

	public void setDispose_date(Date dispose_date) {
		this.dispose_date = dispose_date;
	}

	public Date getApply_date() {
		return apply_date;
	}

	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
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

	public void setAss_purpose_name(String ass_purpose_name) {
		this.ass_purpose_name = ass_purpose_name;
	}

	public Integer getProj_id() {
		return proj_id;
	}

	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}

	public Integer getProj_no() {
		return proj_no;
	}

	public void setProj_no(Integer proj_no) {
		this.proj_no = proj_no;
	}

	public String getProj_id_code() {
		return proj_id_code;
	}

	public void setProj_id_code(String proj_id_code) {
		this.proj_id_code = proj_id_code;
	}

	public String getProj_id_name() {
		return proj_id_name;
	}

	public void setProj_id_name(String proj_id_name) {
		this.proj_id_name = proj_id_name;
	}

	public String getAss_purpose() {
		return ass_purpose;
	}

	public void setAss_purpose(String ass_purpose) {
		this.ass_purpose = ass_purpose;
	}

	public String getAss_purpose_name() {
		return ass_purpose_name;
	}
	
	
	public String getAccept_emp_name() {
		return accept_emp_name;
	}

	public void setAccept_emp_name(String accept_emp_name) {
		this.accept_emp_name = accept_emp_name;
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

	public String getUnit_code_name() {
		return unit_code_name;
	}

	public void setUnit_code_name(String unit_code_name) {
		this.unit_code_name = unit_code_name;
	}

	public String getFac_id_code() {
		return fac_id_code;
	}

	public void setFac_id_code(String fac_id_code) {
		this.fac_id_code = fac_id_code;
	}

	public String getFac_id_name() {
		return fac_id_name;
	}

	public void setFac_id_name(String fac_id_name) {
		this.fac_id_name = fac_id_name;
	}

	public String getVen_id_code() {
		return ven_id_code;
	}

	public void setVen_id_code(String ven_id_code) {
		this.ven_id_code = ven_id_code;
	}

	public String getVen_id_name() {
		return ven_id_name;
	}

	public void setVen_id_name(String ven_id_name) {
		this.ven_id_name = ven_id_name;
	}

	public String getIs_dept_name() {
		return is_dept_name;
	}

	public void setIs_dept_name(String is_dept_name) {
		this.is_dept_name = is_dept_name;
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

	public String getStore_id_code() {
		return store_id_code;
	}

	public void setStore_id_code(String store_id_code) {
		this.store_id_code = store_id_code;
	}

	public String getStore_id_name() {
		return store_id_name;
	}

	public void setStore_id_name(String store_id_name) {
		this.store_id_name = store_id_name;
	}

	public String getBuy_type_name() {
		return buy_type_name;
	}

	public void setBuy_type_name(String buy_type_name) {
		this.buy_type_name = buy_type_name;
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

	public String getIs_throw_name() {
		return is_throw_name;
	}

	public void setIs_throw_name(String is_throw_name) {
		this.is_throw_name = is_throw_name;
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
	* 设置 卡片编号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 原始卡片号
	* @param value 
	*/
	public void setAss_ori_card_no(String value) {
		this.ass_ori_card_no = value;
	}
	
	/**
	* 获取 原始卡片号
	* @return String
	*/
	public String getAss_ori_card_no() {
		return this.ass_ori_card_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(Long value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public Long getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产变更ID
	* @param value 
	*/
	public void setAss_no(Long value) {
		this.ass_no = value;
	}
	
	/**
	* 获取 资产变更ID
	* @return Long
	*/
	public Long getAss_no() {
		return this.ass_no;
	}
	/**
	* 设置 国标码
	* @param value 
	*/
	public void setGb_code(String value) {
		this.gb_code = value;
	}
	
	/**
	* 获取 国标码
	* @return String
	*/
	public String getGb_code() {
		return this.gb_code;
	}
	/**
	* 设置 品牌
	* @param value 
	*/
	public void setAss_brand(String value) {
		this.ass_brand = value;
	}
	
	/**
	* 获取 品牌
	* @return String
	*/
	public String getAss_brand() {
		return this.ass_brand;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setAss_amount(Long value) {
		this.ass_amount = value;
	}
	
	/**
	* 获取 数量
	* @return Long
	*/
	public Long getAss_amount() {
		return this.ass_amount;
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
	* 设置 生产厂商NO
	* @param value 
	*/
	public void setFac_no(Long value) {
		this.fac_no = value;
	}
	
	/**
	* 获取 生产厂商NO
	* @return Long
	*/
	public Long getFac_no() {
		return this.fac_no;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(Long value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setVen_no(Long value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getVen_no() {
		return this.ven_no;
	}
	/**
	* 设置 在用状态：1：在用 0：在库
	* @param value 
	*/
	public void setIs_dept(Integer value) {
		this.is_dept = value;
	}
	
	/**
	* 获取 在用状态：1：在用 0：在库
	* @return Integer
	*/
	public Integer getIs_dept() {
		return this.is_dept;
	}
	/**
	* 设置 管理科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 管理科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 管理科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 管理科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 仓库变更ID
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 仓库变更ID
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 资产原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 资产原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 累计摊销
	* @param value 
	*/
	public void setDepre_money(Double value) {
		this.depre_money = value;
	}
	
	/**
	* 获取 累计摊销
	* @return Double
	*/
	public Double getDepre_money() {
		return this.depre_money;
	}
	/**
	* 设置 累计分摊
	* @param value 
	*/
	public void setManage_depre_money(Double value) {
		this.manage_depre_money = value;
	}
	
	/**
	* 获取 累计分摊
	* @return Double
	*/
	public Double getManage_depre_money() {
		return this.manage_depre_money;
	}
	/**
	* 设置 资产净值
	* @param value 
	*/
	public void setCur_money(Double value) {
		this.cur_money = value;
	}
	
	/**
	* 获取 资产净值
	* @return Double
	*/
	public Double getCur_money() {
		return this.cur_money;
	}
	/**
	* 设置 预留残值
	* @param value 
	*/
	public void setFore_money(Double value) {
		this.fore_money = value;
	}
	
	/**
	* 获取 预留残值
	* @return Double
	*/
	public Double getFore_money() {
		return this.fore_money;
	}
	/**
	* 设置 业务类型
	* @param value 
	*/
	public void setBuy_type(String value) {
		this.buy_type = value;
	}
	
	/**
	* 获取 业务类型
	* @return String
	*/
	public String getBuy_type() {
		return this.buy_type;
	}
	/**
	* 设置 使用状态
	* @param value 
	*/
	public void setUse_state(Long value) {
		this.use_state = value;
	}
	
	/**
	* 获取 使用状态
	* @return Long
	*/
	public Long getUse_state() {
		return this.use_state;
	}
	/**
	* 设置 是否摊销
	* @param value 
	*/
	public void setIs_depr(Integer value) {
		this.is_depr = value;
	}
	
	/**
	* 获取 是否摊销
	* @return Integer
	*/
	public Integer getIs_depr() {
		return this.is_depr;
	}
	/**
	* 设置 摊销方法
	* @param value 
	*/
	public void setDepr_method(String value) {
		this.depr_method = value;
	}
	
	/**
	* 获取 摊销方法
	* @return String
	*/
	public String getDepr_method() {
		return this.depr_method;
	}
	/**
	* 设置 计提年限
	* @param value 
	*/
	public void setAcc_depre_amount(Integer value) {
		this.acc_depre_amount = value;
	}
	
	/**
	* 获取 计提年限
	* @return Integer
	*/
	public Integer getAcc_depre_amount() {
		return this.acc_depre_amount;
	}
	/**
	* 设置 是否分摊
	* @param value 
	*/
	public void setIs_manage_depre(Integer value) {
		this.is_manage_depre = value;
	}
	
	/**
	* 获取 是否分摊
	* @return Integer
	*/
	public Integer getIs_manage_depre() {
		return this.is_manage_depre;
	}
	/**
	* 设置 分摊方法
	* @param value 
	*/
	public void setManage_depr_method(String value) {
		this.manage_depr_method = value;
	}
	
	/**
	* 获取 分摊方法
	* @return String
	*/
	public String getManage_depr_method() {
		return this.manage_depr_method;
	}
	/**
	* 设置 分摊年限
	* @param value 
	*/
	public void setManage_depre_amount(Integer value) {
		this.manage_depre_amount = value;
	}
	
	/**
	* 获取 分摊年限
	* @return Integer
	*/
	public Integer getManage_depre_amount() {
		return this.manage_depre_amount;
	}
	/**
	* 设置 是否投放
	* @param value 
	*/
	public void setIs_throw(Integer value) {
		this.is_throw = value;
	}
	
	/**
	* 获取 是否投放
	* @return Integer
	*/
	public Integer getIs_throw() {
		return this.is_throw;
	}

	
	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	
	/**
	* 设置 安装费用
	* @param value 
	*/
	public void setIns_money(Double value) {
		this.ins_money = value;
	}
	
	/**
	* 获取 安装费用
	* @return Double
	*/
	public Double getIns_money() {
		return this.ins_money;
	}
	/**
	* 设置 安装日期
	* @param value 
	*/
	public void setIns_date(Date value) {
		this.ins_date = value;
	}
	
	/**
	* 获取 安装日期
	* @return Date
	*/
	public Date getIns_date() {
		return this.ins_date;
	}
	/**
	* 设置 验收人
	* @param value 
	*/
	public void setAccept_emp(Long value) {
		this.accept_emp = value;
	}
	
	/**
	* 获取 验收人
	* @return Long
	*/
	public Long getAccept_emp() {
		return this.accept_emp;
	}
	/**
	* 设置 验收日期
	* @param value 
	*/
	public void setAccept_date(Date value) {
		this.accept_date = value;
	}
	
	/**
	* 获取 验收日期
	* @return Date
	*/
	public Date getAccept_date() {
		return this.accept_date;
	}
	/**
	* 设置 服务截止日期
	* @param value 
	*/
	public void setService_date(Date value) {
		this.service_date = value;
	}
	
	/**
	* 获取 服务截止日期
	* @return Date
	*/
	public Date getService_date() {
		return this.service_date;
	}
	/**
	* 设置 存放位置
	* @param value 
	*/
	public void setLocation(String value) {
		this.location = value;
	}
	
	/**
	* 获取 存放位置
	* @return String
	*/
	public String getLocation() {
		return this.location;
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
	* 设置 入库ID
	* @param value 
	*/
	public void setAss_in_no(String value) {
		this.ass_in_no = value;
	}
	
	/**
	* 获取 入库ID
	* @return Long
	*/
	public String getAss_in_no() {
		return this.ass_in_no;
	}
	/**
	* 设置 入库日期
	* @param value 
	*/
	public void setIn_date(Date value) {
		this.in_date = value;
	}
	
	/**
	* 获取 入库日期
	* @return Date
	*/
	public Date getIn_date() {
		return this.in_date;
	}
	/**
	* 设置 投入使用日期
	* @param value 
	*/
	public void setRun_date(Date value) {
		this.run_date = value;
	}
	
	/**
	* 获取 投入使用日期
	* @return Date
	*/
	public Date getRun_date() {
		return this.run_date;
	}
	/**
	* 设置 是否期初
	* @param value 
	*/
	public void setIs_init(Integer value) {
		this.is_init = value;
	}
	
	/**
	* 获取 是否期初
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