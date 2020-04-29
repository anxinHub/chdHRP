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


public class Political implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String political_code;
	private String political_name;
	private int is_stop;
	private String note;

	public void setPolitical_code(String value) {
		this.political_code = value;
	}
		
	public String getPolitical_code() {
		return this.political_code;
	}
	public void setPolitical_name(String value) {
		this.political_name = value;
	}
		
	public String getPolitical_name() {
		return this.political_name;
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