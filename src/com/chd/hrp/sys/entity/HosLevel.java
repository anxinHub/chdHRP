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


public class HosLevel implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String level_code;
	private String level_name;
	private int is_stop;
	private String note;

	public void setLevel_code(String value) {
		this.level_code = value;
	}
		
	public String getLevel_code() {
		return this.level_code;
	}
	public void setLevel_name(String value) {
		this.level_name = value;
	}
		
	public String getLevel_name() {
		return this.level_name;
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
}