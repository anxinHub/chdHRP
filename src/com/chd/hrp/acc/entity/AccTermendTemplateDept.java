/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 期末调汇科室比例表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public class AccTermendTemplateDept implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 科室比例表ID
	 */
	private Long template_dept_id;
	/**
	 * 期末处理凭证模板ID
	 */
	private Long template_id;
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
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更号
	 */
	private Long dept_no;
	/**
	 * 科室编码
	 */
	private String dept_code;
	/**
	 * 科室名称
	 */
	private String dept_name;
	/**
	 * 科室所占比例
	 */
	private float rate;
	
	private double money_sum;
	
	public Long getTemplate_dept_id() {
		return template_dept_id;
	}
	public void setTemplate_dept_id(Long template_dept_id) {
		this.template_dept_id = template_dept_id;
	}
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
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
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public double getMoney_sum() {
		return money_sum;
	}
	public void setMoney_sum(double money_sum) {
		this.money_sum = money_sum;
	}
	
}
