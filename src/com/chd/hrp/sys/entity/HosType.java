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


public class HosType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String type_code;
	private String type_name;
	private int is_stop;
	private String note;

	public void setType_code(String value) {
		this.type_code = value;
	}
		
	public String getType_code() {
		return this.type_code;
	}
	public void setType_name(String value) {
		this.type_name = value;
	}
		
	public String getType_name() {
		return this.type_name;
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