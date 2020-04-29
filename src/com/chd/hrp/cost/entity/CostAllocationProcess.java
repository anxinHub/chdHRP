/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostAllocationProcess implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;

	private String copy_code;

	private String acc_year;

	private String acc_month;

	private Long dept_id;

	private Long dept_no;

	private Long server_dept_id;

	private Long server_dept_no;

	private Long cost_item_id;

	private Long cost_item_no;

	private Long source_id;

	private double dir_amount;

	private double equal_amount;

	private double dir_man_amount;

	private double dir_ass_amount;

	private double dir_med_amount;

	private double indir_ass_man_amount;

	private double indir_med_man_amount;

	private double indir_ass_med_man_amount;

	private double indir_med_ass_amount;

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
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

	public Long getServer_dept_id() {
		return server_dept_id;
	}

	public void setServer_dept_id(Long server_dept_id) {
		this.server_dept_id = server_dept_id;
	}

	public Long getServer_dept_no() {
		return server_dept_no;
	}

	public void setServer_dept_no(Long server_dept_no) {
		this.server_dept_no = server_dept_no;
	}

	public Long getCost_item_id() {
		return cost_item_id;
	}

	public void setCost_item_id(Long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}

	public Long getCost_item_no() {
		return cost_item_no;
	}

	public void setCost_item_no(Long cost_item_no) {
		this.cost_item_no = cost_item_no;
	}

	public Long getSource_id() {
		return source_id;
	}

	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}

	public double getDir_amount() {
		return dir_amount;
	}

	public void setDir_amount(double dir_amount) {
		this.dir_amount = dir_amount;
	}

	public double getEqual_amount() {
		return equal_amount;
	}

	public void setEqual_amount(double equal_amount) {
		this.equal_amount = equal_amount;
	}

	public double getDir_man_amount() {
		return dir_man_amount;
	}

	public void setDir_man_amount(double dir_man_amount) {
		this.dir_man_amount = dir_man_amount;
	}

	public double getDir_ass_amount() {
		return dir_ass_amount;
	}

	public void setDir_ass_amount(double dir_ass_amount) {
		this.dir_ass_amount = dir_ass_amount;
	}

	public double getDir_med_amount() {
		return dir_med_amount;
	}

	public void setDir_med_amount(double dir_med_amount) {
		this.dir_med_amount = dir_med_amount;
	}

	public double getIndir_ass_man_amount() {
		return indir_ass_man_amount;
	}

	public void setIndir_ass_man_amount(double indir_ass_man_amount) {
		this.indir_ass_man_amount = indir_ass_man_amount;
	}

	public double getIndir_med_man_amount() {
		return indir_med_man_amount;
	}

	public void setIndir_med_man_amount(double indir_med_man_amount) {
		this.indir_med_man_amount = indir_med_man_amount;
	}

	public double getIndir_ass_med_man_amount() {
		return indir_ass_med_man_amount;
	}

	public void setIndir_ass_med_man_amount(double indir_ass_med_man_amount) {
		this.indir_ass_med_man_amount = indir_ass_med_man_amount;
	}

	public double getIndir_med_ass_amount() {
		return indir_med_ass_amount;
	}

	public void setIndir_med_ass_amount(double indir_med_ass_amount) {
		this.indir_med_ass_amount = indir_med_ass_amount;
	}

	

}