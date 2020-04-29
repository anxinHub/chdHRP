/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 人员工资明细数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostEmpWageDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

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
	 * 统计年月
	 */
	
	private String acc_year;
	
	private String year_month;
	private String acc_month;
	/**
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	
	private String dept_code;

	private String dept_name;
	/**
	 * 职工ID
	 */
	private Long emp_id;
	/**
	 * 职工变更ID
	 */
	private Long emp_no;
	
	private String emp_code;
	
	private String emp_name;
	/**
	 * 职工分类编码
	 */
	private String emp_kind_code;
	
	
	private String emp_kind_name;
	/**
	 * 工资项1
	 */
	private double wage1;
	/**
	 * 工资项2
	 */
	private double wage2;
	/**
	 * 工资项3
	 */
	private double wage3;
	/**
	 * 工资项4
	 */
	private double wage4;
	/**
	 * 工资项5
	 */
	private double wage5;
	/**
	 * 工资项6
	 */
	private double wage6;
	/**
	 * 工资项7
	 */
	private double wage7;
	/**
	 * 工资项8
	 */
	private double wage8;
	/**
	 * 工资项9
	 */
	private double wage9;
	/**
	 * 工资项10
	 */
	private double wage10;
	/**
	 * 工资项11
	 */
	private double wage11;
	/**
	 * 工资项12
	 */
	private double wage12;
	/**
	 * 工资项13
	 */
	private double wage13;
	/**
	 * 工资项14
	 */
	private double wage14;
	/**
	 * 工资项15
	 */
	private double wage15;
	/**
	 * 工资项16
	 */
	private double wage16;
	/**
	 * 工资项17
	 */
	private double wage17;
	/**
	 * 工资项18
	 */
	private double wage18;
	/**
	 * 工资项19
	 */
	private double wage19;
	/**
	 * 工资项20
	 */
	private double wage20;
	/**
	 * 工资项21
	 */
	private double wage21;
	/**
	 * 工资项22
	 */
	private double wage22;
	/**
	 * 工资项23
	 */
	private double wage23;
	/**
	 * 工资项24
	 */
	private double wage24;
	/**
	 * 工资项25
	 */
	private double wage25;
	/**
	 * 工资项26
	 */
	private double wage26;
	/**
	 * 工资项27
	 */
	private double wage27;
	/**
	 * 工资项28
	 */
	private double wage28;
	/**
	 * 工资项29
	 */
	private double wage29;
	/**
	 * 工资项30
	 */
	private double wage30;
	/**
	 * 工资项31
	 */
	private double wage31;
	/**
	 * 工资项32
	 */
	private double wage32;
	/**
	 * 工资项33
	 */
	private double wage33;
	/**
	 * 工资项34
	 */
	private double wage34;
	/**
	 * 工资项35
	 */
	private double wage35;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_kind_name() {
		return emp_kind_name;
	}
	public void setEmp_kind_name(String emp_kind_name) {
		this.emp_kind_name = emp_kind_name;
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
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 职工ID
	 */
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	/**
	 * 获取 职工ID
	 */	
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	 * 设置 职工变更ID
	 */
	public void setEmp_no(Long value) {
		this.emp_no = value;
	}
	/**
	 * 获取 职工变更ID
	 */	
	public Long getEmp_no() {
		return this.emp_no;
	}
	/**
	 * 设置 职工分类编码
	 */
	public void setEmp_kind_code(String value) {
		this.emp_kind_code = value;
	}
	/**
	 * 获取 职工分类编码
	 */	
	public String getEmp_kind_code() {
		return this.emp_kind_code;
	}
	/**
	 * 设置 工资项1
	 */
	public void setWage1(double value) {
		this.wage1 = value;
	}
	/**
	 * 获取 工资项1
	 */	
	public double getWage1() {
		return this.wage1;
	}
	/**
	 * 设置 工资项2
	 */
	public void setWage2(double value) {
		this.wage2 = value;
	}
	/**
	 * 获取 工资项2
	 */	
	public double getWage2() {
		return this.wage2;
	}
	/**
	 * 设置 工资项3
	 */
	public void setWage3(double value) {
		this.wage3 = value;
	}
	/**
	 * 获取 工资项3
	 */	
	public double getWage3() {
		return this.wage3;
	}
	/**
	 * 设置 工资项4
	 */
	public void setWage4(double value) {
		this.wage4 = value;
	}
	/**
	 * 获取 工资项4
	 */	
	public double getWage4() {
		return this.wage4;
	}
	/**
	 * 设置 工资项5
	 */
	public void setWage5(double value) {
		this.wage5 = value;
	}
	/**
	 * 获取 工资项5
	 */	
	public double getWage5() {
		return this.wage5;
	}
	/**
	 * 设置 工资项6
	 */
	public void setWage6(double value) {
		this.wage6 = value;
	}
	/**
	 * 获取 工资项6
	 */	
	public double getWage6() {
		return this.wage6;
	}
	/**
	 * 设置 工资项7
	 */
	public void setWage7(double value) {
		this.wage7 = value;
	}
	/**
	 * 获取 工资项7
	 */	
	public double getWage7() {
		return this.wage7;
	}
	/**
	 * 设置 工资项8
	 */
	public void setWage8(double value) {
		this.wage8 = value;
	}
	/**
	 * 获取 工资项8
	 */	
	public double getWage8() {
		return this.wage8;
	}
	/**
	 * 设置 工资项9
	 */
	public void setWage9(double value) {
		this.wage9 = value;
	}
	/**
	 * 获取 工资项9
	 */	
	public double getWage9() {
		return this.wage9;
	}
	/**
	 * 设置 工资项10
	 */
	public void setWage10(double value) {
		this.wage10 = value;
	}
	/**
	 * 获取 工资项10
	 */	
	public double getWage10() {
		return this.wage10;
	}
	/**
	 * 设置 工资项11
	 */
	public void setWage11(double value) {
		this.wage11 = value;
	}
	/**
	 * 获取 工资项11
	 */	
	public double getWage11() {
		return this.wage11;
	}
	/**
	 * 设置 工资项12
	 */
	public void setWage12(double value) {
		this.wage12 = value;
	}
	/**
	 * 获取 工资项12
	 */	
	public double getWage12() {
		return this.wage12;
	}
	/**
	 * 设置 工资项13
	 */
	public void setWage13(double value) {
		this.wage13 = value;
	}
	/**
	 * 获取 工资项13
	 */	
	public double getWage13() {
		return this.wage13;
	}
	/**
	 * 设置 工资项14
	 */
	public void setWage14(double value) {
		this.wage14 = value;
	}
	/**
	 * 获取 工资项14
	 */	
	public double getWage14() {
		return this.wage14;
	}
	/**
	 * 设置 工资项15
	 */
	public void setWage15(double value) {
		this.wage15 = value;
	}
	/**
	 * 获取 工资项15
	 */	
	public double getWage15() {
		return this.wage15;
	}
	/**
	 * 设置 工资项16
	 */
	public void setWage16(double value) {
		this.wage16 = value;
	}
	/**
	 * 获取 工资项16
	 */	
	public double getWage16() {
		return this.wage16;
	}
	/**
	 * 设置 工资项17
	 */
	public void setWage17(double value) {
		this.wage17 = value;
	}
	/**
	 * 获取 工资项17
	 */	
	public double getWage17() {
		return this.wage17;
	}
	/**
	 * 设置 工资项18
	 */
	public void setWage18(double value) {
		this.wage18 = value;
	}
	/**
	 * 获取 工资项18
	 */	
	public double getWage18() {
		return this.wage18;
	}
	/**
	 * 设置 工资项19
	 */
	public void setWage19(double value) {
		this.wage19 = value;
	}
	/**
	 * 获取 工资项19
	 */	
	public double getWage19() {
		return this.wage19;
	}
	/**
	 * 设置 工资项20
	 */
	public void setWage20(double value) {
		this.wage20 = value;
	}
	/**
	 * 获取 工资项20
	 */	
	public double getWage20() {
		return this.wage20;
	}
	/**
	 * 设置 工资项21
	 */
	public void setWage21(double value) {
		this.wage21 = value;
	}
	/**
	 * 获取 工资项21
	 */	
	public double getWage21() {
		return this.wage21;
	}
	/**
	 * 设置 工资项22
	 */
	public void setWage22(double value) {
		this.wage22 = value;
	}
	/**
	 * 获取 工资项22
	 */	
	public double getWage22() {
		return this.wage22;
	}
	/**
	 * 设置 工资项23
	 */
	public void setWage23(double value) {
		this.wage23 = value;
	}
	/**
	 * 获取 工资项23
	 */	
	public double getWage23() {
		return this.wage23;
	}
	/**
	 * 设置 工资项24
	 */
	public void setWage24(double value) {
		this.wage24 = value;
	}
	/**
	 * 获取 工资项24
	 */	
	public double getWage24() {
		return this.wage24;
	}
	/**
	 * 设置 工资项25
	 */
	public void setWage25(double value) {
		this.wage25 = value;
	}
	/**
	 * 获取 工资项25
	 */	
	public double getWage25() {
		return this.wage25;
	}
	/**
	 * 设置 工资项26
	 */
	public void setWage26(double value) {
		this.wage26 = value;
	}
	/**
	 * 获取 工资项26
	 */	
	public double getWage26() {
		return this.wage26;
	}
	/**
	 * 设置 工资项27
	 */
	public void setWage27(double value) {
		this.wage27 = value;
	}
	/**
	 * 获取 工资项27
	 */	
	public double getWage27() {
		return this.wage27;
	}
	/**
	 * 设置 工资项28
	 */
	public void setWage28(double value) {
		this.wage28 = value;
	}
	/**
	 * 获取 工资项28
	 */	
	public double getWage28() {
		return this.wage28;
	}
	/**
	 * 设置 工资项29
	 */
	public void setWage29(double value) {
		this.wage29 = value;
	}
	/**
	 * 获取 工资项29
	 */	
	public double getWage29() {
		return this.wage29;
	}
	/**
	 * 设置 工资项30
	 */
	public void setWage30(double value) {
		this.wage30 = value;
	}
	/**
	 * 获取 工资项30
	 */	
	public double getWage30() {
		return this.wage30;
	}
	/**
	 * 设置 工资项31
	 */
	public void setWage31(double value) {
		this.wage31 = value;
	}
	/**
	 * 获取 工资项31
	 */	
	public double getWage31() {
		return this.wage31;
	}
	/**
	 * 设置 工资项32
	 */
	public void setWage32(double value) {
		this.wage32 = value;
	}
	/**
	 * 获取 工资项32
	 */	
	public double getWage32() {
		return this.wage32;
	}
	/**
	 * 设置 工资项33
	 */
	public void setWage33(double value) {
		this.wage33 = value;
	}
	/**
	 * 获取 工资项33
	 */	
	public double getWage33() {
		return this.wage33;
	}
	/**
	 * 设置 工资项34
	 */
	public void setWage34(double value) {
		this.wage34 = value;
	}
	/**
	 * 获取 工资项34
	 */	
	public double getWage34() {
		return this.wage34;
	}
	/**
	 * 设置 工资项35
	 */
	public void setWage35(double value) {
		this.wage35 = value;
	}
	/**
	 * 获取 工资项35
	 */	
	public double getWage35() {
		return this.wage35;
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
	/**
	 * @return the yaer_month
	 */
	public String getYear_month() {
		return year_month;
	}
	/**
	 * @param yaer_month the yaer_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	
}