/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable; 
import java.util.Date;
 
/**
* @Title. @Description. 
* 凭证辅助核算表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccVouchCheck implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 辅助核算ID
	 */
	private Long vouch_check_id;
	/**
	 * 凭证ID
	 */
	private Long vouch_id;
	/**
	 * 凭证明细ID
	 */
	private Long vouch_detail_id;
	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	private Long bank_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 科目编码
	 */
	private String subj_code;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 借方金额
	 */
	private double debit;
	/**
	 * 贷方金额
	 */
	private double credit;
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
	 * 凭证：需要同步凭证日期，往来初始：录入发生日期
	 */
	private Date occur_date;
	/**
	 * 到期日期
	 */
	private Date due_date;
	/**
	 * 结算方式
	 */
	private String pay_type_code;
	
	private String vouch_date;
	
	private Date create_date;
	
	

	/**
	 * 借方金额外币
	 */
	private double debit_w;
	/**
	 * 贷方金额外币
	 */
	private double credit_w;
	/**
	 * 部门核算
	 */
	private long check1_id;
	/**
	 * 部门核算变更号
	 */
	private long check1_no;
	/**
	 * 职工核算
	 */
	private long check2_id;
	/**
	 * 职工核算变更号
	 */
	private long check2_no;
	/**
	 * 项目核算
	 */
	private long check3_id;
	/**
	 * 项目核算变更号
	 */
	private long check3_no;
	/**
	 * 库房核算
	 */
	private long check4_id;
	/**
	 * 库房核算变更号
	 */
	private long check4_no;
	/**
	 * 客户核算
	 */
	private long check5_id;
	/**
	 * 客户核算变更号
	 */
	private long check5_no;
	/**
	 * 供应商核算
	 */
	private long check6_id;
	/**
	 * 供应商核算变更号
	 */
	private long check6_no;
	/**
	 * 资金来源核算
	 */
	private long check7_id;
	
	
	
	private String check1;
	/**
	 * 职工核算
	 */
	private String check2;
	/**
	 * 项目核算
	 */
	private String check3;
	/**
	 * 库房核算
	 */
	private String check4;
	/**
	 * 客户核算
	 */
	private String check5;
	/**
	 * 供应商核算
	 */
	private String check6;
	/**
	 * 资金来源核算
	 */
	private String check7;
	
	private Integer is_veri;
	
	private double price;
	
	/**
	 * 科目名称
	 */
	private String subj_name;
	
	/**
	 * 结算方式名称
	 */
	private String pay_name;
	/**
	 * 2016-06-06
	 * 对方科目名称
	 */
	private String other_subj_name;
	
	/**
	 * 2016-06-06
	 * 对方金额
	 */
	private double other_money;
	
	/**
	 * 2016-06-06
	 * 余额
	 */
	private long bal;
	/**
	 * 2016-06-06
	 * 凭证年度
	 */
	private String v_year;
	/**
	 * 2016-06-06
	 * 凭证月份
	 */
	private String v_month;
	/**
	 * 2016-06-06
	 * 凭证日期
	 */
	private String v_day;
	
	/**
	 * 2016-06-06
	 * 是否对账
	 */
	private Integer is_check;
	
	
	private String t_is_check;
	
	
	

	/**
	 * 2016-06-06
	 * 凭证编号
	 */
	private String vouch_no;

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
	
	
	private String is_checks;
	
	private Long is_auto;
	
	private Long veri_id;
	private double money;
	
	private Long check_id;
	private Double yCheck_money;//已对账金额
	private Double wCheck_money;//未对账金额
	private Long veri_check_id;//对账序列
	private Date veri_date;//对账时间
	private Long create_user;//对账人
	private String create_name;//对账人名
	
	

	/**
	 * 是否往来初始账
	 */
	private Integer is_init;
	/**
	 * 结转下年的时候引用旧辅助ID
	 */
	private double old_check_id;
	public Long getVouch_check_id() {
		return vouch_check_id;
	}
	public void setVouch_check_id(Long vouch_check_id) {
		this.vouch_check_id = vouch_check_id;
	}
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}
	public Long getVouch_detail_id() {
		return vouch_detail_id;
	}
	public void setVouch_detail_id(Long vouch_detail_id) {
		this.vouch_detail_id = vouch_detail_id;
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
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
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
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
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
	public Date getOccur_date() {
		return occur_date;
	}
	public void setOccur_date(Date occur_date) {
		this.occur_date = occur_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public String getPay_type_code() {
		return pay_type_code;
	}
	public void setPay_type_code(String pay_type_code) {
		this.pay_type_code = pay_type_code;
	}
	public String getVouch_date() {
		return vouch_date;
	}
	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}
	public double getDebit_w() {
		return debit_w;
	}
	public void setDebit_w(double debit_w) {
		this.debit_w = debit_w;
	}
	public double getCredit_w() {
		return credit_w;
	}
	public void setCredit_w(double credit_w) {
		this.credit_w = credit_w;
	}
	public long getCheck1_id() {
		return check1_id;
	}
	public void setCheck1_id(long check1_id) {
		this.check1_id = check1_id;
	}
	public long getCheck1_no() {
		return check1_no;
	}
	public void setCheck1_no(long check1_no) {
		this.check1_no = check1_no;
	}
	public long getCheck2_id() {
		return check2_id;
	}
	public void setCheck2_id(long check2_id) {
		this.check2_id = check2_id;
	}
	public long getCheck2_no() {
		return check2_no;
	}
	public void setCheck2_no(long check2_no) {
		this.check2_no = check2_no;
	}
	public long getCheck3_id() {
		return check3_id;
	}
	public void setCheck3_id(long check3_id) {
		this.check3_id = check3_id;
	}
	public long getCheck3_no() {
		return check3_no;
	}
	public void setCheck3_no(long check3_no) {
		this.check3_no = check3_no;
	}
	public long getCheck4_id() {
		return check4_id;
	}
	public void setCheck4_id(long check4_id) {
		this.check4_id = check4_id;
	}
	public long getCheck4_no() {
		return check4_no;
	}
	public void setCheck4_no(long check4_no) {
		this.check4_no = check4_no;
	}
	public long getCheck5_id() {
		return check5_id;
	}
	public void setCheck5_id(long check5_id) {
		this.check5_id = check5_id;
	}
	public long getCheck5_no() {
		return check5_no;
	}
	public void setCheck5_no(long check5_no) {
		this.check5_no = check5_no;
	}
	public long getCheck6_id() {
		return check6_id;
	}
	public void setCheck6_id(long check6_id) {
		this.check6_id = check6_id;
	}
	public long getCheck6_no() {
		return check6_no;
	}
	public void setCheck6_no(long check6_no) {
		this.check6_no = check6_no;
	}
	public long getCheck7_id() {
		return check7_id;
	}
	public void setCheck7_id(long check7_id) {
		this.check7_id = check7_id;
	}
	public String getCheck1() {
		return check1;
	}
	public void setCheck1(String check1) {
		this.check1 = check1;
	}
	public String getCheck2() {
		return check2;
	}
	public void setCheck2(String check2) {
		this.check2 = check2;
	}
	public String getCheck3() {
		return check3;
	}
	public void setCheck3(String check3) {
		this.check3 = check3;
	}
	public String getCheck4() {
		return check4;
	}
	public void setCheck4(String check4) {
		this.check4 = check4;
	}
	public String getCheck5() {
		return check5;
	}
	public void setCheck5(String check5) {
		this.check5 = check5;
	}
	public String getCheck6() {
		return check6;
	}
	public void setCheck6(String check6) {
		this.check6 = check6;
	}
	public String getCheck7() {
		return check7;
	}
	public void setCheck7(String check7) {
		this.check7 = check7;
	}
	public Integer getIs_veri() {
		return is_veri;
	}
	public void setIs_veri(Integer is_veri) {
		this.is_veri = is_veri;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public String getPay_name() {
		return pay_name;
	}
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	public String getOther_subj_name() {
		return other_subj_name;
	}
	public void setOther_subj_name(String other_subj_name) {
		this.other_subj_name = other_subj_name;
	}
	public double getOther_money() {
		return other_money;
	}
	public void setOther_money(double other_money) {
		this.other_money = other_money;
	}
	public long getBal() {
		return bal;
	}
	public void setBal(long bal) {
		this.bal = bal;
	}
	public String getV_year() {
		return v_year;
	}
	public void setV_year(String v_year) {
		this.v_year = v_year;
	}
	public String getV_month() {
		return v_month;
	}
	public void setV_month(String v_month) {
		this.v_month = v_month;
	}
	public String getV_day() {
		return v_day;
	}
	public void setV_day(String v_day) {
		this.v_day = v_day;
	}
	public Integer getIs_check() {
		return is_check;
	}
	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}
	public String getT_is_check() {
		return t_is_check;
	}
	public void setT_is_check(String t_is_check) {
		this.t_is_check = t_is_check;
	}
	public String getVouch_no() {
		return vouch_no;
	}
	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
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
	}
	public String getCus_name() {
		return cus_name;
	}
	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
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
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
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
	public String getSup_code() {
		return sup_code;
	}
	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
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
	public String getIs_checks() {
		return is_checks;
	}
	public void setIs_checks(String is_checks) {
		this.is_checks = is_checks;
	}
	public Long getIs_auto() {
		return is_auto;
	}
	public void setIs_auto(Long is_auto) {
		this.is_auto = is_auto;
	}
	public Long getVeri_id() {
		return veri_id;
	}
	public void setVeri_id(Long veri_id) {
		this.veri_id = veri_id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Long getCheck_id() {
		return check_id;
	}
	public void setCheck_id(Long check_id) {
		this.check_id = check_id;
	}
	public Double getyCheck_money() {
		return yCheck_money;
	}
	public void setyCheck_money(Double yCheck_money) {
		this.yCheck_money = yCheck_money;
	}
	public Double getwCheck_money() {
		return wCheck_money;
	}
	public void setwCheck_money(Double wCheck_money) {
		this.wCheck_money = wCheck_money;
	}
	public Long getVeri_check_id() {
		return veri_check_id;
	}
	public void setVeri_check_id(Long veri_check_id) {
		this.veri_check_id = veri_check_id;
	}
	public Date getVeri_date() {
		return veri_date;
	}
	public void setVeri_date(Date veri_date) {
		this.veri_date = veri_date;
	}
	public Long getCreate_user() {
		return create_user;
	}
	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}
	public String getCreate_name() {
		return create_name;
	}
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	public Integer getIs_init() {
		return is_init;
	}
	public void setIs_init(Integer is_init) {
		this.is_init = is_init;
	}
	public double getOld_check_id() {
		return old_check_id;
	}
	public void setOld_check_id(double old_check_id) {
		this.old_check_id = old_check_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	
	
}