/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.measure;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051204 检测计量记录明细
 * @Table:
 * ASS_MEASURE_REC_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMeasureRecDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

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
	 * 计量记录ID
	 */
	private Long rec_id;
	
	private Long detail_id;
	
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	private String ass_code ;
	
	private String ass_name ;
	
	private String ass_spec;
	
	private String ass_mondl;
	
	private String fac_name ;
	
	/**
	 * 计量结果
	 */
	private Integer measure_result;
	
	/**
	 * 计量说明
	 */
	private String measure_memo;
	
	/**
	 * 处理意见
	 */
	private String measure_idea;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String cert_no;
	
	private String dept_name;
	
	private String ass_seq_no;
	
	private Date pre_next_date;
	
	public Date getPre_next_date() {
		return pre_next_date;
	}

	public void setPre_next_date(Date pre_next_date) {
		this.pre_next_date = pre_next_date;
	}

	public String getAss_seq_no() {
		return ass_seq_no;
	}

	public void setAss_seq_no(String ass_seq_no) {
		this.ass_seq_no = ass_seq_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 计量记录ID
	* @param value 
	*/
	public void setRec_id(Long value) {
		this.rec_id = value;
	}
	
	/**
	* 获取 计量记录ID
	* @return Long
	*/
	public Long getRec_id() {
		return this.rec_id;
	}
	
	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	/**
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	
	
	
	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	/**
	* 设置 计量结果
	* @param value 
	*/
	public void setMeasure_result(Integer value) {
		this.measure_result = value;
	}
	
	/**
	* 获取 计量结果
	* @return Integer
	*/
	public Integer getMeasure_result() {
		return this.measure_result;
	}
	/**
	* 设置 计量说明
	* @param value 
	*/
	public void setMeasure_memo(String value) {
		this.measure_memo = value;
	}
	
	/**
	* 获取 计量说明
	* @return String
	*/
	public String getMeasure_memo() {
		return this.measure_memo;
	}
	/**
	* 设置 处理意见
	* @param value 
	*/
	public void setMeasure_idea(String value) {
		this.measure_idea = value;
	}
	
	/**
	* 获取 处理意见
	* @return String
	*/
	public String getMeasure_idea() {
		return this.measure_idea;
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