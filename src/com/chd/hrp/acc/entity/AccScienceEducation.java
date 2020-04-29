package com.chd.hrp.acc.entity;

import java.io.Serializable;
 
public class AccScienceEducation implements Serializable{
	
	/**
	 * 
	 */
	private static final Long serialVersionUID = -8129811048257791784L;
	private Double proj_id;
	private Double proj_no;
	private String proj_code;
	private String proj_name;
	private String emp_name;
	private Double bal_os;
	private Double bal_sr;
	private Double bal_ot;
	private Double bal_od;
	
	private Double match_os;
	private Double match_sr;
	private Double match_ot;
	private Double match_od;
	
	
	public Double getProj_id() {
		return proj_id;
	}
	public void setProj_id(Double proj_id) {
		this.proj_id = proj_id;
	}
	
	public Double getProj_no() {
		return proj_no;
	}
	public void setProj_no(Double proj_no) {
		this.proj_no = proj_no;
	}
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Double getBal_os() {
		return bal_os;
	}
	public void setBal_os(Double bal_os) {
		this.bal_os = bal_os;
	}
	public Double getBal_sr() {
		return bal_sr;
	}
	public void setBal_sr(Double bal_sr) {
		this.bal_sr = bal_sr;
	}
	public Double getBal_ot() {
		return bal_ot;
	}
	public void setBal_ot(Double bal_ot) {
		this.bal_ot = bal_ot;
	}
	public Double getBal_od() {
		return bal_od;
	}
	public void setBal_od(Double bal_od) {
		this.bal_od = bal_od;
	}
	public Double getMatch_os() {
		return match_os;
	}
	public void setMatch_os(Double match_os) {
		this.match_os = match_os;
	}
	public Double getMatch_sr() {
		return match_sr;
	}
	public void setMatch_sr(Double match_sr) {
		this.match_sr = match_sr;
	}
	public Double getMatch_ot() {
		return match_ot;
	}
	public void setMatch_ot(Double match_ot) {
		this.match_ot = match_ot;
	}
	public Double getMatch_od() {
		return match_od;
	}
	public void setMatch_od(Double match_od) {
		this.match_od = match_od;
	}
	
}
