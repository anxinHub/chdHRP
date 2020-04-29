package com.chd.hrp.hr.entity.salarymanagement.wagePlanManage;
/**
 * 【薪资管理-薪资方案管理】：工资项bean
 * @author yang
 *
 */
public class HrWageItem {
	
	private Integer group_id;
	private Integer hos_id;
	
	private String plan_code;
	private String plan_name;
	
	private String item_code;
	private String item_name;
	
	private String item_nature;
	private String nature_name;
	private String item_type;
	private String type_name;
	
	private String data_type;
	private String data_type_cn;
	
	private Integer is_sum;
	private String is_sum_cn;
	private Integer is_stop;
	private String is_stop_cn;
	
	private String spell_code;
	private String wbx_code;
	
	private String note;
	
	private String column_item;
	
	// 排序号
	private Integer sort_code;
	
	public HrWageItem(){}

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

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_nature() {
		return item_nature;
	}

	public void setItem_nature(String item_nature) {
		this.item_nature = item_nature;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getData_type_cn() {
		return data_type_cn;
	}

	public void setData_type_cn(String data_type_cn) {
		this.data_type_cn = data_type_cn;
	}

	public Integer getIs_sum() {
		return is_sum;
	}

	public void setIs_sum(Integer is_sum) {
		this.is_sum = is_sum;
	}

	public String getIs_sum_cn() {
		return is_sum_cn;
	}

	public void setIs_sum_cn(String is_sum_cn) {
		this.is_sum_cn = is_sum_cn;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getIs_stop_cn() {
		return is_stop_cn;
	}

	public void setIs_stop_cn(String is_stop_cn) {
		this.is_stop_cn = is_stop_cn;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getColumn_item() {
		return column_item;
	}

	public void setColumn_item(String column_item) {
		this.column_item = column_item;
	}

	public Integer getSort_code() {
		return sort_code;
	}

	public void setSort_code(Integer sort_code) {
		this.sort_code = sort_code;
	}

}