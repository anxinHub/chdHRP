/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccVouch implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 凭证ID
	 */
	private Long vouch_id;
	
	private String vouchId;
	private String vouch_detail_id;
	private String vouch_check_id;
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
	 * 会计期间
	 */
	private String acc_month;
	/**
	 * 会计期间(年月)
	 */
	private String year_month;
	/**
	 * 凭证类型
	 */
	private String vouch_type_code;
	/**
	 * 凭证编号
	 */
	private String vouch_no;
	/**
	 * 下一条凭证编号
	 */
	private String next_vouch_no;
	/**
	 * 凭证日期
	 */
	private Date vouch_date;
	/**
	 * 附件数量
	 */
	private double vouch_att_num;
	/**
	 * 被冲销凭证ID
	 */
	private double vouch_id_cx;
	/**
	 * 制单人ID
	 */
	private double create_user;
	/**
	 * 制单人
	 */
	private String create_name;
	/**
	 * 制单时间
	 */
	private Date create_date;
	/**
	 * 出纳签字人id
	 */
	private double cash_user;
	/**
	 * 出纳签字人
	 */
	private String cash_name;
	/**
	 * 出纳签字时间
	 */
	private Date cashe_date;
	/**
	 * 审核人id
	 */
	private double audit_user;
	/**
	 * 审核人
	 */
	private String audit_name;
	/**
	 * 审核时间
	 */
	private Date audit_date;
	/**
	 * 记账人id
	 */
	private double acc_user;
	/**
	 * 记账人
	 */
	private String acc_name;
	/**
	 * 记账时间
	 */
	private Date acc_date;
	/**
	 * -1草稿，0作废，1新建，2出纳签字，3审核，4记账
	 */
	private Integer state;
	
	private String vouch_state;
	/**
	 * -1草稿，0作废，1新建，2出纳签字，3审核，4记账
	 */
	private String state_name;
	/**
	 * 标注说明
	 */
	private String note;
	/**
	 * 凭证类型名称
	 */
	private String vouch_type_name;
	/**
	 * 凭证类型简称
	 */
	private String vouch_type_short;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 借方
	 */
	private double debit;
	/**
	 * 贷方
	 */
	private double credit;
	/**
	 * 借方外币
	 */
	private double debit_w;
	/**
	 * 贷方外币
	 */
	private double credit_w;
	
	private Integer is_check;
	
	private String subj_code;
	/**
	 * 现金科目名称
	 */
	private String subj_name;
	
	private String subj_no;
	
	/**
	 * 方向
	 */
	private Integer subj_dire;
	/**
	 * 2016-06-03
	 * 对方科目名称
	 */
	private String other_subj_name;
	
	/**
	 * 2016-06-03
	 * 对方金额
	 */
	private double other_money;
	
	/**
	 * 2016-06-03
	 * 余额
	 */
	private double bal;
	/**
	 * 2016-06-04
	 * 凭证年度
	 */
	private String v_year;
	/**
	 * 2016-06-04
	 * 凭证月份
	 */
	private String v_month;
	/**
	 * 2016-06-04
	 * 凭证日期
	 */
	private String v_day;
	
	private Integer unvouch_accounting;
	
	private Integer vouch_accounting;
	
	private String cash_item_name;
	
	private Long cash_item_id;
	
	private String att_path;
	
	/**
	 * *******************************
	 * 凭证统计中使用
	 */
	//凭证总数
	private Integer vouch_tote;
	//正常凭证
	private Integer vouch_normal;
	//作废凭证
	private Integer vouch_cancel;
	//未出纳
	private Integer no_cashier;
	//已出纳
    private Integer cashier;
    
	//未审核
	private Integer no_examine;
	//已审核
    private Integer examine;
    
	//未记账
	private Integer no_telly;
	//已记账
    private Integer telly;
    
    private Integer draft;
    //凭证来源
	private String busi_type_name;
	public Integer getDraft() {
		return draft;
	}
	public void setDraft(Integer draft) {
		this.draft = draft;
	}
	public Integer getVouch_tote() {
		return vouch_tote;
	}
	public void setVouch_tote(Integer vouch_tote) {
		this.vouch_tote = vouch_tote;
	}
	public Integer getVouch_normal() {
		return vouch_normal;
	}
	public void setVouch_normal(Integer vouch_normal) {
		this.vouch_normal = vouch_normal;
	}
	public Integer getVouch_cancel() {
		return vouch_cancel;
	}
	public void setVouch_cancel(Integer vouch_cancel) {
		this.vouch_cancel = vouch_cancel;
	}
	public Integer getNo_cashier() {
		return no_cashier;
	}
	public void setNo_cashier(Integer no_cashier) {
		this.no_cashier = no_cashier;
	}
	public Integer getCashier() {
		return cashier;
	}
	public void setCashier(Integer cashier) {
		this.cashier = cashier;
	}
	public Integer getNo_examine() {
		return no_examine;
	}
	public void setNo_examine(Integer no_examine) {
		this.no_examine = no_examine;
	}
	public Integer getExamine() {
		return examine;
	}
	public void setExamine(Integer examine) {
		this.examine = examine;
	}
	public Integer getNo_telly() {
		return no_telly;
	}
	public void setNo_telly(Integer no_telly) {
		this.no_telly = no_telly;
	}
	public Integer getTelly() {
		return telly;
	}
	public void setTelly(Integer telly) {
		this.telly = telly;
	}
	/**
	 * *******************************
	 * 凭证统计中使用
	 */
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
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
	 * 设置 凭证类型
	 */
	public void setVouch_type_code(String value) {
		this.vouch_type_code = value;
	}
	/**
	 * 获取 凭证类型
	 */	
	public String getVouch_type_code() {
		return this.vouch_type_code;
	}
	/**
	 * 设置 凭证编号
	 */
	public void setVouch_no(String value) {
		this.vouch_no = value;
	}
	/**
	 * 获取 凭证编号
	 */	
	public String getVouch_no() {
		return this.vouch_no;
	}
	/**
	 * 设置 凭证日期
	 */
	public void setVouch_date(Date value) {
		this.vouch_date = value;
	}
	/**
	 * 获取 凭证日期
	 */	
	public Date getVouch_date() {
		return this.vouch_date;
	}
	/**
	 * 设置 附件数量
	 */
	public void setVouch_att_num(double value) {
		this.vouch_att_num = value;
	}
	/**
	 * 获取 附件数量
	 */	
	public double getVouch_att_num() {
		return this.vouch_att_num;
	}
	/**
	 * 设置 被冲销凭证ID
	 */
	public void setVouch_id_cx(double value) {
		this.vouch_id_cx = value;
	}
	/**
	 * 获取 被冲销凭证ID
	 */	
	public double getVouch_id_cx() {
		return this.vouch_id_cx;
	}
	/**
	 * 设置 制单人
	 */
	public void setCreate_user(double value) {
		this.create_user = value;
	}
	/**
	 * 获取 制单人
	 */	
	public double getCreate_user() {
		return this.create_user;
	}
	/**
	 * 设置 制单时间
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	/**
	 * 获取 制单时间
	 */	
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	 * 设置 出纳签字人
	 */
	public void setCash_user(double value) {
		this.cash_user = value;
	}
	/**
	 * 获取 出纳签字人
	 */	
	public double getCash_user() {
		return this.cash_user;
	}
	/**
	 * 设置 出纳签字时间
	 */
	public void setCashe_date(Date value) {
		this.cashe_date = value;
	}
	/**
	 * 获取 出纳签字时间
	 */	
	public Date getCashe_date() {
		return this.cashe_date;
	}
	/**
	 * 设置 审核人
	 */
	public void setAudit_user(double value) {
		this.audit_user = value;
	}
	/**
	 * 获取 审核人
	 */	
	public double getAudit_user() {
		return this.audit_user;
	}
	/**
	 * 设置 审核时间
	 */
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	/**
	 * 获取 审核时间
	 */	
	public Date getAudit_date() {
		return this.audit_date;
	}
	/**
	 * 设置 记账人
	 */
	public void setAcc_user(double value) {
		this.acc_user = value;
	}
	/**
	 * 获取 记账人
	 */	
	public double getAcc_user() {
		return this.acc_user;
	}
	/**
	 * 设置 记账时间
	 */
	public void setAcc_date(Date value) {
		this.acc_date = value;
	}
	/**
	 * 获取 记账时间
	 */	
	public Date getAcc_date() {
		return this.acc_date;
	}
	/**
	 * 设置 -1草稿，0作废，1新建，2出纳签字，3审核，4记账
	 */
	public void setState(Integer value) {
		this.state = value;
	}
	/**
	 * 获取 -1草稿，0作废，1新建，2出纳签字，3审核，4记账
	 */	
	public Integer getState() {
		return this.state;
	}
	/**
	 * 设置 标注说明
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 标注说明
	 */	
	public String getNote() {
		return this.note;
	}
	
	/**
	 * 获取 create_name
	 * @return create_name
	 */
	public String getCreate_name() {
		return create_name;
	}
	
	/**
	 * 设置 create_name
	 * @param create_name 
	 */
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	
	/**
	 * 获取 cash_name
	 * @return cash_name
	 */
	public String getCash_name() {
		return cash_name;
	}
	
	/**
	 * 设置 cash_name
	 * @param cash_name 
	 */
	public void setCash_name(String cash_name) {
		this.cash_name = cash_name;
	}
	
	/**
	 * 获取 audit_name
	 * @return audit_name
	 */
	public String getAudit_name() {
		return audit_name;
	}
	
	/**
	 * 设置 audit_name
	 * @param audit_name 
	 */
	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}
	
	/**
	 * 获取 acc_name
	 * @return acc_name
	 */
	public String getAcc_name() {
		return acc_name;
	}
	
	/**
	 * 设置 acc_name
	 * @param acc_name 
	 */
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	
	/**
	 * 获取 vouch_type_name
	 * @return vouch_type_name
	 */
	public String getVouch_type_name() {
		return vouch_type_name;
	}
	
	/**
	 * 设置 vouch_type_name
	 * @param vouch_type_name 
	 */
	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}
	
	/**
	 * 获取 vouch_type_short
	 * @return vouch_type_short
	 */
	public String getVouch_type_short() {
		return vouch_type_short;
	}
	
	/**
	 * 设置 vouch_type_short
	 * @param vouch_type_short 
	 */
	public void setVouch_type_short(String vouch_type_short) {
		this.vouch_type_short = vouch_type_short;
	}
	
	/**
	 * 获取 summary
	 * @return summary
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * 设置 summary
	 * @param summary 
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * 获取 debit
	 * @return debit
	 */
	public double getDebit() {
		return debit;
	}
	
	/**
	 * 设置 debit
	 * @param debit 
	 */
	public void setDebit(double debit) {
		this.debit = debit;
	}
	
	/**
	 * 获取 credit
	 * @return credit
	 */
	public double getCredit() {
		return credit;
	}
	
	/**
	 * 设置 credit
	 * @param credit 
	 */
	public void setCredit(double credit) {
		this.credit = credit;
	}
	
	/**
	 * 获取 debit_w
	 * @return debit_w
	 */
	public double getDebit_w() {
		return debit_w;
	}
	
	/**
	 * 设置 debit_w
	 * @param debit_w 
	 */
	public void setDebit_w(double debit_w) {
		this.debit_w = debit_w;
	}
	
	/**
	 * 获取 credit_w
	 * @return credit_w
	 */
	public double getCredit_w() {
		return credit_w;
	}
	
	/**
	 * 设置 credit_w
	 * @param credit_w 
	 */
	public void setCredit_w(double credit_w) {
		this.credit_w = credit_w;
	}
	
	/**
	 * 获取 state_name
	 * @return state_name
	 */
	public String getState_name() {
		return state_name;
	}
	
	/**
	 * 设置 state_name
	 * @param state_name 
	 */
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public Integer getIs_check() {
		return is_check;
	}
	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public Integer getSubj_dire() {
		return subj_dire;
	}
	public void setSubj_dire(Integer subj_dire) {
		this.subj_dire = subj_dire;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 *获取 对方科目名称 
	 */
	public String getOther_subj_name() {
		return other_subj_name;
	}
	/**
	 *设置 对方科目名称 
	 */
	public void setOther_subj_name(String other_subj_name) {
		this.other_subj_name = other_subj_name;
	}
	/**
	 *获取 对方金额
	 */
	public double getOther_money() {
		return other_money;
	}
	/**
	 *设置  对方金额
	 */
	public void setOther_money(double other_money) {
		this.other_money = other_money;
	}
	/**
	 *获取 余额 
	 */
	public double getBal() {
		return bal;
	}
	/**
	 *设置 余额 
	 */
	public void setBal(double bal) {
		this.bal = bal;
	}
	/**
	 *获取 凭证年度
	 */
	public String getV_year() {
		return v_year;
	}
	/**
	 *设置 凭证年度
	 */
	public void setV_year(String v_year) {
		this.v_year = v_year;
	}
	/**
	 *获取 凭证月份
	 */
	public String getV_month() {
		return v_month;
	}
	/**
	 *设置 凭证月份
	 */
	public void setV_month(String v_month) {
		this.v_month = v_month;
	}
	/**
	 *获取 凭证日期
	 */
	public String getV_day() {
		return v_day;
	}
	/**
	 *设置 凭证日期
	 */
	public void setV_day(String v_day) {
		this.v_day = v_day;
	}
	public String getYear_month() {
		return year_month;
	}
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	public Integer getUnvouch_accounting() {
		return unvouch_accounting;
	}
	public void setUnvouch_accounting(Integer unvouch_accounting) {
		this.unvouch_accounting = unvouch_accounting;
	}
	public Integer getVouch_accounting() {
		return vouch_accounting;
	}
	public void setVouch_accounting(Integer vouch_accounting) {
		this.vouch_accounting = vouch_accounting;
	}
	public String getCash_item_name() {
		return cash_item_name;
	}
	public void setCash_item_name(String cash_item_name) {
		this.cash_item_name = cash_item_name;
	}
	public Long getCash_item_id() {
		return cash_item_id;
	}
	public void setCash_item_id(Long cash_item_id) {
		this.cash_item_id = cash_item_id;
	}
	public String getNext_vouch_no() {
		return next_vouch_no;
	}
	public void setNext_vouch_no(String next_vouch_no) {
		this.next_vouch_no = next_vouch_no;
	}
	public String getVouchId() {
		return vouchId;
	}
	public void setVouchId(String vouchId) {
		this.vouchId = vouchId;
	}
	public String getVouch_state() {
		return vouch_state;
	}
	public void setVouch_state(String vouch_state) {
		this.vouch_state = vouch_state;
	}
	public String getSubj_no() {
		return subj_no;
	}
	public void setSubj_no(String subj_no) {
		this.subj_no = subj_no;
	}
	public String getVouch_detail_id() {
		return vouch_detail_id;
	}
	public void setVouch_detail_id(String vouch_detail_id) {
		this.vouch_detail_id = vouch_detail_id;
	}
	public String getBusi_type_name() {
		return busi_type_name;
	}
	public void setBusi_type_name(String busi_type_name) {
		this.busi_type_name = busi_type_name;
	}
	public String getVouch_check_id() {
		return vouch_check_id;
	}
	public void setVouch_check_id(String vouch_check_id) {
		this.vouch_check_id = vouch_check_id;
	}
	/**
	 * @return the att_path
	 */
	public String getAtt_path() {
		return att_path;
	}
	/**
	 * @param att_path the att_path to set
	 */
	public void setAtt_path(String att_path) {
		this.att_path = att_path;
	}
	
}