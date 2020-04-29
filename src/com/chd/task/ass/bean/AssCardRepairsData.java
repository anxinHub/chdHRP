package com.chd.task.ass.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

/**
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2017年11月2日 下午9:54:55
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Table("ass_card_repairs_mobile")
@PK({ "ass_card_no", "app_time","rep_user","rep_dept" })
public class AssCardRepairsData {

	@Column("group_id")
	private int group_id;
	
	@Column("hos_id")
	private int hos_id;
	
	@Column("copy_code")
	private String copy_code;
	
	@Column("id")
	private int id;
	
	@Column("fau_code")
	@ColDefine(notNull = true, width = 50)
	private String fau_code;
	
	@Column("loc_code")
	@ColDefine(notNull = true, width = 100)
	private String loc_code;
	
	@Column("rep_user")
	@ColDefine(notNull = true)
	private String rep_user;
	
	@Column("rep_user_id")
	@ColDefine(notNull = true)
	private int rep_user_id;
	
	@Column("eme_status")
	@ColDefine(notNull = true)
	private int eme_status;
	

	@Column("rep_dept")
	@ColDefine(notNull = true)
	private int rep_dept;

	@Column("phone")
	@ColDefine(notNull = true)
	private String phone;
	
	@Column("fau_note")
	@ColDefine(notNull = true, width = 100)
	private String fau_note;
	
	@Column("app_time")
	@ColDefine(notNull = false, width = 50)
	private Date app_time;

	@Column("ass_card_no")
	@ColDefine(notNull = false, width = 100)
	private String ass_card_no;
	
	@Column("ass_name")
	@ColDefine(notNull = false, width = 100)
	private String ass_name;

	@Column("is_urg")
	@ColDefine(notNull = true)
	private int is_urg;
	
	@Column("state")
	@ColDefine(notNull = true)
	private int state;
	
	@Column("flag")
	@ColDefine(notNull = true)
	private int flag;
	
	@Column("is_any")
	@ColDefine(notNull = true)
	private int is_any;
	
	@Column("is_up")
	private int is_up;

	/**
	 * @return the group_id
	 */
	public int getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return the hos_id
	 */
	public int getHos_id() {
		return hos_id;
	}

	/**
	 * @param hos_id the hos_id to set
	 */
	public void setHos_id(int hos_id) {
		this.hos_id = hos_id;
	}

	/**
	 * @return the copy_code
	 */
	public String getCopy_code() {
		return copy_code;
	}

	/**
	 * @param copy_code the copy_code to set
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fau_code
	 */
	public String getFau_code() {
		return fau_code;
	}

	/**
	 * @param fau_code the fau_code to set
	 */
	public void setFau_code(String fau_code) {
		this.fau_code = fau_code;
	}

	/**
	 * @return the loc_code
	 */
	public String getLoc_code() {
		return loc_code;
	}

	/**
	 * @param loc_code the loc_code to set
	 */
	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
	}

	/**
	 * @return the eme_status
	 */
	public int getEme_status() {
		return eme_status;
	}

	/**
	 * @param eme_status the eme_status to set
	 */
	public void setEme_status(int eme_status) {
		this.eme_status = eme_status;
	}
	

	/**
	 * @return the rep_user
	 */
	public String getRep_user() {
		return rep_user;
	}

	/**
	 * @param rep_user the rep_user to set
	 */
	public void setRep_user(String rep_user) {
		this.rep_user = rep_user;
	}

	/**
	 * @return the rep_dept
	 */
	public int getRep_dept() {
		return rep_dept;
	}

	/**
	 * @param rep_dept the rep_dept to set
	 */
	public void setRep_dept(int rep_dept) {
		this.rep_dept = rep_dept;
	}

	

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fau_note
	 */
	public String getFau_note() {
		return fau_note;
	}

	/**
	 * @param fau_note the fau_note to set
	 */
	public void setFau_note(String fau_note) {
		this.fau_note = fau_note;
	}

	/**
	 * @return the app_time
	 */
	public Date getApp_time() {
		return app_time;
	}

	/**
	 * @param app_time the app_time to set
	 */
	public void setApp_time(Date app_time) {
		this.app_time = app_time;
	}

	/**
	 * @return the ass_card_no
	 */
	public String getAss_card_no() {
		return ass_card_no;
	}

	/**
	 * @param ass_card_no the ass_card_no to set
	 */
	public void setAss_card_no(String ass_card_no) {
		this.ass_card_no = ass_card_no;
	}

	/**
	 * @return the ass_name
	 */
	public String getAss_name() {
		return ass_name;
	}

	/**
	 * @param ass_name the ass_name to set
	 */
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	/**
	 * @return the is_urg
	 */
	public int getIs_urg() {
		return is_urg;
	}

	/**
	 * @param is_urg the is_urg to set
	 */
	public void setIs_urg(int is_urg) {
		this.is_urg = is_urg;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @return the is_any
	 */
	public int getIs_any() {
		return is_any;
	}

	/**
	 * @param is_any the is_any to set
	 */
	public void setIs_any(int is_any) {
		this.is_any = is_any;
	}

	/**
	 * @return the is_up
	 */
	public int getIs_up() {
		return is_up;
	}

	/**
	 * @param is_up the is_up to set
	 */
	public void setIs_up(int is_up) {
		this.is_up = is_up;
	}

	/**
	 * @return the rep_user_id
	 */
	public int getRep_user_id() {
		return rep_user_id;
	}

	/**
	 * @param rep_user_id the rep_user_id to set
	 */
	public void setRep_user_id(int rep_user_id) {
		this.rep_user_id = rep_user_id;
	}

	

}
