package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
import java.util.*;
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

public class HtcPeopleTitleDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id; 
	private long hos_id;
	private String copy_code;
	private String title_code;
	private String title_name;
	private String title_desc;
	private long is_stop;
	private String spell_code;
	private String wbx_code;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getTitle_code() {
		return title_code;
	}
	public String getTitle_name() {
		return title_name;
	}
	public String getTitle_desc() {
		return title_desc;
	}
	public long getIs_stop() {
		return is_stop;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setTitle_code(String title_code) {
		this.title_code = title_code;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public void setTitle_desc(String title_desc) {
		this.title_desc = title_desc;
	}
	public void setIs_stop(long is_stop) {
		this.is_stop = is_stop;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
}