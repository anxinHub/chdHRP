/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.ass.entity.base;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 会计期间<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AssYearMonth implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String text;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
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
	 * 年度
	 */
	private String acc_year;
	/**
	 * 会计期间
	 */
	private String acc_month;
	/**
	 * 开始日期
	 */
	private String begin_date;
	/**
	 * 结束日期
	 */
	private String end_date;
	/**
	 * 财务结账标记
	 */
	private Integer acc_flag;
	/**
	 * 财务结账人
	 */
	private String acc_user;
	/**
	 * 财务结账时间
	 */
	private Date acc_date;
	/**
	 * 出纳结账标记
	 */
	private Integer cash_flag;
	/**
	 * 出纳结账人
	 */
	private String cash_user;
	/**
	 * 出纳结账时间
	 */
	private Date cash_date;
	/**
	 * 工资结账标记
	 */
	private Integer wage_flag;
	/**
	 * 工资结账人
	 */
	private String wage_user;
	/**
	 * 工资结账时间
	 */
	private Date wage_date;
	
	private String year_month;
	private String rnum;

	/**
	 * 资产结账标记
	 */
	private Integer ass_flag;
	/**
	 * 资产结账人
	 */
	private String ass_user;
	/**
	 * 资产结账时间
	 */
	private Date ass_date;
	
	
	/**
	 * 无形资产结账标记
	 */
	private Integer inass_flag;
	/**
	 * 无形资产结账人
	 */
	private String inass_user;
	/**
	 * 无形资产结账时间
	 */
	private Date inass_date;
	
	/**
	 * 物流结账标记
	 */
	private Integer mat_flag;
	/**
	 * 物流结账人
	 */
	private String mat_user;
	/**
	 * 物流结账时间
	 */
	private Date mat_date;
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
	 * 设置 年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	
	
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 设置 财务结账标记
	 */
	public void setAcc_flag(Integer value) {
		this.acc_flag = value;
	}
	/**
	 * 获取 财务结账标记
	 */	
	public Integer getAcc_flag() {
		return this.acc_flag;
	}
	/**
	 * 设置 财务结账人
	 */
	public void setAcc_user(String value) {
		this.acc_user = value;
	}
	/**
	 * 获取 财务结账人
	 */	
	public String getAcc_user() {
		return this.acc_user;
	}
	/**
	 * 设置 财务结账时间
	 */
	public void setAcc_date(Date value) {
		this.acc_date = value;
	}
	/**
	 * 获取 财务结账时间
	 */	
	public Date getAcc_date() {
		return this.acc_date;
	}
	/**
	 * 设置 出纳结账标记
	 */
	public void setCash_flag(Integer value) {
		this.cash_flag = value;
	}
	/**
	 * 获取 出纳结账标记
	 */	
	public Integer getCash_flag() {
		return this.cash_flag;
	}
	/**
	 * 设置 出纳结账人
	 */
	public void setCash_user(String value) {
		this.cash_user = value;
	}
	/**
	 * 获取 出纳结账人
	 */	
	public String getCash_user() {
		return this.cash_user;
	}
	/**
	 * 设置 出纳结账时间
	 */
	public void setCash_date(Date value) {
		this.cash_date = value;
	}
	/**
	 * 获取 出纳结账时间
	 */	
	public Date getCash_date() {
		return this.cash_date;
	}
	public Integer getWage_flag() {
		return wage_flag;
	}
	public void setWage_flag(Integer wage_flag) {
		this.wage_flag = wage_flag;
	}
	public String getWage_user() {
		return wage_user;
	}
	public void setWage_user(String wage_user) {
		this.wage_user = wage_user;
	}
	public Date getWage_date() {
		return wage_date;
	}
	public void setWage_date(Date wage_date) {
		this.wage_date = wage_date;
	}
	public String getYear_month() {
		return year_month;
	}
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	
    /**
     * 获取 ass_flag
     * @return ass_flag
     */
    public Integer getAss_flag() {
    	return ass_flag;
    }
	
    /**
     * 设置 ass_flag
     * @param ass_flag 
     */
    public void setAss_flag(Integer ass_flag) {
    	this.ass_flag = ass_flag;
    }
	
    /**
     * 获取 ass_user
     * @return ass_user
     */
    public String getAss_user() {
    	return ass_user;
    }
	
    /**
     * 设置 ass_user
     * @param ass_user 
     */
    public void setAss_user(String ass_user) {
    	this.ass_user = ass_user;
    }
	
    /**
     * 获取 ass_date
     * @return ass_date
     */
    public Date getAss_date() {
    	return ass_date;
    }
	
    /**
     * 设置 ass_date
     * @param ass_date 
     */
    public void setAss_date(Date ass_date) {
    	this.ass_date = ass_date;
    }
	
    /**
     * 获取 mat_flag
     * @return mat_flag
     */
    public Integer getMat_flag() {
    	return mat_flag;
    }
	
    /**
     * 设置 mat_flag
     * @param mat_flag 
     */
    public void setMat_flag(Integer mat_flag) {
    	this.mat_flag = mat_flag;
    }
	
    /**
     * 获取 mat_user
     * @return mat_user
     */
    public String getMat_user() {
    	return mat_user;
    }
	
    /**
     * 设置 mat_user
     * @param mat_user 
     */
    public void setMat_user(String mat_user) {
    	this.mat_user = mat_user;
    }
	
    /**
     * 获取 mat_date
     * @return mat_date
     */
    public Date getMat_date() {
    	return mat_date;
    }
	
    /**
     * 设置 mat_date
     * @param mat_date 
     */
    public void setMat_date(Date mat_date) {
    	this.mat_date = mat_date;
    }
	public Integer getInass_flag() {
		return inass_flag;
	}
	public void setInass_flag(Integer inass_flag) {
		this.inass_flag = inass_flag;
	}
	public String getInass_user() {
		return inass_user;
	}
	public void setInass_user(String inass_user) {
		this.inass_user = inass_user;
	}
	public Date getInass_date() {
		return inass_date;
	}
	public void setInass_date(Date inass_date) {
		this.inass_date = inass_date;
	}
	
}