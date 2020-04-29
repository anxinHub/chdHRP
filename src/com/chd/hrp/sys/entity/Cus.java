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


public class Cus implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long cus_id;
	private String cus_code;
	private String type_code;
	private String type_name;
	private String cus_name;
	private Long sort_code;
	private String spell_code;
	private String wbx_code;
	private int is_stop;
	private String note;
	private String error_type;
	private int is_mat;
	private int is_ass;
	private int is_med;
	private int is_sup;
	private String user_code;
	private Date create_date;
	private String dlog;
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
	public void setCus_id(Long value) {
		this.cus_id = value;
	}
		
	public Long getCus_id() {
		return this.cus_id;
	}
	public void setCus_code(String value) {
		this.cus_code = value;
	}
		
	public String getCus_code() {
		return this.cus_code;
	}
	public void setType_code(String value) {
		this.type_code = value;
	}
		
	public String getType_code() {
		return this.type_code;
	}
	public void setCus_name(String value) {
		this.cus_name = value;
	}
		
	public String getCus_name() {
		return this.cus_name;
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

	/**
	 * @return the error_type
	 */
	public String getError_type() {
		return error_type;
	}

	/**
	 * @param error_type the error_type to set
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
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

	/**
	 * @return the user_code
	 */
	public String getUser_code() {
		return user_code;
	}

	/**
	 * @param user_code the user_code to set
	 */
	public void setUser_code(String user_code) {
		this.user_code = user_code;
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
	 * @return the dlog
	 */
	public String getDlog() {
		return dlog;
	}

	/**
	 * @param dlog the dlog to set
	 */
	public void setDlog(String dlog) {
		this.dlog = dlog;
	}
	
}