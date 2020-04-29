/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MED_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedTranMain implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	/**
	 * 
	 */
	private Long tran_id;
	
	/**
	 * 
	 */
	private Object tran_no;
	
	/**
	 * 
	 */
	private Object year;
	
	/**
	 * 
	 */
	private Object month;
	
	/**
	 * 
	 */
	private Integer bus_type;
	
	/**
	 * 
	 */
	private Integer tran_method;
	
	/**
	 * 
	 */
	private Integer tran_type;
	
	/**
	 * 
	 */
	private Long out_hos_id;
	
	private String out_hos_code ;
	private String out_hos_name ;
	
	/**
	 * 
	 */
	private Long out_store_id;
	
	/**
	 * 
	 */
	private Long out_store_no;
	
	private String out_store_code ;
	private String out_store_name ;
	/**
	 * 
	 */
	private Long in_hos_id;
	
	private String in_hos_code ;
	private String in_hos_name ;
	/**
	 * 
	 */
	private Long in_store_id;
	
	/**
	 * 
	 */
	private Long in_store_no;
	private String in_store_code ;
	
	private String in_store_name ;
	/**
	 * 
	 */
	private Object brief;
	
	/**
	 * 
	 */
	private Date tran_date;
	
	/**
	 * 
	 */
	private Long maker;
	
	/**
	 * 
	 */
	private Long checker;
	
	/**
	 * 
	 */
	private Date check_date;
	
	/**
	 * 
	 */
	private Long confirmer;
	
	/**
	 * 
	 */
	private Date confirm_date;
	
	/**
	 * 
	 */
	private Long out_hos_no;
	
	/**
	 * 
	 */
	private Long in_hos_no;

	
	/**
	 * 
	 */
	private Integer state;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_id(Long value) {
		this.tran_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getTran_id() {
		return this.tran_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_no(Object value) {
		this.tran_no = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getTran_no() {
		return this.tran_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setYear(Object value) {
		this.year = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getYear() {
		return this.year;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMonth(Object value) {
		this.month = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getMonth() {
		return this.month;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBus_type(Integer value) {
		this.bus_type = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getBus_type() {
		return this.bus_type;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_method(Integer value) {
		this.tran_method = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getTran_method() {
		return this.tran_method;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_type(Integer value) {
		this.tran_type = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getTran_type() {
		return this.tran_type;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_hos_id(Long value) {
		this.out_hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_hos_id() {
		return this.out_hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_store_id(Long value) {
		this.out_store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_store_id() {
		return this.out_store_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_store_no(Long value) {
		this.out_store_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_store_no() {
		return this.out_store_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_hos_id(Long value) {
		this.in_hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getIn_hos_id() {
		return this.in_hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_store_id(Long value) {
		this.in_store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getIn_store_id() {
		return this.in_store_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_store_no(Long value) {
		this.in_store_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getIn_store_no() {
		return this.in_store_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBrief(Object value) {
		this.brief = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBrief() {
		return this.brief;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_date(Date value) {
		this.tran_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getTran_date() {
		return this.tran_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setConfirmer(Long value) {
		this.confirmer = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getConfirmer() {
		return this.confirmer;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setConfirm_date(Date value) {
		this.confirm_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getConfirm_date() {
		return this.confirm_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
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

	public Long getOut_hos_no() {
		return out_hos_no;
	}

	public void setOut_hos_no(Long out_hos_no) {
		this.out_hos_no = out_hos_no;
	}

	public Long getIn_hos_no() {
		return in_hos_no;
	}

	public void setIn_hos_no(Long in_hos_no) {
		this.in_hos_no = in_hos_no;
	}

	public String getOut_hos_code() {
		return out_hos_code;
	}

	public void setOut_hos_code(String out_hos_code) {
		this.out_hos_code = out_hos_code;
	}

	public String getOut_hos_name() {
		return out_hos_name;
	}

	public void setOut_hos_name(String out_hos_name) {
		this.out_hos_name = out_hos_name;
	}

	public String getOut_store_code() {
		return out_store_code;
	}

	public void setOut_store_code(String out_store_code) {
		this.out_store_code = out_store_code;
	}

	public String getOut_store_name() {
		return out_store_name;
	}

	public void setOut_store_name(String out_store_name) {
		this.out_store_name = out_store_name;
	}

	public String getIn_hos_code() {
		return in_hos_code;
	}

	public void setIn_hos_code(String in_hos_code) {
		this.in_hos_code = in_hos_code;
	}

	public String getIn_hos_name() {
		return in_hos_name;
	}

	public void setIn_hos_name(String in_hos_name) {
		this.in_hos_name = in_hos_name;
	}

	public String getIn_store_code() {
		return in_store_code;
	}

	public void setIn_store_code(String in_store_code) {
		this.in_store_code = in_store_code;
	}

	public String getIn_store_name() {
		return in_store_name;
	}

	public void setIn_store_name(String in_store_name) {
		this.in_store_name = in_store_name;
	}
	
}