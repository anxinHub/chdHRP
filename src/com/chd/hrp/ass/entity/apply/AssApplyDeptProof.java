package com.chd.hrp.ass.entity.apply;

import java.io.Serializable;
/**
 * 采购明细论证主表
 * @author cyw
 *
 */
public class AssApplyDeptProof  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 集体ID
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
	 * id
	 */
	private Long proof_id;

	/**
	 * 回本年限
	 */
	private Integer bcost_year;
	/**
	 * 年平均成本
	 */
	private double avg_cost;
	/**
	 * 年平均列润
	 */
	private double avg_profit;
	/**
	 * 投资效益率
	 */
	private double benefit_rate;
	/**
	 * 操作人员
	 */
	private String create_user;
	/**
	 * 使用场地
	 */
	private String use_place;
	/**
	 * 临床应用分析
	 */
	private String apply_analyze;
	/**
	 * 调研分析
	 */
	private String investigate_analyze;
	/**
	 * 说明
	 */
	private String describ;
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Long getProof_id() {
		return proof_id;
	}
	public void setProof_id(Long proof_id) {
		this.proof_id = proof_id;
	}

	public Integer getBcost_year() {
		return bcost_year;
	}
	public void setBcost_year(Integer bcost_year) {
		this.bcost_year = bcost_year;
	}
	public double getAvg_cost() {
		return avg_cost;
	}
	public void setAvg_cost(double avg_cost) {
		this.avg_cost = avg_cost;
	}
	public double getAvg_profit() {
		return avg_profit;
	}
	public void setAvg_profit(double avg_profit) {
		this.avg_profit = avg_profit;
	}
	public double getBenefit_rate() {
		return benefit_rate;
	}
	public void setBenefit_rate(double benefit_rate) {
		this.benefit_rate = benefit_rate;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getUse_place() {
		return use_place;
	}
	public void setUse_place(String use_place) {
		this.use_place = use_place;
	}
	public String getApply_analyze() {
		return apply_analyze;
	}
	public void setApply_analyze(String apply_analyze) {
		this.apply_analyze = apply_analyze;
	}
	public String getInvestigate_analyze() {
		return investigate_analyze;
	}
	public void setInvestigate_analyze(String investigate_analyze) {
		this.investigate_analyze = investigate_analyze;
	}
	public String getDescrib() {
		return describ;
	}
	public void setDescrib(String describ) {
		this.describ = describ;
	}
	@Override
	public String toString() {
		return "AssApplyDeptProof [group_id=" + group_id + ", hos_id=" + hos_id
				+ ", copy_code=" + copy_code + ", proof_id=" + proof_id
				 + ", bcost_year="
				+ bcost_year + ", avg_cost=" + avg_cost + ", avg_profit="
				+ avg_profit + ", benefit_rate=" + benefit_rate
				+ ", create_user=" + create_user + ", use_place=" + use_place
				+ ", apply_analyze=" + apply_analyze + ", investigate_analyze="
				+ investigate_analyze + ", describ=" + describ + "]";
	}
	
	
	
}
