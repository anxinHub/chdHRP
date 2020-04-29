/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 鏅烘収浜戝悍锛堝寳浜級鏁版嵁绉戞妧鏈夐檺鍏徃
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class Store implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long store_id;
	private String store_code;
	private String type_code;
	private String type_name;
	private String store_name;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private String error_type;
	private int is_mat;
	private int is_ass;
	private int is_med;
	private int is_sup;
	private int is_disable;
	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

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
	

	/**
	 * @return the is_sup
	 */
	public int getIs_sup() {
		return is_sup;
	}

	/**
	 * @param is_sup the is_sup to set
	 */
	public void setIs_sup(int is_sup) {
		this.is_sup = is_sup;
	}


	//鎺掑簭鍙�
	private Long sort_code;

	
	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
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
	public void setStore_id(Long value) {
		this.store_id = value;
	}
		
	public Long getStore_id() {
		return this.store_id;
	}
	public void setStore_code(String value) {
		this.store_code = value;
	}
		
	public String getStore_code() {
		return this.store_code;
	}
	public void setType_code(String value) {
		this.type_code = value;
	}
		
	public String getType_code() {
		return this.type_code;
	}
	public void setStore_name(String value) {
		this.store_name = value;
	}
		
	public String getStore_name() {
		return this.store_name;
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

	public Long getSort_code() {
		return sort_code;
	}

	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}
	
	
}