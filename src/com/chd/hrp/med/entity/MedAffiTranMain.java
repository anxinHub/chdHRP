/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MED_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedAffiTranMain implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private int group_id;	
	private int hos_id;	
	private String copy_code;	
	private int tran_id;	
	private String tran_no;	
	private String year;	
	private String month;	
	private int bus_type;	
	private int tran_method;	
	private int tran_type;
	
	private int out_hos_id;
	private int out_hos_no;
	
	private String out_copy_code;
	private String out_hos_code;
	private String out_hos_name;
	
	private int in_hos_id;
	private int in_hos_no;
	
	private String in_copy_code;
	private String in_hos_code;
	private String in_hos_name;
	
	private int out_store_id;
	private int out_store_no;
	private String out_store_code;
	private String out_store_name;
	
	private int in_store_id;
	private int in_store_no;
	private String in_store_code;
	private String in_store_name;
	
	private String brief;
	
	private Date tran_date;
	
	private int maker;
	
	private int checker;
	
	private Date check_date;
	
	private int confirmer;
	
	private Date confirm_date;
	
	private int state;

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

	public int getTran_id() {
		return tran_id;
	}

	public void setTran_id(int tran_id) {
		this.tran_id = tran_id;
	}

	public String getTran_no() {
		return tran_no;
	}

	public void setTran_no(String tran_no) {
		this.tran_no = tran_no;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getBus_type() {
		return bus_type;
	}

	public void setBus_type(int bus_type) {
		this.bus_type = bus_type;
	}

	public int getTran_method() {
		return tran_method;
	}

	public void setTran_method(int tran_method) {
		this.tran_method = tran_method;
	}

	public int getTran_type() {
		return tran_type;
	}

	public void setTran_type(int tran_type) {
		this.tran_type = tran_type;
	}

	public int getOut_hos_id() {
		return out_hos_id;
	}

	public void setOut_hos_id(int out_hos_id) {
		this.out_hos_id = out_hos_id;
	}

	public int getOut_hos_no() {
		return out_hos_no;
	}

	public void setOut_hos_no(int out_hos_no) {
		this.out_hos_no = out_hos_no;
	}

	public String getOut_copy_code() {
		return out_copy_code;
	}

	public void setOut_copy_code(String out_copy_code) {
		this.out_copy_code = out_copy_code;
	}

	public String getOut_hos_code() {
		return out_hos_code;
	}

	public void setOut_hos_code(String out_hos_code) {
		this.out_hos_code = out_hos_code;
	}

	public String getOut_hos_name() {
		return out_hos_name;
	}

	public void setOut_hos_name(String out_hos_name) {
		this.out_hos_name = out_hos_name;
	}

	public int getIn_hos_id() {
		return in_hos_id;
	}

	public void setIn_hos_id(int in_hos_id) {
		this.in_hos_id = in_hos_id;
	}

	public int getIn_hos_no() {
		return in_hos_no;
	}

	public void setIn_hos_no(int in_hos_no) {
		this.in_hos_no = in_hos_no;
	}

	public String getIn_copy_code() {
		return in_copy_code;
	}

	public void setIn_copy_code(String in_copy_code) {
		this.in_copy_code = in_copy_code;
	}

	public String getIn_hos_code() {
		return in_hos_code;
	}

	public void setIn_hos_code(String in_hos_code) {
		this.in_hos_code = in_hos_code;
	}

	public String getIn_hos_name() {
		return in_hos_name;
	}

	public void setIn_hos_name(String in_hos_name) {
		this.in_hos_name = in_hos_name;
	}

	public int getOut_store_id() {
		return out_store_id;
	}

	public void setOut_store_id(int out_store_id) {
		this.out_store_id = out_store_id;
	}

	public int getOut_store_no() {
		return out_store_no;
	}

	public void setOut_store_no(int out_store_no) {
		this.out_store_no = out_store_no;
	}

	public String getOut_store_code() {
		return out_store_code;
	}

	public void setOut_store_code(String out_store_code) {
		this.out_store_code = out_store_code;
	}

	public String getOut_store_name() {
		return out_store_name;
	}

	public void setOut_store_name(String out_store_name) {
		this.out_store_name = out_store_name;
	}

	public int getIn_store_id() {
		return in_store_id;
	}

	public void setIn_store_id(int in_store_id) {
		this.in_store_id = in_store_id;
	}

	public int getIn_store_no() {
		return in_store_no;
	}

	public void setIn_store_no(int in_store_no) {
		this.in_store_no = in_store_no;
	}

	public String getIn_store_code() {
		return in_store_code;
	}

	public void setIn_store_code(String in_store_code) {
		this.in_store_code = in_store_code;
	}

	public String getIn_store_name() {
		return in_store_name;
	}

	public void setIn_store_name(String in_store_name) {
		this.in_store_name = in_store_name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Date getTran_date() {
		return tran_date;
	}

	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}

	public int getMaker() {
		return maker;
	}

	public void setMaker(int maker) {
		this.maker = maker;
	}

	public int getChecker() {
		return checker;
	}

	public void setChecker(int checker) {
		this.checker = checker;
	}

	public Date getCheck_date() {
		return check_date;
	}

	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}

	public int getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(int confirmer) {
		this.confirmer = confirmer;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	

	
}