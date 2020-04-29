package com.chd.hrp.htc.entity.task.basic;

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

public class HtcPeopleTypeDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long   group_id;
	private Long   hos_id;
	private String copy_code;
	private String peop_type_code;
	private String peop_type_name;
	private String peop_type_desc;
	private int    is_stop;
	private String spell_code;
	private String wbx_code;
	
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getPeop_type_code() {
		return peop_type_code;
	}
	public String getPeop_type_name() {
		return peop_type_name;
	}
	public String getPeop_type_desc() {
		return peop_type_desc;
	}
	public int getIs_stop() {
		return is_stop;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
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
	public void setPeop_type_code(String peop_type_code) {
		this.peop_type_code = peop_type_code;
	}
	public void setPeop_type_name(String peop_type_name) {
		this.peop_type_name = peop_type_name;
	}
	public void setPeop_type_desc(String peop_type_desc) {
		this.peop_type_desc = peop_type_desc;
	}
	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	
	

	
	
	
}