/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Title. @Description. 科室材料支出明细表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostImputation implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String source_code;

	private String source_name;

	private String dept_code;

	private String dept_name;

	private String cost_item_code;

	private String cost_item_name;
	
	private Long dept_id;
	private Long dept_no;
	private Long source_id;
	private Long cost_item_id;
	private Long cost_item_no;
	

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
	 * 金额
	 */
	private double amount;

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

	public String getCost_item_code() {
		return cost_item_code;
	}

	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}

	public String getCost_item_name() {
		return cost_item_name;
	}

	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}

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

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	
    public Long getSource_id() {
    	return source_id;
    }

	
    public void setSource_id(Long source_id) {
    	this.source_id = source_id;
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

}