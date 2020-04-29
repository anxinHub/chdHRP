/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: 成本_分摊类别
 * @Table: COST_PARA_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostParaNatur implements Serializable {

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
	 * 分摊类别编码
	 */
	private String para_code;

	/**
	 * 分摊类别名称
	 */
	private String para_name;

	/**
	 * 默认分摊参数
	 */
	private String para_value;
	/**
	 * 默认分摊参数名称
	 */
	private String para_value_name;

	/**
	 * 分摊级次 01表示 管理<br>
	 * 02表示 医辅<br>
	 * 03表示 医技
	 */
	private String bill_type;
	/**
	 * 分摊级次 01表示 管理<br>
	 * 02表示 医辅<br>
	 * 03表示 医技
	 */
	private String bill_name;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 分摊类别编码
	 * 
	 * @param value
	 */
	public void setPara_code(String value) {
		this.para_code = value;
	}

	/**
	 * 获取 分摊类别编码
	 * 
	 * @return String
	 */
	public String getPara_code() {
		return this.para_code;
	}

	/**
	 * 设置 分摊类别名称
	 * 
	 * @param value
	 */
	public void setPara_name(String value) {
		this.para_name = value;
	}

	/**
	 * 获取 分摊类别名称
	 * 
	 * @return String
	 */
	public String getPara_name() {
		return this.para_name;
	}

	/**
	 * 设置 备注
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取 备注
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
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

	/**
	 * 默认分摊参数
	 */
    public String getPara_value() {
    	return para_value;
    }

    /**
	 *  默认分摊参数
	 */
    public void setPara_value(String para_value) {
    	this.para_value = para_value;
    }

    /**
	 * 分摊级次 01表示 管理<br>
	 * 02表示 医辅<br>
	 * 03表示 医技
	 */
    public String getBill_type() {
    	return bill_type;
    }

    /**
	 * 分摊级次 01表示 管理<br>
	 * 02表示 医辅<br>
	 * 03表示 医技
	 */
    public void setBill_type(String bill_type) {
    	this.bill_type = bill_type;
    }

	
    public String getPara_value_name() {
    	return para_value_name;
    }

	
    public void setPara_value_name(String para_value_name) {
    	this.para_value_name = para_value_name;
    }

	
    public String getBill_name() {
    	return bill_name;
    }

	
    public void setBill_name(String bill_name) {
    	this.bill_name = bill_name;
    }
	
}