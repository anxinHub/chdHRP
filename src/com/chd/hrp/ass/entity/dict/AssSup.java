/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AssSup implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long sup_id;
	private Long sup_no;
	private String sup_code;
	private String hos_hos_id;
	private String type_code;
	private String type_name;
	private String sup_name;
	private Long sort_code;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private String error_type;
	private int is_mat;
	private int is_ass;
	private int is_med;
	public int getIs_mat() {
		return is_mat;
	}

	public void setIs_mat(int is_mat) {
		this.is_mat = is_mat;
	}

	public int getIs_ass() {
		return is_ass;
	}

	public void setIs_ass(int is_ass) {
		this.is_ass = is_ass;
	}

	public int getIs_med() {
		return is_med;
	}

	public void setIs_med(int is_med) {
		this.is_med = is_med;
	}

	public void setGroup_id(Long value) {
		this.group_id = value;
	}
		
	public Long getGroup_id() {
		return this.group_id;
	}
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
		
	public Long getHos_id() {
		return this.hos_id;
	}
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
		
	public Long getSup_id() {
		return this.sup_id;
	}
	
	/**
	 * @return the sup_no
	 */
	public Long getSup_no() {
		return sup_no;
	}

	/**
	 * @param sup_no the sup_no to set
	 */
	public void setSup_no(Long sup_no) {
		this.sup_no = sup_no;
	}

	public void setSup_code(String value) {
		this.sup_code = value;
	}
		
	public String getSup_code() {
		return this.sup_code;
	}
	public void setHos_hos_id(String value) {
		this.hos_hos_id = value;
	}
		
	public String getHos_hos_id() {
		return this.hos_hos_id;
	}
	public void setType_code(String value) {
		this.type_code = value;
	}
		
	public String getType_code() {
		return this.type_code;
	}
	public void setSup_name(String value) {
		this.sup_name = value;
	}
		
	public String getSup_name() {
		return this.sup_name;
	}
	
	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}

	public void setSpell_code(String value) {
		this.spell_code = value;
	}
		
	public String getSpell_code() {
		return this.spell_code;
	}
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
		
	public String getWbx_code() {
		return this.wbx_code;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}