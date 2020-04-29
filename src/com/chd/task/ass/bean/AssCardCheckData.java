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
@Table("ass_card_check_mobile")
@PK({ "dept_id", "bar_code" })
public class AssCardCheckData {

	@Column("group_id")
	private int group_id;
	
	@Column("hos_id")
	private int hos_id;
	
	@Column("copy_code")
	@ColDefine(notNull = false, width = 20)
	private String copy_code;
	
	@Column("id")
	private int id;

	@Column("check_name")
	@ColDefine(notNull = true, width = 50)
	private String check_name;

	@Column("dept_id")
	@ColDefine(notNull = true)
	private int dept_id;

	@Column("ass_id")
	private int ass_id;

	@Column("ass_card_no")
	@ColDefine(notNull = false, width = 100)
	private String ass_card_no;

	@Column("bar_code")
	@ColDefine(notNull = true, width = 100)
	private String bar_code;

	@Column("check_date")
	@ColDefine(notNull = false, width = 50)
	private Date check_date;
 
	@Column("check_state")
	private int check_state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheck_name() {
		return check_name;
	}

	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}

	public String getAss_card_no() {
		return ass_card_no;
	}

	public void setAss_card_no(String ass_card_no) {
		this.ass_card_no = ass_card_no;
	}

	public Date getCheck_date() {
		return check_date;
	}

	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}

	public int getCheck_state() {
		return check_state;
	}

	public void setCheck_state(int check_state) {
		this.check_state = check_state;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	
    public String getBar_code() {
    	return bar_code;
    }

	
    public void setBar_code(String bar_code) {
    	this.bar_code = bar_code;
    }

	public int getAss_id() {
		return ass_id;
	}

	public void setAss_id(int ass_id) {
		this.ass_id = ass_id;
	}

	
    public int getGroup_id() {
    	return group_id;
    }

	
    public void setGroup_id(int group_id) {
    	this.group_id = group_id;
    }

	
    public int getHos_id() {
    	return hos_id;
    }

	
    public void setHos_id(int hos_id) {
    	this.hos_id = hos_id;
    }

	
    public String getCopy_code() {
    	return copy_code;
    }

    public void setCopy_code(String copy_code) {
    	this.copy_code = copy_code;
    }

}
