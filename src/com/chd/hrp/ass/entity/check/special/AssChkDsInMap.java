/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.entity.check.special;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 050201 盘点与盘盈入库关系表
 * @Table:
 * ASS_CHK_IN_DS_MAP_SPECIAL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public class AssChkDsInMap implements Serializable{

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
	 * 入库单号
	 */
	private String ass_in_no;
	/**
	 * 盘点单号
	 */
	private String check_no;
	/**
	 * 任务单号
	 */
	 private String check_plan_no;
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAss_in_no() {
		return ass_in_no;
	}
	public void setAss_in_no(String ass_in_no) {
		this.ass_in_no = ass_in_no;
	}
	public String getCheck_no() {
		return check_no;
	}
	public void setCheck_no(String check_no) {
		this.check_no = check_no;
	}
	public String getCheck_plan_no() {
		return check_plan_no;
	}
	public void setCheck_plan_no(String check_plan_no) {
		this.check_plan_no = check_plan_no;
	}
}
