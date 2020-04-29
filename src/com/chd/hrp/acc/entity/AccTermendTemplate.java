/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 期末处理凭证模板主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public class AccTermendTemplate implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 模板ID
	 */
	private Long template_id;
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
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 模板名称
	 */
	private String template_name;
	/**
	 * 模板类型
	 */
	private String template_type_code;
	/**
	 * 凭证类型编码
	 */
	private String vouch_type_code;
	/**
	 * 凭证类型名称
	 */
	private String vouch_type_name;
	/**
	 * 提取比例
	 */
	private double rate;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 按资金来源生成
	 */
	private int source_id;
	private String source_code;
	private String source_name;
	/**
	 * 借方科目ID
	 */
	private Long debit_subj_id1;
	/**
	 * 借方科目编码
	 */
	private String debit_subj_code1;
	/**
	 * 借方科目方向
	 */
	private Integer debit_subj_dire1;
	/**
	 * 借方科目是否辅助核算
	 */
	private Integer debit_is_check1;
	/**
	 * 借方科目是否现金流量标注
	 */
	private String debit_is_cash1;
	/**
	 * 借方科目类别
	 */
	private String debit_subj_type_code1;
	/**
	 * 借方科目性质
	 */
	private String debit_subj_nature_code1;
	/**
	 * 借方科目名称
	 */
	private String debit_subj_name1;
	/**
	 * 第二个借方科目ID
	 */
	private Long debit_subj_id2;
	/**
	 * 第二个借方科目编码
	 */
	private String debit_subj_code2;
	/**
	 * 第二个借方科目名称
	 */
	private String debit_subj_name2;
	/**
	 * 第二个借方科目方向
	 */
	private Integer debit_subj_dire2;
	/**
	 * 第二个借方科目是否辅助核算
	 */
	private Integer debit_is_check2;
	/**
	 * 第二个借方科目是否现金流量标注
	 */
	private String debit_is_cash2;
	/**
	 * 借方科目类别
	 */
	private String debit_subj_type_code2;
	/**
	 * 第二个借方科目性质
	 */
	private String debit_subj_nature_code2;
	/**
	 * 第三个借方科目ID
	 */
	private Long debit_subj_id3;
	/**
	 * 第三个借方科目编码
	 */
	private String debit_subj_code3;
	/**
	 * 第三个借方科目名称
	 */
	private String debit_subj_name3;
	/**
	 * 第三个借方科目方向
	 */
	private Integer debit_subj_dire3;
	/**
	 * 第三个借方科目是否辅助核算
	 */
	private Integer debit_is_check3;
	/**
	 * 第三个借方科目是否现金流量标注
	 */
	private String debit_is_cash3;
	/**
	 * 借方科目类别
	 */
	private String debit_subj_type_code3;
	/**
	 * 第三个借方科目性质
	 */
	private String debit_subj_nature_code3;
	/**
	 * 第四个借方科目ID
	 */
	private Long debit_subj_id4;
	/**
	 * 第四个借方科目编码
	 */
	private String debit_subj_code4;
	/**
	 * 第四个借方科目名称
	 */
	private String debit_subj_name4;
	/**
	 * 第四个借方科目方向
	 */
	private Integer debit_subj_dire4;
	/**
	 * 第四个借方科目是否辅助核算
	 */
	private Integer debit_is_check4;
	/**
	 * 第四个借方科目是否现金流量标注
	 */
	private String debit_is_cash4;
	/**
	 * 第四个借方科目类别
	 */
	private String debit_subj_type_code4;
	/**
	 * 第四个借方科目性质
	 */
	private String debit_subj_nature_code4;
	/**
	 * 第五个借方科目ID
	 */
	private Long debit_subj_id5;
	/**
	 * 第五个借方科目编码
	 */
	private String debit_subj_code5;
	/**
	 * 第五个借方科目名称
	 */
	private String debit_subj_name5;
	/**
	 * 第五个借方科目方向
	 */
	private Integer debit_subj_dire5;
	/**
	 * 第五个借方科目是否辅助核算
	 */
	private Integer debit_is_check5;
	/**
	 * 第五个借方科目是否现金流量标注
	 */
	private String debit_is_cash5;
	/**
	 * 第五个借方科目类别
	 */
	private String debit_subj_type_code5;
	/**
	 * 第五个借方科目性质
	 */
	private String debit_subj_nature_code5;
	/**
	 * 第一个贷方科目ID
	 */
	private Long credit_subj_id1;
	/**
	 * 第一个贷方科目编码
	 */
	private String credit_subj_code1;
	/**
	 * 第一个贷方科目名称
	 */
	private String credit_subj_name1;
	/**
	 * 第一个贷方科目方向
	 */
	private Integer credit_subj_dire1;
	/**
	 * 第一个贷方科目是否辅助核算
	 */
	private Integer credit_is_check1;
	/**
	 * 第一个贷方科目是否现金流量标注
	 */
	private String credit_is_cash1;
	/**
	 * 第一个贷方科目类别
	 */
	private String credit_subj_type_code1;
	/**
	 * 第一个贷方科目性质
	 */
	private String credit_subj_nature_code1;
	/**
	 * 第二个贷方科目ID
	 */
	private Long credit_subj_id2;
	/**
	 * 第二个贷方科目编码
	 */
	private String credit_subj_code2;
	/**
	 * 第二个贷方科目名称
	 */
	private String credit_subj_name2;
	/**
	 * 第二个贷方科目方向
	 */
	private Integer credit_subj_dire2;
	/**
	 * 第二个贷方科目是否辅助核算
	 */
	private Integer credit_is_check2;
	/**
	 * 第二个贷方科目是否现金流量标注
	 */
	private String credit_is_cash2;
	/**
	 * 第二个贷方科目类别
	 */
	private String credit_subj_type_code2;
	/**
	 * 第二个贷方科目性质
	 */
	private String credit_subj_nature_code2;
	/**
	 * 第三个贷方科目ID
	 */
	private Long credit_subj_id3;
	/**
	 * 第三个贷方科目编码
	 */
	private String credit_subj_code3;
	/**
	 * 第三个贷方科目名称
	 */
	private String credit_subj_name3;
	/**
	 * 第三个贷方科目方向
	 */
	private Integer credit_subj_dire3;
	/**
	 * 第三个贷方科目是否辅助核算
	 */
	private Integer credit_is_check3;
	/**
	 * 第三个贷方科目是否现金流量标注
	 */
	private String credit_is_cash3;
	/**
	 * 第三个贷方科目类别
	 */
	private String credit_subj_type_code3;
	/**
	 * 第三个贷方科目性质
	 */
	private String credit_subj_nature_code3;
	/**
	 * 第四个贷方科目ID
	 */
	private Long credit_subj_id4;
	/**
	 * 第四个贷方科目编码
	 */
	private String credit_subj_code4;
	/**
	 * 第四个贷方科目名称
	 */
	private String credit_subj_name4;
	/**
	 * 第四个贷方科目方向
	 */
	private Integer credit_subj_dire4;
	/**
	 * 第四个贷方科目是否辅助核算
	 */
	private Integer credit_is_check4;
	/**
	 * 第四个贷方科目是否现金流量标注
	 */
	private String credit_is_cash4;
	/**
	 * 第四个贷方科目类别
	 */
	private String credit_subj_type_code4;
	/**
	 * 第四个贷方科目性质
	 */
	private String credit_subj_nature_code4;
	/**
	 * 第五个贷方科目ID
	 */
	private Long credit_subj_id5;
	/**
	 * 第五个贷方科目编码
	 */
	private String credit_subj_code5;
	/**
	 * 第五个贷方科目名称
	 */
	private String credit_subj_name5;
	/**
	 * 第五个贷方科目方向
	 */
	private Integer credit_subj_dire5;
	/**
	 * 第五个贷方科目是否辅助核算
	 */
	private Integer credit_is_check5;
	/**
	 * 第五个贷方科目是否现金流量标注
	 */
	private String credit_is_cash5;
	/**
	 * 第五个贷方科目类别
	 */
	private String credit_subj_type_code5;
	/**
	 * 第五个贷方科目性质
	 */
	private String credit_subj_nature_code5;
	
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
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
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_type_code() {
		return template_type_code;
	}
	public void setTemplate_type_code(String template_type_code) {
		this.template_type_code = template_type_code;
	}
	public String getVouch_type_code() {
		return vouch_type_code;
	}
	public void setVouch_type_code(String vouch_type_code) {
		this.vouch_type_code = vouch_type_code;
	}
	public String getVouch_type_name() {
		return vouch_type_name;
	}
	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getSource_id() {
		return source_id;
	}
	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}
	public String getSource_code() {
		return source_code;
	}
	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public Long getDebit_subj_id1() {
		return debit_subj_id1;
	}
	public void setDebit_subj_id1(Long debit_subj_id1) {
		this.debit_subj_id1 = debit_subj_id1;
	}
	public String getDebit_subj_code1() {
		return debit_subj_code1;
	}
	public void setDebit_subj_code1(String debit_subj_code1) {
		this.debit_subj_code1 = debit_subj_code1;
	}
	public String getDebit_subj_name1() {
		return debit_subj_name1;
	}
	public void setDebit_subj_name1(String debit_subj_name1) {
		this.debit_subj_name1 = debit_subj_name1;
	}
	public Long getDebit_subj_id2() {
		return debit_subj_id2;
	}
	public void setDebit_subj_id2(Long debit_subj_id2) {
		this.debit_subj_id2 = debit_subj_id2;
	}
	public String getDebit_subj_code2() {
		return debit_subj_code2;
	}
	public void setDebit_subj_code2(String debit_subj_code2) {
		this.debit_subj_code2 = debit_subj_code2;
	}
	public String getDebit_subj_name2() {
		return debit_subj_name2;
	}
	public void setDebit_subj_name2(String debit_subj_name2) {
		this.debit_subj_name2 = debit_subj_name2;
	}
	public Integer getDebit_subj_dire1() {
		return debit_subj_dire1;
	}
	public void setDebit_subj_dire1(Integer debit_subj_dire1) {
		this.debit_subj_dire1 = debit_subj_dire1;
	}
	public Integer getDebit_is_check1() {
		return debit_is_check1;
	}
	public void setDebit_is_check1(Integer debit_is_check1) {
		this.debit_is_check1 = debit_is_check1;
	}
	public String getDebit_is_cash1() {
		return debit_is_cash1;
	}
	public void setDebit_is_cash1(String debit_is_cash1) {
		this.debit_is_cash1 = debit_is_cash1;
	}
	public String getDebit_subj_nature_code1() {
		return debit_subj_nature_code1;
	}
	public void setDebit_subj_nature_code1(String debit_subj_nature_code1) {
		this.debit_subj_nature_code1 = debit_subj_nature_code1;
	}
	public Integer getDebit_subj_dire2() {
		return debit_subj_dire2;
	}
	public void setDebit_subj_dire2(Integer debit_subj_dire2) {
		this.debit_subj_dire2 = debit_subj_dire2;
	}
	public Integer getDebit_is_check2() {
		return debit_is_check2;
	}
	public void setDebit_is_check2(Integer debit_is_check2) {
		this.debit_is_check2 = debit_is_check2;
	}
	public String getDebit_is_cash2() {
		return debit_is_cash2;
	}
	public void setDebit_is_cash2(String debit_is_cash2) {
		this.debit_is_cash2 = debit_is_cash2;
	}
	public String getDebit_subj_nature_code2() {
		return debit_subj_nature_code2;
	}
	public void setDebit_subj_nature_code2(String debit_subj_nature_code2) {
		this.debit_subj_nature_code2 = debit_subj_nature_code2;
	}
	public String getDebit_subj_type_code1() {
		return debit_subj_type_code1;
	}
	public void setDebit_subj_type_code1(String debit_subj_type_code1) {
		this.debit_subj_type_code1 = debit_subj_type_code1;
	}
	public String getDebit_subj_type_code2() {
		return debit_subj_type_code2;
	}
	public void setDebit_subj_type_code2(String debit_subj_type_code2) {
		this.debit_subj_type_code2 = debit_subj_type_code2;
	}
	public Long getDebit_subj_id3() {
		return debit_subj_id3;
	}
	public void setDebit_subj_id3(Long debit_subj_id3) {
		this.debit_subj_id3 = debit_subj_id3;
	}
	public String getDebit_subj_code3() {
		return debit_subj_code3;
	}
	public void setDebit_subj_code3(String debit_subj_code3) {
		this.debit_subj_code3 = debit_subj_code3;
	}
	public String getDebit_subj_name3() {
		return debit_subj_name3;
	}
	public void setDebit_subj_name3(String debit_subj_name3) {
		this.debit_subj_name3 = debit_subj_name3;
	}
	public Integer getDebit_subj_dire3() {
		return debit_subj_dire3;
	}
	public void setDebit_subj_dire3(Integer debit_subj_dire3) {
		this.debit_subj_dire3 = debit_subj_dire3;
	}
	public Integer getDebit_is_check3() {
		return debit_is_check3;
	}
	public void setDebit_is_check3(Integer debit_is_check3) {
		this.debit_is_check3 = debit_is_check3;
	}
	public String getDebit_is_cash3() {
		return debit_is_cash3;
	}
	public void setDebit_is_cash3(String debit_is_cash3) {
		this.debit_is_cash3 = debit_is_cash3;
	}
	public String getDebit_subj_type_code3() {
		return debit_subj_type_code3;
	}
	public void setDebit_subj_type_code3(String debit_subj_type_code3) {
		this.debit_subj_type_code3 = debit_subj_type_code3;
	}
	public String getDebit_subj_nature_code3() {
		return debit_subj_nature_code3;
	}
	public void setDebit_subj_nature_code3(String debit_subj_nature_code3) {
		this.debit_subj_nature_code3 = debit_subj_nature_code3;
	}
	public Long getCredit_subj_id1() {
		return credit_subj_id1;
	}
	public void setCredit_subj_id1(Long credit_subj_id1) {
		this.credit_subj_id1 = credit_subj_id1;
	}
	public String getCredit_subj_code1() {
		return credit_subj_code1;
	}
	public void setCredit_subj_code1(String credit_subj_code1) {
		this.credit_subj_code1 = credit_subj_code1;
	}
	public String getCredit_subj_name1() {
		return credit_subj_name1;
	}
	public void setCredit_subj_name1(String credit_subj_name1) {
		this.credit_subj_name1 = credit_subj_name1;
	}
	public Integer getCredit_subj_dire1() {
		return credit_subj_dire1;
	}
	public void setCredit_subj_dire1(Integer credit_subj_dire1) {
		this.credit_subj_dire1 = credit_subj_dire1;
	}
	public Integer getCredit_is_check1() {
		return credit_is_check1;
	}
	public void setCredit_is_check1(Integer credit_is_check1) {
		this.credit_is_check1 = credit_is_check1;
	}
	public String getCredit_is_cash1() {
		return credit_is_cash1;
	}
	public void setCredit_is_cash1(String credit_is_cash1) {
		this.credit_is_cash1 = credit_is_cash1;
	}
	public String getCredit_subj_type_code1() {
		return credit_subj_type_code1;
	}
	public void setCredit_subj_type_code1(String credit_subj_type_code1) {
		this.credit_subj_type_code1 = credit_subj_type_code1;
	}
	public String getCredit_subj_nature_code1() {
		return credit_subj_nature_code1;
	}
	public void setCredit_subj_nature_code1(String credit_subj_nature_code1) {
		this.credit_subj_nature_code1 = credit_subj_nature_code1;
	}
	public Long getDebit_subj_id4() {
		return debit_subj_id4;
	}
	public void setDebit_subj_id4(Long debit_subj_id4) {
		this.debit_subj_id4 = debit_subj_id4;
	}
	public String getDebit_subj_code4() {
		return debit_subj_code4;
	}
	public void setDebit_subj_code4(String debit_subj_code4) {
		this.debit_subj_code4 = debit_subj_code4;
	}
	public String getDebit_subj_name4() {
		return debit_subj_name4;
	}
	public void setDebit_subj_name4(String debit_subj_name4) {
		this.debit_subj_name4 = debit_subj_name4;
	}
	public Integer getDebit_subj_dire4() {
		return debit_subj_dire4;
	}
	public void setDebit_subj_dire4(Integer debit_subj_dire4) {
		this.debit_subj_dire4 = debit_subj_dire4;
	}
	public Integer getDebit_is_check4() {
		return debit_is_check4;
	}
	public void setDebit_is_check4(Integer debit_is_check4) {
		this.debit_is_check4 = debit_is_check4;
	}
	public String getDebit_is_cash4() {
		return debit_is_cash4;
	}
	public void setDebit_is_cash4(String debit_is_cash4) {
		this.debit_is_cash4 = debit_is_cash4;
	}
	public String getDebit_subj_type_code4() {
		return debit_subj_type_code4;
	}
	public void setDebit_subj_type_code4(String debit_subj_type_code4) {
		this.debit_subj_type_code4 = debit_subj_type_code4;
	}
	public String getDebit_subj_nature_code4() {
		return debit_subj_nature_code4;
	}
	public void setDebit_subj_nature_code4(String debit_subj_nature_code4) {
		this.debit_subj_nature_code4 = debit_subj_nature_code4;
	}
	public Long getDebit_subj_id5() {
		return debit_subj_id5;
	}
	public void setDebit_subj_id5(Long debit_subj_id5) {
		this.debit_subj_id5 = debit_subj_id5;
	}
	public String getDebit_subj_code5() {
		return debit_subj_code5;
	}
	public void setDebit_subj_code5(String debit_subj_code5) {
		this.debit_subj_code5 = debit_subj_code5;
	}
	public String getDebit_subj_name5() {
		return debit_subj_name5;
	}
	public void setDebit_subj_name5(String debit_subj_name5) {
		this.debit_subj_name5 = debit_subj_name5;
	}
	public Integer getDebit_subj_dire5() {
		return debit_subj_dire5;
	}
	public void setDebit_subj_dire5(Integer debit_subj_dire5) {
		this.debit_subj_dire5 = debit_subj_dire5;
	}
	public Integer getDebit_is_check5() {
		return debit_is_check5;
	}
	public void setDebit_is_check5(Integer debit_is_check5) {
		this.debit_is_check5 = debit_is_check5;
	}
	public String getDebit_is_cash5() {
		return debit_is_cash5;
	}
	public void setDebit_is_cash5(String debit_is_cash5) {
		this.debit_is_cash5 = debit_is_cash5;
	}
	public String getDebit_subj_type_code5() {
		return debit_subj_type_code5;
	}
	public void setDebit_subj_type_code5(String debit_subj_type_code5) {
		this.debit_subj_type_code5 = debit_subj_type_code5;
	}
	public String getDebit_subj_nature_code5() {
		return debit_subj_nature_code5;
	}
	public void setDebit_subj_nature_code5(String debit_subj_nature_code5) {
		this.debit_subj_nature_code5 = debit_subj_nature_code5;
	}
	public Long getCredit_subj_id2() {
		return credit_subj_id2;
	}
	public void setCredit_subj_id2(Long credit_subj_id2) {
		this.credit_subj_id2 = credit_subj_id2;
	}
	public String getCredit_subj_code2() {
		return credit_subj_code2;
	}
	public void setCredit_subj_code2(String credit_subj_code2) {
		this.credit_subj_code2 = credit_subj_code2;
	}
	public String getCredit_subj_name2() {
		return credit_subj_name2;
	}
	public void setCredit_subj_name2(String credit_subj_name2) {
		this.credit_subj_name2 = credit_subj_name2;
	}
	public Integer getCredit_subj_dire2() {
		return credit_subj_dire2;
	}
	public void setCredit_subj_dire2(Integer credit_subj_dire2) {
		this.credit_subj_dire2 = credit_subj_dire2;
	}
	public Integer getCredit_is_check2() {
		return credit_is_check2;
	}
	public void setCredit_is_check2(Integer credit_is_check2) {
		this.credit_is_check2 = credit_is_check2;
	}
	public String getCredit_is_cash2() {
		return credit_is_cash2;
	}
	public void setCredit_is_cash2(String credit_is_cash2) {
		this.credit_is_cash2 = credit_is_cash2;
	}
	public String getCredit_subj_type_code2() {
		return credit_subj_type_code2;
	}
	public void setCredit_subj_type_code2(String credit_subj_type_code2) {
		this.credit_subj_type_code2 = credit_subj_type_code2;
	}
	public String getCredit_subj_nature_code2() {
		return credit_subj_nature_code2;
	}
	public void setCredit_subj_nature_code2(String credit_subj_nature_code2) {
		this.credit_subj_nature_code2 = credit_subj_nature_code2;
	}
	public Long getCredit_subj_id3() {
		return credit_subj_id3;
	}
	public void setCredit_subj_id3(Long credit_subj_id3) {
		this.credit_subj_id3 = credit_subj_id3;
	}
	public String getCredit_subj_code3() {
		return credit_subj_code3;
	}
	public void setCredit_subj_code3(String credit_subj_code3) {
		this.credit_subj_code3 = credit_subj_code3;
	}
	public String getCredit_subj_name3() {
		return credit_subj_name3;
	}
	public void setCredit_subj_name3(String credit_subj_name3) {
		this.credit_subj_name3 = credit_subj_name3;
	}
	public Integer getCredit_subj_dire3() {
		return credit_subj_dire3;
	}
	public void setCredit_subj_dire3(Integer credit_subj_dire3) {
		this.credit_subj_dire3 = credit_subj_dire3;
	}
	public Integer getCredit_is_check3() {
		return credit_is_check3;
	}
	public void setCredit_is_check3(Integer credit_is_check3) {
		this.credit_is_check3 = credit_is_check3;
	}
	public String getCredit_is_cash3() {
		return credit_is_cash3;
	}
	public void setCredit_is_cash3(String credit_is_cash3) {
		this.credit_is_cash3 = credit_is_cash3;
	}
	public String getCredit_subj_type_code3() {
		return credit_subj_type_code3;
	}
	public void setCredit_subj_type_code3(String credit_subj_type_code3) {
		this.credit_subj_type_code3 = credit_subj_type_code3;
	}
	public String getCredit_subj_nature_code3() {
		return credit_subj_nature_code3;
	}
	public void setCredit_subj_nature_code3(String credit_subj_nature_code3) {
		this.credit_subj_nature_code3 = credit_subj_nature_code3;
	}
	public Long getCredit_subj_id4() {
		return credit_subj_id4;
	}
	public void setCredit_subj_id4(Long credit_subj_id4) {
		this.credit_subj_id4 = credit_subj_id4;
	}
	public String getCredit_subj_code4() {
		return credit_subj_code4;
	}
	public void setCredit_subj_code4(String credit_subj_code4) {
		this.credit_subj_code4 = credit_subj_code4;
	}
	public String getCredit_subj_name4() {
		return credit_subj_name4;
	}
	public void setCredit_subj_name4(String credit_subj_name4) {
		this.credit_subj_name4 = credit_subj_name4;
	}
	public Integer getCredit_subj_dire4() {
		return credit_subj_dire4;
	}
	public void setCredit_subj_dire4(Integer credit_subj_dire4) {
		this.credit_subj_dire4 = credit_subj_dire4;
	}
	public Integer getCredit_is_check4() {
		return credit_is_check4;
	}
	public void setCredit_is_check4(Integer credit_is_check4) {
		this.credit_is_check4 = credit_is_check4;
	}
	public String getCredit_is_cash4() {
		return credit_is_cash4;
	}
	public void setCredit_is_cash4(String credit_is_cash4) {
		this.credit_is_cash4 = credit_is_cash4;
	}
	public String getCredit_subj_type_code4() {
		return credit_subj_type_code4;
	}
	public void setCredit_subj_type_code4(String credit_subj_type_code4) {
		this.credit_subj_type_code4 = credit_subj_type_code4;
	}
	public String getCredit_subj_nature_code4() {
		return credit_subj_nature_code4;
	}
	public void setCredit_subj_nature_code4(String credit_subj_nature_code4) {
		this.credit_subj_nature_code4 = credit_subj_nature_code4;
	}
	public Long getCredit_subj_id5() {
		return credit_subj_id5;
	}
	public void setCredit_subj_id5(Long credit_subj_id5) {
		this.credit_subj_id5 = credit_subj_id5;
	}
	public String getCredit_subj_code5() {
		return credit_subj_code5;
	}
	public void setCredit_subj_code5(String credit_subj_code5) {
		this.credit_subj_code5 = credit_subj_code5;
	}
	public String getCredit_subj_name5() {
		return credit_subj_name5;
	}
	public void setCredit_subj_name5(String credit_subj_name5) {
		this.credit_subj_name5 = credit_subj_name5;
	}
	public Integer getCredit_subj_dire5() {
		return credit_subj_dire5;
	}
	public void setCredit_subj_dire5(Integer credit_subj_dire5) {
		this.credit_subj_dire5 = credit_subj_dire5;
	}
	public Integer getCredit_is_check5() {
		return credit_is_check5;
	}
	public void setCredit_is_check5(Integer credit_is_check5) {
		this.credit_is_check5 = credit_is_check5;
	}
	public String getCredit_is_cash5() {
		return credit_is_cash5;
	}
	public void setCredit_is_cash5(String credit_is_cash5) {
		this.credit_is_cash5 = credit_is_cash5;
	}
	public String getCredit_subj_type_code5() {
		return credit_subj_type_code5;
	}
	public void setCredit_subj_type_code5(String credit_subj_type_code5) {
		this.credit_subj_type_code5 = credit_subj_type_code5;
	}
	public String getCredit_subj_nature_code5() {
		return credit_subj_nature_code5;
	}
	public void setCredit_subj_nature_code5(String credit_subj_nature_code5) {
		this.credit_subj_nature_code5 = credit_subj_nature_code5;
	}
	
}
