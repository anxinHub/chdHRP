package com.chd.hrp.budg.entity;

public class BudgSysSetControlSet {
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
	 * 预算年度
	 */
	private String budg_year;

	

	/**
	 * 方案编码
	 */
	private String plan_code;
	private String plan_name;
	
	/**
	 * 模块编码
	 */
	private String mod_code;
	private String mod_name;
	/**
	 * 环节编码
	 */
	private String link_code;
	private String link_name;
	/**
	 * 预算表编码
	 */
	private String tab_code;
	private String tab_name;
	/**
	 * 控制模式
	 */
	private String cont_m;
	private String m_name;
	/**
	 * 控制层次
	 */
	private String cont_l;
	private String l_name;
	/**
	 * 控制期间
	 */
	private String cont_p;
	private String p_name;
	/**
	 * 控制方式
	 */
	private String cont_w;
	private String w_name;
	/**
	 * 占用性质
	 */
	private Integer use_nature;
	private String nature_name;
	/**
	 *关联环节
	 */
	private String re_link;
	private String re_name;
	/**
	 * 控制节点
	 */
	private String cont_note;
	private String note_name;
	/**
	 * 占用状态
	 */
	private String use_state;
	private String use_name;
	/**
	 * 是否启用
	 */
    private Integer is_start;
    private String start_name;
	
	
	
	
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

	public String getBudg_year() {
		return budg_year;
	}

	public void setBudg_year(String budg_year) {
		this.budg_year = budg_year;
	}

	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	public String getLink_code() {
		return link_code;
	}

	public void setLink_code(String link_code) {
		this.link_code = link_code;
	}

	public String getTab_code() {
		return tab_code;
	}

	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}

	public String getCont_m() {
		return cont_m;
	}

	public void setCont_m(String cont_m) {
		this.cont_m = cont_m;
	}

	public String getCont_l() {
		return cont_l;
	}

	public void setCont_l(String cont_l) {
		this.cont_l = cont_l;
	}

	public String getCont_p() {
		return cont_p;
	}

	public void setCont_p(String cont_p) {
		this.cont_p = cont_p;
	}

	public String getCont_w() {
		return cont_w;
	}

	public void setCont_w(String cont_w) {
		this.cont_w = cont_w;
	}

	public Integer getUse_nature() {
		return use_nature;
	}

	public void setUse_nature(Integer use_nature) {
		this.use_nature = use_nature;
	}

	public String getRe_link() {
		return re_link;
	}

	public void setRe_link(String re_link) {
		this.re_link = re_link;
	}

	public String getCont_note() {
		return cont_note;
	}

	public void setCont_note(String cont_note) {
		this.cont_note = cont_note;
	}

	public String getUse_state() {
		return use_state;
	}

	public void setUse_state(String use_state) {
		this.use_state = use_state;
	}

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}

	public String getLink_name() {
		return link_name;
	}

	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}

	public String getTab_name() {
		return tab_name;
	}

	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getW_name() {
		return w_name;
	}

	public void setW_name(String w_name) {
		this.w_name = w_name;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}

	public String getRe_name() {
		return re_name;
	}

	public void setRe_name(String re_name) {
		this.re_name = re_name;
	}

	public String getNote_name() {
		return note_name;
	}

	public void setNote_name(String note_name) {
		this.note_name = note_name;
	}

	public String getUse_name() {
		return use_name;
	}

	public void setUse_name(String use_name) {
		this.use_name = use_name;
	}

	public Integer getIs_start() {
		return is_start;
	}

	public void setIs_start(Integer is_start) {
		this.is_start = is_start;
	}

	public String getStart_name() {
		return start_name;
	}

	public void setStart_name(String start_name) {
		this.start_name = start_name;
	}
	
	
	
}
