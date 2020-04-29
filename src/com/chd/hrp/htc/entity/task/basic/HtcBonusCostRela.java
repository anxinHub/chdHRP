package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
import java.util.*;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class HtcBonusCostRela implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	
	private long hos_id;

	private String copy_code;

	private String acc_year;

	private String people_type_code;
	
	private String people_type_name;

	private String bonus_item_code;
	
	private String bonus_item_name;

	private long cost_item_no;
	
	private long cost_item_id;
	
	private String cost_item_code;

	private String cost_item_name;

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

	public String getPeople_type_code() {
		return people_type_code;
	}

	public String getPeople_type_name() {
		return people_type_name;
	}

	public String getBonus_item_code() {
		return bonus_item_code;
	}

	public String getBonus_item_name() {
		return bonus_item_name;
	}

	public long getCost_item_no() {
		return cost_item_no;
	}

	public long getCost_item_id() {
		return cost_item_id;
	}

	public String getCost_item_code() {
		return cost_item_code;
	}

	public String getCost_item_name() {
		return cost_item_name;
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

	public void setPeople_type_code(String people_type_code) {
		this.people_type_code = people_type_code;
	}

	public void setPeople_type_name(String people_type_name) {
		this.people_type_name = people_type_name;
	}

	public void setBonus_item_code(String bonus_item_code) {
		this.bonus_item_code = bonus_item_code;
	}

	public void setBonus_item_name(String bonus_item_name) {
		this.bonus_item_name = bonus_item_name;
	}

	public void setCost_item_no(long cost_item_no) {
		this.cost_item_no = cost_item_no;
	}

	public void setCost_item_id(long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}

	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}

	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
}