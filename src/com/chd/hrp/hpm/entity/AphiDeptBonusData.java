package com.chd.hrp.hpm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class AphiDeptBonusData implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;
	
	private String acct_year;
	
	private String acct_month;
	
	private String item_code;
	
	private String item_name;
	
	private Long dept_id;
	
	private Long dept_no;
	
	private String dept_code;
	
	private String emp_code;
	
	private String dept_name;
	
	private double bonus_money;
	
	private String dept_kind_code;
	
	private String dept_kind_name;
	
	private String formula_name;
	
	private String formula_code;
	
	private String method_code;
	
	private String formula_method_chs;
	
	private String formula_method_eng;
	
	private String target_code;
	
	private String target_name;
	
	private String nature_name;
	
	private String fun_code;
	
	private String source_name;
	
	private String target_value;
	
	private String nature_code;
	
	private String target_nature;
	
	private String target_value_hosp;
	
	private String target_value_dept_kind;
	
	private String target_value_dept;
	
	private int is_audit;
	
	private Date audit_date;
	
	private String note;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Long getUser_code() {
		return user_code;
	}

	public void setUser_code(Long user_code) {
		this.user_code = user_code;
	}

	private Long user_code;

	public int getIs_audit() {
		return is_audit;
	}

	public void setIs_audit(int is_audit) {
		this.is_audit = is_audit;
	}

	public String getTarget_value_hosp() {
		return target_value_hosp;
	}

	public void setTarget_value_hosp(String target_value_hosp) {
		this.target_value_hosp = target_value_hosp;
	}

	public String getTarget_value_dept_kind() {
		return target_value_dept_kind;
	}

	public void setTarget_value_dept_kind(String target_value_dept_kind) {
		this.target_value_dept_kind = target_value_dept_kind;
	}

	public String getTarget_value_dept() {
		return target_value_dept;
	}

	public void setTarget_value_dept(String target_value_dept) {
		this.target_value_dept = target_value_dept;
	}

	public String getNature_code() {
		return nature_code;
	}

	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}

	public String getTarget_value() {
		return target_value;
	}

	public void setTarget_value(String target_value) {
		this.target_value = target_value;
	}

	public String getFun_code() {
		return fun_code;
	}

	public void setFun_code(String fun_code) {
		this.fun_code = fun_code;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getTarget_code() {
		return target_code;
	}

	public void setTarget_code(String target_code) {
		this.target_code = target_code;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
 

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setCopy_code(String value) {
		this.copy_code = value;
	}
		
	public String getCopy_code() {
		return this.copy_code;
	}
	public void setAcct_year(String value) {
		this.acct_year = value;
	}
		
	public String getAcct_year() {
		return this.acct_year;
	}
	public void setAcct_month(String value) {
		this.acct_month = value;
	}
		
	public String getAcct_month() {
		return this.acct_month;
	}
	public void setItem_code(String value) {
		this.item_code = value;
	}
		
	public String getItem_code() {
		return this.item_code;
	}
	public void setDept_code(String value) {
		this.dept_code = value;
	}
		
	public String getDept_code() {
		return this.dept_code;
	}
	public void setBonus_money(double value) {
		this.bonus_money = value;
	}
		
	public double getBonus_money() {
		return this.bonus_money;
	}

	public String getDept_kind_name() {
		return dept_kind_name;
	}

	public void setDept_kind_name(String dept_kind_name) {
		this.dept_kind_name = dept_kind_name;
	}

	public String getFormula_name() {
		return formula_name;
	}

	public void setFormula_name(String formula_name) {
		this.formula_name = formula_name;
	}

	public String getFormula_code() {
		return formula_code;
	}

	public void setFormula_code(String formula_code) {
		this.formula_code = formula_code;
	}

	public String getMethod_code() {
		return method_code;
	}

	public void setMethod_code(String method_code) {
		this.method_code = method_code;
	}

	public String getFormula_method_chs() {
		
		return this.formula_method_chs;
		/*String str = null;
		
		try {
			
			str = new String(formula_method_chs,"UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;*/
	}

	public void setFormula_method_chs(String formula_method_chs) {
		this.formula_method_chs = formula_method_chs;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getDept_kind_code() {
		return dept_kind_code;
	}

	public void setDept_kind_code(String dept_kind_code) {
		this.dept_kind_code = dept_kind_code;
	}

	public String getTarget_nature() {
		return target_nature;
	}

	public void setTarget_nature(String target_nature) {
		this.target_nature = target_nature;
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

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}
	
	
}