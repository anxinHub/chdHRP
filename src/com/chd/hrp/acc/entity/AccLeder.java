/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 财务账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccLeder implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 会计期间
	 */
	private String acc_month;
	
	/**
	 * 科目名称
	 */
	private String subj_name;
	
	private String subj_name_all;
	/**
	 * 期初余额
	 */
	private double bal_os;
	/**
	 * 本期借方金额
	 */
	private double this_od;
	/**
	 * 本期贷方金额
	 */
	private double this_oc;
	/**
	 * 本年累计借方金额
	 */
	private double sum_od;
	/**
	 * 本年累计贷方金额
	 */
	private double sum_oc;
	/**
	 * 期末余额
	 */
	private double end_os;
	/**
	 * 期初余额外币
	 */
	private double bal_os_w;
	/**
	 * 本期借方金额外币
	 */
	private double this_od_w;
	/**
	 * 本期贷方金额外币
	 */
	private double this_oc_w;
	/**
	 * 本年累计借方金额外币
	 */
	private double sum_od_w;
	/**
	 * 本年累计贷方金额外币
	 */
	private double sum_oc_w;
	/**
	 * 期末余额外币
	 */
	private double end_os_w;
	
	/**
	 * 方向
	 */
	private String subj_dire;
	
	/**
	 * 币种
	 */
	private String cur_code;
	
	private Integer is_check;
	
	private Integer is_last;
	
	private String subj_code;

	private String start_month;
	
	private String check_type_name1;
	
	private String check_type_name2;
	
	private String check_type_name3;
	
	private String check_type_name4;
	
	private String column_check1;
	
	private String column_check2;
	
	private String column_check3;
	
	private String column_check4;
	
	private String column_item1;
	
	private String column_item2;
	
	private String column_item3;
	
	private String column_item4;
	
	private double credit;
	
	private double debit;
	
	private String check_type_id1;
	
	private String check_type_id2;
	
	private String check_type_id3;
	
	private String check_type_id4;
	
	private String error_type;
	
	private String check1;
	
	private String check2;
	
	private String check3;
	
	private String check4;
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(double value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public double getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(double value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public double getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	 * 设置 会计年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 会计年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 会计期间
	 */
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	/**
	 * 获取 会计期间
	 */	
	public String getAcc_month() {
		return this.acc_month;
	}
	
	/**
	 * 设置 期初余额
	 */
	public void setBal_os(double value) {
		this.bal_os = value;
	}
	/**
	 * 获取 期初余额
	 */	
	public double getBal_os() {
		return this.bal_os;
	}
	/**
	 * 设置 本期借方金额
	 */
	public void setThis_od(double value) {
		this.this_od = value;
	}
	/**
	 * 获取 本期借方金额
	 */	
	public double getThis_od() {
		return this.this_od;
	}
	/**
	 * 设置 本期贷方金额
	 */
	public void setThis_oc(double value) {
		this.this_oc = value;
	}
	/**
	 * 获取 本期贷方金额
	 */	
	public double getThis_oc() {
		return this.this_oc;
	}
	/**
	 * 设置 本年累计借方金额
	 */
	public void setSum_od(double value) {
		this.sum_od = value;
	}
	/**
	 * 获取 本年累计借方金额
	 */	
	public double getSum_od() {
		return this.sum_od;
	}
	/**
	 * 设置 本年累计贷方金额
	 */
	public void setSum_oc(double value) {
		this.sum_oc = value;
	}
	/**
	 * 获取 本年累计贷方金额
	 */	
	public double getSum_oc() {
		return this.sum_oc;
	}
	/**
	 * 设置 期末余额
	 */
	public void setEnd_os(double value) {
		this.end_os = value;
	}
	/**
	 * 获取 期末余额
	 */	
	public double getEnd_os() {
		return this.end_os;
	}
	/**
	 * 设置 期初余额外币
	 */
	public void setBal_os_w(double value) {
		this.bal_os_w = value;
	}
	/**
	 * 获取 期初余额外币
	 */	
	public double getBal_os_w() {
		return this.bal_os_w;
	}
	/**
	 * 设置 本期借方金额外币
	 */
	public void setThis_od_w(double value) {
		this.this_od_w = value;
	}
	/**
	 * 获取 本期借方金额外币
	 */	
	public double getThis_od_w() {
		return this.this_od_w;
	}
	/**
	 * 设置 本期贷方金额外币
	 */
	public void setThis_oc_w(double value) {
		this.this_oc_w = value;
	}
	/**
	 * 获取 本期贷方金额外币
	 */	
	public double getThis_oc_w() {
		return this.this_oc_w;
	}
	/**
	 * 设置 本年累计借方金额外币
	 */
	public void setSum_od_w(double value) {
		this.sum_od_w = value;
	}
	/**
	 * 获取 本年累计借方金额外币
	 */	
	public double getSum_od_w() {
		return this.sum_od_w;
	}
	/**
	 * 设置 本年累计贷方金额外币
	 */
	public void setSum_oc_w(double value) {
		this.sum_oc_w = value;
	}
	/**
	 * 获取 本年累计贷方金额外币
	 */	
	public double getSum_oc_w() {
		return this.sum_oc_w;
	}
	/**
	 * 设置 期末余额外币
	 */
	public void setEnd_os_w(double value) {
		this.end_os_w = value;
	}
	/**
	 * 获取 期末余额外币
	 */	
	public double getEnd_os_w() {
		return this.end_os_w;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	
	public String getSubj_dire() {
		return subj_dire;
	}
	public void setSubj_dire(String subj_dire) {
		this.subj_dire = subj_dire;
	}
	public String getCur_code() {
		return cur_code;
	}
	public void setCur_code(String cur_code) {
		this.cur_code = cur_code;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getIs_check() {
		return is_check;
	}
	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getStart_month() {
		return start_month;
	}
	public void setStart_month(String start_month) {
		this.start_month = start_month;
	}
	public String getCheck_type_name1() {
		return check_type_name1;
	}
	public void setCheck_type_name1(String check_type_name1) {
		this.check_type_name1 = check_type_name1;
	}
	public String getCheck_type_name2() {
		return check_type_name2;
	}
	public void setCheck_type_name2(String check_type_name2) {
		this.check_type_name2 = check_type_name2;
	}
	public String getCheck_type_name3() {
		return check_type_name3;
	}
	public void setCheck_type_name3(String check_type_name3) {
		this.check_type_name3 = check_type_name3;
	}
	public String getCheck_type_name4() {
		return check_type_name4;
	}
	public void setCheck_type_name4(String check_type_name4) {
		this.check_type_name4 = check_type_name4;
	}
	public String getColumn_check1() {
		return column_check1;
	}
	public void setColumn_check1(String column_check1) {
		this.column_check1 = column_check1;
	}
	public String getColumn_check2() {
		return column_check2;
	}
	public void setColumn_check2(String column_check2) {
		this.column_check2 = column_check2;
	}
	public String getColumn_check3() {
		return column_check3;
	}
	public void setColumn_check3(String column_check3) {
		this.column_check3 = column_check3;
	}
	public String getColumn_check4() {
		return column_check4;
	}
	public void setColumn_check4(String column_check4) {
		this.column_check4 = column_check4;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public String getCheck1() {
		return check1;
	}
	public void setCheck1(String check1) {
		this.check1 = check1;
	}
	public String getCheck2() {
		return check2;
	}
	public void setCheck2(String check2) {
		this.check2 = check2;
	}
	public String getCheck3() {
		return check3;
	}
	public void setCheck3(String check3) {
		this.check3 = check3;
	}
	public String getCheck4() {
		return check4;
	}
	public void setCheck4(String check4) {
		this.check4 = check4;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public Integer getIs_last() {
		return is_last;
	}
	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}
	public String getColumn_item1() {
		return column_item1;
	}
	public void setColumn_item1(String column_item1) {
		this.column_item1 = column_item1;
	}
	public String getColumn_item2() {
		return column_item2;
	}
	public void setColumn_item2(String column_item2) {
		this.column_item2 = column_item2;
	}
	public String getColumn_item3() {
		return column_item3;
	}
	public void setColumn_item3(String column_item3) {
		this.column_item3 = column_item3;
	}
	public String getColumn_item4() {
		return column_item4;
	}
	public void setColumn_item4(String column_item4) {
		this.column_item4 = column_item4;
	}
	public String getSubj_name_all() {
		return subj_name_all;
	}
	public void setSubj_name_all(String subj_name_all) {
		this.subj_name_all = subj_name_all;
	}
	public String getCheck_type_id1() {
		return check_type_id1;
	}
	public void setCheck_type_id1(String check_type_id1) {
		this.check_type_id1 = check_type_id1;
	}
	public String getCheck_type_id2() {
		return check_type_id2;
	}
	public void setCheck_type_id2(String check_type_id2) {
		this.check_type_id2 = check_type_id2;
	}
	public String getCheck_type_id3() {
		return check_type_id3;
	}
	public void setCheck_type_id3(String check_type_id3) {
		this.check_type_id3 = check_type_id3;
	}
	public String getCheck_type_id4() {
		return check_type_id4;
	}
	public void setCheck_type_id4(String check_type_id4) {
		this.check_type_id4 = check_type_id4;
	}
	
}