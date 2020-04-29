/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证模板自定义辅助核算表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

public class AccTemplateZCheck implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 模板id
	 */
	private Long template_id;
	/**
	 * 模版辅助核算ID
	 */
	private String template_check_id;
	/**
	 * 模版自定义辅助核算ID
	 */
	private String template_zcheck_id;
	/**
	 * 核算类ID
	 */
	private String check_type_id;
	/**
	 * 核算项ID
	 */
	private String check_item_id;
	
	public double getGroup_id() {
		return group_id;
	}
	public void setGroup_id(double group_id) {
		this.group_id = group_id;
	}
	public double getHos_id() {
		return hos_id;
	}
	public void setHos_id(double hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
	public String getTemplate_check_id() {
		return template_check_id;
	}
	public void setTemplate_check_id(String template_check_id) {
		this.template_check_id = template_check_id;
	}
	public String getTemplate_zcheck_id() {
		return template_zcheck_id;
	}
	public void setTemplate_zcheck_id(String template_zcheck_id) {
		this.template_zcheck_id = template_zcheck_id;
	}
	public String getCheck_type_id() {
		return check_type_id;
	}
	public void setCheck_type_id(String check_type_id) {
		this.check_type_id = check_type_id;
	}
	public String getCheck_item_id() {
		return check_item_id;
	}
	public void setCheck_item_id(String check_item_id) {
		this.check_item_id = check_item_id;
	}
}