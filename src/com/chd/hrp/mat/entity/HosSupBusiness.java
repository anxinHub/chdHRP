/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 供应商经营许可证信息
 * @Table:
 * HOS_SUP_BANK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
  


public class HosSupBusiness implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 经营企业ID
	 */
	private Long business_id;
	
	/**
	 * 经营企业名称
	 */
	private String business_name;
	
	/**
	 * 经营许可证号
	 */
	private String business_no;
	
	/**
	 * 发证日期
	 */
	private Date cert_date;
	
	/**
	 * 启用日期
	 */
	private Date cert_start_date;
	
	/**
	 * 失效日期
	 */
	private Date cert_end_date;
	
	/**
	 * 发证机关
	 */
	private String issuing_authority;
	
	/**
	 * 证件描述
	 */
	private Integer cert_memo;
	
	/**
	 * 证件状态
	 */
	private String cert_state;

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

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getBusiness_no() {
		return business_no;
	}

	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}

	public Date getCert_date() {
		return cert_date;
	}

	public void setCert_date(Date cert_date) {
		this.cert_date = cert_date;
	}

	public Date getCert_start_date() {
		return cert_start_date;
	}

	public void setCert_start_date(Date cert_start_date) {
		this.cert_start_date = cert_start_date;
	}

	public Date getCert_end_date() {
		return cert_end_date;
	}

	public void setCert_end_date(Date cert_end_date) {
		this.cert_end_date = cert_end_date;
	}

	public String getIssuing_authority() {
		return issuing_authority;
	}

	public void setIssuing_authority(String issuing_authority) {
		this.issuing_authority = issuing_authority;
	}

	public Integer getCert_memo() {
		return cert_memo;
	}

	public void setCert_memo(Integer cert_memo) {
		this.cert_memo = cert_memo;
	}

	public String getCert_state() {
		return cert_state;
	}

	public void setCert_state(String cert_state) {
		this.cert_state = cert_state;
	}

	public Long getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}
	
	
}