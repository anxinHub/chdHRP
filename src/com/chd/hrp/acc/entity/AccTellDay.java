/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @Title. @Description.
* 出纳账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccTellDay implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String acc_year;
	private String acc_month;
	private Date cash_date;
	private int cash_flag;
	private String create_user;
	private Date create_date;
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
	public Date getCash_date() {
		return cash_date;
	}
	public void setCash_date(Date cash_date) {
		this.cash_date = cash_date;
	}
	public int getCash_flag() {
		return cash_flag;
	}
	public void setCash_flag(int cash_flag) {
		this.cash_flag = cash_flag;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}