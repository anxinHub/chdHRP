package com.chd.hrp.htc.entity.relative.cost.deptcost;

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

public class HtcRelativeDeptCost implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long    group_id;
	private long    hos_id;
	private String   copy_code;
	private String    acc_year;
	private String    acc_month;
	private long    dept_id;
	private long    dept_no;
	private String   dept_code;
	private String   dept_name;
	private long    cost_item_id;
	private long    cost_item_no;
	private String   cost_item_code;
	private String   cost_item_name;
	private long    source_id;
	private String   source_code;
	private String   source_name;
	private double    tot_amount;
	private double    prime_amount;
	private double    pub_amount;
	private double    man_amount;
	private double    ass_amount;
	
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
	public long getCost_item_id() {
		return cost_item_id;
	}
	public long getCost_item_no() {
		return cost_item_no;
	}
	public String getCost_item_code() {
		return cost_item_code;
	}
	public String getCost_item_name() {
		return cost_item_name;
	}
	public long getSource_id() {
		return source_id;
	}
	public String getSource_code() {
		return source_code;
	}
	public String getSource_name() {
		return source_name;
	}
	public double getTot_amount() {
		return tot_amount;
	}
	public double getPrime_amount() {
		return prime_amount;
	}
	public double getPub_amount() {
		return pub_amount;
	}
	public double getMan_amount() {
		return man_amount;
	}
	public double getAss_amount() {
		return ass_amount;
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
	public void setCost_item_id(long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}
	public void setCost_item_no(long cost_item_no) {
		this.cost_item_no = cost_item_no;
	}
	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}
	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
	public void setSource_id(long source_id) {
		this.source_id = source_id;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public void setTot_amount(double tot_amount) {
		this.tot_amount = tot_amount;
	}
	public void setPrime_amount(double prime_amount) {
		this.prime_amount = prime_amount;
	}
	public void setPub_amount(double pub_amount) {
		this.pub_amount = pub_amount;
	}
	public void setMan_amount(double man_amount) {
		this.man_amount = man_amount;
	}
	public void setAss_amount(double ass_amount) {
		this.ass_amount = ass_amount;
	}
	
	
	
}