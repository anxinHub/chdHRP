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
 * MAT_REQUIRE_MAIN
 * @Table:
 * MAT_REQUIRE_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatRequireMain implements Serializable {

	
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
	 * 计划单ID
	 */
	private Long req_id;
	
	/**
	 * 计划单号
	 */
	private String req_code;
	
	/**
	 * 摘要
	 */
	private String brif;
	
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	
	/**
	 * 科室ID
	 */
	private Long dept_id;
	
	/**
	 * 响应库房变更ID
	 */
	private Long stock_no;
	
	/**
	 * 响应库房ID
	 */
	private Long stock_id;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 制单日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 计划类型
	 */
	private Integer req_type;
	
	/**
	 * 单据汇总
	 */
	private Integer is_collect;
	
	/**
	 * 是否提交
	 */
	private Integer is_submit;
	
	/**
	 * 是否退回
	 */
	private Integer is_return;
	
	/**
	 * 退回理由
	 */
	private String return_reason;
	
	/**
	 * 其他需求物资
	 */
	private String other_inv;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String store_name;
	private String dept_names;
	
	
	
	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getDept_names() {
		return dept_names;
	}

	public void setDept_names(String dept_names) {
		this.dept_names = dept_names;
	}

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
	* 设置 计划单ID
	* @param value 
	*/
	public void setReq_id(Long value) {
		this.req_id = value;
	}
	
	/**
	* 获取 计划单ID
	* @return Long
	*/
	public Long getReq_id() {
		return this.req_id;
	}
	/**
	* 设置 计划单号
	* @param value 
	*/
	public void setReq_code(String value) {
		this.req_code = value;
	}
	
	/**
	* 获取 计划单号
	* @return String
	*/
	public String getReq_code() {
		return this.req_code;
	}
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setBrif(String value) {
		this.brif = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getBrif() {
		return this.brif;
	}
	/**
	* 设置 科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 响应库房变更ID
	* @param value 
	*/
	public void setStock_no(Long value) {
		this.stock_no = value;
	}
	
	/**
	* 获取 响应库房变更ID
	* @return Long
	*/
	public Long getStock_no() {
		return this.stock_no;
	}
	/**
	* 设置 响应库房ID
	* @param value 
	*/
	public void setStock_id(Long value) {
		this.stock_id = value;
	}
	
	/**
	* 获取 响应库房ID
	* @return Long
	*/
	public Long getStock_id() {
		return this.stock_id;
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
	* 设置 制单日期
	* @param value 
	*/
	public void setMake_date(Date value) {
		this.make_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getMake_date() {
		return this.make_date;
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
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 计划类型
	* @param value 
	*/
	public void setReq_type(Integer value) {
		this.req_type = value;
	}
	
	/**
	* 获取 计划类型
	* @return Integer
	*/
	public Integer getReq_type() {
		return this.req_type;
	}
	/**
	* 设置 单据汇总
	* @param value 
	*/
	public void setIs_collect(Integer value) {
		this.is_collect = value;
	}
	
	/**
	* 获取 单据汇总
	* @return Integer
	*/
	public Integer getIs_collect() {
		return this.is_collect;
	}
	/**
	* 设置 是否提交
	* @param value 
	*/
	public void setIs_submit(Integer value) {
		this.is_submit = value;
	}
	
	/**
	* 获取 是否提交
	* @return Integer
	*/
	public Integer getIs_submit() {
		return this.is_submit;
	}
	/**
	* 设置 是否退回
	* @param value 
	*/
	public void setIs_return(Integer value) {
		this.is_return = value;
	}
	
	/**
	* 获取 是否退回
	* @return Integer
	*/
	public Integer getIs_return() {
		return this.is_return;
	}
	/**
	* 设置 退回理由
	* @param value 
	*/
	public void setReturn_reason(String value) {
		this.return_reason = value;
	}
	
	/**
	* 获取 退回理由
	* @return String
	*/
	public String getReturn_reason() {
		return this.return_reason;
	}
	/**
	* 设置 其他需求物资
	* @param value 
	*/
	public void setOther_inv(String value) {
		this.other_inv = value;
	}
	
	/**
	* 获取 其他需求物资
	* @return String
	*/
	public String getOther_inv() {
		return this.other_inv;
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