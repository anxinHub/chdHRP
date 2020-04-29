package com.chd.hrp.hpm.entity;

import java.io.Serializable;
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

public class AphiDeptBonusGrantItem implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;
	
	private String acct_year;
	
	private String acct_month;
	
	private Long dept_no;
	
	private Long dept_id;
	
	private String dept_code;
	
	private String dept_name;
	
	private String item_code;
	
	private String item_name;
	
	private double bonus_money;
	
	private double activity_money;
	
	private double activity_percent;
	
	private int is_audit;
	
	private double grant_money;

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
	
	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
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

	public void setBonus_money(double value) {
		this.bonus_money = value;
	}
		
	public double getBonus_money() {
		return this.bonus_money;
	}
	public void setActivity_money(double value) {
		this.activity_money = value;
	}
		
	public double getActivity_money() {
		return this.activity_money;
	}
	public void setActivity_percent(double value) {
		this.activity_percent = value;
	}
		
	public double getActivity_percent() {
		return this.activity_percent;
	}
	public void setGrant_money(double value) {
		this.grant_money = value;
	}
		
	public double getGrant_money() {
		return this.grant_money;
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

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public int getIs_audit() {
		return is_audit;
	}

	public void setIs_audit(int is_audit) {
		this.is_audit = is_audit;
	}
	
	
	
}