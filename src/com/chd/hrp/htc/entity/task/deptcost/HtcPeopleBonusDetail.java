package com.chd.hrp.htc.entity.task.deptcost;

import java.io.Serializable;
import java.util.*;
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

public class HtcPeopleBonusDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String acc_year;
	private String acc_month;
	private long dept_no;
	private long dept_id;
	private String dept_code;
	private String dept_name;
	private String people_type_code;
	private String people_type_name;
	private String people_code;
	private String people_name;
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
	public long getDept_no() {
		return dept_no;
	}
	public long getDept_id() {
		return dept_id;
	}
	public String getDept_code() {
		return dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public String getPeople_type_code() {
		return people_type_code;
	}
	public String getPeople_type_name() {
		return people_type_name;
	}
	public String getPeople_code() {
		return people_code;
	}
	public String getPeople_name() {
		return people_name;
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
	public void setDept_no(long dept_no) {
		this.dept_no = dept_no;
	}
	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public void setPeople_type_code(String people_type_code) {
		this.people_type_code = people_type_code;
	}
	public void setPeople_type_name(String people_type_name) {
		this.people_type_name = people_type_name;
	}
	public void setPeople_code(String people_code) {
		this.people_code = people_code;
	}
	public void setPeople_name(String people_name) {
		this.people_name = people_name;
	}
}