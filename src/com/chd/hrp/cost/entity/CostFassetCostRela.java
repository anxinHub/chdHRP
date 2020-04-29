/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Title. @Description. 固定资产分类与成本项目的对应关系<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostFassetCostRela implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String asset_type_code;

	private String cost_item_name;
	
	private String cost_item_code;

	private String asset_type_name;

	/**
	 * ID
	 */
	private Long id;

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

	/**
	 * 资产分类ID
	 */
	private Long asset_type_id;

	/**
	 * 统计年月
	 */
	private String year_month;
	private String acc_year;
	private String acc_month;

	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;

	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 ID
	 */
	public void setId(Long value) {
		this.id = value;
	}

	/**
	 * 获取 ID
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 资产分类ID
	 */
	public void setAsset_type_id(Long value) {
		this.asset_type_id = value;
	}

	/**
	 * 获取 资产分类ID
	 */
	public Long getAsset_type_id() {
		return this.asset_type_id;
	}

	/**
	 * 设置 统计年月
	 */
	public void setYear_month(String value) {
		this.year_month = value;
	}

	/**
	 * 获取 统计年月
	 */
	public String getYear_month() {
		return this.year_month;
	}

	/**
	 * 设置 成本项目ID
	 */
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}

	/**
	 * 获取 成本项目ID
	 */
	public Long getCost_item_id() {
		return this.cost_item_id;
	}

	/**
	 * 设置 成本项目变更ID
	 */
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}

	/**
	 * 获取 成本项目变更ID
	 */
	public Long getCost_item_no() {
		return this.cost_item_no;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}

	public String getAsset_type_code() {
		return asset_type_code;
	}

	public void setAsset_type_code(String asset_type_code) {
		this.asset_type_code = asset_type_code;
	}

	public String getCost_item_name() {
		return cost_item_name;
	}

	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}

	public String getAsset_type_name() {
		return asset_type_name;
	}

	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name;
	}

	
    public String getCost_item_code() {
    	return cost_item_code;
    }

	
    public void setCost_item_code(String cost_item_code) {
    	this.cost_item_code = cost_item_code;
    }

	/**
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}

	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	/**
	 * @return the acc_month
	 */
	public String getAcc_month() {
		return acc_month;
	}

	/**
	 * @param acc_month the acc_month to set
	 */
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

}