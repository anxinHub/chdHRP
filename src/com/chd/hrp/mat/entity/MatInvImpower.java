
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
 * 注册授权表
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatInvImpower implements Serializable {

	
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
	 * 账套编码
	 */
	private String copy_code;
	
	
	/**
	 * 授权ID
	 */
	private Long impower_id;
	
	/**
	 * 授权编码
	 */
	private String impower_code;
	
	
	/**
	 * 生产厂商ID
	 */
	private Long fac_id;
	
    private String fac_code;
	
	private String fac_name;
	
	/**
	 * 授权单位
	 */
	private Long sup_id;
	
    private String sup_code;
	
	private String sup_name;
	
	/**
	 * 被授权单位
	 */
	private Long sup_id_b;
	
	private String sup_name_b;
	
	/**
	 * 起始日期
	 */
	private Date impower_start_date;
	
	
	/**
	 * 截止日期
	 */
	private Date impower_end_date;
	
	
	/**
	 * 联系人
	 */
	private String impower_person;
	
	/**
	 * 手机
	 */
	private String impower_tel;
	
	/**
	 * 联系电话
	 */
	private String impower_mobile;
	
	/**
	 * 存档位置
	 */
	private String file_address;
	
	
	/**
	 * 文档路径
	 */
	private String file_path;
	
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;



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



	public Long getImpower_id() {
		return impower_id;
	}



	public void setImpower_id(Long impower_id) {
		this.impower_id = impower_id;
	}

	public Integer getIs_stop() {
		return is_stop;
	}



	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}



	public String getImpower_code() {
		return impower_code;
	}



	public void setImpower_code(String impower_code) {
		this.impower_code = impower_code;
	}



	public String getCopy_code() {
		return copy_code;
	}



	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}



	public Long getFac_id() {
		return fac_id;
	}



	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
	}



	public Long getSup_id() {
		return sup_id;
	}



	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}



	public Long getSup_id_b() {
		return sup_id_b;
	}



	public void setSup_id_b(Long sup_id_b) {
		this.sup_id_b = sup_id_b;
	}



	public Date getImpower_start_date() {
		return impower_start_date;
	}



	public void setImpower_start_date(Date impower_start_date) {
		this.impower_start_date = impower_start_date;
	}



	public String getImpower_person() {
		return impower_person;
	}



	public void setImpower_person(String impower_person) {
		this.impower_person = impower_person;
	}



	public String getImpower_tel() {
		return impower_tel;
	}



	public void setImpower_tel(String impower_tel) {
		this.impower_tel = impower_tel;
	}



	public String getImpower_mobile() {
		return impower_mobile;
	}



	public void setImpower_mobile(String impower_mobile) {
		this.impower_mobile = impower_mobile;
	}



	public String getFile_address() {
		return file_address;
	}



	public void setFile_address(String file_address) {
		this.file_address = file_address;
	}



	public String getFile_path() {
		return file_path;
	}



	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}



	public Date getImpower_end_date() {
		return impower_end_date;
	}



	public void setImpower_end_date(Date impower_end_date) {
		this.impower_end_date = impower_end_date;
	}



	public String getFac_code() {
		return fac_code;
	}



	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}



	public String getFac_name() {
		return fac_name;
	}



	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}



	public String getSup_code() {
		return sup_code;
	}



	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}



	public String getSup_name() {
		return sup_name;
	}



	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}



	public String getSup_name_b() {
		return sup_name_b;
	}



	public void setSup_name_b(String sup_name_b) {
		this.sup_name_b = sup_name_b;
	}
	
	
}