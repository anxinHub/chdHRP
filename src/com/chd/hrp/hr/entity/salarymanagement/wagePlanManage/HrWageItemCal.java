package com.chd.hrp.hr.entity.salarymanagement.wagePlanManage;

/**
 * 【薪资管理-薪资方案】：工资项取值方法
 * @author yang
 *
 */
public class HrWageItemCal {

	private Long cal_id;
	
	private Integer group_id;
	private Integer hos_id;
	
	private String plan_code;
	private String item_code;
	private String item_name;
	private String kind_code;// 职工分类编码, null值表示全部
	private String kind_name;
	
	private String item_cal;// 取值方法0：录入，1：公式，2：薪资标准表
	private String item_cal_cn;
	
	private Long stan_id;
	private String stan_name;
	
	private String cal_name;// 公式名称
	private String cal_eng;// 公式转译
	
	private String note;
	
	public HrWageItemCal(){}

	public Long getStan_id() {
		return stan_id;
	}

	public void setStan_id(Long stan_id) {
		this.stan_id = stan_id;
	}

	public String getStan_name() {
		return stan_name;
	}

	public void setStan_name(String stan_name) {
		this.stan_name = stan_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public String getItem_cal_cn() {
		return item_cal_cn;
	}

	public void setItem_cal_cn(String item_cal_cn) {
		this.item_cal_cn = item_cal_cn;
	}

	public Long getCal_id() {
		return cal_id;
	}

	public void setCal_id(Long cal_id) {
		this.cal_id = cal_id;
	}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getItem_cal() {
		return item_cal;
	}

	public void setItem_cal(String item_cal) {
		this.item_cal = item_cal;
	}

	public String getCal_name() {
		return cal_name;
	}

	public void setCal_name(String cal_name) {
		this.cal_name = cal_name;
	}

	public String getCal_eng() {
		return cal_eng;
	}

	public void setCal_eng(String cal_eng) {
		this.cal_eng = cal_eng;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
