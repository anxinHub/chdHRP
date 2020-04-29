package com.chd.hrp.htc.entity.task.plan.set;

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

public class HtcTaskPlanSet implements Serializable {

	
private static final long serialVersionUID = 5454155825314635342L;
	
	private long group_id;
	private long hos_id;
	private String copy_code;
	private String acc_year;
	private String plan_code;
	private String plan_name;
	private String start_month;
	private String end_month;
	private long is_check;
	private String method;
	private long is_current;
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
	public String getPlan_code() {
		return plan_code;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public String getStart_month() {
		return start_month;
	}
	public String getEnd_month() {
		return end_month;
	}
	public long getIs_check() {
		return is_check;
	}
	public String getMethod() {
		return method;
	}
	public long getIs_current() {
		return is_current;
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
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public void setStart_month(String start_month) {
		this.start_month = start_month;
	}
	public void setEnd_month(String end_month) {
		this.end_month = end_month;
	}
	public void setIs_check(long is_check) {
		this.is_check = is_check;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setIs_current(long is_current) {
		this.is_current = is_current;
	}
}