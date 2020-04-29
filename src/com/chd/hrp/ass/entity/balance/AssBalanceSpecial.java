package com.chd.hrp.ass.entity.balance;

public class AssBalanceSpecial {

	
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
	
	
	private String year_month;
	
	private Integer ass_id;
	
	private Integer store_id;
	
	private Integer dept_id;
	

	private Integer begin_amount;
	
	private double begin_money;
	
	private Integer add_amount;
	
	private double add_money;
	
	
    private Integer dec_amount;
	
	private double  dec_money;
	
	
    private Integer end_amount;
	
	private double  end_money;

	
	
	private String ass_type_code;
	
	private String ass_type_name;
	
	private double  acc_begin_money;
	
	private double  acc_add_money;
	
	private double  acc_dec_money;
	
	private double  acc_end_money;
	
	private String ass_name;
	
	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_type_code() {
		return ass_type_code;
	}

	public void setAss_type_code(String ass_type_code) {
		this.ass_type_code = ass_type_code;
	}

	public String getAss_type_name() {
		return ass_type_name;
	}

	public void setAss_type_name(String ass_type_name) {
		this.ass_type_name = ass_type_name;
	}

	public double getAcc_begin_money() {
		return acc_begin_money;
	}

	public void setAcc_begin_money(double acc_begin_money) {
		this.acc_begin_money = acc_begin_money;
	}

	public double getAcc_add_money() {
		return acc_add_money;
	}

	public void setAcc_add_money(double acc_add_money) {
		this.acc_add_money = acc_add_money;
	}

	public double getAcc_dec_money() {
		return acc_dec_money;
	}

	public void setAcc_dec_money(double acc_dec_money) {
		this.acc_dec_money = acc_dec_money;
	}

	public double getAcc_end_money() {
		return acc_end_money;
	}

	public void setAcc_end_money(double acc_end_money) {
		this.acc_end_money = acc_end_money;
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

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	public Integer getAss_id() {
		return ass_id;
	}

	public void setAss_id(Integer ass_id) {
		this.ass_id = ass_id;
	}

	public Integer getStore_id() {
		return store_id;
	}

	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getBegin_amount() {
		return begin_amount;
	}

	public void setBegin_amount(Integer begin_amount) {
		this.begin_amount = begin_amount;
	}

	public double getBegin_money() {
		return begin_money;
	}

	public void setBegin_money(double begin_money) {
		this.begin_money = begin_money;
	}

	public Integer getAdd_amount() {
		return add_amount;
	}

	public void setAdd_amount(Integer add_amount) {
		this.add_amount = add_amount;
	}

	public double getAdd_money() {
		return add_money;
	}

	public void setAdd_money(double add_money) {
		this.add_money = add_money;
	}

	public Integer getDec_amount() {
		return dec_amount;
	}

	public void setDec_amount(Integer dec_amount) {
		this.dec_amount = dec_amount;
	}

	public double getDec_money() {
		return dec_money;
	}

	public void setDec_money(double dec_money) {
		this.dec_money = dec_money;
	}

	public Integer getEnd_amount() {
		return end_amount;
	}

	public void setEnd_amount(Integer end_amount) {
		this.end_amount = end_amount;
	}

	public double getEnd_money() {
		return end_money;
	}

	public void setEnd_money(double end_money) {
		this.end_money = end_money;
	}
	
}
