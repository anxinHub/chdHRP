/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.disposal;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051001 资产处置明细
 * @Table:
 * ASS_DISPOSAL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssDisposalDetail implements Serializable {

	
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
	 * 处置ID
	 */
	private Integer ass_dis_id;
	private Integer dis_id ;
	
	/**
	 * 明细ID
	 */
	private Integer ass_detail_id;
	
	/**
	 * 资产卡片
	 */
	private String ass_card_no;
	private String ass_code ;
	private String ass_name ;
	private String ass_spec;
	private String ass_mondl ;
	private String fac_name ;
	
	/**
	 * 处置费用
	 */
	private Double deal_money;
	
	/**
	 * 处置收入
	 */
	private Double deal_oncome;
	
	/**
	 * 备注
	 */
	private String note;
	

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
	* 设置 处置ID
	* @param value 
	*/
	public void setAss_dis_id(Integer value) {
		this.ass_dis_id = value;
	}
	
	/**
	* 获取 处置ID
	* @return Integer
	*/
	public Integer getAss_dis_id() {
		return this.ass_dis_id;
	}
	
	public Integer getDis_id() {
		return dis_id;
	}

	public void setDis_id(Integer dis_id) {
		this.dis_id = dis_id;
	}

	/**
	* 设置 明细ID
	* @param value 
	*/
	public void setAss_detail_id(Integer value) {
		this.ass_detail_id = value;
	}
	
	/**
	* 获取 明细ID
	* @return Integer
	*/
	public Integer getAss_detail_id() {
		return this.ass_detail_id;
	}
	/**
	* 设置 资产卡片
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片
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
	* 设置 处置费用
	* @param value 
	*/
	public void setDeal_money(Double value) {
		this.deal_money = value;
	}
	
	/**
	* 获取 处置费用
	* @return Double
	*/
	public Double getDeal_money() {
		return this.deal_money;
	}
	/**
	* 设置 处置收入
	* @param value 
	*/
	public void setDeal_oncome(Double value) {
		this.deal_oncome = value;
	}
	
	/**
	* 获取 处置收入
	* @return Double
	*/
	public Double getDeal_oncome() {
		return this.deal_oncome;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
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