package com.chd.hrp.ass.entity.tend;

import java.io.Serializable;

import java.util.*;

public class AssTendDetail  implements Serializable{
	
	
	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private Long bid_id;
	
	private Long detail_id;
	/**
	 * 设备单价
	 */
	private double bidd_price;
	/**
	 * 设备数量
	 */
	private double bidd_no;
	/**
	 * 招标编码
	 */
	private String bidd_bidcode;
	/**
	 * 资产变更ID
	 */
	private Long ass_no;
	/**
	 * 资产ID
	 */
	private Long ass_id;
	/**
	 * 资产名称
	 */
	private String ass_name;
	/**
	 * 型号
	 */
	private String ass_model;
	/**
	 * 规格
	 */
	private String ass_spec;
	/**
	 * 品牌
	 */
	private String ass_brand;
	/**
	 * 金额
	 */
	private double bidd_value;
	/**
	 * 申请科室ID
	 */
	private Long bidd_dept;
	
	/**
	 * 申请科室NAME
	 */
	private String bidd_deptname;
	
	/**
	 * 使用科室ID
	 */
	private Long bidd_usedept;
	
	/**
	 * 使用科室NAME
	 */
	private String bidd_usedeptname;
	/**
	 * 项目
	 */
	private String prj_name;
	
	/**
	 * 项目描述
	 */
	private String prj_desc;
	/**
	 * 项目联系人
	 */
	private String prj_contactor;
	/**
	 * 项目传真
	 */
	private Long prj_fox;
	
	/**
	 * 生产厂家
	 */
	private String fac_name;
	
	/**
	 * 生产厂家
	 */
	private Long fac_id;
	/**
	 * 中标价格
	 */
	private double bidd_winvalue;
	/**
	 * 备注
	 */
	private String bidd_remark;
	/**
	 * 预算数量
	 */
	private Long bidd_budgno;
	/**
	 * 预算单价
	 */
	private double bidd_budgprice;
	/**
	 * 预计到货日期
	 */
	private Date bidd_budgreachdate;
	/**
	 * 功能要求
	 */
	private String bidd_budgfunction;
	/**
	 * 评估信息
	 */
	private String bidd_budgevaluation;
	
	private Long bidd_budgid;
	private String bidd_budgidname;
	private String bidd_other1;
	private String bidd_other2;
	private String bidd_other3;
	public Long getBidd_budgid() {
		return bidd_budgid;
	}
	public void setBidd_budgid(Long bidd_budgid) {
		this.bidd_budgid = bidd_budgid;
	}
	public String getBidd_budgidname() {
		return bidd_budgidname;
	}
	public void setBidd_budgidname(String bidd_budgidname) {
		this.bidd_budgidname = bidd_budgidname;
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
	public Long getBid_id() {
		return bid_id;
	}
	public void setBid_id(Long bid_id) {
		this.bid_id = bid_id;
	}
	public Long getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}
	public double getBidd_price() {
		return bidd_price;
	}
	public void setBidd_price(double bidd_price) {
		this.bidd_price = bidd_price;
	}
	public double getBidd_no() {
		return bidd_no;
	}
	public void setBidd_no(double bidd_no) {
		this.bidd_no = bidd_no;
	}
	public String getBidd_bidcode() {
		return bidd_bidcode;
	}
	public void setBidd_bidcode(String bidd_bidcode) {
		this.bidd_bidcode = bidd_bidcode;
	}
	public Long getAss_no() {
		return ass_no;
	}
	public void setAss_no(Long ass_no) {
		this.ass_no = ass_no;
	}
	public Long getAss_id() {
		return ass_id;
	}
	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}
	public String getAss_name() {
		return ass_name;
	}
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}
	public String getAss_model() {
		return ass_model;
	}
	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}
	public String getAss_spec() {
		return ass_spec;
	}
	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}
	public String getAss_brand() {
		return ass_brand;
	}
	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}
	public double getBidd_value() {
		return bidd_value;
	}
	public void setBidd_value(double bidd_value) {
		this.bidd_value = bidd_value;
	}
	public Long getBidd_dept() {
		return bidd_dept;
	}
	public void setBidd_dept(Long bidd_dept) {
		this.bidd_dept = bidd_dept;
	}
	public String getBidd_deptname() {
		return bidd_deptname;
	}
	public void setBidd_deptname(String bidd_deptname) {
		this.bidd_deptname = bidd_deptname;
	}
	public Long getBidd_usedept() {
		return bidd_usedept;
	}
	public void setBidd_usedept(Long bidd_usedept) {
		this.bidd_usedept = bidd_usedept;
	}
	public String getBidd_usedeptname() {
		return bidd_usedeptname;
	}
	public void setBidd_usedeptname(String bidd_usedeptname) {
		this.bidd_usedeptname = bidd_usedeptname;
	}
	public String getPrj_name() {
		return prj_name;
	}
	public void setPrj_name(String prj_name) {
		this.prj_name = prj_name;
	}
	public String getPrj_desc() {
		return prj_desc;
	}
	public void setPrj_desc(String prj_desc) {
		this.prj_desc = prj_desc;
	}
	public String getPrj_contactor() {
		return prj_contactor;
	}
	public void setPrj_contactor(String prj_contactor) {
		this.prj_contactor = prj_contactor;
	}
	public Long getPrj_fox() {
		return prj_fox;
	}
	public void setPrj_fox(Long prj_fox) {
		this.prj_fox = prj_fox;
	}
	public String getFac_name() {
		return fac_name;
	}
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	public double getBidd_winvalue() {
		return bidd_winvalue;
	}
	public void setBidd_winvalue(double bidd_winvalue) {
		this.bidd_winvalue = bidd_winvalue;
	}
	public String getBidd_remark() {
		return bidd_remark;
	}
	public void setBidd_remark(String bidd_remark) {
		this.bidd_remark = bidd_remark;
	}
	public Long getBidd_budgno() {
		return bidd_budgno;
	}
	public void setBidd_budgno(Long bidd_budgno) {
		this.bidd_budgno = bidd_budgno;
	}
	public double getBidd_budgprice() {
		return bidd_budgprice;
	}
	public void setBidd_budgprice(double bidd_budgprice) {
		this.bidd_budgprice = bidd_budgprice;
	}
	public Date getBidd_budgreachdate() {
		return bidd_budgreachdate;
	}
	public void setBidd_budgreachdate(Date bidd_budgreachdate) {
		this.bidd_budgreachdate = bidd_budgreachdate;
	}
	public String getBidd_budgfunction() {
		return bidd_budgfunction;
	}
	public void setBidd_budgfunction(String bidd_budgfunction) {
		this.bidd_budgfunction = bidd_budgfunction;
	}
	public String getBidd_budgevaluation() {
		return bidd_budgevaluation;
	}
	public void setBidd_budgevaluation(String bidd_budgevaluation) {
		this.bidd_budgevaluation = bidd_budgevaluation;
	}
	public String getBidd_other1() {
		return bidd_other1;
	}
	public void setBidd_other1(String bidd_other1) {
		this.bidd_other1 = bidd_other1;
	}
	public String getBidd_other2() {
		return bidd_other2;
	}
	public void setBidd_other2(String bidd_other2) {
		this.bidd_other2 = bidd_other2;
	}
	public String getBidd_other3() {
		return bidd_other3;
	}
	public void setBidd_other3(String bidd_other3) {
		this.bidd_other3 = bidd_other3;
	}
	public Long getFac_id() {
		return fac_id;
	}
	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
	}
	@Override
	public String toString() {
		return "AssTendDetail [group_id=" + group_id + ", hos_id=" + hos_id
				+ ", copy_code=" + copy_code + ", bid_id=" + bid_id
				+ ", detail_id=" + detail_id + ", bidd_price=" + bidd_price
				+ ", bidd_no=" + bidd_no + ", bidd_bidcode=" + bidd_bidcode
				+ ", ass_no=" + ass_no + ", ass_id=" + ass_id + ", ass_name="
				+ ass_name + ", ass_model=" + ass_model + ", ass_spec="
				+ ass_spec + ", ass_brand=" + ass_brand + ", bidd_value="
				+ bidd_value + ", bidd_dept=" + bidd_dept + ", bidd_deptname="
				+ bidd_deptname + ", bidd_usedept=" + bidd_usedept
				+ ", bidd_usedeptname=" + bidd_usedeptname + ", prj_name="
				+ prj_name + ", prj_desc=" + prj_desc + ", prj_contactor="
				+ prj_contactor + ", prj_fox=" + prj_fox + ", fac_name="
				+ fac_name + ", bidd_winvalue=" + bidd_winvalue
				+ ", bidd_remark=" + bidd_remark + ", bidd_budgno="
				+ bidd_budgno + ", bidd_budgprice=" + bidd_budgprice
				+ ", bidd_budgreachdate=" + bidd_budgreachdate
				+ ", bidd_budgfunction=" + bidd_budgfunction
				+ ", bidd_budgevaluation=" + bidd_budgevaluation
				+ ", bidd_other1=" + bidd_other1 + ", bidd_other2="
				+ bidd_other2 + ", bidd_other3=" + bidd_other3 + "]";
	}
	
	
}
