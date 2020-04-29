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


public class Rules implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String mod_code;
	private String mod_name;
	private String copy_code;
	private String copy_name;
	private String proj_code;
	private String proj_name;
	private Integer max_level;
	private Long max_length;
	private Long level1;
	private Long level2;
	private Long level3;
	private Long level4;
	private Long level5;
	private Long level6;
	private Long level7;
	private Long level8;
	private Long level9;
	private Long level10;
	private Long is_auto;
	

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
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
	public void setMod_code(String value) {
		this.mod_code = value;
	}
		
	public String getMod_code() {
		return this.mod_code;
	}
	public void setProj_code(String value) {
		this.proj_code = value;
	}
		
	public String getProj_code() {
		return this.proj_code;
	}
	public void setProj_name(String value) {
		this.proj_name = value;
	}
		
	public String getProj_name() {
		return this.proj_name;
	}
	public void setMax_level(Integer value) {
		this.max_level = value;
	}
		
	public Integer getMax_level() {
		return this.max_level;
	}
	public void setMax_length(Long value) {
		this.max_length = value;
	}
		
	public Long getMax_length() {
		return this.max_length;
	}
	public void setLevel1(Long value) {
		this.level1 = value;
	}
		
	public Long getLevel1() {
		return this.level1;
	}
	public void setLevel2(Long value) {
		this.level2 = value;
	}
		
	public Long getLevel2() {
		return this.level2;
	}
	public void setLevel3(Long value) {
		this.level3 = value;
	}
		
	public Long getLevel3() {
		return this.level3;
	}
	public void setLevel4(Long value) {
		this.level4 = value;
	}
		
	public Long getLevel4() {
		return this.level4;
	}
	public void setLevel5(Long value) {
		this.level5 = value;
	}
		
	public Long getLevel5() {
		return this.level5;
	}
	public void setLevel6(Long value) {
		this.level6 = value;
	}
		
	public Long getLevel6() {
		return this.level6;
	}
	public void setLevel7(Long value) {
		this.level7 = value;
	}
		
	public Long getLevel7() {
		return this.level7;
	}
	public void setLevel8(Long value) {
		this.level8 = value;
	}
		
	public Long getLevel8() {
		return this.level8;
	}
	public void setLevel9(Long value) {
		this.level9 = value;
	}
		
	public Long getLevel9() {
		return this.level9;
	}
	public void setLevel10(Long value) {
		this.level10 = value;
	}
		
	public Long getLevel10() {
		return this.level10;
	}
	public void setIs_auto(Long value) {
		this.is_auto = value;
	}
		
	public Long getIs_auto() {
		return this.is_auto;
	}

	
	/**
	 * 获取 copy_code
	 * @return copy_code
	 */
	public String getCopy_code() {
		return copy_code;
	}

	
	/**
	 * 设置 copy_code
	 * @param copy_code 
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	
	/**
	 * 获取 copy_name
	 * @return copy_name
	 */
	public String getCopy_name() {
		return copy_name;
	}

	
	/**
	 * 设置 copy_name
	 * @param copy_name 
	 */
	public void setCopy_name(String copy_name) {
		this.copy_name = copy_name;
	}
	
}