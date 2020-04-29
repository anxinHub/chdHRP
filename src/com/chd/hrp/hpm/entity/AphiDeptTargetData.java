package com.chd.hrp.hpm.entity;

import java.io.Serializable;
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

public class AphiDeptTargetData implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	
	
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
	private String copy_code;
	private String acct_year;
	private String acct_month;
	private String target_code;
	private String target_name;
	private Long dept_no;
	
	private Long dept_id;
	private String dept_name;
	private double target_value;
	private Integer is_audit;
	private String user_code;
	private String check_name;
	private Date audit_time;
	private String error_type;
	
	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
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
	public void setTarget_code(String value) {
		this.target_code = value;
	}
		
	public String getTarget_code() {
		return this.target_code;
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

	public void setTarget_value(double value) {
		this.target_value = value;
	}
		
	public double getTarget_value() {
		return this.target_value;
	}
	public void setIs_audit(Integer value) {
		this.is_audit = value;
	}
		
	public Integer getIs_audit() {
		return this.is_audit;
	}
	public void setUser_code(String value) {
		this.user_code = value;
	}
		
	public String getUser_code() {
		return this.user_code;
	}
	public void setAudit_time(Date value) {
		this.audit_time = value;
	}
		
	public Date getAudit_time() {
		return this.audit_time;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getCheck_name() {
		return check_name;
	}

	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	
	
}