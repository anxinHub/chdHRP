/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Title. @Description. 成本_无形资产字典<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostIassetArrt implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String asset_type_code;

	private String asset_type_name;

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
	 * 资产ID
	 */
	private Long asset_id;

	/**
	 * 资产编码
	 */
	private String asset_code;

	/**
	 * 资产名称
	 */
	private String asset_name;

	/**
	 * 资产原值
	 */
	private double prim_value;

	/**
	 * 启用年月
	 */
	private Date start_date;

	/**
	 * 结束年月
	 */
	private Date end_date;

	/**
	 * 折旧年限
	 */
	private Integer dep_year;

	/**
	 * 拼音码
	 */
	private String spell_code;

	/**
	 * 五笔码
	 */
	private String wbx_code;

	/**
	 * 导入验证信息
	 */
	private String error_type;

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
	 * 设置 资产ID
	 */
	public void setAsset_id(Long value) {
		this.asset_id = value;
	}

	/**
	 * 获取 资产ID
	 */
	public Long getAsset_id() {
		return this.asset_id;
	}

	/**
	 * 设置 资产编码
	 */
	public void setAsset_code(String value) {
		this.asset_code = value;
	}

	/**
	 * 获取 资产编码
	 */
	public String getAsset_code() {
		return this.asset_code;
	}

	/**
	 * 设置 资产名称
	 */
	public void setAsset_name(String value) {
		this.asset_name = value;
	}

	/**
	 * 获取 资产名称
	 */
	public String getAsset_name() {
		return this.asset_name;
	}

	/**
	 * 设置 资产原值
	 */
	public void setPrim_value(double value) {
		this.prim_value = value;
	}

	/**
	 * 获取 资产原值
	 */
	public double getPrim_value() {
		return this.prim_value;
	}

	/**
	 * 设置 启用年月
	 */
	public void setStart_date(Date value) {
		this.start_date = value;
	}

	/**
	 * 获取 启用年月
	 */
	public Date getStart_date() {
		return this.start_date;
	}

	/**
	 * 设置 结束年月
	 */
	public void setEnd_date(Date value) {
		this.end_date = value;
	}

	/**
	 * 获取 结束年月
	 */
	public Date getEnd_date() {
		return this.end_date;
	}

	/**
	 * 设置 折旧年限
	 */
	public void setDep_year(Integer value) {
		this.dep_year = value;
	}

	/**
	 * 获取 折旧年限
	 */
	public Integer getDep_year() {
		return this.dep_year;
	}

	/**
	 * 设置 拼音码
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}

	/**
	 * 获取 拼音码
	 */
	public String getSpell_code() {
		return this.spell_code;
	}

	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}

	/**
	 * 获取 五笔码
	 */
	public String getWbx_code() {
		return this.wbx_code;
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

	public String getAsset_type_name() {
		return asset_type_name;
	}

	public void setAsset_type_name(String asset_type_name) {
		this.asset_type_name = asset_type_name;
	}

}