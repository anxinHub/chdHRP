package com.chd.hrp.acc.entity.payable;
 
import java.io.Serializable;
import java.util.Date;

public class BudgChargeApply implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String apply_code;
	private Date apply_date;
	private Integer dept_id;
	private Integer dept_no;
	private Integer proj_id;
	private Integer proj_no;
	private Integer emp_id;
	private Integer emp_no;
	private String dept_code;
	private String dept_name;
	private String proj_code;
	private String proj_name;
	private String emp_code;
	private String emp_name;
	private Double payment_amount;
	/*private Double offset_amount;
	private Double pay_amount;*/
	private String remark;
	private Integer maker;
	private String maker_name;
	private Date make_date; 
	private Integer checker;
	private String checker_name;
	private Date check_date;
	private Integer payer;
	private String payer_name;
	private Date pay_date;
	private String pay_way;
	private String state;
	private String pay_way_name;
	private String state_name;
	
	private String bank_name;
	private String bank_location;
	private Long source_id;
	private String source_code;
	private String source_name;
	private Integer payment_item_id;
	private Integer payment_item_no;
	private String payment_item_code;
	private String payment_item_name;
	private String use_apply_code;
	
	
	
	public String getUse_apply_code() {
		return use_apply_code;
	}
	public void setUse_apply_code(String use_apply_code) {
		this.use_apply_code = use_apply_code;
	}
	public Double getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(Double payment_amount) {
		this.payment_amount = payment_amount;
	}
	/*public Double getOffset_amount() {
		return offset_amount;
	}
	public void setOffset_amount(Double offset_amount) {
		this.offset_amount = offset_amount;
	}
	public Double getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(Double pay_amount) {
		this.pay_amount = pay_amount;
	}*/
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public Date getApply_date() {
		return apply_date;
	}
	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}
	public String getPayer_name() {
		return payer_name;
	}
	public void setPayer_name(String payer_name) {
		this.payer_name = payer_name;
	}
	public String getPay_way_name() {
		return pay_way_name;
	}
	public void setPay_way_name(String pay_way_name) {
		this.pay_way_name = pay_way_name;
	}
	public Integer getPayer() {
		return payer;
	}
	public void setPayer(Integer payer) {
		this.payer = payer;
	}
	public Date getPay_date() {
		return pay_date;
	}
	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getProj_code() {
		return proj_code;
	}
	public void setProj_code(String proj_code) {
		this.proj_code = proj_code;
	}
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getMaker_name() {
		return maker_name;
	}
	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}
	public String getChecker_name() {
		return checker_name;
	}
	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getHos_id() {
		return hos_id;
	}
	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Integer getDept_id() {
		return dept_id;
	}
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}
	public Integer getDept_no() {
		return dept_no;
	}
	public void setDept_no(Integer dept_no) {
		this.dept_no = dept_no;
	}
	public Integer getProj_no() {
		return proj_no;
	}
	public void setProj_no(Integer proj_no) {
		this.proj_no = proj_no;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getChecker() {
		return checker;
	}
	public void setChecker(Integer checker) {
		this.checker = checker;
	}
	public Date getCheck_date() {
		return check_date;
	}
	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getApply_code() {
		return apply_code;
	}
	public void setApply_code(String apply_code) {
		this.apply_code = apply_code;
	}
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	public Integer getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}
	public Integer getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(Integer emp_no) {
		this.emp_no = emp_no;
	}
	public Integer getMaker() {
		return maker;
	}
	public void setMaker(Integer maker) {
		this.maker = maker;
	}
	public Date getMake_date() {
		return make_date;
	}
	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}
	
	
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_location() {
		return bank_location;
	}
	public void setBank_location(String bank_location) {
		this.bank_location = bank_location;
	}
	
	/**
	* 设置 资金来源ID
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源ID
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
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
	public Integer getPayment_item_id() {
		return payment_item_id;
	}
	public void setPayment_item_id(Integer payment_item_id) {
		this.payment_item_id = payment_item_id;
	}
	public Integer getPayment_item_no() {
		return payment_item_no;
	}
	public void setPayment_item_no(Integer payment_item_no) {
		this.payment_item_no = payment_item_no;
	}
	public String getPayment_item_code() {
		return payment_item_code;
	}
	public void setPayment_item_code(String payment_item_code) {
		this.payment_item_code = payment_item_code;
	}
	public String getPayment_item_name() {
		return payment_item_name;
	}
	public void setPayment_item_name(String payment_item_name) {
		this.payment_item_name = payment_item_name;
	}
	 
	
}
