/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 现金流量初始帐<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCashFlowInit implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	 
	
	private String acc_year;
	
	private Long subj_id;
	
	private String subj_code;
	
	private String subj_name;
	
	private Long cash_item_id;
	
	private String cash_item_name;
	
	private String summary;
	
	private Double cash_money;
	
	private String error_type ;

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

	public Long getSubj_id() {
		return subj_id;
	}

	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}

	public Long getCash_item_id() {
		return cash_item_id;
	}

	public void setCash_item_id(Long cash_item_id) {
		this.cash_item_id = cash_item_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Double getCash_money() {
		return cash_money;
	}

	public void setCash_money(Double cash_money) {
		this.cash_money = cash_money;
	}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public String getSubj_name() {
		return subj_name;
	}

	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}

	public String getCash_item_name() {
		return cash_item_name;
	}

	public void setCash_item_name(String cash_item_name) {
		this.cash_item_name = cash_item_name;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}