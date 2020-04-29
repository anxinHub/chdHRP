package com.chd.hrp.htcg.entity.making;
import java.io.Serializable;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcgSchemeSimilarTreat implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String scheme_code;
	private String scheme_name;
	private String drgs_code;
	private String drgs_name;
	private String charge_nature_code;
	private String charge_nature_name;
	private String charge_code;
	private String charge_name;
	private String similar_code;
	private String similar_name;
	
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getDrgs_code() {
		return drgs_code;
	}
	public String getDrgs_name() {
		return drgs_name;
	}
	public String getCharge_nature_code() {
		return charge_nature_code;
	}
	public String getCharge_nature_name() {
		return charge_nature_name;
	}
	public String getCharge_code() {
		return charge_code;
	}
	public String getCharge_name() {
		return charge_name;
	}
	public String getSimilar_code() {
		return similar_code;
	}
	public String getSimilar_name() {
		return similar_name;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setDrgs_code(String drgs_code) {
		this.drgs_code = drgs_code;
	}
	public void setDrgs_name(String drgs_name) {
		this.drgs_name = drgs_name;
	}
	public void setCharge_nature_code(String charge_nature_code) {
		this.charge_nature_code = charge_nature_code;
	}
	public void setCharge_nature_name(String charge_nature_name) {
		this.charge_nature_name = charge_nature_name;
	}
	public void setCharge_code(String charge_code) {
		this.charge_code = charge_code;
	}
	public void setCharge_name(String charge_name) {
		this.charge_name = charge_name;
	}
	public void setSimilar_code(String similar_code) {
		this.similar_code = similar_code;
	}
	public void setSimilar_name(String similar_name) {
		this.similar_name = similar_name;
	}
}