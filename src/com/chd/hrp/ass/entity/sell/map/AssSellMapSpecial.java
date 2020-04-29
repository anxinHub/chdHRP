/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.sell.map;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050201 有偿调拨出库与入库关系表（专用设备）
 * @Table:
 * ASS_SELL_MAP_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssSellMapSpecial implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 入库集团ID
	 */
	private Long group_id;
	
	/**
	 * 入库医院ID
	 */
	private Long hos_id;
	
	/**
	 * 入库账套编码
	 */
	private String copy_code;
	
	/**
	 * 入库单号
	 */
	private String sell_in_no;
	
	/**
	 * 出库集团ID
	 */
	private Long out_group_id;
	
	/**
	 * 出库医院ID
	 */
	private Long out_hos_id;
	
	/**
	 * 出库账套编码
	 */
	private String out_copy_code;
	
	/**
	 * 出库单号
	 */
	private String sell_out_no;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 入库集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 入库集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 入库医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 入库医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 入库账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 入库账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 入库单号
	* @param value 
	*/
	public void setSell_in_no(String value) {
		this.sell_in_no = value;
	}
	
	/**
	* 获取 入库单号
	* @return String
	*/
	public String getSell_in_no() {
		return this.sell_in_no;
	}
	/**
	* 设置 出库集团ID
	* @param value 
	*/
	public void setOut_group_id(Long value) {
		this.out_group_id = value;
	}
	
	/**
	* 获取 出库集团ID
	* @return Long
	*/
	public Long getOut_group_id() {
		return this.out_group_id;
	}
	/**
	* 设置 出库医院ID
	* @param value 
	*/
	public void setOut_hos_id(Long value) {
		this.out_hos_id = value;
	}
	
	/**
	* 获取 出库医院ID
	* @return Long
	*/
	public Long getOut_hos_id() {
		return this.out_hos_id;
	}
	/**
	* 设置 出库账套编码
	* @param value 
	*/
	public void setOut_copy_code(String value) {
		this.out_copy_code = value;
	}
	
	/**
	* 获取 出库账套编码
	* @return String
	*/
	public String getOut_copy_code() {
		return this.out_copy_code;
	}
	/**
	* 设置 出库单号
	* @param value 
	*/
	public void setSell_out_no(String value) {
		this.sell_out_no = value;
	}
	
	/**
	* 获取 出库单号
	* @return String
	*/
	public String getSell_out_no() {
		return this.sell_out_no;
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