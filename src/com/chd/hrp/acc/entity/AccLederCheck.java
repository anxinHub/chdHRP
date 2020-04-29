/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 财务辅助账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccLederCheck implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private double check_id;
	
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
	 * 会计期间
	 */
	private String acc_month;
	/**
	 * 科目编码
	 */
	private String subj_code;
	/**
	 * 期初余额
	 */
	private double bal_os;
	/**
	 * 本期借方金额
	 */
	private double this_od;
	/**
	 * 本期贷方金额
	 */
	private double this_oc;
	/**
	 * 本年累计借方金额
	 */
	private double sum_od;
	/**
	 * 本年累计贷方金额
	 */
	private double sum_oc;
	/**
	 * 期末余额
	 */
	private double end_os;
	/**
	 * 期初余额外币
	 */
	private double bal_os_w;
	/**
	 * 本期借方金额外币
	 */
	private double this_od_w;
	/**
	 * 本期贷方金额外币
	 */
	private double this_oc_w;
	/**
	 * 本年累计借方金额外币
	 */
	private double sum_od_w;
	/**
	 * 本年累计贷方金额外币
	 */
	private double sum_oc_w;
	/**
	 * 期末余额外币
	 */
	private double end_os_w;
	/**
	 * 部门核算
	 */
	private Long check1_id;
	/**
	 * 部门核算变更号
	 */
	private Long check1_no;
	/**
	 * 职工核算
	 */
	private Long check2_id;
	/**
	 * 职工核算变更号
	 */
	private Long check2_no;
	/**
	 * 项目核算
	 */
	private Long check3_id;
	/**
	 * 项目核算变更号
	 */
	private Long check3_no;
	/**
	 * 库房核算
	 */
	private Long check4_id;
	/**
	 * 库房核算变更号
	 */
	private Long check4_no;
	/**
	 * 客户核算
	 */
	private Long check5_id;
	/**
	 * 客户核算变更号
	 */
	private Long check5_no;
	/**
	 * 供应商核算
	 */
	private Long check6_id;
	/**
	 * 供应商核算变更号
	 */
	private Long check6_no;
	/**
	 * 资金来源核算
	 */
	private Long check7_id;
	
	private double balance;
	
	private String subj_dire;
	
	private String cus_code;
	private String cus_name;

	private String dept_code;
	private String dept_name;
	
	private String emp_code;
	private String emp_name;
	
	private String proj_code;
	private String proj_name;
	
	private String store_code;
	private String store_name;
	
	private String sup_code;
	private String sup_name;
	
	private String source_code;
	private String source_name;
	
	private String check_item_code;
	private String check_item_name;
	
	
	private Long vouch_id;
	
	/*group_id,hos_id,copy_code,acc_year,acc_month,acc_day,subj_code,subj_name,cus_code,cus_name,vouch_no,summary,
	state,con_no,business_no
 occur_date，due_date,debit,credit,subj_dire,end_os,vouch_date,ver_date,bal_amt,ybal_amt,wbal_amt*/
	private String acc_day;
	private String check_code;
	private String check_name;
	private String vouch_no;
	private String summary;
	private String state;
	private String con_no;
	private String business_no;
	private String occur_date;
	private String due_date;
	private String debit;
	private String credit;
	private String vouch_date;
	private String ver_date;
	private String ver_person;
	private String bal_amt;
	private String ybal_amt;
	private String wbal_amt;
	private Long veri_check_id;
	private Long is_check;
	
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}
	
	public Long getIs_check() {
		return is_check;
	}
	public void setIs_check(Long is_check) {
		this.is_check = is_check;
	}
	public Long getVeri_check_id() {
		return veri_check_id;
	}
	public void setVeri_check_id(Long veri_check_id) {
		this.veri_check_id = veri_check_id;
	}
	public double getCheck_id() {
		return check_id;
	}
	public void setCheck_id(double check_id) {
		this.check_id = check_id;
	}
	public String getVer_person() {
		return ver_person;
	}
	public void setVer_person(String ver_person) {
		this.ver_person = ver_person;
	}
	public String getAcc_day() {
		return acc_day;
	}
	public void setAcc_day(String acc_day) {
		this.acc_day = acc_day;
	}
	public String getCheck_code() {
		return check_code;
	}
	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}
	public String getCheck_name() {
		return check_name;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public String getVouch_no() {
		return vouch_no;
	}
	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCon_no() {
		return con_no;
	}
	public void setCon_no(String con_no) {
		this.con_no = con_no;
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
	public String getVouch_date() {
		return vouch_date;
	}
	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}
	public String getVer_date() {
		return ver_date;
	}
	public void setVer_date(String ver_date) {
		this.ver_date = ver_date;
	}
	public String getBal_amt() {
		return bal_amt;
	}
	public void setBal_amt(String bal_amt) {
		this.bal_amt = bal_amt;
	}
	public String getYbal_amt() {
		return ybal_amt;
	}
	public void setYbal_amt(String ybal_amt) {
		this.ybal_amt = ybal_amt;
	}
	public String getWbal_amt() {
		return wbal_amt;
	}
	public void setWbal_amt(String wbal_amt) {
		this.wbal_amt = wbal_amt;
	}
	public String getCheck_item_code() {
		return check_item_code;
	}
	public void setCheck_item_code(String check_item_code) {
		this.check_item_code = check_item_code;
	}
	public String getCheck_item_name() {
		return check_item_name;
	}
	public void setCheck_item_name(String check_item_name) {
		this.check_item_name = check_item_name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getSubj_dire() {
		return subj_dire;
	}
	public void setSubj_dire(String subj_dire) {
		this.subj_dire = subj_dire;
	}
	public String getCus_code() {
		return cus_code;
	}
	public void setCus_code(String cus_code) {
		this.cus_code = cus_code;
		this.check_item_code = cus_code;
	}
	public String getCus_name() {
		return cus_name;
	}
	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
		this.check_item_name = cus_name;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
		this.check_item_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
		this.check_item_name = dept_name;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
		this.check_item_code = emp_code;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
		this.check_item_name = emp_name;
	}
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
		this.check_item_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
		this.check_item_name = proj_name;
	}
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
		this.check_item_code = store_code;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
		this.check_item_name = store_name;
	}
	public String getSup_code() {
		return sup_code;
	}
	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
		this.check_item_code = sup_code;
	}
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
		this.check_item_name = sup_name;
	}
	public String getSource_code() {
		return source_code;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
		this.check_item_code = source_code;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
		this.check_item_name = source_name;
	}
	
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(double value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public double getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(double value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public double getHos_id() {
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
	 * 设置 会计年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 会计年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 会计期间
	 */
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	/**
	 * 获取 会计期间
	 */	
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	 * 设置 科目编码
	 */
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	/**
	 * 获取 科目编码
	 */	
	public String getSubj_code() {
		return this.subj_code;
	}
	/**
	 * 设置 期初余额
	 */
	public void setBal_os(double value) {
		this.bal_os = value;
	}
	/**
	 * 获取 期初余额
	 */	
	public double getBal_os() {
		return this.bal_os;
	}
	/**
	 * 设置 本期借方金额
	 */
	public void setThis_od(double value) {
		this.this_od = value;
	}
	/**
	 * 获取 本期借方金额
	 */	
	public double getThis_od() {
		return this.this_od;
	}
	/**
	 * 设置 本期贷方金额
	 */
	public void setThis_oc(double value) {
		this.this_oc = value;
	}
	/**
	 * 获取 本期贷方金额
	 */	
	public double getThis_oc() {
		return this.this_oc;
	}
	/**
	 * 设置 本年累计借方金额
	 */
	public void setSum_od(double value) {
		this.sum_od = value;
	}
	/**
	 * 获取 本年累计借方金额
	 */	
	public double getSum_od() {
		return this.sum_od;
	}
	/**
	 * 设置 本年累计贷方金额
	 */
	public void setSum_oc(double value) {
		this.sum_oc = value;
	}
	/**
	 * 获取 本年累计贷方金额
	 */	
	public double getSum_oc() {
		return this.sum_oc;
	}
	/**
	 * 设置 期末余额
	 */
	public void setEnd_os(double value) {
		this.end_os = value;
	}
	/**
	 * 获取 期末余额
	 */	
	public double getEnd_os() {
		return this.end_os;
	}
	/**
	 * 设置 期初余额外币
	 */
	public void setBal_os_w(double value) {
		this.bal_os_w = value;
	}
	/**
	 * 获取 期初余额外币
	 */	
	public double getBal_os_w() {
		return this.bal_os_w;
	}
	/**
	 * 设置 本期借方金额外币
	 */
	public void setThis_od_w(double value) {
		this.this_od_w = value;
	}
	/**
	 * 获取 本期借方金额外币
	 */	
	public double getThis_od_w() {
		return this.this_od_w;
	}
	/**
	 * 设置 本期贷方金额外币
	 */
	public void setThis_oc_w(double value) {
		this.this_oc_w = value;
	}
	/**
	 * 获取 本期贷方金额外币
	 */	
	public double getThis_oc_w() {
		return this.this_oc_w;
	}
	/**
	 * 设置 本年累计借方金额外币
	 */
	public void setSum_od_w(double value) {
		this.sum_od_w = value;
	}
	/**
	 * 获取 本年累计借方金额外币
	 */	
	public double getSum_od_w() {
		return this.sum_od_w;
	}
	/**
	 * 设置 本年累计贷方金额外币
	 */
	public void setSum_oc_w(double value) {
		this.sum_oc_w = value;
	}
	/**
	 * 获取 本年累计贷方金额外币
	 */	
	public double getSum_oc_w() {
		return this.sum_oc_w;
	}
	/**
	 * 设置 期末余额外币
	 */
	public void setEnd_os_w(double value) {
		this.end_os_w = value;
	}
	/**
	 * 获取 期末余额外币
	 */	
	public double getEnd_os_w() {
		return this.end_os_w;
	}
	public Long getCheck1_id() {
		return check1_id;
	}
	public void setCheck1_id(Long check1_id) {
		this.check1_id = check1_id;
	}
	public Long getCheck1_no() {
		return check1_no;
	}
	public void setCheck1_no(Long check1_no) {
		this.check1_no = check1_no;
	}
	public Long getCheck2_id() {
		return check2_id;
	}
	public void setCheck2_id(Long check2_id) {
		this.check2_id = check2_id;
	}
	public Long getCheck2_no() {
		return check2_no;
	}
	public void setCheck2_no(Long check2_no) {
		this.check2_no = check2_no;
	}
	public Long getCheck3_id() {
		return check3_id;
	}
	public void setCheck3_id(Long check3_id) {
		this.check3_id = check3_id;
	}
	public Long getCheck3_no() {
		return check3_no;
	}
	public void setCheck3_no(Long check3_no) {
		this.check3_no = check3_no;
	}
	public Long getCheck4_id() {
		return check4_id;
	}
	public void setCheck4_id(Long check4_id) {
		this.check4_id = check4_id;
	}
	public Long getCheck4_no() {
		return check4_no;
	}
	public void setCheck4_no(Long check4_no) {
		this.check4_no = check4_no;
	}
	public Long getCheck5_id() {
		return check5_id;
	}
	public void setCheck5_id(Long check5_id) {
		this.check5_id = check5_id;
	}
	public Long getCheck5_no() {
		return check5_no;
	}
	public void setCheck5_no(Long check5_no) {
		this.check5_no = check5_no;
	}
	public Long getCheck6_id() {
		return check6_id;
	}
	public void setCheck6_id(Long check6_id) {
		this.check6_id = check6_id;
	}
	public Long getCheck6_no() {
		return check6_no;
	}
	public void setCheck6_no(Long check6_no) {
		this.check6_no = check6_no;
	}
	public Long getCheck7_id() {
		return check7_id;
	}
	public void setCheck7_id(Long check7_id) {
		this.check7_id = check7_id;
	}
	
}