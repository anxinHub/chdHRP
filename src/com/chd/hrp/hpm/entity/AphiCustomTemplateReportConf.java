package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class AphiCustomTemplateReportConf implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/*
	 集团ID 
	 */
	private Long group_id;
	
	/*
	 医院ID 
	 */
	private Long hos_id;
	
	/*
	 账套编码
	 */
	private String copy_code;
	
	/*
	 模板编码
	 */
	private String template_code;
	
	/*
	 模板名称
	 */
	private String template_name;
	
	/*
	 模板分类
	 */
	private String template_kind_code;
	
	/*
	 模板分类名称
	 */
	private String template_kind_name;
	

	/*
	 功能类型
	 */
	private String template_type;
	
	/*
	 功能SQL
	 */
	private byte[] template_sql;

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

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}

	public byte[] getTemplate_sql() {
		return template_sql;
	}

	public void setTemplate_sql(byte[] template_sql) {
		this.template_sql = template_sql;
	}
	
	/*public String getTemplate_sql() {
		return template_sql;
	}

	public void setTemplate_sql(String template_sql) {
		this.template_sql = template_sql;
	}*/

	public String getTemplate_kind_code() {
		return template_kind_code;
	}

	public void setTemplate_kind_code(String template_kind_code) {
		this.template_kind_code = template_kind_code;
	}
	

	public String getTemplate_kind_name() {
		return template_kind_name;
	}

	public void setTemplate_kind_name(String template_kind_name) {
		this.template_kind_name = template_kind_name;
	}
}
