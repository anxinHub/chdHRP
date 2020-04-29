package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccItialIncome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ID
	 */
	private Long sub_id;
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
	 * 发生日期
	 */
	private Date occur_date;
	/**
	 * 会计年度
	 */ 
	private String acc_year;
	/**
	 * 科目ID
	 * */
	private Long subj_id;
	/**
	 *支出功能分类
	 * */
	private String fun_code;
	/**
	 *支出经济分类
	 * */
	private String eco_code;
	/**
	 *摘要
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
	 * 凭证编号
	 */
	private  String vouch_no;
	/**
	 * 凭证ID
	 */
	private  Long vouch_id;
	/**
	 *辅助核算ID
	 */
	private  String vouch_check_id;
	/**
	 *创建人
	 */
	private  Long create_user;
	/**
	 *创建时间
	 */
	private  Date create_date;
	/**
	 *是否初始
	 */
	private  Long is_init;
	/**
	 *是否凭证导入
	 */
	private  Long is_import;
	/**
	 *科目编码
	 */
	private String subj_code;
	/**
	 *科目名称
	 */
	private String subj_name;
	/**
	 *支出功能分类名称
	 */
	private String fun_name;
	/**
	 *支出经济分类名称
	 */
	private String eco_name;
	/**
	 *制单人名称
	 */
	private String create_username;
	/**
	 *制单人id
	 */
	private Long user_id;
	
	public Long getSub_id() {
		return sub_id;
	}
	public void setSub_id(Long sub_id) {
		this.sub_id = sub_id;
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
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Date getOccur_date() {
		return occur_date;
	}
	public void setOccur_date(Date occur_date) {
		this.occur_date = occur_date;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public Long getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}
	public String getFun_code() {
		return fun_code;
	}
	public void setFun_code(String fun_code) {
		this.fun_code = fun_code;
	}
	public String getEco_code() {
		return eco_code;
	}
	public void setEco_code(String eco_code) {
		this.eco_code = eco_code;
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
	public String getVouch_no() {
		return vouch_no;
	}
	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}
	public String getVouch_check_id() {
		return vouch_check_id;
	}
	public void setVouch_check_id(String vouch_check_id) {
		this.vouch_check_id = vouch_check_id;
	}
	public Long getCreate_user() {
		return create_user;
	}
	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Long getIs_init() {
		return is_init;
	}
	public void setIs_init(Long is_init) {
		this.is_init = is_init;
	}
	public Long getIs_import() {
		return is_import;
	}
	public void setIs_import(Long is_import) {
		this.is_import = is_import;
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
	public String getFun_name() {
		return fun_name;
	}
	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}
	public String getEco_name() {
		return eco_name;
	}
	public void setEco_name(String eco_name) {
		this.eco_name = eco_name;
	}
	public String getCreate_username() {
		return create_username;
	}
	public void setCreate_username(String create_username) {
		this.create_username = create_username;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	

}
