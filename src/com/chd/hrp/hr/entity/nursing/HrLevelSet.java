/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.nursing;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_LEVEL_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrLevelSet implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Integer group_id;
	
	/**
	 * 
	 */
	private Integer hos_id;
	
	/**
	 * 
	 */
	private String year;
	
	/**
	 * DIC_LEVEL
	 */
	private String level_code;
	private String level_name;
	
	/**
	 * 
	 */
	private Double education;
	
	/**
	 * 
	 */
	private Double book_report;
	
	/**
	 * 
	 */
	private Double case_analy;
	
	/**
	 * 
	 */
	private Double special_case_analy;
	
	/**
	 * 
	 */
	private Double cpr_score;
	
	/**
	 * 
	 */
	private Double peer_score;
	
	/**
	 * 
	 */
	private Double write_score;
	
	/**
	 * 
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Integer value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Integer value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 DIC_LEVEL
	* @param value 
	*/
	public void setLevel_code(String value) {
		this.level_code = value;
	}
	
	/**
	* 获取 DIC_LEVEL
	* @return String
	*/
	public String getLevel_code() {
		return this.level_code;
	}
	
	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	/**
	* 设置 
	* @param value 
	*/
	public void setEducation(Double value) {
		this.education = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getEducation() {
		return this.education;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBook_report(Double value) {
		this.book_report = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getBook_report() {
		return this.book_report;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCase_analy(Double value) {
		this.case_analy = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCase_analy() {
		return this.case_analy;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSpecial_case_analy(Double value) {
		this.special_case_analy = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSpecial_case_analy() {
		return this.special_case_analy;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCpr_score(Double value) {
		this.cpr_score = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCpr_score() {
		return this.cpr_score;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPeer_score(Double value) {
		this.peer_score = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getPeer_score() {
		return this.peer_score;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setWrite_score(Double value) {
		this.write_score = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getWrite_score() {
		return this.write_score;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}