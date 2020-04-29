/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
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


public class Fac implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long fac_id;
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


	private String fac_code;
	private String type_code;
	private String type_name;
	private String fac_name;
	private Long fac_sort;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private String error_type;
	private int is_mat;
	private int is_ass;
	private int is_med;
	private int is_sup;
	
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
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
		
	public Long getFac_id() {
		return this.fac_id;
	}
	public void setFac_code(String value) {
		this.fac_code = value;
	}
		
	public String getFac_code() {
		return this.fac_code;
	}
	public void setType_code(String value) {
		this.type_code = value;
	}
		
	public String getType_code() {
		return this.type_code;
	}
	public void setFac_name(String value) {
		this.fac_name = value;
	}
		
	public String getFac_name() {
		return this.fac_name;
	}
	public void setFac_sort(Long value) {
		this.fac_sort = value;
	}
		
	public Long getFac_sort() {
		return this.fac_sort;
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

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}