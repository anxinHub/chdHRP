/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Title. @Description. 成本_无形资产分类字典<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostIassetTypeArrt implements Serializable {

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

	/**
	 * 资产分类ID
	 */
	private Long asset_type_id;

	/**
	 * 资产分类编码
	 */
	private String asset_type_code;

	/**
	 * 资产分类名称
	 */
	private String asset_type_name;

	/**
	 * 上级编码码
	 */
	private String supper_code;

	/**
	 * 是否末级
	 */
	private Integer is_last;

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
	 * 设置 资产分类编码
	 */
	public void setAsset_type_code(String value) {
		this.asset_type_code = value;
	}

	/**
	 * 获取 资产分类编码
	 */
	public String getAsset_type_code() {
		return this.asset_type_code;
	}

	/**
	 * 设置 资产分类名称
	 */
	public void setAsset_type_name(String value) {
		this.asset_type_name = value;
	}

	/**
	 * 获取 资产分类名称
	 */
	public String getAsset_type_name() {
		return this.asset_type_name;
	}

	/**
	 * 设置 上级编码码
	 */
	public void setSupper_code(String value) {
		this.supper_code = value;
	}

	/**
	 * 获取 上级编码码
	 */
	public String getSupper_code() {
		return this.supper_code;
	}

	/**
	 * 设置 是否末级
	 */
	public void setIs_last(Integer value) {
		this.is_last = value;
	}

	/**
	 * 获取 是否末级
	 */
	public Integer getIs_last() {
		return this.is_last;
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
}