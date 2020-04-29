/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 人员奖金明细数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostEmpBonusDetail implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8754435050642651419L;
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

	private String acc_year;
	
	private String acc_month;
	private String year_month;
	
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
	 * 奖金项1
	 */
	private double bonus1;
	/**
	 * 奖金项2
	 */
	private double bonus2;
	/**
	 * 奖金项3
	 */
	private double bonus3;
	/**
	 * 奖金项4
	 */
	private double bonus4;
	/**
	 * 奖金项5
	 */
	private double bonus5;
	/**
	 * 奖金项6
	 */
	private double bonus6;
	/**
	 * 奖金项7
	 */
	private double bonus7;
	/**
	 * 奖金项8
	 */
	private double bonus8;
	/**
	 * 奖金项9
	 */
	private double bonus9;
	/**
	 * 奖金项10
	 */
	private double bonus10;
	/**
	 * 奖金项11
	 */
	private double bonus11;
	/**
	 * 奖金项12
	 */
	private double bonus12;
	/**
	 * 奖金项13
	 */
	private double bonus13;
	/**
	 * 奖金项14
	 */
	private double bonus14;
	/**
	 * 奖金项15
	 */
	private double bonus15;
	/**
	 * 奖金项16
	 */
	private double bonus16;
	/**
	 * 奖金项17
	 */
	private double bonus17;
	/**
	 * 奖金项18
	 */
	private double bonus18;
	/**
	 * 奖金项19
	 */
	private double bonus19;
	/**
	 * 奖金项20
	 */
	private double bonus20;
	/**
	 * 奖金项21
	 */
	private double bonus21;
	/**
	 * 奖金项22
	 */
	private double bonus22;
	/**
	 * 奖金项23
	 */
	private double bonus23;
	/**
	 * 奖金项24
	 */
	private double bonus24;
	/**
	 * 奖金项25
	 */
	private double bonus25;
	/**
	 * 奖金项26
	 */
	private double bonus26;
	/**
	 * 奖金项27
	 */
	private double bonus27;
	/**
	 * 奖金项28
	 */
	private double bonus28;
	/**
	 * 奖金项29
	 */
	private double bonus29;
	/**
	 * 奖金项30
	 */
	private double bonus30;
	/**
	 * 导入验证信息
	 */
	private String error_type;
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
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public Long getDept_id() {
		return dept_id;
	}
	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}
	public Long getDept_no() {
		return dept_no;
	}
	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
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
	public Long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}
	public Long getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
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
	public String getEmp_kind_code() {
		return emp_kind_code;
	}
	public void setEmp_kind_code(String emp_kind_code) {
		this.emp_kind_code = emp_kind_code;
	}
	public String getEmp_kind_name() {
		return emp_kind_name;
	}
	public void setEmp_kind_name(String emp_kind_name) {
		this.emp_kind_name = emp_kind_name;
	}
	public double getBonus1() {
		return bonus1;
	}
	public void setBonus1(double bonus1) {
		this.bonus1 = bonus1;
	}
	public double getBonus2() {
		return bonus2;
	}
	public void setBonus2(double bonus2) {
		this.bonus2 = bonus2;
	}
	public double getBonus3() {
		return bonus3;
	}
	public void setBonus3(double bonus3) {
		this.bonus3 = bonus3;
	}
	public double getBonus4() {
		return bonus4;
	}
	public void setBonus4(double bonus4) {
		this.bonus4 = bonus4;
	}
	public double getBonus5() {
		return bonus5;
	}
	public void setBonus5(double bonus5) {
		this.bonus5 = bonus5;
	}
	public double getBonus6() {
		return bonus6;
	}
	public void setBonus6(double bonus6) {
		this.bonus6 = bonus6;
	}
	public double getBonus7() {
		return bonus7;
	}
	public void setBonus7(double bonus7) {
		this.bonus7 = bonus7;
	}
	public double getBonus8() {
		return bonus8;
	}
	public void setBonus8(double bonus8) {
		this.bonus8 = bonus8;
	}
	public double getBonus9() {
		return bonus9;
	}
	public void setBonus9(double bonus9) {
		this.bonus9 = bonus9;
	}
	public double getBonus10() {
		return bonus10;
	}
	public void setBonus10(double bonus10) {
		this.bonus10 = bonus10;
	}
	public double getBonus11() {
		return bonus11;
	}
	public void setBonus11(double bonus11) {
		this.bonus11 = bonus11;
	}
	public double getBonus12() {
		return bonus12;
	}
	public void setBonus12(double bonus12) {
		this.bonus12 = bonus12;
	}
	public double getBonus13() {
		return bonus13;
	}
	public void setBonus13(double bonus13) {
		this.bonus13 = bonus13;
	}
	public double getBonus14() {
		return bonus14;
	}
	public void setBonus14(double bonus14) {
		this.bonus14 = bonus14;
	}
	public double getBonus15() {
		return bonus15;
	}
	public void setBonus15(double bonus15) {
		this.bonus15 = bonus15;
	}
	public double getBonus16() {
		return bonus16;
	}
	public void setBonus16(double bonus16) {
		this.bonus16 = bonus16;
	}
	public double getBonus17() {
		return bonus17;
	}
	public void setBonus17(double bonus17) {
		this.bonus17 = bonus17;
	}
	public double getBonus18() {
		return bonus18;
	}
	public void setBonus18(double bonus18) {
		this.bonus18 = bonus18;
	}
	public double getBonus19() {
		return bonus19;
	}
	public void setBonus19(double bonus19) {
		this.bonus19 = bonus19;
	}
	public double getBonus20() {
		return bonus20;
	}
	public void setBonus20(double bonus20) {
		this.bonus20 = bonus20;
	}
	public double getBonus21() {
		return bonus21;
	}
	public void setBonus21(double bonus21) {
		this.bonus21 = bonus21;
	}
	public double getBonus22() {
		return bonus22;
	}
	public void setBonus22(double bonus22) {
		this.bonus22 = bonus22;
	}
	public double getBonus23() {
		return bonus23;
	}
	public void setBonus23(double bonus23) {
		this.bonus23 = bonus23;
	}
	public double getBonus24() {
		return bonus24;
	}
	public void setBonus24(double bonus24) {
		this.bonus24 = bonus24;
	}
	public double getBonus25() {
		return bonus25;
	}
	public void setBonus25(double bonus25) {
		this.bonus25 = bonus25;
	}
	public double getBonus26() {
		return bonus26;
	}
	public void setBonus26(double bonus26) {
		this.bonus26 = bonus26;
	}
	public double getBonus27() {
		return bonus27;
	}
	public void setBonus27(double bonus27) {
		this.bonus27 = bonus27;
	}
	public double getBonus28() {
		return bonus28;
	}
	public void setBonus28(double bonus28) {
		this.bonus28 = bonus28;
	}
	public double getBonus29() {
		return bonus29;
	}
	public void setBonus29(double bonus29) {
		this.bonus29 = bonus29;
	}
	public double getBonus30() {
		return bonus30;
	}
	public void setBonus30(double bonus30) {
		this.bonus30 = bonus30;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * @return the year_month
	 */
	public String getYear_month() {
		return year_month;
	}
	/**
	 * @param year_month the year_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	
}