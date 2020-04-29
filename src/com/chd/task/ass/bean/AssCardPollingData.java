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
@Table("ass_card_polling_mobile")
@PK({ "ass_card_no", "create_date","create_emp","dept_id" })
public class AssCardPollingData {


	@Column("id")
	private int id;
	
	@Column("group_id")
	private int group_id;
	
	@Column("hos_id")
	private int hos_id;
	
	@Column("copy_code")
	@ColDefine(notNull = true, width = 50)
	private String copy_code;
	
	@Column("ass_year")
	@ColDefine(notNull = true, width = 50)
	private String ass_year;
	
	@Column("ass_month")
	@ColDefine(notNull = true, width = 50)
	private String ass_month;

	@Column("ass_card_no")
	@ColDefine(notNull = false, width = 100)
	private String ass_card_no;
	
	@Column("ass_name")
	@ColDefine(notNull = false, width = 100)
	private String ass_name;
	
	@Column("dept_id")
	@ColDefine(notNull = true)
	private int dept_id;
	
	@Column("dept_no")
	@ColDefine(notNull = true)
	private int dept_no;
	
	@Column("ass_nature")
	@ColDefine(notNull = true, width = 50)
	private String ass_nature;
	
	@Column("create_emp")
	@ColDefine(notNull = true)
	private int create_emp;
	
	@Column("create_date")
	@ColDefine(notNull = true)
	private Date create_date;

	@Column("state")
	@ColDefine(notNull = true)
	private int state;
	
	@Column("result")
	@ColDefine(notNull = true)
	private int result;

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
	 * @return the ass_year
	 */
	public String getAss_year() {
		return ass_year;
	}

	/**
	 * @param ass_year the ass_year to set
	 */
	public void setAss_year(String ass_year) {
		this.ass_year = ass_year;
	}

	/**
	 * @return the ass_month
	 */
	public String getAss_month() {
		return ass_month;
	}

	/**
	 * @param ass_month the ass_month to set
	 */
	public void setAss_month(String ass_month) {
		this.ass_month = ass_month;
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
	 * @return the dept_id
	 */
	public int getDept_id() {
		return dept_id;
	}

	/**
	 * @param dept_id the dept_id to set
	 */
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	/**
	 * @return the dept_no
	 */
	public int getDept_no() {
		return dept_no;
	}

	/**
	 * @param dept_no the dept_no to set
	 */
	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}

	/**
	 * @return the ass_nature
	 */
	public String getAss_nature() {
		return ass_nature;
	}

	/**
	 * @param ass_nature the ass_nature to set
	 */
	public void setAss_nature(String ass_nature) {
		this.ass_nature = ass_nature;
	}

	/**
	 * @return the create_emp
	 */
	public int getCreate_emp() {
		return create_emp;
	}

	/**
	 * @param create_emp the create_emp to set
	 */
	public void setCreate_emp(int create_emp) {
		this.create_emp = create_emp;
	}

	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
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
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	
	

}
