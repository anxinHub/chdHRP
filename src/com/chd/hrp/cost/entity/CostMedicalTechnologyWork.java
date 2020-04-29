/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 医技科室工作量<BR>
* @Author: 
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostMedicalTechnologyWork implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	
	private long hos_id;
	
	private String copy_code;
	
	private String acc_year;
	
	private String acc_month;
	
	private long dept_id;
	
	private long dept_no;
	
    private String dept_code;
	
	private String dept_name;
	
	private long medical_num;

	public long getGroup_id() {
		return group_id;
	}

	public long getHos_id() {
		return hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public long getDept_id() {
		return dept_id;
	}

	public long getDept_no() {
		return dept_no;
	}

	public String getDept_code() {
		return dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public long getMedical_num() {
		return medical_num;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}

	public void setDept_no(long dept_no) {
		this.dept_no = dept_no;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setMedical_num(long medical_num) {
		this.medical_num = medical_num;
	}
}