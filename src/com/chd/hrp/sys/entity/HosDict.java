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


public class HosDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	
	private Long group_id;
	private Long hos_id;
	private Long hos_no;
	
	private String hos_code;
	private String hos_name;
	private String hos_simple;
	private int is_stop;


	public void setHos_no(Long hos_no) {
		this.hos_no = hos_no;
	}
		
	public Long getHos_no() {
		return this.hos_no;
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
	
	public void setHos_code(String value) {
		this.hos_code = value;
	}
		
	public String getHos_code() {
		return this.hos_code;
	}
	public void setHos_name(String value) {
		this.hos_name = value;
	}
		
	public String getHos_name() {
		return this.hos_name;
	}
	public void setHos_simple(String value) {
		this.hos_simple = value;
	}
		
	public String getHos_simple() {
		return this.hos_simple;
	}
	
	public void setIs_stop(int value) {
		this.is_stop = value;
	}
		
	public int getIs_stop() {
		return this.is_stop;
	}

	
	
	
}