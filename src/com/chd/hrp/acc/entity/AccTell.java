/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;
  
import java.io.Serializable;
import java.util.Date;

/**
* @Title. @Description.
* 出纳账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccTell implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 出纳账ID
	 */
	private String tell_id;
	/**
	 * 集团ID
	 */
	private String group_id;
	/**
	 * 医院ID
	 */
	private String hos_id;
	
	private Long bank_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	
	private String vouch_date;
	
	private Double tell_end_os;
	
	private Double acct_end_os;
	
	private Double rest_end_os;
	
	private Double tell_debit;
	
	private Double acct_debit;
	
	private Double rest_debit;
	
    private Double tell_credit;
    
    private Double acct_credit;
    
    private Double rest_credit;
    
    private Double price;
    
    private String tell_type_code;
    private String subj_code;
    //出纳管理 摘要ID
    private Long sid;
    
    private Long user_id;
	
	 
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public Double getTell_end_os() {
		return tell_end_os;
	}
	public void setTell_end_os(Double tell_end_os) {
		this.tell_end_os = tell_end_os;
	}
	public Double getAcct_end_os() {
		return acct_end_os;
	}
	public void setAcct_end_os(Double acct_end_os) {
		this.acct_end_os = acct_end_os;
	}
	public Double getRest_end_os() {
		return rest_end_os;
	}
	public void setRest_end_os(Double rest_end_os) {
		this.rest_end_os = rest_end_os;
	}
	public Double getTell_debit() {
		return tell_debit;
	}
	public void setTell_debit(Double tell_debit) {
		this.tell_debit = tell_debit;
	}
	public Double getAcct_debit() {
		return acct_debit;
	}
	public void setAcct_debit(Double acct_debit) {
		this.acct_debit = acct_debit;
	}
	public Double getRest_debit() {
		return rest_debit;
	}
	public void setRest_debit(Double rest_debit) {
		this.rest_debit = rest_debit;
	}
	public Double getTell_credit() {
		return tell_credit;
	}
	public void setTell_credit(Double tell_credit) {
		this.tell_credit = tell_credit;
	}
	public Double getAcct_credit() {
		return acct_credit;
	}
	public void setAcct_credit(Double acct_credit) {
		this.acct_credit = acct_credit;
	}
	public Double getRest_credit() {
		return rest_credit;
	}
	public void setRest_credit(Double rest_credit) {
		this.rest_credit = rest_credit;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getVouch_date() {
		return vouch_date;
	}
	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}

	/**
	 * 现金科目
	 */
	private String cash_subj_code;
	/**
	 * 对方科目
	 */
	private String other_subj_id;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 附件数量
	 */
	private long att_num;
	/**
	 * 借方金额
	 */
	private double debit;
	/**
	 * 贷方金额
	 */
	private double credit;
	/**
	 * 币种编码
	 */
	private String cur_code;
	/**
	 * 借方金额外币
	 */
	private double debit_w;
	/**
	 * 贷方金额外币
	 */
	private double credit_w;
	/**
	 * 余额外币
	 */
	private double bal_w;
	/**
	 * 票据号
	 */
	private String check_no;
	/**
	 * 业务类型
	 */
	private String busi_type;
	/**
	 * 单据号
	 */
	private String business_no;
	/**
	 * 发生日期
	 */
	private Date occur_date;
	/**
	 * 结算方式
	 */
	private String pay_type_code;
	/**
	 * 制单人
	 */
	private double create_user;
	
	/**
	 * 制单人名称
	 * */
	private String create_user_name;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	/**
	 * 确认人
	 */
	private double con_user;
	
	/**
	 * 确认人名称
	 */
	private String con_user_name;

	/**
	 * 确认日期
	 */
	private Date con_date;
	/**
	 * 是否对账
	 */
	private Integer is_check;
	/**
	 * 对账人
	 */
	private double check_user;
	/**
	 * 对账日期
	 */
	private Date check_date;
	/**
	 * 是否初始
	 */
	private Integer is_init;
	/**
	 * 辅助核算ID
	 */
	private double vouch_check_id;
	/**
	 * 凭证ID
	 */
	private double vouch_id;
	/**
	 * 是否外部导入
	 */
	private Integer is_import;
	
	/**
	 * 科目ID
	 */
	private Long subj_id;
	
	/**
	 * 科目名称
	 */
	private String subj_name;
	
	/**
	 * 结算方式编码
	 */
	
	private String pay_code;
	/**
	 * 结算方式名称
	 */
	private String pay_name;
	
	/**
	 * 余额
	 */
	private double bal;
	

	/**
	 * 凭证是否导入
	 */
	private String is_por;
	
	/**
	 * 凭证编号
	 */
	private String vouch_no;
	
	/**
	 * 现金出纳账制单日期
	 */
	private String s_create_date;
	
	
	/**
	 * 现金出纳账制单日期
	 */
	private String s_occur_date;
	
	private Long check_id;
	private Long veri_check_id ;//对账ID
	private double yCheck_money;//已对账金额
	private double wCheck_money;//未对账金额
	
	
	public Long getCheck_id() {
		return check_id;
	}
	public void setCheck_id(Long check_id) {
		this.check_id = check_id;
	}
	public Long getVeri_check_id() {
		return veri_check_id;
	}
	public void setVeri_check_id(Long veri_check_id) {
		this.veri_check_id = veri_check_id;
	}
	public double getyCheck_money() {
		return yCheck_money;
	}
	public void setyCheck_money(double yCheck_money) {
		this.yCheck_money = yCheck_money;
	}
	public double getwCheck_money() {
		return wCheck_money;
	}
	public void setwCheck_money(double wCheck_money) {
		this.wCheck_money = wCheck_money;
	}
	public String getS_occur_date() {
		return s_occur_date;
	}
	public void setS_occur_date(String s_occur_date) {
		this.s_occur_date = s_occur_date;
	}

	/**
	 * 现金出纳账确认日期
	 * */
	private String s_con_date;
	
	/**
	 * 是否确认
	 * */
	private int is_con;
	
	/**
	 * 对方科目名称
	 * */
	private String other_subj_name;
	
	/**
	 * 对方科目编码
	 * */
	private String other_subj_code;
	
	private Date tmp_date;
	private double tell_amt_debit;
	private double acct_amt_debit;
	private double rest_amt_debit;
	private double tell_amt_credit;
	private double acct_amt_credit;
	private double rest_amt_credit;
	private double tell_result;
	private double acct_result;
	private double rest_result ;

	private String is_checks;
	
	private Long is_auto;
	
	private Long veri_id;
	
	private String subj_dire;
	
	private String tell_number;
	
	

	public String getTell_number() {
		return tell_number;
	}
	public void setTell_number(String tell_number) {
		this.tell_number = tell_number;
	}
	public String getSubj_dire() {
		return subj_dire;
	}
	public void setSubj_dire(String subj_dire) {
		this.subj_dire = subj_dire;
	}
	public Long getVeri_id() {
		return veri_id;
	}
	public void setVeri_id(Long veri_id) {
		this.veri_id = veri_id;
	}
	
	public Long getIs_auto() {
		return is_auto;
	}
	public void setIs_auto(Long is_auto) {
		this.is_auto = is_auto;
	}
	
	public String getIs_checks() {
		return is_checks;
	}
	public void setIs_checks(String is_checks) {
		this.is_checks = is_checks;
	}
	/**
	 * 获取 出纳账ID
	 */	
	
	/**
	 * 获取 集团ID
	 */	
	
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	public String getTell_id() {
		return tell_id;
	}
	public void setTell_id(String tell_id) {
		this.tell_id = tell_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getHos_id() {
		return hos_id;
	}
	public void setHos_id(String hos_id) {
		this.hos_id = hos_id;
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
	 * 设置 现金科目
	 */
	public void setCash_subj_code(String value) {
		this.cash_subj_code = value;
	}
	/**
	 * 获取制单人名称
	 */
	public String getCreate_user_name() {
		return create_user_name;
	}
	/**
	 * 设置 制单人名称
	 */
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	/**
	 * 获取 确认人名称
	 */
	public String getCon_user_name() {
		return con_user_name;
	}
	
	/**
	 * 设置 确认人名称
	 */
	public void setCon_user_name(String con_user_name) {
		this.con_user_name = con_user_name;
	}
	/**
	 * 获取 现金科目
	 */	
	public String getCash_subj_code() {
		return this.cash_subj_code;
	}
	/**
	 * 设置 对方科目
	 */
	public void setOther_subj_id(String value) {
		this.other_subj_id = value;
	}
	/**
	 * 获取 对方科目
	 */	
	public String getOther_subj_id() {
		return this.other_subj_id;
	}
	/**
	 * 设置 摘要
	 */
	public void setSummary(String value) {
		this.summary = value;
	}
	/**
	 * 获取 摘要
	 */	
	public String getSummary() {
		return this.summary;
	}
	/**
	 * 设置 附件数量
	 */
	public void setAtt_num(long value) {
		this.att_num = value;
	}
	/**
	 * 获取 附件数量
	 */	
	public long getAtt_num() {
		return this.att_num;
	}
	/**
	 * 设置 借方金额
	 */
	public void setDebit(double value) {
		this.debit = value;
	}
	/**
	 * 获取 借方金额
	 */	
	public double getDebit() {
		return this.debit;
	}
	/**
	 * 设置 贷方金额
	 */
	public void setCredit(double value) {
		this.credit = value;
	}
	/**
	 * 获取 贷方金额
	 */	
	public double getCredit() {
		return this.credit;
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
	 * 设置 借方金额外币
	 */
	public void setDebit_w(double value) {
		this.debit_w = value;
	}
	/**
	 * 获取 借方金额外币
	 */	
	public double getDebit_w() {
		return this.debit_w;
	}
	/**
	 * 设置 贷方金额外币
	 */
	public void setCredit_w(double value) {
		this.credit_w = value;
	}
	/**
	 * 获取 贷方金额外币
	 */	
	public double getCredit_w() {
		return this.credit_w;
	}
	/**
	 * 设置 余额外币
	 */
	public void setBal_w(double value) {
		this.bal_w = value;
	}
	/**
	 * 获取 余额外币
	 */	
	public double getBal_w() {
		return this.bal_w;
	}
	/**
	 * 设置 票据号
	 */
	public void setCheck_no(String value) {
		this.check_no = value;
	}
	/**
	 * 获取 票据号
	 */	
	public String getCheck_no() {
		return this.check_no;
	}
	/**
	 * 设置 业务类型
	 */
	public void setBusi_type(String value) {
		this.busi_type = value;
	}
	/**
	 * 获取 业务类型
	 */	
	public String getBusi_type() {
		return this.busi_type;
	}
	/**
	 * 设置 单据号
	 */
	public void setBusiness_no(String value) {
		this.business_no = value;
	}
	/**
	 * 获取 单据号
	 */	
	public String getBusiness_no() {
		return this.business_no;
	}
	/**
	 * 设置 发生日期
	 */
	public void setOccur_date(Date value) {
		this.occur_date = value;
	}
	/**
	 * 获取 发生日期
	 */	
	public Date getOccur_date() {
		return this.occur_date;
	}
	/**
	 * 设置 结算方式
	 */
	public void setPay_type_code(String value) {
		this.pay_type_code = value;
	}
	/**
	 * 获取 结算方式
	 */	
	public String getPay_type_code() {
		return this.pay_type_code;
	}
	/**
	 * 设置 制单人
	 */
	public void setCreate_user(double value) {
		this.create_user = value;
	}
	/**
	 * 获取 制单人
	 */	
	public double getCreate_user() {
		return this.create_user;
	}
	/**
	 * 设置 制单日期
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	/**
	 * 获取 制单日期
	 */	
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	 * 设置 确认人
	 */
	public void setCon_user(double value) {
		this.con_user = value;
	}
	/**
	 * 获取 确认人
	 */	
	public double getCon_user() {
		return this.con_user;
	}
	/**
	 * 设置 确认日期
	 */
	public void setCon_date(Date value) {
		this.con_date = value;
	}
	/**
	 * 获取 确认日期
	 */	
	public Date getCon_date() {
		return this.con_date;
	}
	/**
	 * 设置 是否对账
	 */
	public void setIs_check(Integer value) {
		this.is_check = value;
	}
	/**
	 * 获取 是否对账
	 */	
	public Integer getIs_check() {
		return this.is_check;
	}
	/**
	 * 设置 对账人
	 */
	public void setCheck_user(double value) {
		this.check_user = value;
	}
	/**
	 * 获取 对账人
	 */	
	public double getCheck_user() {
		return this.check_user;
	}
	/**
	 * 设置 对账日期
	 */
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	/**
	 * 获取 对账日期
	 */	
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	 * 设置 是否初始
	 */
	public void setIs_init(Integer value) {
		this.is_init = value;
	}
	/**
	 * 获取 是否初始
	 */	
	public Integer getIs_init() {
		return this.is_init;
	}
	/**
	 * 设置 辅助核算ID
	 */
	public void setVouch_check_id(double value) {
		this.vouch_check_id = value;
	}
	/**
	 * 获取 辅助核算ID
	 */	
	public double getVouch_check_id() {
		return this.vouch_check_id;
	}
	/**
	 * 设置 凭证ID
	 */
	public void setVouch_id(double value) {
		this.vouch_id = value;
	}
	/**
	 * 获取 凭证ID
	 */	
	public double getVouch_id() {
		return this.vouch_id;
	}
	/**
	 * 设置 是否外部导入
	 */
	public void setIs_import(Integer value) {
		this.is_import = value;
	}
	/**
	 * 获取 是否外部导入
	 */	
	public Integer getIs_import() {
		return this.is_import;
	}
	
	/**
	 * 获取科目Id
	 */
	public Long getSubj_id() {
		return subj_id;
	}
	
	/**
	 * 设置科目Id
	 */
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}
	
	/**
	 * 获取科目名称
	 */
	public String getSubj_name() {
		return subj_name;
	}
	
	/**
	 * 设置科目名称
	 */
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	
	/**
	 * 获取结算方式编码
	 */
	
	
	/**
	 * 获取结算方式名称
	 */
	public String getPay_name() {
		return pay_name;
	}
	
	public String getPay_code() {
		return pay_code;
	}
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	/**
	 * 设置结算方式名称
	 * */
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	
	private String error_type;
	
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public Date getTmp_date() {
		return tmp_date;
	}
	public void setTmp_date(Date tmp_date) {
		this.tmp_date = tmp_date;
	}
	public double getTell_amt_debit() {
		return tell_amt_debit;
	}
	public void setTell_amt_debit(double tell_amt_debit) {
		this.tell_amt_debit = tell_amt_debit;
	}
	public double getAcct_amt_debit() {
		return acct_amt_debit;
	}
	public void setAcct_amt_debit(double acct_amt_debit) {
		this.acct_amt_debit = acct_amt_debit;
	}
	public double getRest_amt_debit() {
		return rest_amt_debit;
	}
	public void setRest_amt_debit(double rest_amt_debit) {
		this.rest_amt_debit = rest_amt_debit;
	}
	public double getTell_amt_credit() {
		return tell_amt_credit;
	}
	public void setTell_amt_credit(double tell_amt_credit) {
		this.tell_amt_credit = tell_amt_credit;
	}
	public double getAcct_amt_credit() {
		return acct_amt_credit;
	}
	public void setAcct_amt_credit(double acct_amt_credit) {
		this.acct_amt_credit = acct_amt_credit;
	}
	public double getRest_amt_credit() {
		return rest_amt_credit;
	}
	public void setRest_amt_credit(double rest_amt_credit) {
		this.rest_amt_credit = rest_amt_credit;
	}
	public double getTell_result() {
		return tell_result;
	}
	public void setTell_result(double tell_result) {
		this.tell_result = tell_result;
	}
	public double getAcct_result() {
		return acct_result;
	}
	public void setAcct_result(double acct_result) {
		this.acct_result = acct_result;
	}
	public double getRest_result() {
		return rest_result;
	}
	public void setRest_result(double rest_result) {
		this.rest_result = rest_result;
	}
	/**
	 * 获取 余额
	 * */
	public double getBal() {
		return bal;
	}
	
	/**
	 * 设置 余额
	 * */
	public void setBal(double bal) {
		this.bal = bal;
	}
	
	/**
	 * 获取 是否导入
	 * */
	public String getIs_por() {
		return is_por;
	}
	
	/**
	 * 设置 是否导入
	 * */
	public void setIs_por(String is_por) {
		this.is_por = is_por;
	}
	
	/**
	 * 获取 凭证编号
	 * */
	public String getVouch_no() {
		return vouch_no;
	}
	
	/**
	 * 设置 凭证编号
	 * */
	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}
	
	
	/**
	 * 获取 现金出纳账制单日期
	 * */
	public String getS_create_date() {
		return s_create_date;
	}
	
	/**
	 * 设置 现金出纳账制单日期
	 * */
	public void setS_create_date(String s_create_date) {
		this.s_create_date = s_create_date;
	}
	
	/**
	 * 获取 现金出纳账确认日期
	 * */
	public String getS_con_date() {
		return s_con_date;
	}
	/**
	 * 设置 现金出纳账确认日期
	 * */
	public void setS_con_date(String s_con_date) {
		this.s_con_date = s_con_date;
	}
	
	/**
	 * 获取 是否确认
	 * */
	public int getIs_con() {
		return is_con;
	}
	
	/**
	 * 设置 是否确认
	 * */
	public void setIs_con(int is_con) {
		this.is_con = is_con;
	}
	
	/**
	 * 获取 对方科目名称
	 * */
	public String getOther_subj_name() {
		return other_subj_name;
	}
	
	/**
	 * 设置 对方科目名称
	 * */
	public void setOther_subj_name(String other_subj_name) {
		this.other_subj_name = other_subj_name;
	}

	/**
	 * 获取  对方科目编码
	 * */
	public String getOther_subj_code() {
		return other_subj_code;
	}

	/**
	 * 设置 对方科目编码
	 * */
	public void setOther_subj_code(String other_subj_code) {
		this.other_subj_code = other_subj_code;
	}
	
	
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public String getTell_type_code() {
		return tell_type_code;
	}
	public void setTell_type_code(String tell_type_code) {
		this.tell_type_code = tell_type_code;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
}