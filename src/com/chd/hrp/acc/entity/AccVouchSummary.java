/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccVouchSummary implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 摘要ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long user_id;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 排序号
	 */
	private Long sort_code;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getSort_code() {
		return sort_code;
	}
	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
	}
	
}