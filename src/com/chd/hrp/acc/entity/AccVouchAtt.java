package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccVouchAtt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private Long vouch_id;
	
	private String att_name_n;
	
	private String att_name_o;
	
	private String att_path;
	
	private Integer att_size;
	
	private Integer create_user;
	
	private Date create_date;
	
	private String create_name;
	
	private Integer att_type;
	
	private String invo_num;
	
	private Date invo_date;
	
	private double invo_money;

	/**
	 * @return the group_id
	 */
	public Long getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return the hos_id
	 */
	public Long getHos_id() {
		return hos_id;
	}

	/**
	 * @param hos_id the hos_id to set
	 */
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	/**
	 * @return the copy_code
	 */
	public String getCopy_code() {
		return copy_code;
	}

	/**
	 * @param copy_code the copy_code to set
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	/**
	 * @return the vouch_id
	 */
	public Long getVouch_id() {
		return vouch_id;
	}

	/**
	 * @param vouch_id the vouch_id to set
	 */
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}

	/**
	 * @return the att_name_n
	 */
	public String getAtt_name_n() {
		return att_name_n;
	}

	/**
	 * @param att_name_n the att_name_n to set
	 */
	public void setAtt_name_n(String att_name_n) {
		this.att_name_n = att_name_n;
	}

	/**
	 * @return the att_name_o
	 */
	public String getAtt_name_o() {
		return att_name_o;
	}

	/**
	 * @param att_name_o the att_name_o to set
	 */
	public void setAtt_name_o(String att_name_o) {
		this.att_name_o = att_name_o;
	}

	/**
	 * @return the att_path
	 */
	public String getAtt_path() {
		return att_path;
	}

	/**
	 * @param att_path the att_path to set
	 */
	public void setAtt_path(String att_path) {
		this.att_path = att_path;
	}

	/**
	 * @return the att_size
	 */
	public Integer getAtt_size() {
		return att_size;
	}

	/**
	 * @param att_size the att_size to set
	 */
	public void setAtt_size(Integer att_size) {
		this.att_size = att_size;
	}

	/**
	 * @return the create_user
	 */
	public Integer getCreate_user() {
		return create_user;
	}

	/**
	 * @param create_user the create_user to set
	 */
	public void setCreate_user(Integer create_user) {
		this.create_user = create_user;
	}

	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the att_type
	 */
	public Integer getAtt_type() {
		return att_type;
	}

	/**
	 * @param att_type the att_type to set
	 */
	public void setAtt_type(Integer att_type) {
		this.att_type = att_type;
	}

	/**
	 * @return the invo_num
	 */
	public String getInvo_num() {
		return invo_num;
	}

	/**
	 * @param invo_num the invo_num to set
	 */
	public void setInvo_num(String invo_num) {
		this.invo_num = invo_num;
	}

	/**
	 * @return the invo_date
	 */
	public Date getInvo_date() {
		return invo_date;
	}

	/**
	 * @param invo_date the invo_date to set
	 */
	public void setInvo_date(Date invo_date) {
		this.invo_date = invo_date;
	}

	/**
	 * @return the invo_money
	 */
	public double getInvo_money() {
		return invo_money;
	}

	/**
	 * @param invo_money the invo_money to set
	 */
	public void setInvo_money(double invo_money) {
		this.invo_money = invo_money;
	}

	/**
	 * @return the create_name
	 */
	public String getCreate_name() {
		return create_name;
	}

	/**
	 * @param create_name the create_name to set
	 */
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	

}
