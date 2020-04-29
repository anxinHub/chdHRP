/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 保存本月、期初各材料、各批、条码、单价、收发数量和金额。
APPLY_NO：每个库房每个月从SS-YYYYMMU0001开始编号 ss库号U=1入库=0出库
state：状态
1：新建
2：审核
3：发送
4：退回

 * @Table:
 * MAT_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatApplyMain implements Serializable {

	
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
	 * 单据ID
	 */
	private Long apply_id;
	
	/**
	 * 申请单号
	 */
	private String apply_no;
	
	/**
	 * 申请科室ID
	 */
	private Long dept_id;
	
	/**
	 * 申请科室变更ID
	 */
	private Long dept_no;
	
	/**
	 * 申请科室编码
	 */
	private String dept_code;
	
	/**
	 * 申请科室名称
	 */
	private String dept_name;
	
	/**
	 * 响应库房ID
	 */
	private Long store_id;
	
	/**
	 * 响应库房变更ID
	 */
	private Long store_no;
	
	/**
	 * 响应库房编码
	 */
	private String store_code;
	
	/**
	 * 响应库房名称
	 */
	private String store_name;
	
	/**
	 * 申请人ID
	 */
	private Long app_emp;
	
	/**
	 * 申请人编码
	 */
	private String emp_code;
	
	/**
	 * 申请人名称
	 */
	private String emp_name;
	
	/**
	 * 申请日期
	 */
	private Date app_date;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 申请状态
	 */
	private Integer state;
	
	/**
	 * 退回原因
	 */
	private String back_reason;
	
	/**
	 * 说明
	 */
	private String brief;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 单据ID
	* @param value 
	*/
	public void setApply_id(Long value) {
		this.apply_id = value;
	}
	
	/**
	* 获取 单据ID
	* @return Long
	*/
	public Long getApply_id() {
		return this.apply_id;
	}
	/**
	* 设置 申请单号
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 申请单号
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
	}
	/**
	* 设置 申请科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 申请科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 申请科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 申请科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 响应库房ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 响应库房ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 响应库房变更ID
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 响应库房变更ID
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 申请人
	* @param value 
	*/
	public void setApp_emp(Long value) {
		this.app_emp = value;
	}
	
	/**
	* 获取 申请人
	* @return Long
	*/
	public Long getApp_emp() {
		return this.app_emp;
	}
	/**
	* 设置 申请日期
	* @param value 
	*/
	public void setApp_date(Date value) {
		this.app_date = value;
	}
	
	/**
	* 获取 申请日期
	* @return Date
	*/
	public Date getApp_date() {
		return this.app_date;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 退回原因
	* @param value 
	*/
	public void setBack_reason(String value) {
		this.back_reason = value;
	}
	
	/**
	* 获取 退回原因
	* @return String
	*/
	public String getBack_reason() {
		return this.back_reason;
	}
	/**
	* 设置 说明
	* @param value 
	*/
	public void setBrief(String value) {
		this.brief = value;
	}
	
	/**
	* 获取 说明
	* @return String
	*/
	public String getBrief() {
		return this.brief;
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

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}