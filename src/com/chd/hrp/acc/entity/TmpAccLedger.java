package com.chd.hrp.acc.entity;
/**
 * 新旧制度衔接-新旧余额
 * @author yang
 *
 */
public class TmpAccLedger {

	private String subj_code;
	private String subj_name;
	
	private Double bal_od;
	private Double bal_oc;
	
	private Double this_od;
	private Double this_oc;
	
	private Double sum_od;
	private Double sum_oc;
	
	private Double end_od;
	private Double end_oc;
	
	public TmpAccLedger(){}

	public String getSubj_code() {
		return subj_code;
	}

	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}

	public String getSubj_name() {
		return subj_name;
	}

	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}

	public Double getBal_od() {
		return bal_od;
	}

	public void setBal_od(Double bal_od) {
		this.bal_od = bal_od;
	}

	public Double getBal_oc() {
		return bal_oc;
	}

	public void setBal_oc(Double bal_oc) {
		this.bal_oc = bal_oc;
	}

	public Double getThis_od() {
		return this_od;
	}

	public void setThis_od(Double this_od) {
		this.this_od = this_od;
	}

	public Double getThis_oc() {
		return this_oc;
	}

	public void setThis_oc(Double this_oc) {
		this.this_oc = this_oc;
	}

	public Double getSum_od() {
		return sum_od;
	}

	public void setSum_od(Double sum_od) {
		this.sum_od = sum_od;
	}

	public Double getSum_oc() {
		return sum_oc;
	}

	public void setSum_oc(Double sum_oc) {
		this.sum_oc = sum_oc;
	}

	public Double getEnd_od() {
		return end_od;
	}

	public void setEnd_od(Double end_od) {
		this.end_od = end_od;
	}

	public Double getEnd_oc() {
		return end_oc;
	}

	public void setEnd_oc(Double end_oc) {
		this.end_oc = end_oc;
	}
}
