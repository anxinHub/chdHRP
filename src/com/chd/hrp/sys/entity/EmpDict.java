/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class EmpDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long emp_no;
	private Long group_id;
	private Long hos_id;
	private Long emp_id;
	private String emp_code;
	private String emp_name;
	private Long dept_no;
	private Long dept_id;
	private String kind_code;
	private String kind_name;
	private Long sort_code;
	private String note;
	private String spell_code;
	private String wbx_code;
	private String user_code;
	private Date create_date;
	private String dlog;
	private int is_stop;
	private int is_pay;
	private String id_number;
	private String pay_code;
	private String pay_type_name;
	private String pay_type_code;
	
	private String pay ;
	
	private String station_code ;
	private String station_name ;
	private String duty_code ;
	private String duty_name ;
	
	private String countries_code ;
	private String countries_name ;
	//页面查询用字段
	private String dept_name;
	private int is_disable;
	private String attr_code;
	private String attr_name;

	
	public String getAttr_code() {
		return attr_code;
	}

	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	public void setEmp_no(Long value) {
		this.emp_no = value;
	}
		
	public Long getEmp_no() {
		return this.emp_no;
	}
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
		
	public Long getHos_id() {
		return this.hos_id;
	}
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
		
	public Long getEmp_id() {
		return this.emp_id;
	}
	public void setEmp_code(String value) {
		this.emp_code = value;
	}
		
	public String getEmp_code() {
		return this.emp_code;
	}
	public void setEmp_name(String value) {
		this.emp_name = value;
	}
		
	public String getEmp_name() {
		return this.emp_name;
	}
	public void setUser_code(String value) {
		this.user_code = value;
	}
		
	public String getUser_code() {
		return this.user_code;
	}
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
		
	public Date getCreate_date() {
		return this.create_date;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getDlog() {
		return dlog;
	}

	public void setDlog(String dlog) {
		this.dlog = dlog;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * @return the kind_name
	 */
	public String getKind_name() {
		return kind_name;
	}

	/**
	 * @param kind_name the kind_name to set
	 */
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	/**
	 * @return the is_pay
	 */
	public int getIs_pay() {
		return is_pay;
	}

	/**
	 * @param is_pay the is_pay to set
	 */
	public void setIs_pay(int is_pay) {
		this.is_pay = is_pay;
	}

	/**
	 * @return the is_number
	 */
	public String getId_number() {
		return id_number;
	}

	/**
	 * @param id_number the id_number to set
	 */
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	/**
	 * @return the pay_code
	 */
	public String getPay_code() {
		return pay_code;
	}

	/**
	 * @param pay_code the pay_code to set
	 */
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	/**
	 * @return the pay_type_name
	 */
	public String getPay_type_name() {
		return pay_type_name;
	}

	/**
	 * @param pay_type_name the pay_type_name to set
	 */
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}

	/**
	 * @return the pay_type_code
	 */
	public String getPay_type_code() {
		return pay_type_code;
	}

	/**
	 * @param pay_type_code the pay_type_code to set
	 */
	public void setPay_type_code(String pay_type_code) {
		this.pay_type_code = pay_type_code;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getStation_code() {
		return station_code;
	}

	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getDuty_code() {
		return duty_code;
	}

	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getCountries_code() {
		return countries_code;
	}

	public void setCountries_code(String countries_code) {
		this.countries_code = countries_code;
	}

	public String getCountries_name() {
		return countries_name;
	}

	public void setCountries_name(String countries_name) {
		this.countries_name = countries_name;
	}
	
	
}