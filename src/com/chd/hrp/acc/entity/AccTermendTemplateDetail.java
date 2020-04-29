/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 期末处理凭证模板明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public class AccTermendTemplateDetail implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 模板明细表ID
	 */
	private Long template_detail_id;
	/**
	 * /**
	 * 模板主表ID
	 */
	private Long template_id;
	/**
	 * 集团ID
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
	 * 科目ID
	 */
	private Long subj_id;
	/**
	 * 科目编码
	 */
	private String subj_code;
	/**
	 * 科目名称
	 */
	private String subj_name;
	/**
	 * 科目全称
	 */
	private String subj_name_all;
	
	/**
	 * 空值：不区分，debit：借方科目对应分录，debit2：第二个借方科目对应分录，credit：贷方科目对应分录
	 */
	private String detail_type;
	
	public Long getTemplate_detail_id() {
		return template_detail_id;
	}
	public void setTemplate_detail_id(Long template_detail_id) {
		this.template_detail_id = template_detail_id;
	}
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
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
	public Long getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}
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
	public String getSubj_name_all() {
		return subj_name_all;
	}
	public void setSubj_name_all(String subj_name_all) {
		this.subj_name_all = subj_name_all;
	}
	public String getDetail_type() {
		return detail_type;
	}
	public void setDetail_type(String detail_type) {
		this.detail_type = detail_type;
	}
}
