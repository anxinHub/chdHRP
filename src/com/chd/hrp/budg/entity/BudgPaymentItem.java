/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgPaymentItem implements Serializable {

	
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
	 * 支出项目ID
	 */
	private Long payment_item_id;
	
	/**
	 * 项目编码
	 */
	private String payment_item_code;
	
	/**
	 * 项目名称
	 */
	private String payment_item_name;
	
	/**
	 * 项目全称
	 */
	private String item_name_all;
	
	/**
	 * 上级项目
	 */
	private Long super_code;
	
	/**
	 * 项目级次
	 */
	private Integer item_level;
	
	/**
	 * 是否末级
	 */
	private Integer is_last;
	
	/**
	 * 支出项目性质
	 */
	private String payment_item_nature;
	
	/**
	 * 是否管理费
	 */
	private Integer is_manage;
	
	/**
	 * 控制方式
	 */
	private String control_way;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 管理支出对应会计科目
	 */
	private Long acc_subj_manage;
	
	/**
	 * 医疗支出对应会计科目
	 */
	private Long acc_subj_medical;
	
	/**
	 * 教学支出对应会计科目
	 */
	private Long acc_subj_education;
	
	/**
	 * 科研支出对应会计科目
	 */
	private Long acc_subj_scientific;
	
	/**
	 * 财政支出对应会计科目
	 */
	private Long acc_subj_financial;
	

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
	* 设置 支出项目ID
	* @param value 
	*/
	public void setPayment_item_id(Long value) {
		this.payment_item_id = value;
	}
	
	/**
	* 获取 支出项目ID
	* @return Long
	*/
	public Long getPayment_item_id() {
		return this.payment_item_id;
	}
	/**
	* 设置 项目编码
	* @param value 
	*/
	public void setPayment_item_code(String value) {
		this.payment_item_code = value;
	}
	
	/**
	* 获取 项目编码
	* @return String
	*/
	public String getPayment_item_code() {
		return this.payment_item_code;
	}
	/**
	* 设置 项目名称
	* @param value 
	*/
	public void setPayment_item_name(String value) {
		this.payment_item_name = value;
	}
	
	/**
	* 获取 项目名称
	* @return String
	*/
	public String getPayment_item_name() {
		return this.payment_item_name;
	}
	/**
	* 设置 项目全称
	* @param value 
	*/
	public void setItem_name_all(String value) {
		this.item_name_all = value;
	}
	
	/**
	* 获取 项目全称
	* @return String
	*/
	public String getItem_name_all() {
		return this.item_name_all;
	}
	/**
	* 设置 上级项目
	* @param value 
	*/
	public void setSuper_code(Long value) {
		this.super_code = value;
	}
	
	/**
	* 获取 上级项目
	* @return Long
	*/
	public Long getSuper_code() {
		return this.super_code;
	}
	/**
	* 设置 项目级次
	* @param value 
	*/
	public void setItem_level(Integer value) {
		this.item_level = value;
	}
	
	/**
	* 获取 项目级次
	* @return Integer
	*/
	public Integer getItem_level() {
		return this.item_level;
	}
	/**
	* 设置 是否末级
	* @param value 
	*/
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	
	/**
	* 获取 是否末级
	* @return Integer
	*/
	public Integer getIs_last() {
		return this.is_last;
	}
	/**
	* 设置 支出项目性质
	* @param value 
	*/
	public void setPayment_item_nature(String value) {
		this.payment_item_nature = value;
	}
	
	/**
	* 获取 支出项目性质
	* @return String
	*/
	public String getPayment_item_nature() {
		return this.payment_item_nature;
	}
	/**
	* 设置 是否管理费
	* @param value 
	*/
	public void setIs_manage(Integer value) {
		this.is_manage = value;
	}
	
	/**
	* 获取 是否管理费
	* @return Integer
	*/
	public Integer getIs_manage() {
		return this.is_manage;
	}
	/**
	* 设置 控制方式
	* @param value 
	*/
	public void setControl_way(String value) {
		this.control_way = value;
	}
	
	/**
	* 获取 控制方式
	* @return String
	*/
	public String getControl_way() {
		return this.control_way;
	}
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
	}
	/**
	* 设置 管理支出对应会计科目
	* @param value 
	*/
	public void setAcc_subj_manage(Long value) {
		this.acc_subj_manage = value;
	}
	
	/**
	* 获取 管理支出对应会计科目
	* @return Long
	*/
	public Long getAcc_subj_manage() {
		return this.acc_subj_manage;
	}
	/**
	* 设置 医疗支出对应会计科目
	* @param value 
	*/
	public void setAcc_subj_medical(Long value) {
		this.acc_subj_medical = value;
	}
	
	/**
	* 获取 医疗支出对应会计科目
	* @return Long
	*/
	public Long getAcc_subj_medical() {
		return this.acc_subj_medical;
	}
	/**
	* 设置 教学支出对应会计科目
	* @param value 
	*/
	public void setAcc_subj_education(Long value) {
		this.acc_subj_education = value;
	}
	
	/**
	* 获取 教学支出对应会计科目
	* @return Long
	*/
	public Long getAcc_subj_education() {
		return this.acc_subj_education;
	}
	/**
	* 设置 科研支出对应会计科目
	* @param value 
	*/
	public void setAcc_subj_scientific(Long value) {
		this.acc_subj_scientific = value;
	}
	
	/**
	* 获取 科研支出对应会计科目
	* @return Long
	*/
	public Long getAcc_subj_scientific() {
		return this.acc_subj_scientific;
	}
	/**
	* 设置 财政支出对应会计科目
	* @param value 
	*/
	public void setAcc_subj_financial(Long value) {
		this.acc_subj_financial = value;
	}
	
	/**
	* 获取 财政支出对应会计科目
	* @return Long
	*/
	public Long getAcc_subj_financial() {
		return this.acc_subj_financial;
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