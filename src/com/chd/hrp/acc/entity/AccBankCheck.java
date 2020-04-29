/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @Title. @Description.
* 银行对账单<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccBankCheck implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 银行对账单ID
	 */
	private Long bank_id;
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
	 * 余额
	 */
	private double bal;
	/**
	 * 币种编码
	 */
	private String cur_code;
	/**
	 * 借方金额外币
	 */
	private double debit_w;
	/**
	 * 贷方金额外币
	 */
	private double credit_w;
	/**
	 * 余额外币
	 */
	private double bal_w;
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
	private Date occur_date;
	/**
	 * 结算方式
	 */
	private String pay_type_code;
	
	private String pay_type_name;
	/**
	 * 是否对账
	 */
	private Integer is_check;
	/**
	 * 对账人
	 */
	private Long check_user;
	/**
	 * 对账日期
	 */
	private Date check_date;
	/**
	 * 是否初始
	 */
	private Integer is_init;
	/**
	 * 是否外部导入
	 */
	private Integer is_import;
	
	/**
	 * 科目ID
	 */
	private Long subj_id;
	
	/**
	 * 科目名称
	 */
	private String subj_name;
	
	/**
	 * 支付方式名称
	 */
	private String pay_name;
	
	private Long veri_check_id ;//对账ID
	private double yCheck_money;//已对账金额
	private double wCheck_money;//未对账金额
	private Date veri_date;//对账时间
	private Long create_user;//对账人
	private String create_name;//对账人名
	private String error_type;
	
	
	
	public String getPay_type_name() {
		return pay_type_name;
	}
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
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
	public Long getVeri_check_id() {
		return veri_check_id;
	}
	public void setVeri_check_id(Long veri_check_id) {
		this.veri_check_id = veri_check_id;
	}
	public double getyCheck_money() {
		return yCheck_money;
	}
	public void setyCheck_money(double yCheck_money) {
		this.yCheck_money = yCheck_money;
	}
	public double getwCheck_money() {
		return wCheck_money;
	}
	public void setwCheck_money(double wCheck_money) {
		this.wCheck_money = wCheck_money;
	}
	
	private String is_checks;
	
	private Long is_auto;
	
	private Long veri_id;
	
	public Long getVeri_id() {
		return veri_id;
	}
	public void setVeri_id(Long veri_id) {
		this.veri_id = veri_id;
	}
	
	public Long getIs_auto() {
		return is_auto;
	}
	public void setIs_auto(Long is_auto) {
		this.is_auto = is_auto;
	}
	

	public String getIs_checks() {
		return is_checks;
	}
	public void setIs_checks(String is_checks) {
		this.is_checks = is_checks;
	}
	/**
	 * 设置 银行对账单ID
	 */
	public void setBank_id(Long value) {
		this.bank_id = value;
	}
	/**
	 * 获取 银行对账单ID
	 */	
	public Long getBank_id() {
		return this.bank_id;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
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
	 * 设置 摘要
	 */
	public void setSummary(String value) {
		this.summary = value;
	}
	/**
	 * 获取 摘要
	 */	
	public String getSummary() {
		return this.summary;
	}
	/**
	 * 设置 借方金额
	 */
	public void setDebit(double value) {
		this.debit = value;
	}
	/**
	 * 获取 借方金额
	 */	
	public double getDebit() {
		return this.debit;
	}
	/**
	 * 设置 贷方金额
	 */
	public void setCredit(double value) {
		this.credit = value;
	}
	/**
	 * 获取 贷方金额
	 */	
	public double getCredit() {
		return this.credit;
	}
	/**
	 * 设置 余额
	 */
	public void setBal(double value) {
		this.bal = value;
	}
	/**
	 * 获取 余额
	 */	
	public double getBal() {
		return this.bal;
	}
	/**
	 * 设置 币种编码
	 */
	public void setCur_code(String value) {
		this.cur_code = value;
	}
	/**
	 * 获取 币种编码
	 */	
	public String getCur_code() {
		return this.cur_code;
	}
	/**
	 * 设置 借方金额外币
	 */
	public void setDebit_w(double value) {
		this.debit_w = value;
	}
	/**
	 * 获取 借方金额外币
	 */	
	public double getDebit_w() {
		return this.debit_w;
	}
	/**
	 * 设置 贷方金额外币
	 */
	public void setCredit_w(double value) {
		this.credit_w = value;
	}
	/**
	 * 获取 贷方金额外币
	 */	
	public double getCredit_w() {
		return this.credit_w;
	}
	/**
	 * 设置 余额外币
	 */
	public void setBal_w(double value) {
		this.bal_w = value;
	}
	/**
	 * 获取 余额外币
	 */	
	public double getBal_w() {
		return this.bal_w;
	}
	/**
	 * 设置 票据号
	 */
	public void setCheck_no(String value) {
		this.check_no = value;
	}
	/**
	 * 获取 票据号
	 */	
	public String getCheck_no() {
		return this.check_no;
	}
	/**
	 * 设置 单据号
	 */
	public void setBusiness_no(String value) {
		this.business_no = value;
	}
	/**
	 * 获取 单据号
	 */	
	public String getBusiness_no() {
		return this.business_no;
	}
	/**
	 * 设置 发生日期
	 */
	public void setOccur_date(Date value) {
		this.occur_date = value;
	}
	/**
	 * 获取 发生日期
	 */	
	public Date getOccur_date() {
		return this.occur_date;
	}
	/**
	 * 设置 结算方式
	 */
	public void setPay_type_code(String value) {
		this.pay_type_code = value;
	}
	/**
	 * 获取 结算方式
	 */	
	public String getPay_type_code() {
		return this.pay_type_code;
	}
	/**
	 * 设置 是否对账
	 */
	public void setIs_check(Integer value) {
		this.is_check = value;
	}
	/**
	 * 获取 是否对账
	 */	
	public Integer getIs_check() {
		return this.is_check;
	}
	/**
	 * 设置 对账人
	 */
	public void setCheck_user(Long value) {
		this.check_user = value;
	}
	/**
	 * 获取 对账人
	 */	
	public Long getCheck_user() {
		return this.check_user;
	}
	/**
	 * 设置 对账日期
	 */
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	/**
	 * 获取 对账日期
	 */	
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	 * 设置 是否初始
	 */
	public void setIs_init(Integer value) {
		this.is_init = value;
	}
	/**
	 * 获取 是否初始
	 */	
	public Integer getIs_init() {
		return this.is_init;
	}
	/**
	 * 设置 是否外部导入
	 */
	public void setIs_import(Integer value) {
		this.is_import = value;
	}
	/**
	 * 获取 是否外部导入
	 */	
	public Integer getIs_import() {
		return this.is_import;
	}
	public Long getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
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
	
	
}