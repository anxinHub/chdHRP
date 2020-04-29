package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AphiWorkitemConfSeq implements Serializable {

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

	private Integer scheme_seq_no;

	private String dept_code;
	
	private Long dept_id;
	
	private Long dept_no;

	private String work_item_code;

	private Integer is_acc;

	private double work_standard;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setScheme_seq_no(Integer value) {
		this.scheme_seq_no = value;
	}

	public Integer getScheme_seq_no() {
		return this.scheme_seq_no;
	}

	public void setDept_code(String value) {
		this.dept_code = value;
	}

	public String getDept_code() {
		return this.dept_code;
	}

	public void setWork_item_code(String value) {
		this.work_item_code = value;
	}

	public String getWork_item_code() {
		return this.work_item_code;
	}

	public void setIs_acc(Integer value) {
		this.is_acc = value;
	}

	public Integer getIs_acc() {
		return this.is_acc;
	}

	public void setWork_standard(double value) {
		this.work_standard = value;
	}

	public double getWork_standard() {
		return this.work_standard;
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