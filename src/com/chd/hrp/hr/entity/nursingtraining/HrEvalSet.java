package com.chd.hrp.hr.entity.nursingtraining;

import java.io.Serializable;

/**
 * 
 * @ClassName: HrEvalSet 
 * @Description: 护理考核标准设定
 * @author zn 
 * @date 2018年1月19日 下午3:20:59 
 * 
 *
 */
public class HrEvalSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long group_id;
	private Long hos_id;
	private String year;// 年度
	private String eval_code;// 考核编码
	private String eval_name;// 考核名称
	private Double eval_goal;// 考核合格分
	private String note;

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEval_code() {
		return eval_code;
	}

	public void setEval_code(String eval_code) {
		this.eval_code = eval_code;
	}

	public String getEval_name() {
		return eval_name;
	}

	public void setEval_name(String eval_name) {
		this.eval_name = eval_name;
	}

	public Double getEval_goal() {
		return eval_goal;
	}

	public void setEval_goal(Double eval_goal) {
		this.eval_goal = eval_goal;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
