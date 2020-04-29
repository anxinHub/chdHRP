package com.chd.hrp.htcg.entity.making;

import java.io.Serializable;
import java.util.*;
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

public class HtcgClpStepRule implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private String clp_step_code;
	private String clp_step_name;
	private String beg_date;
	private String beg_date_name;
	private String end_date;
	private String end_date_name;
	private String place;
	private String place_name;
	
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
	public String getClp_step_code() {
		return clp_step_code;
	}
	public String getClp_step_name() {
		return clp_step_name;
	}
	public String getBeg_date() {
		return beg_date;
	}
	public String getBeg_date_name() {
		return beg_date_name;
	}
	public String getEnd_date() {
		return end_date;
	}
	public String getEnd_date_name() {
		return end_date_name;
	}
	public String getPlace() {
		return place;
	}
	public String getPlace_name() {
		return place_name;
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
	public void setClp_step_code(String clp_step_code) {
		this.clp_step_code = clp_step_code;
	}
	public void setClp_step_name(String clp_step_name) {
		this.clp_step_name = clp_step_name;
	}
	public void setBeg_date(String beg_date) {
		this.beg_date = beg_date;
	}
	public void setBeg_date_name(String beg_date_name) {
		this.beg_date_name = beg_date_name;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public void setEnd_date_name(String end_date_name) {
		this.end_date_name = end_date_name;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
		
}