package com.chd.hrp.htcg.entity.making;
import java.io.Serializable;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcgSchemePeriodConf implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private Integer month_flag;
	private Integer quarter_flag;
	private Integer half_year_flag;
	private Integer year_flag;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public Integer getMonth_flag() {
		return month_flag;
	}
	public Integer getQuarter_flag() {
		return quarter_flag;
	}
	public Integer getHalf_year_flag() {
		return half_year_flag;
	}
	public Integer getYear_flag() {
		return year_flag;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setMonth_flag(Integer month_flag) {
		this.month_flag = month_flag;
	}
	public void setQuarter_flag(Integer quarter_flag) {
		this.quarter_flag = quarter_flag;
	}
	public void setHalf_year_flag(Integer half_year_flag) {
		this.half_year_flag = half_year_flag;
	}
	public void setYear_flag(Integer year_flag) {
		this.year_flag = year_flag;
	}

	
	
}