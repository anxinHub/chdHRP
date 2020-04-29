package com.chd.hrp.ass.entity.tend;

import java.io.Serializable;
import java.util.Date;



public class AssTendMain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private Long bid_id;
	/**
	 * 招标编码
	 */
	private String bid_code;
	/**
	 * 招标方式
	 */
	private String bid_method;
	
	private String bid_methodname;
	/**
	 * 招标人
	 */
	private String bid_tenderee;
	/**
	 * 招标地址
	 */
	private String bid_tenderaddress;
	/**
	 * 招标联系电话
	 */
	private Long bid_phone;
	/**
	 * 开标地点
	 */
	private String bid_openplace;
	/**
	 * 申请金额
	 */
	private double bid_value;
	/**
	 * 采购方式
	 */
	private String bid_purchasemode;
	/**
	 * 采购方式名称
	 */
	private String value_name;
	public String getValue_name() {
		return value_name;
	}

	public void setValue_name(String value_name) {
		this.value_name = value_name;
	}

	/**
	 * 开始时间
	 */
	private Date bid_purstart;
	
	/**
	 * 结束时间
	 */
	private Date bid_purend;
	
	/**
	 * 购买标书地点
	 */
	private String bid_puraddr;
	/**
	 * 保证金
	 */
	private double bid_bond;
	/**
	 * 代理
	 */
	private String bid_agent;
	/**
	 * 代理电话
	 */
	private Long bid_agentphone;
	/**
	 * 代理传真
	 */
	private Long bid_agentfox;
	/**
	 * 代理人
	 */
	private String bid_agenter;
	/**
	 * 公告日期
	 */
	private Date bid_noticedate;
	/**
	 * 公告媒介
	 */
	private String bid_notice;
	/**
	 * 答疑日期
	 */
	private Date bid_answerdate;
	/**
	 * 招标截止时间
	 */
	private Date bid_end;
	/**
	 * 招标地点
	 */
	private String bid_addr;
	/**
	 * 招标条件
	 */
	private String bid_condition;
	/**
	 * 一连网那个编码
	 */
	private String bid_ylwcode;
	/**
	 * 招标文件
	 */
	private String bid_filedr;
	/**
	 * 开标时间
	 */
	private Date bid_start;
	/**
	 * 评标委员会人员
	 */
	private String bid_committee;
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	/**
	 * 供应商编码
	 */
	private String ven_no;
	/**
	 * 定标日期
	 */
	private Date bid_calibratedate;
	/**
	 * 中标标记
	 */
	private String bid_winflag;
	/**
	 * 中标文件
	 */
	private String  bid_winfiledr;
	/**
	 * 制单人
	 */
	private String bid_maker;
	/**
	 * 制单日期
	 */
	private Date bid_makertime;
	/**
	 * 审核日期
	 */
	private Date bid_checkdate;
	/**
	 * 状态
	 */
	private String bid_state;
	
	private String bid_other1;
	
	private String bid_other2;
	
	private String bid_other3;
	
	private String bid_other4;

	public String getBid_methodname() {
		return bid_methodname;
	}

	public void setBid_methodname(String bid_methodname) {
		this.bid_methodname = bid_methodname;
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

	public Long getBid_id() {
		return bid_id;
	}

	public void setBid_id(Long bid_id) {
		this.bid_id = bid_id;
	}

	public String getBid_code() {
		return bid_code;
	}

	public void setBid_code(String bid_code) {
		this.bid_code = bid_code;
	}

	public String getBid_method() {
		return bid_method;
	}

	public void setBid_method(String bid_method) {
		this.bid_method = bid_method;
	}

	public String getBid_tenderee() {
		return bid_tenderee;
	}

	public void setBid_tenderee(String bid_tenderee) {
		this.bid_tenderee = bid_tenderee;
	}

	public String getBid_tenderaddress() {
		return bid_tenderaddress;
	}

	public void setBid_tenderaddress(String bid_tenderaddress) {
		this.bid_tenderaddress = bid_tenderaddress;
	}

	public Long getBid_phone() {
		return bid_phone;
	}

	public void setBid_phone(Long bid_phone) {
		this.bid_phone = bid_phone;
	}

	public String getBid_openplace() {
		return bid_openplace;
	}

	public void setBid_openplace(String bid_openplace) {
		this.bid_openplace = bid_openplace;
	}

	public double getBid_value() {
		return bid_value;
	}

	public void setBid_value(double bid_value) {
		this.bid_value = bid_value;
	}

	public String getBid_purchasemode() {
		return bid_purchasemode;
	}

	public void setBid_purchasemode(String bid_purchasemode) {
		this.bid_purchasemode = bid_purchasemode;
	}

	public Date getBid_purstart() {
		return bid_purstart;
	}

	public void setBid_purstart(Date bid_purstart) {
		this.bid_purstart = bid_purstart;
	}

	public Date getBid_purend() {
		return bid_purend;
	}

	public void setBid_purend(Date bid_purend) {
		this.bid_purend = bid_purend;
	}

	public String getBid_puraddr() {
		return bid_puraddr;
	}

	public void setBid_puraddr(String bid_puraddr) {
		this.bid_puraddr = bid_puraddr;
	}

	public double getBid_bond() {
		return bid_bond;
	}

	public void setBid_bond(double bid_bond) {
		this.bid_bond = bid_bond;
	}

	public String getBid_agent() {
		return bid_agent;
	}

	public void setBid_agent(String bid_agent) {
		this.bid_agent = bid_agent;
	}

	public Long getBid_agentphone() {
		return bid_agentphone;
	}

	public void setBid_agentphone(Long bid_agentphone) {
		this.bid_agentphone = bid_agentphone;
	}

	public Long getBid_agentfox() {
		return bid_agentfox;
	}

	public void setBid_agentfox(Long bid_agentfox) {
		this.bid_agentfox = bid_agentfox;
	}

	public String getBid_agenter() {
		return bid_agenter;
	}

	public void setBid_agenter(String bid_agenter) {
		this.bid_agenter = bid_agenter;
	}

	public Date getBid_noticedate() {
		return bid_noticedate;
	}

	public void setBid_noticedate(Date bid_noticedate) {
		this.bid_noticedate = bid_noticedate;
	}

	public String getBid_notice() {
		return bid_notice;
	}

	public void setBid_notice(String bid_notice) {
		this.bid_notice = bid_notice;
	}

	public Date getBid_answerdate() {
		return bid_answerdate;
	}

	public void setBid_answerdate(Date bid_answerdate) {
		this.bid_answerdate = bid_answerdate;
	}

	public Date getBid_end() {
		return bid_end;
	}

	public void setBid_end(Date bid_end) {
		this.bid_end = bid_end;
	}

	public String getBid_addr() {
		return bid_addr;
	}

	public void setBid_addr(String bid_addr) {
		this.bid_addr = bid_addr;
	}

	public String getBid_condition() {
		return bid_condition;
	}

	public void setBid_condition(String bid_condition) {
		this.bid_condition = bid_condition;
	}

	public String getBid_ylwcode() {
		return bid_ylwcode;
	}

	public void setBid_ylwcode(String bid_ylwcode) {
		this.bid_ylwcode = bid_ylwcode;
	}

	public String getBid_filedr() {
		return bid_filedr;
	}

	public void setBid_filedr(String bid_filedr) {
		this.bid_filedr = bid_filedr;
	}

	public Date getBid_start() {
		return bid_start;
	}

	public void setBid_start(Date bid_start) {
		this.bid_start = bid_start;
	}

	public String getBid_committee() {
		return bid_committee;
	}

	public void setBid_committee(String bid_committee) {
		this.bid_committee = bid_committee;
	}

	public Long getVen_id() {
		return ven_id;
	}

	public void setVen_id(Long ven_id) {
		this.ven_id = ven_id;
	}

	public String getVen_no() {
		return ven_no;
	}

	public void setVen_no(String ven_no) {
		this.ven_no = ven_no;
	}

	public Date getBid_calibratedate() {
		return bid_calibratedate;
	}

	public void setBid_calibratedate(Date bid_calibratedate) {
		this.bid_calibratedate = bid_calibratedate;
	}

	public String getBid_winflag() {
		return bid_winflag;
	}

	public void setBid_winflag(String bid_winflag) {
		this.bid_winflag = bid_winflag;
	}

	public String getBid_winfiledr() {
		return bid_winfiledr;
	}

	public void setBid_winfiledr(String bid_winfiledr) {
		this.bid_winfiledr = bid_winfiledr;
	}

	public String getBid_maker() {
		return bid_maker;
	}

	public void setBid_maker(String bid_maker) {
		this.bid_maker = bid_maker;
	}

	public Date getBid_makertime() {
		return bid_makertime;
	}

	public void setBid_makertime(Date bid_makertime) {
		this.bid_makertime = bid_makertime;
	}

	public Date getBid_checkdate() {
		return bid_checkdate;
	}

	public void setBid_checkdate(Date bid_checkdate) {
		this.bid_checkdate = bid_checkdate;
	}

	public String getBid_state() {
		return bid_state;
	}

	public void setBid_state(String bid_state) {
		this.bid_state = bid_state;
	}

	public String getBid_other1() {
		return bid_other1;
	}

	public void setBid_other1(String bid_other1) {
		this.bid_other1 = bid_other1;
	}

	public String getBid_other2() {
		return bid_other2;
	}

	public void setBid_other2(String bid_other2) {
		this.bid_other2 = bid_other2;
	}

	public String getBid_other3() {
		return bid_other3;
	}

	public void setBid_other3(String bid_other3) {
		this.bid_other3 = bid_other3;
	}

	public String getBid_other4() {
		return bid_other4;
	}

	public void setBid_other4(String bid_other4) {
		this.bid_other4 = bid_other4;
	}

	@Override
	public String toString() {
		return "AssTendMain [group_id=" + group_id + ", hos_id=" + hos_id
				+ ", copy_code=" + copy_code + ", bid_id=" + bid_id
				+ ", bid_code=" + bid_code + ", bid_method=" + bid_method
				+ ", bid_tenderee=" + bid_tenderee + ", bid_tenderaddress="
				+ bid_tenderaddress + ", bid_phone=" + bid_phone
				+ ", bid_openplace=" + bid_openplace + ", bid_value="
				+ bid_value + ", bid_purchasemode=" + bid_purchasemode
				+ ", bid_purstart=" + bid_purstart + ", bid_purend="
				+ bid_purend + ", bid_puraddr=" + bid_puraddr + ", bid_bond="
				+ bid_bond + ", bid_agent=" + bid_agent + ", bid_agentphone="
				+ bid_agentphone + ", bid_agentfox=" + bid_agentfox
				+ ", bid_agenter=" + bid_agenter + ", bid_noticedate="
				+ bid_noticedate + ", bid_notice=" + bid_notice
				+ ", bid_answerdate=" + bid_answerdate + ", bid_end=" + bid_end
				+ ", bid_addr=" + bid_addr + ", bid_condition=" + bid_condition
				+ ", bid_ylwcode=" + bid_ylwcode + ", bid_filedr=" + bid_filedr
				+ ", bid_start=" + bid_start + ", bid_committee="
				+ bid_committee + ", ven_id=" + ven_id + ", ven_no=" + ven_no
				+ ", bid_calibratedate=" + bid_calibratedate + ", bid_winflag="
				+ bid_winflag + ", bid_winfiledr=" + bid_winfiledr
				+ ", bid_maker=" + bid_maker + ", bid_makertime="
				+ bid_makertime + ", bid_checkdate=" + bid_checkdate
				+ ", bid_state=" + bid_state + ", bid_other1=" + bid_other1
				+ ", bid_other2=" + bid_other2 + ", bid_other3=" + bid_other3
				+ ", bid_other4=" + bid_other4 + "]";
	}
	
	
}
