/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证模板辅助核算表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccTemplateCheck implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 模板id
	 */
	private Long template_id;
	/**
	 * 模板明细ID
	 */
	private double template_detail_id;
	/**
	 * 模板辅助核算ID
	 */
	private double template_check_id;
	/**
	 * 科目ID
	 */
	private String subj_id;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 借方金额
	 */
	private String debit;
	/**
	 * 贷方金额
	 */
	private String credit;
	/**
	 * 借方金额外币
	 */
	private String debit_w;
	/**
	 * 贷方金额外币
	 */
	private String crebit_w;
	/**
	 * 合同号
	 */
	private String con_no;
	/**
	 * 票据号
	 */
	private String check_no;
	/**
	 * 单据号
	 */
	private String business_no;
	/**
	 * 发生日期
	 */
	private String occur_date;
	/**
	 * 到期日期
	 */
	private String due_date;
	/**
	 * 结算方式
	 */
	private String pay_type_code;
	/**
	 * 部门核算
	 */
	private String check1_id;
	/**
	 * 部门核算变更号
	 */
	private String check1_no;
	/**
	 * 职工核算
	 */
	private String check2_id;
	/**
	 * 职工核算变更号
	 */
	private String check2_no;
	/**
	 * 项目核算
	 */
	private String check3_id;
	/**
	 * 项目核算变更号
	 */
	private String check3_no;
	/**
	 * 库房核算
	 */
	private String check4_id;
	/**
	 * 库房核算变更号
	 */
	private String check4_no;
	/**
	 * 客户核算
	 */
	private String check5_id;
	/**
	 * 客户核算变更号
	 */
	private String check5_no;
	/**
	 * 供应商核算
	 */
	private String check6_id;
	/**
	 * 供应商核算变更号
	 */
	private String check6_no;
	/**
	 * 资金来源核算
	 */
	private String check7_id;
	
	public double getGroup_id() {
		return group_id;
	}
	public void setGroup_id(double group_id) {
		this.group_id = group_id;
	}
	public double getHos_id() {
		return hos_id;
	}
	public void setHos_id(double hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
	public double getTemplate_detail_id() {
		return template_detail_id;
	}
	public void setTemplate_detail_id(double template_detail_id) {
		this.template_detail_id = template_detail_id;
	}
	public double getTemplate_check_id() {
		return template_check_id;
	}
	public void setTemplate_check_id(double template_check_id) {
		this.template_check_id = template_check_id;
	}
	public String getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(String subj_id) {
		this.subj_id = subj_id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDebit_w() {
		return debit_w;
	}
	public void setDebit_w(String debit_w) {
		this.debit_w = debit_w;
	}
	public String getCrebit_w() {
		return crebit_w;
	}
	public void setCrebit_w(String crebit_w) {
		this.crebit_w = crebit_w;
	}
	public String getCon_no() {
		return con_no;
	}
	public void setCon_no(String con_no) {
		this.con_no = con_no;
	}
	public String getCheck_no() {
		return check_no;
	}
	public void setCheck_no(String check_no) {
		this.check_no = check_no;
	}
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}
	public String getOccur_date() {
		return occur_date;
	}
	public void setOccur_date(String occur_date) {
		this.occur_date = occur_date;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getPay_type_code() {
		return pay_type_code;
	}
	public void setPay_type_code(String pay_type_code) {
		this.pay_type_code = pay_type_code;
	}
	public String getCheck1_id() {
		return check1_id;
	}
	public void setCheck1_id(String check1_id) {
		this.check1_id = check1_id;
	}
	public String getCheck1_no() {
		return check1_no;
	}
	public void setCheck1_no(String check1_no) {
		this.check1_no = check1_no;
	}
	public String getCheck2_id() {
		return check2_id;
	}
	public void setCheck2_id(String check2_id) {
		this.check2_id = check2_id;
	}
	public String getCheck2_no() {
		return check2_no;
	}
	public void setCheck2_no(String check2_no) {
		this.check2_no = check2_no;
	}
	public String getCheck3_id() {
		return check3_id;
	}
	public void setCheck3_id(String check3_id) {
		this.check3_id = check3_id;
	}
	public String getCheck3_no() {
		return check3_no;
	}
	public void setCheck3_no(String check3_no) {
		this.check3_no = check3_no;
	}
	public String getCheck4_id() {
		return check4_id;
	}
	public void setCheck4_id(String check4_id) {
		this.check4_id = check4_id;
	}
	public String getCheck4_no() {
		return check4_no;
	}
	public void setCheck4_no(String check4_no) {
		this.check4_no = check4_no;
	}
	public String getCheck5_id() {
		return check5_id;
	}
	public void setCheck5_id(String check5_id) {
		this.check5_id = check5_id;
	}
	public String getCheck5_no() {
		return check5_no;
	}
	public void setCheck5_no(String check5_no) {
		this.check5_no = check5_no;
	}
	public String getCheck6_id() {
		return check6_id;
	}
	public void setCheck6_id(String check6_id) {
		this.check6_id = check6_id;
	}
	public String getCheck6_no() {
		return check6_no;
	}
	public void setCheck6_no(String check6_no) {
		this.check6_no = check6_no;
	}
	public String getCheck7_id() {
		return check7_id;
	}
	public void setCheck7_id(String check7_id) {
		this.check7_id = check7_id;
	}	

}