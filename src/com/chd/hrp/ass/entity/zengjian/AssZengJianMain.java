package com.chd.hrp.ass.entity.zengjian;

import java.io.Serializable;
import java.util.*;
public class AssZengJianMain implements Serializable {

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
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产NO
	 */
	private String ass_no;
	/**
	 * 数量
	 */
	private Integer ass_amount;
	
	/**
	 * 卡片原值
	 */
	private Double price;
	
	/**
	 * 期初金额
	 */
	private Double begin_money;
	/**
	 * 累计折旧
	 * @return
	 */
    private Double depre_money;
    
    /**
     * 卡片净值
     * @return
     */
    private Double cur_money;
    /**
     * 卡片残值
     * @return
     */
    private Double fore_money;
    /**
     * 业务类型
     * @return
     */
    private String buy_type;
    /**
     * 卡片位置
     * @return
     */
    private String at_loca;
    /**
	 * 使用科室ID
	 */
	private Long user_dept_id;
	
	/**
	 * 使用科室NO
	 */
	private Long user_dept_no;
	
	/**
	 * 使用科室ID
	 */
    private Long store_id;
		
	/**
	 * 使用科室NO
	 */
	private Long store_no;
	
	/**
	 * 计量单位
	 * @return
	 */
    private String unit_code;
    /**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 规格
	 */
	private String ass_spec;
    /**
     * 使用状态
     * @return
     */
    private Integer use_state;
    /**
     * 是否折旧
     * @return
     */
    private Integer is_depr;
    
    /**
     * 折旧方法
     * @return
     */
    private String depr_method;
    /**
     * 财务计提年数
     * @return
     */
    private Double acc_depre_amount;
    /**
     * 管理计提年数
     * @return
     */
    private Double manage_depre_amount;
    /**
     * 供应商ID
     * @return
     */
    private Long ven_id;
    /**
     * 供应商NO
     * @return
     */
    private Long ven_no;
    /**
     * 是否计量
     * @return
     */
    private Integer is_measure;
    /**
     * 是否投放
     * @return
     */
    private Integer is_throw;
    /**
     * 生产厂商ID
     * @return
     */
    private Long fac_id;
    /**
     * 生产厂商NO
     * @return
     */
    private Long fac_no;
    /**
     * 当前卡片位置
     * @return
     */
    private String card;
    /**
     * 制单人
     * @return
     */
    private Long create_emp;
    /**
	 * 制单日期
	 */
	private Date create_date;
	/**
	 * 审核人
	 */
	private Long audit_emp;
	/**
	 * 审核日期
	 */
	private Date audit_date;
	/**
	 * 入库ID
	 * @return
	 */
	private Long ass_in_id;
	
    
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDepre_money() {
		return depre_money;
	}

	public void setDepre_money(Double depre_money) {
		this.depre_money = depre_money;
	}

	public Double getCur_money() {
		return cur_money;
	}

	public void setCur_money(Double cur_money) {
		this.cur_money = cur_money;
	}

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

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_no() {
		return ass_no;
	}

	public void setAss_no(String ass_no) {
		this.ass_no = ass_no;
	}

	public Integer getAss_amount() {
		return ass_amount;
	}

	public void setAss_amount(Integer ass_amount) {
		this.ass_amount = ass_amount;
	}

	public Double getFore_money() {
		return fore_money;
	}

	public void setFore_money(Double fore_money) {
		this.fore_money = fore_money;
	}

	public String getBuy_type() {
		return buy_type;
	}

	public void setBuy_type(String buy_type) {
		this.buy_type = buy_type;
	}

	public String getAt_loca() {
		return at_loca;
	}

	public void setAt_loca(String at_loca) {
		this.at_loca = at_loca;
	}

	public Long getUser_dept_id() {
		return user_dept_id;
	}

	public void setUser_dept_id(Long user_dept_id) {
		this.user_dept_id = user_dept_id;
	}

	public Long getUser_dept_no() {
		return user_dept_no;
	}

	public void setUser_dept_no(Long user_dept_no) {
		this.user_dept_no = user_dept_no;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getStore_no() {
		return store_no;
	}

	public void setStore_no(Long store_no) {
		this.store_no = store_no;
	}

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}

	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public Integer getUse_state() {
		return use_state;
	}

	public void setUse_state(Integer use_state) {
		this.use_state = use_state;
	}

	public Integer getIs_depr() {
		return is_depr;
	}

	public void setIs_depr(Integer is_depr) {
		this.is_depr = is_depr;
	}

	public String getDepr_method() {
		return depr_method;
	}

	public void setDepr_method(String depr_method) {
		this.depr_method = depr_method;
	}

	public Double getAcc_depre_amount() {
		return acc_depre_amount;
	}

	public void setAcc_depre_amount(Double acc_depre_amount) {
		this.acc_depre_amount = acc_depre_amount;
	}

	public Double getManage_depre_amount() {
		return manage_depre_amount;
	}

	public void setManage_depre_amount(Double manage_depre_amount) {
		this.manage_depre_amount = manage_depre_amount;
	}

	public Long getVen_id() {
		return ven_id;
	}

	public void setVen_id(Long ven_id) {
		this.ven_id = ven_id;
	}

	public Long getVen_no() {
		return ven_no;
	}

	public void setVen_no(Long ven_no) {
		this.ven_no = ven_no;
	}

	

	public Integer getIs_throw() {
		return is_throw;
	}

	public void setIs_throw(Integer is_throw) {
		this.is_throw = is_throw;
	}

	public Long getFac_id() {
		return fac_id;
	}

	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
	}

	public Long getFac_no() {
		return fac_no;
	}

	public void setFac_no(Long fac_no) {
		this.fac_no = fac_no;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public Long getCreate_emp() {
		return create_emp;
	}

	public void setCreate_emp(Long create_emp) {
		this.create_emp = create_emp;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Long getAudit_emp() {
		return audit_emp;
	}

	public void setAudit_emp(Long audit_emp) {
		this.audit_emp = audit_emp;
	}

	public Long getAss_in_id() {
		return ass_in_id;
	}

	public void setAss_in_id(Long ass_in_id) {
		this.ass_in_id = ass_in_id;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Integer getIs_measure() {
		return is_measure;
	}

	public void setIs_measure(Integer is_measure) {
		this.is_measure = is_measure;
	}

	public Double getBegin_money() {
		return begin_money;
	}

	public void setBegin_money(Double begin_money) {
		this.begin_money = begin_money;
	}
}
