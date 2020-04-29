package com.chd.hrp.ass.entity.guanLiReports;

import java.io.Serializable;
import java.util.Date;

public class AssPropertyMonthMain implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7989328482619033916L;

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
	 * 折旧年度
	 */
	private String depre_year;
	
	/**
	 * 折旧期间
	 */
	private String depre_month;
	
	/**
	 * 分类编码
	 */
	private String ass_naturs ;
	/**
	 * 分类编码
	 */
	private String ass_naturs_name ;
	/**
	 * 分类编码
	 */
	private String ass_type_code ;
	
	/**
	 * 分类名称
	 */
	private String ass_type_name ;
	
	/**
	 * 科室编码
	 */
	private String dept_code ;
	
	/**
	 * 科室名称
	 */
	private String dept_name ;
	
	/**
	 * 期初金额
	 */
	private Double begin_money ;
	
	/**
	 * 本期增加 （借方）
	 */
	private Double add_money ;
	
	/**
	 * 本期减少 （贷方）
	 */
	private Double dec_money ;
	
	/**
	 * 期末金额
	 */
	private Double end_money ;
	
	/**
	 * 余额
	 */
	private Double balance_money ;
	
	/**
	 * 累计折旧  本期期初金额
	 */
	private Double depre_begin_money;
	
	/**
	 * 累计折旧  本期借方
	 */
	private Double depre_add_money;
	
	/**
	 * 累计折旧  本期贷方
	 */
	private Double depre_dec_money ;
	
	/**
	 * 累计折旧  本期余额
	 */
	private Double depre_balance_money ;
	
	 
	/**
	 * 净值   期初
	 */
	private Double remain_begin_money;
	
	/**
	 * 净值   期末
	 */
	private Double remain_end_money ;
	
	 
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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
	* 设置 折旧年度
	* @param value 
	*/
	public void setDepre_year(String value) {
		this.depre_year = value;
	}
	
	/**
	* 获取 折旧年度
	* @return String
	*/
	public String getDepre_year() {
		return this.depre_year;
	}
	/**
	* 设置 折旧期间
	* @param value 
	*/
	public void setDepre_month(String value) {
		this.depre_month = value;
	}
	
	/**
	* 获取 折旧期间
	* @return String
	*/
	public String getDepre_month() {
		return this.depre_month;
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

	public String getAss_type_code() {
		return ass_type_code;
	}

	public void setAss_type_code(String ass_type_code) {
		this.ass_type_code = ass_type_code;
	}

	public String getAss_type_name() {
		return ass_type_name;
	}

	public void setAss_type_name(String ass_type_name) {
		this.ass_type_name = ass_type_name;
	}
	
	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Double getBegin_money() {
		return begin_money;
	}

	public void setBegin_money(Double begin_money) {
		this.begin_money = begin_money;
	}

	public Double getAdd_money() {
		return add_money;
	}

	public void setAdd_money(Double add_money) {
		this.add_money = add_money;
	}

	public Double getDec_money() {
		return dec_money;
	}

	public void setDec_money(Double dec_money) {
		this.dec_money = dec_money;
	}

	public Double getEnd_money() {
		return end_money;
	}

	public void setEnd_money(Double end_money) {
		this.end_money = end_money;
	}

	public Double getBalance_money() {
		return balance_money;
	}

	public void setBalance_money(Double balance_money) {
		this.balance_money = balance_money;
	}

	public Double getDepre_begin_money() {
		return depre_begin_money;
	}

	public void setDepre_begin_money(Double depre_begin_money) {
		this.depre_begin_money = depre_begin_money;
	}

	public Double getDepre_add_money() {
		return depre_add_money;
	}

	public void setDepre_add_money(Double depre_add_money) {
		this.depre_add_money = depre_add_money;
	}

	public Double getDepre_dec_money() {
		return depre_dec_money;
	}

	public void setDepre_dec_money(Double depre_dec_money) {
		this.depre_dec_money = depre_dec_money;
	}

	public Double getDepre_balance_money() {
		return depre_balance_money;
	}

	public void setDepre_balance_money(Double depre_balance_money) {
		this.depre_balance_money = depre_balance_money;
	}

	public Double getRemain_begin_money() {
		return remain_begin_money;
	}

	public void setRemain_begin_money(Double remain_begin_money) {
		this.remain_begin_money = remain_begin_money;
	}

	public Double getRemain_end_money() {
		return remain_end_money;
	}

	public void setRemain_end_money(Double remain_end_money) {
		this.remain_end_money = remain_end_money;
	}

	public String getAss_naturs() {
		return ass_naturs;
	}
 
	public void setAss_naturs(String ass_naturs) {
		this.ass_naturs = ass_naturs;
	}

	public String getAss_naturs_name() {
		return ass_naturs_name;
	}

	public void setAss_naturs_name(String ass_naturs_name) {
		this.ass_naturs_name = ass_naturs_name;
	}
	

	
}
