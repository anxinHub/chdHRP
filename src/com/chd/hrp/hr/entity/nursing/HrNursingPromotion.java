package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;
import java.util.Date;

/**
 * 护理晋级申请
 * 
 * @author Administrator
 *
 */
public class HrNursingPromotion implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;

	/**
	 * 
	 */
	private Long hos_id;

	/**
	 * 
	 */
	private String year;

	/**
	 * 
	 */
	private Integer emp_id;
	private String emp_code;
	private String emp_name;

	/**
	 * 
	 */
	private Integer dept_id;

	/**
	 * 
	 */
	private Integer dept_no;
	
	private String dept_name;

	/**
	 * HR_EMP_DEGREE中毕业学校不为空最高学历
	 */
	private String degree_code;
	/**
	 * 毕业学历
	 */
	private String degree_name;

	/**
	 * 
	 */
	private Date graduation_date;

	/**
	 * HR_EMP_DEGREE中学历最高的
	 */
	private String max_degree_code;
    /**
     * 最高学历
     */
	private String max_degree_name;
	/**
	 * 
	 */
	private Date max_graduation_date;

	/**
	 * 
	 */
	private String birthday;

	/**
	 * DIC_LEVEL
	 */
	private String cur_level_code;
   /**
    * 现有阶别名称
    */
	private String cur_level_name;
	/**
	 * 
	 */
	private Date cur_get_date;

	/**
	 * DIC_TITLE
	 */
	private String cur_title_code;
	/**
	 * 现有职称
	 */
    private String cur_title_name;
	/**
	 * 
	 */
	private Date cur_title_date;

	/**
	 * DIC_LEVEL
	 */
	private String apply_level_code;
	/**
	 * 级别名称
	 */
    private String apply_level_name;
	/**
	 * 
	 */
	private String worktime;

	/**
	 * 
	 */
	private Date apply_date;

	/**
	 * 
	 */
	private Date audit_date;

	/**
	 * 0：新建 1:提交
	 */
	private Integer state;
	
	private String state_name;

	/**
	 * 
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

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

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setYear(String value) {
		this.year = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getYear() {
		return this.year;
	}

	

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getDept_no() {
		return dept_no;
	}

	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * 设置 HR_EMP_DEGREE中毕业学校不为空最高学历
	 * 
	 * @param value
	 */
	public void setDegree_code(String value) {
		this.degree_code = value;
	}

	/**
	 * 获取 HR_EMP_DEGREE中毕业学校不为空最高学历
	 * 
	 * @return String
	 */
	public String getDegree_code() {
		return this.degree_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setGraduation_date(Date value) {
		this.graduation_date = value;
	}

	/**
	 * 获取
	 * 
	 * @return Date
	 */
	public Date getGraduation_date() {
		return this.graduation_date;
	}

	/**
	 * 设置 HR_EMP_DEGREE中学历最高的
	 * 
	 * @param value
	 */
	public void setMax_degree_code(String value) {
		this.max_degree_code = value;
	}

	/**
	 * 获取 HR_EMP_DEGREE中学历最高的
	 * 
	 * @return String
	 */
	public String getMax_degree_code() {
		return this.max_degree_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setMax_graduation_date(Date value) {
		this.max_graduation_date = value;
	}

	/**
	 * 获取
	 * 
	 * @return Date
	 */
	public Date getMax_graduation_date() {
		return this.max_graduation_date;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setBirthday(String value) {
		this.birthday = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getBirthday() {
		return this.birthday;
	}

	/**
	 * 设置 DIC_LEVEL
	 * 
	 * @param value
	 */
	public void setCur_level_code(String value) {
		this.cur_level_code = value;
	}

	/**
	 * 获取 DIC_LEVEL
	 * 
	 * @return String
	 */
	public String getCur_level_code() {
		return this.cur_level_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setCur_get_date(Date value) {
		this.cur_get_date = value;
	}

	/**
	 * 获取
	 * 
	 * @return Date
	 */
	public Date getCur_get_date() {
		return this.cur_get_date;
	}

	/**
	 * 设置 DIC_TITLE
	 * 
	 * @param value
	 */
	public void setCur_title_code(String value) {
		this.cur_title_code = value;
	}

	/**
	 * 获取 DIC_TITLE
	 * 
	 * @return String
	 */
	public String getCur_title_code() {
		return this.cur_title_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setCur_title_date(Date value) {
		this.cur_title_date = value;
	}

	/**
	 * 获取
	 * 
	 * @return Date
	 */
	public Date getCur_title_date() {
		return this.cur_title_date;
	}

	/**
	 * 设置 DIC_LEVEL
	 * 
	 * @param value
	 */
	public void setApply_level_code(String value) {
		this.apply_level_code = value;
	}

	/**
	 * 获取 DIC_LEVEL
	 * 
	 * @return String
	 */
	public String getApply_level_code() {
		return this.apply_level_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setWorktime(String value) {
		this.worktime = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getWorktime() {
		return this.worktime;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setApply_date(Date value) {
		this.apply_date = value;
	}

	/**
	 * 获取
	 * 
	 * @return Date
	 */
	public Date getApply_date() {
		return this.apply_date;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}

	/**
	 * 获取
	 * 
	 * @return Date
	 */
	public Date getAudit_date() {
		return this.audit_date;
	}

	/**
	 * 设置 0：新建 1:提交
	 * 
	 * @param value
	 */
	public void setState(Integer value) {
		this.state = value;
	}

	/**
	 * 获取 0：新建 1:提交
	 * 
	 * @return Integer
	 */
	public Integer getState() {
		return this.state;
	}
  
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取
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

	public String getDegree_name() {
		return degree_name;
	}

	public void setDegree_name(String degree_name) {
		this.degree_name = degree_name;
	}

	public String getMax_degree_name() {
		return max_degree_name;
	}

	public void setMax_degree_name(String max_degree_name) {
		this.max_degree_name = max_degree_name;
	}

	public String getCur_level_name() {
		return cur_level_name;
	}

	public void setCur_level_name(String cur_level_name) {
		this.cur_level_name = cur_level_name;
	}

	public String getCur_title_name() {
		return cur_title_name;
	}

	public void setCur_title_name(String cur_title_name) {
		this.cur_title_name = cur_title_name;
	}

	public String getApply_level_name() {
		return apply_level_name;
	}

	public void setApply_level_name(String apply_level_name) {
		this.apply_level_name = apply_level_name;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	
}
