package com.chd.hrp.htc.entity.task.deptcost;

import java.io.Serializable;
import java.util.*;

import com.github.abel533.echarts.Data;

/**
 * 2015-3-18 author:alfred
 */
public class HtcDeptFassetDetail implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

    private long group_id;
	
	private long hos_id;

	private String copy_code;
	
	private String acc_year;

	private String acc_month;
	
	private long dept_no;
	
	private long dept_id;

	private String dept_name;
	
	private String dept_code;
	
    private String asset_type_code;
	
	private String asset_type_name;
	
	private String asset_code;
	
	private String asset_name;
	
	private double depre_amount;
	
	private long source_id;

	private String source_code;
	
	private String source_name;
	
    private long cost_item_id;
	
	private long cost_item_no;
	
	private String cost_item_code;
	
	private String cost_item_name;
	
    private double tot_amount;
	
	private double num_amount;

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

	public String getDept_name() {
		return dept_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public String getAsset_type_code() {
		return asset_type_code;
	}

	public String getAsset_type_name() {
		return asset_type_name;
	}

	public String getAsset_code() {
		return asset_code;
	}

	public String getAsset_name() {
		return asset_name;
	}

	public double getDepre_amount() {
		return depre_amount;
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

	public double getTot_amount() {
		return tot_amount;
	}

	public double getNum_amount() {
		return num_amount;
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

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public void setAsset_type_code(String asset_type_code) {
		this.asset_type_code = asset_type_code;
	}

	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name;
	}

	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}

	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}

	public void setDepre_amount(double depre_amount) {
		this.depre_amount = depre_amount;
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

	public void setTot_amount(double tot_amount) {
		this.tot_amount = tot_amount;
	}

	public void setNum_amount(double num_amount) {
		this.num_amount = num_amount;
	}
	
}