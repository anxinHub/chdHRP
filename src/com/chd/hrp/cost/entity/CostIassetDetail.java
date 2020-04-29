/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室无形资产折旧明细<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostIassetDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String dept_code;
	private String dept_name;
	private String asset_code;
	private String asset_name;
	private String asset_type_code;
	private String asset_type_name;
	private String source_code;
	private String source_name;
	
	private String dept_no;
	
	
	
	
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
	 * 统计年月
	 */
	private String year_month;
	
	/**
	 * 统计年
	 */
	private String acc_year;
	
	
	/**
	 * 统计月
	 */
	private String acc_month;
	/**
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 资产分类ID
	 */
	private Long asset_type_id;
	/**
	 * 资产ID
	 */
	private Long asset_id;
	/**
	 * 资金来源
	 */
	private Long source_id;
	/**
	 * 折旧额
	 */
	private double depre_amount;
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
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 资产分类ID
	 */
	public void setAsset_type_id(Long value) {
		this.asset_type_id = value;
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
	 * 设置 资金来源
	 */
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	/**
	 * 获取 资金来源
	 */	
	public Long getSource_id() {
		return this.source_id;
	}
	/**
	 * 设置 折旧额
	 */
	public void setDepre_amount(double value) {
		this.depre_amount = value;
	}
	/**
	 * 获取 折旧额
	 */	
	public double getDepre_amount() {
		return this.depre_amount;
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
	
    public String getDept_code() {
    	return dept_code;
    }
	
    public void setDept_code(String dept_code) {
    	this.dept_code = dept_code;
    }
	
    public String getDept_name() {
    	return dept_name;
    }
	
    public void setDept_name(String dept_name) {
    	this.dept_name = dept_name;
    }
	
    public String getAsset_code() {
    	return asset_code;
    }
	
    public void setAsset_code(String asset_code) {
    	this.asset_code = asset_code;
    }
	
    public String getAsset_name() {
    	return asset_name;
    }
	
    public void setAsset_name(String asset_name) {
    	this.asset_name = asset_name;
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
	
    public String getSource_code() {
    	return source_code;
    }
	
    public void setSource_code(String source_code) {
    	this.source_code = source_code;
    }
	
    public String getSource_name() {
    	return source_name;
    }
	
    public void setSource_name(String source_name) {
    	this.source_name = source_name;
    }
	
    public String getDept_no() {
    	return dept_no;
    }
	
    public void setDept_no(String dept_no) {
    	this.dept_no = dept_no;
    }
	
}