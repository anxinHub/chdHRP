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


public class DeptDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long dept_no;
	private Long group_id;
	private Long hos_id;
	private Long dept_id;
	private String dept_code;
	private String dept_name;
	private String kind_code;
	private String kind_name;
	private String super_code;
	private Integer dept_level;
	private String udefine_code;
	private Integer sort_code;
	private Integer is_last;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String user_code;
	private String user_name;	
	private Date create_date;
	private String dlog;
	private int is_stop;
	
	private String type_code;
	private String natur_code;
	private String type_name;
	private String natur_name;
	
	private String out_code;
	private String out_name;
	private String para_code;
	private String para_name;
	
	
	private Long sys_dept_id;
	private Long sys_dept_no;
	private String sys_dept_code;
	private String sys_dept_name;
	private int is_disable;
	
	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	public Long getSys_dept_id() {
		return sys_dept_id;
	}

	public void setSys_dept_id(Long sys_dept_id) {
		this.sys_dept_id = sys_dept_id;
	}

	public Long getSys_dept_no() {
		return sys_dept_no;
	}

	public void setSys_dept_no(Long sys_dept_no) {
		this.sys_dept_no = sys_dept_no;
	}

	public String getSys_dept_code() {
		return sys_dept_code;
	}

	public void setSys_dept_code(String sys_dept_code) {
		this.sys_dept_code = sys_dept_code;
	}

	public String getSys_dept_name() {
		return sys_dept_name;
	}

	public void setSys_dept_name(String sys_dept_name) {
		this.sys_dept_name = sys_dept_name;
	}


	private String error_type;

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public void setDept_no(Long value) {
		this.dept_no = value;
	}
		
	public Long getDept_no() {
		return this.dept_no;
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
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
		
	public Long getDept_id() {
		return this.dept_id;
	}
	public void setDept_code(String value) {
		this.dept_code = value;
	}
		
	public String getDept_code() {
		return this.dept_code;
	}
	public void setDept_name(String value) {
		this.dept_name = value;
	}
		
	public String getDept_name() {
		return this.dept_name;
	}
	public void setUser_code(String value) {
		this.user_code = value;
	}
		
	public String getUser_code() {
		return this.user_code;
	}
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
		
	public Date getCreate_date() {
		return this.create_date;
	}
	public void setNote(String value) {
		this.note = value;
	}
		
	public String getNote() {
		return this.note;
	}
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}

	public String getKind_code() {
		return kind_code;
	}

	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}

	public String getSuper_code() {
		return super_code;
	}

	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}

	public Integer getDept_level() {
		return dept_level;
	}

	public void setDept_level(Integer dept_level) {
		this.dept_level = dept_level;
	}

	public String getUdefine_code() {
		return udefine_code;
	}

	public void setUdefine_code(String udefine_code) {
		this.udefine_code = udefine_code;
	}

	public Integer getSort_code() {
		return sort_code;
	}

	public void setSort_code(Integer sort_code) {
		this.sort_code = sort_code;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getDlog() {
		return dlog;
	}

	public void setDlog(String dlog) {
		this.dlog = dlog;
	}

	/**
	 * @return the type_code
	 */
	public String getType_code() {
		return type_code;
	}

	/**
	 * @param type_code the type_code to set
	 */
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	/**
	 * @return the natur_code
	 */
	public String getNatur_code() {
		return natur_code;
	}

	/**
	 * @param natur_code the natur_code to set
	 */
	public void setNatur_code(String natur_code) {
		this.natur_code = natur_code;
	}

	/**
	 * @return the type_name
	 */
	public String getType_name() {
		return type_name;
	}

	/**
	 * @param type_name the type_name to set
	 */
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	/**
	 * @return the natur_name
	 */
	public String getNatur_name() {
		return natur_name;
	}

	/**
	 * @param natur_name the natur_name to set
	 */
	public void setNatur_name(String natur_name) {
		this.natur_name = natur_name;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public String getOut_code() {
		return out_code;
	}

	public void setOut_code(String out_code) {
		this.out_code = out_code;
	}

	public String getOut_name() {
		return out_name;
	}

	public void setOut_name(String out_name) {
		this.out_name = out_name;
	}

	
    public String getPara_code() {
    	return para_code;
    }

	
    public void setPara_code(String para_code) {
    	this.para_code = para_code;
    }

	
    public String getPara_name() {
    	return para_name;
    }

	
    public void setPara_name(String para_name) {
    	this.para_name = para_name;
    }

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}