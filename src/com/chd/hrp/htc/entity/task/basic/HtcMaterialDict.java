package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
import java.util.*;

/**
 * 2015-3-17 author:alfred
 */

public class HtcMaterialDict implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String mate_code;
	private String mate_name;
	private String mate_type_code;
	private String mate_type_name;	
	private String mate_mode;
	private String meas_code;
	private String meas_name;
	private double price;
	private long fac_no;
	private long fac_id;
	private String fac_code;
	private String fac_name;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
	
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getMate_code() {
		return mate_code;
	}
	public String getMate_name() {
		return mate_name;
	}
	public String getMate_type_code() {
		return mate_type_code;
	}
	public String getMate_type_name() {
		return mate_type_name;
	}
	public String getMate_mode() {
		return mate_mode;
	}
	public String getMeas_code() {
		return meas_code;
	}
	
	public String getMeas_name() {
		return meas_name;
	}
	public void setMeas_name(String meas_name) {
		this.meas_name = meas_name;
	}
	public double getPrice() {
		return price;
	}
	public long getFac_no() {
		return fac_no;
	}
	public long getFac_id() {
		return fac_id;
	}
	public String getFac_code() {
		return fac_code;
	}
	public String getFac_name() {
		return fac_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setMate_code(String mate_code) {
		this.mate_code = mate_code;
	}
	public void setMate_name(String mate_name) {
		this.mate_name = mate_name;
	}
	public void setMate_type_code(String mate_type_code) {
		this.mate_type_code = mate_type_code;
	}
	public void setMate_type_name(String mate_type_name) {
		this.mate_type_name = mate_type_name;
	}
	public void setMate_mode(String mate_mode) {
		this.mate_mode = mate_mode;
	}
	public void setMeas_code(String meas_code) {
		this.meas_code = meas_code;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setFac_no(long fac_no) {
		this.fac_no = fac_no;
	}
	public void setFac_id(long fac_id) {
		this.fac_id = fac_id;
	}
	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
}