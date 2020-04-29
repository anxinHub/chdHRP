/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.bid;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050501 集团与医院资产招标关系表
 * @Table:
 * ASS_BID_GROUP_HOS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssBidGroupHos implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 集团招标号
	 */
	private Long group_bid_id;
	
	/**
	 * 医院招标号
	 */
	private Long hos_bid_id;
	
	/**
	 * 集体招标明细ID
	 */
	private Long group_detail_id;
	
	/**
	 * 医院招标明细ID
	 */
	private Long hos_detail_id;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 集团招标号
	* @param value 
	*/
	public void setGroup_bid_id(Long value) {
		this.group_bid_id = value;
	}
	
	/**
	* 获取 集团招标号
	* @return Long
	*/
	public Long getGroup_bid_id() {
		return this.group_bid_id;
	}
	/**
	* 设置 医院招标号
	* @param value 
	*/
	public void setHos_bid_id(Long value) {
		this.hos_bid_id = value;
	}
	
	/**
	* 获取 医院招标号
	* @return Long
	*/
	public Long getHos_bid_id() {
		return this.hos_bid_id;
	}
	/**
	* 设置 集体招标明细ID
	* @param value 
	*/
	public void setGroup_detail_id(Long value) {
		this.group_detail_id = value;
	}
	
	/**
	* 获取 集体招标明细ID
	* @return Long
	*/
	public Long getGroup_detail_id() {
		return this.group_detail_id;
	}
	/**
	* 设置 医院招标明细ID
	* @param value 
	*/
	public void setHos_detail_id(Long value) {
		this.hos_detail_id = value;
	}
	
	/**
	* 获取 医院招标明细ID
	* @return Long
	*/
	public Long getHos_detail_id() {
		return this.hos_detail_id;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}