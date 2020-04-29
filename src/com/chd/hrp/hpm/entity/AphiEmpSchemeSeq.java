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

public class AphiEmpSchemeSeq implements Serializable {

	
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
	private Integer sub_scheme_seq_no;
	private String dept_code;
	private String duty_code;
	private String method_code;
	private String formula_code;
	private String fun_code;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}
		
	public String getCopy_code() {
		return this.copy_code;
	}
	public void setSub_scheme_seq_no(Integer value) {
		this.sub_scheme_seq_no = value;
	}
		
	public Integer getSub_scheme_seq_no() {
		return this.sub_scheme_seq_no;
	}
	public void setDept_code(String value) {
		this.dept_code = value;
	}
		
	public String getDept_code() {
		return this.dept_code;
	}
	public void setDuty_code(String value) {
		this.duty_code = value;
	}
		
	public String getDuty_code() {
		return this.duty_code;
	}
	public void setMethod_code(String value) {
		this.method_code = value;
	}
		
	public String getMethod_code() {
		return this.method_code;
	}
	public void setFormula_code(String value) {
		this.formula_code = value;
	}
		
	public String getFormula_code() {
		return this.formula_code;
	}
	public void setFun_code(String value) {
		this.fun_code = value;
	}
		
	public String getFun_code() {
		return this.fun_code;
	}
}