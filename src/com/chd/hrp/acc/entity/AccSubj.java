/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/** 
* @Title. @Description.
* 会计科目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccSubj implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 科目ID
	 */
	private Long subj_id;
	/**
	 * 集团ID
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
	
	private String bal_os;
	
	public String getBal_os() {
		return bal_os;
	}
	public void setBal_os(String bal_os) {
		this.bal_os = bal_os;
	}
	private String end_os;
	
	public String getEnd_os() {
		return end_os;
	}
	public void setEnd_os(String end_os) {
		this.end_os = end_os;
	}
	/**
	 * 年度
	 */
	private String acc_year;

	/**
	 * 科目编码
	 */
	private String subj_code;
	
	/**
	 * 币种编码
	 */
	private String cur_code;
	
	private String cur_name;
	/**
	 * 科目类别编码
	 */
	private String subj_type_code;

	/**
	 * 科目类别属性
	 */
	private String kind_code;
	
	private String subj_type_name;
	/**
	 * 科目性质编码
	 */
	private String subj_nature_code;
	/**
	 * 科目性质名称
	 */
	private String subj_nature_name;
	/**
	 * 凭证类型编码
	 */
	private String vouch_type_code;
	
	private String vouch_type_name;
	/**
	 * 上级编码
	 */
	private String super_code;
	/**
	 * 科目名称
	 */
	private String subj_name;
	/**
	 * 科目全称
	 */
	private String subj_name_all;
	/**
	 * 英文名称
	 */
	private String subj_name_en;
	/**
	 * 是否核算现金流
	 */
	private Integer is_cash;
	
	private Integer is_bill;
	
	private Integer is_jrsz;
	
	private Integer is_wlhx;
	
	private String is_bill_name;
	
	private String is_cash_name;
	/**
	 * 0借，1贷
	 */
	private Integer subj_dire;
	
	private String subj_dire_name;
	
	private String debit;
	
	private List<AccSubj> subjList;

	// 以下用于返回科目挂的辅助核算信息 add by alfred
	
	private String check_type_name;
	
	private String column_id;
	
	private String column_no;
	
	private String column_code;
	
	private String column_name;
	
	private String column_check;
	
	// 以上用于返回科目挂的辅助核算信息

	
	public List<AccSubj> getSubjList() {
		return subjList;
	}
	public void setSubjList(List<AccSubj> subjList) {
		this.subjList = subjList;
	}

	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	/**
	 * 科目级次
	 */
	private Integer subj_level;
	/**
	 * 是否末级
	 */
	private Integer is_last;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	private Integer is_remit;
	
	private String is_remit_name;
	
	/**
	 * 是否辅助核算
	 */
	private Integer is_check;
	/**
	 * 辅助核算1
	 */
	private Long check1;
	/**
	 * 辅助核算2
	 */
	private Long check2;
	/**
	 * 辅助核算3
	 */
	private Long check3;
	/**
	 * 辅助核算4
	 */
	private Long check4;
	/**
	 * 辅助核算5
	 */
	private Long check5;
	/**
	 * 辅助核算6
	 */
	private Long check6;
	/**
	 * 辅助核算7
	 */
	private Long check7;
	/**
	 * 辅助核算8
	 */
	private Long check8;
	/**
	 * 辅助核算9
	 */
	private Long check9;
	/**
	 * 辅助核算10
	 */
	private Long check10;
		
	/**
	 * 辅助核算1
	 */
	private String column1_check;//核算字段
	private String check1_name;
	/**
	 * 辅助核算2
	 */
	private String column2_check;
	private String check2_name;
	/**
	 * 辅助核算3
	 */
	private String column3_check;
	private String check3_name;
	/**
	 * 辅助核算4
	 */
	private String column4_check;
	private String check4_name;
	/**
	 * 辅助核算5
	 */
	private String check5_name;
	/**
	 * 辅助核算6
	 */
	private String check6_name;
	/**
	 * 辅助核算7
	 */
	private String check7_name;
	/**
	 * 辅助核算8
	 */
	private String check8_name;
	/**
	 * 辅助核算9
	 */
	private String check9_name;
	/**
	 * 辅助核算10
	 */
	private String check10_name;
	
	
	private String acc_year_month;
	private String check1_str;
	private String check2_str;
	private String check3_str;
	private String check4_str;
	private String check5_str;
	private String check6_str;
	private String check7_str;
	
	private String error_type;
	
	
	private String check_type_code1;

	private String check_type_code2;

	private String check_type_code3;
	
	private String check_type_code4;
	
	private String out_code;
	
	private String out_name;
	
	private String judge_subj_code;
	
	public String getJudge_subj_code() {
		return judge_subj_code;
	}
	public void setJudge_subj_code(String judge_subj_code) {
		this.judge_subj_code = judge_subj_code;
	}
	public String getAcc_year_month() {
		return acc_year_month;
	}
	public void setAcc_year_month(String acc_year_month) {
		this.acc_year_month = acc_year_month;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public String getCheck1_str() {
		return check1_str;
	}
	public void setCheck1_str(String check1_str) {
		this.check1_str = check1_str;
	}
	public String getCheck2_str() {
		return check2_str;
	}
	public void setCheck2_str(String check2_str) {
		this.check2_str = check2_str;
	}
	public String getCheck3_str() {
		return check3_str;
	}
	public void setCheck3_str(String check3_str) {
		this.check3_str = check3_str;
	}
	public String getCheck4_str() {
		return check4_str;
	}
	public void setCheck4_str(String check4_str) {
		this.check4_str = check4_str;
	}
	public String getCheck5_str() {
		return check5_str;
	}
	public void setCheck5_str(String check5_str) {
		this.check5_str = check5_str;
	}
	public String getCheck6_str() {
		return check6_str;
	}
	public void setCheck6_str(String check6_str) {
		this.check6_str = check6_str;
	}
	public String getCheck7_str() {
		return check7_str;
	}
	public void setCheck7_str(String check7_str) {
		this.check7_str = check7_str;
	}
	
	public String getCheck1_name() {		
		return check1_name;
	}
	public void setCheck1_name(String check1_name) {
		this.check1_name = check1_name;
	}
	public String getCheck2_name() {		
		return check2_name;
	}
	public void setCheck2_name(String check2_name) {
		this.check2_name = check2_name;
	}
	public String getCheck3_name() {		
		return check3_name;
	}
	public void setCheck3_name(String check3_name) {
		this.check3_name = check3_name;
	}
	public String getCheck4_name() {		
		return check4_name;
	}
	public void setCheck4_name(String check4_name) {
		this.check4_name = check4_name;
	}
	public String getCheck5_name() {
		return check5_name;
	}
	public void setCheck5_name(String check5_name) {
		this.check5_name = check5_name;
	}
	public String getCheck6_name() {
		return check6_name;
	}
	public void setCheck6_name(String check6_name) {
		this.check6_name = check6_name;
	}
	public String getCheck7_name() {
		return check7_name;
	}
	public void setCheck7_name(String check7_name) {
		this.check7_name = check7_name;
	}
	public String getCheck8_name() {
		return check8_name;
	}
	public void setCheck8_name(String check8_name) {
		this.check8_name = check8_name;
	}
	public String getCheck9_name() {
		return check9_name;
	}
	public void setCheck9_name(String check9_name) {
		this.check9_name = check9_name;
	}
	public String getCheck10_name() {
		return check10_name;
	}
	public void setCheck10_name(String check10_name) {
		this.check10_name = check10_name;
	}
	public String getCur_name() {
		return cur_name;
	}
	public void setCur_name(String cur_name) {
		this.cur_name = cur_name;
	}
	public String getSubj_type_name() {
		return subj_type_name;
	}
	public void setSubj_type_name(String subj_type_name) {
		this.subj_type_name = subj_type_name;
	}
	public String getVouch_type_name() {
		return vouch_type_name;
	}
	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}
	public Integer getIs_remit() {
		return is_remit;
	}
	public void setIs_remit(Integer is_remit) {
		this.is_remit = is_remit;
	}
	/**
	 * 设置 科目ID
	 */
	public void setSubj_id(Long value) {
		this.subj_id = value;
	}
	/**
	 * 获取 科目ID
	 */	
	public Long getSubj_id() {
		return this.subj_id;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
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
	 * 设置 年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 科目编码
	 */
	public void setSubj_code(String value) {
		this.subj_code = value;
	}
	/**
	 * 获取 科目编码
	 */	
	public String getSubj_code() {
		return this.subj_code;
	}
	/**
	 * 设置 币种编码
	 */
	public void setCur_code(String value) {
		this.cur_code = value;
	}
	/**
	 * 获取 币种编码
	 */	
	public String getCur_code() {
		return this.cur_code;
	}
	/**
	 * 设置 科目类别编码
	 */
	public void setSubj_type_code(String value) {
		this.subj_type_code = value;
	}
	/**
	 * 获取 科目类别编码
	 */	
	public String getSubj_type_code() {
		return this.subj_type_code;
	}
	/**
	 * 设置 科目类别属性
	 */
	public void setKind_code(String value) {
		this.kind_code = value;
	}
	/**
	 * 获取 科目类别属性
	 */	
	public String getKind_code() {
		return this.kind_code;
	}
	/**
	 * 设置 科目性质编码
	 */
	public void setSubj_nature_code(String value) {
		this.subj_nature_code = value;
	}
	/**
	 * 获取 科目性质编码
	 */	
	public String getSubj_nature_code() {
		return this.subj_nature_code;
	}
	
	/**
	 * 设置 科目性质名称
	 */
	public void setSubj_nature_name(String value) {
		this.subj_nature_name = value;
	}
	/**
	 * 获取 科目性质名称
	 */	
	public String getSubj_nature_name() {
		return this.subj_nature_name;
	}
	
	/**
	 * 设置 凭证类型编码
	 */
	public void setVouch_type_code(String value) {
		this.vouch_type_code = value;
	}
	/**
	 * 获取 凭证类型编码
	 */	
	public String getVouch_type_code() {
		return this.vouch_type_code;
	}
	/**
	 * 设置 上级编码
	 */
	public void setSuper_code(String value) {
		this.super_code = value;
	}
	/**
	 * 获取 上级编码
	 */	
	public String getSuper_code() {
		return this.super_code;
	}
	
	/**
	 * 设置 是否核算现金流
	 */
	public void setIs_cash(Integer value) {
		this.is_cash = value;
	}
	/**
	 * 获取 是否核算现金流
	 */	
	public Integer getIs_cash() {
		return this.is_cash;
	}
	/**
	 * 设置 0借，1贷
	 */
	public void setSubj_dire(Integer value) {
		this.subj_dire = value;
	}
	/**
	 * 获取 0借，1贷
	 */	
	public Integer getSubj_dire() {
		return this.subj_dire;
	}
	/**
	 * 设置 科目级次
	 */
	public void setSubj_level(Integer value) {
		this.subj_level = value;
	}
	/**
	 * 获取 科目级次
	 */	
	public Integer getSubj_level() {
		return this.subj_level;
	}
	/**
	 * 设置 是否末级
	 */
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	/**
	 * 获取 是否末级
	 */	
	public Integer getIs_last() {
		return this.is_last;
	}
	/**
	 * 设置 是否停用
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 是否停用
	 */	
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	 * 设置 是否辅助核算
	 */
	public void setIs_check(Integer value) {
		this.is_check = value;
	}
	/**
	 * 获取 是否辅助核算
	 */	
	public Integer getIs_check() {
		return this.is_check;
	}
	/**
	 * 设置 辅助核算1
	 */
	public void setCheck1(Long value) {
		this.check1 = value;
	}
	/**
	 * 获取 辅助核算1
	 */	
	public Long getCheck1() {
		return this.check1;
	}
	/**
	 * 设置 辅助核算2
	 */
	public void setCheck2(Long value) {
		this.check2 = value;
	}
	/**
	 * 获取 辅助核算2
	 */	
	public Long getCheck2() {
		return this.check2;
	}
	/**
	 * 设置 辅助核算3
	 */
	public void setCheck3(Long value) {
		this.check3 = value;
	}
	/**
	 * 获取 辅助核算3
	 */	
	public Long getCheck3() {
		return this.check3;
	}
	/**
	 * 设置 辅助核算4
	 */
	public void setCheck4(Long value) {
		this.check4 = value;
	}
	/**
	 * 获取 辅助核算4
	 */	
	public Long getCheck4() {
		return this.check4;
	}
	/**
	 * 设置 辅助核算5
	 */
	public void setCheck5(Long value) {
		this.check5 = value;
	}
	/**
	 * 获取 辅助核算5
	 */	
	public Long getCheck5() {
		return this.check5;
	}
	/**
	 * 设置 辅助核算6
	 */
	public void setCheck6(Long value) {
		this.check6 = value;
	}
	/**
	 * 获取 辅助核算6
	 */	
	public Long getCheck6() {
		return this.check6;
	}
	/**
	 * 设置 辅助核算7
	 */
	public void setCheck7(Long value) {
		this.check7 = value;
	}
	/**
	 * 获取 辅助核算7
	 */	
	public Long getCheck7() {
		return this.check7;
	}
	/**
	 * 设置 辅助核算8
	 */
	public void setCheck8(Long value) {
		this.check8 = value;
	}
	/**
	 * 获取 辅助核算8
	 */	
	public Long getCheck8() {
		return this.check8;
	}
	/**
	 * 设置 辅助核算9
	 */
	public void setCheck9(Long value) {
		this.check9 = value;
	}
	/**
	 * 获取 辅助核算9
	 */	
	public Long getCheck9() {
		return this.check9;
	}
	/**
	 * 设置 辅助核算10
	 */
	public void setCheck10(Long value) {
		this.check10 = value;
	}
	/**
	 * 获取 辅助核算10
	 */	
	public Long getCheck10() {
		return this.check10;
	}
	public String getSubj_name_en() {
		return subj_name_en;
	}
	public void setSubj_name_en(String subj_name_en) {
		this.subj_name_en = subj_name_en;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public void setSubj_name_all(String subj_name_all) {
		this.subj_name_all = subj_name_all;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public String getSubj_name_all() {
		return subj_name_all;
	}
	public String getCheck_type_name() {
		return check_type_name;
	}
	public void setCheck_type_name(String check_type_name) {
		this.check_type_name = check_type_name;
	}
	public String getColumn_id() {
		return column_id;
	}
	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}
	public String getColumn_no() {
		return column_no;
	}
	public void setColumn_no(String column_no) {
		this.column_no = column_no;
	}
	public String getColumn_code() {
		return column_code;
	}
	public void setColumn_code(String column_code) {
		this.column_code = column_code;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getColumn_check() {
		return column_check;
	}
	public void setColumn_check(String column_check) {
		this.column_check = column_check;
	}
	public String getColumn1_check() {
		return column1_check;
	}
	public void setColumn1_check(String column1_check) {
		this.column1_check = column1_check;
	}
	public String getColumn2_check() {
		return column2_check;
	}
	public void setColumn2_check(String column2_check) {
		this.column2_check = column2_check;
	}
	public String getColumn3_check() {
		return column3_check;
	}
	public void setColumn3_check(String column3_check) {
		this.column3_check = column3_check;
	}
	public String getColumn4_check() {
		return column4_check;
	}
	public void setColumn4_check(String column4_check) {
		this.column4_check = column4_check;
	}
	public String getIs_cash_name() {
		return is_cash_name;
	}
	public void setIs_cash_name(String is_cash_name) {
		this.is_cash_name = is_cash_name;
	}
	public String getSubj_dire_name() {
		return subj_dire_name;
	}
	public void setSubj_dire_name(String subj_dire_name) {
		this.subj_dire_name = subj_dire_name;
	}
	public String getIs_remit_name() {
		return is_remit_name;
	}
	public void setIs_remit_name(String is_remit_name) {
		this.is_remit_name = is_remit_name;
	}
	public String getCheck_type_code1() {
		return check_type_code1;
	}
	public void setCheck_type_code1(String check_type_code1) {
		this.check_type_code1 = check_type_code1;
	}
	public String getCheck_type_code2() {
		return check_type_code2;
	}
	public void setCheck_type_code2(String check_type_code2) {
		this.check_type_code2 = check_type_code2;
	}
	public String getCheck_type_code3() {
		return check_type_code3;
	}
	public void setCheck_type_code3(String check_type_code3) {
		this.check_type_code3 = check_type_code3;
	}
	public String getCheck_type_code4() {
		return check_type_code4;
	}
	public void setCheck_type_code4(String check_type_code4) {
		this.check_type_code4 = check_type_code4;
	}
	public String getOut_code() {
		return out_code;
	}
	public void setOut_code(String out_code) {
		this.out_code = out_code;
	}
	public String getOut_name() {
		return out_name;
	}
	public void setOut_name(String out_name) {
		this.out_name = out_name;
	}
	public Integer getIs_bill() {
		return is_bill;
	}
	public void setIs_bill(Integer is_bill) {
		this.is_bill = is_bill;
	}
	public String getIs_bill_name() {
		return is_bill_name;
	}
	public void setIs_bill_name(String is_bill_name) {
		this.is_bill_name = is_bill_name;
	}

	public Integer getIs_jrsz() {
		return is_jrsz;
	}
	public void setIs_jrsz(Integer is_jrsz) {
		this.is_jrsz = is_jrsz;
	}
	public Integer getIs_wlhx() {
		return is_wlhx;
	}
	public void setIs_wlhx(Integer is_wlhx) {
		this.is_wlhx = is_wlhx;
	}
	
}