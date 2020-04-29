package com.chd.hrp.pac.entity.cmitype;


/**
 * add by yh 20200310
 * 下拉数据源
 */
public class PACTTemplateDataSource {

	/**
	 * 集团ID
	 */
	private Long IFB_GROUPID;
	/**
	 * 医院ID
	 */
	private Long IFB_PrjName;
	/**
	 * 账套编码
	 */
	private String COPY_CODE;
	/**
	 * 数据源ID
	 */
	private Integer CS_Rowid;
	/**
	 * 取数函数编码
	 */
	private String CS_Code;
	/**
	 * 取数函数名称
	 */
	private String CS_Name;
	/**
	 * 下拉属性
	 */
	private int CS_Attribute;
	/**
	 * 数据源入参
	 */
	private String CS_Input;
	/**
	 * SQL
	 */
	private String CS_SQL;
	/**
	 * 数据源返回值
	 */
	private String CS_OutPut;
	public Long getIFB_GROUPID() {
		return IFB_GROUPID;
	}
	public void setIFB_GROUPID(Long iFB_GROUPID) {
		IFB_GROUPID = iFB_GROUPID;
	}
	public Long getIFB_PrjName() {
		return IFB_PrjName;
	}
	public void setIFB_PrjName(Long iFB_PrjName) {
		IFB_PrjName = iFB_PrjName;
	}
	public String getCOPY_CODE() {
		return COPY_CODE;
	}
	public void setCOPY_CODE(String cOPY_CODE) {
		COPY_CODE = cOPY_CODE;
	}
	public Integer getCS_Rowid() {
		return CS_Rowid;
	}
	public void setCS_Rowid(Integer cS_Rowid) {
		CS_Rowid = cS_Rowid;
	}
	public String getCS_Code() {
		return CS_Code;
	}
	public void setCS_Code(String cS_Code) {
		CS_Code = cS_Code;
	}
	public String getCS_Name() {
		return CS_Name;
	}
	public void setCS_Name(String cS_Name) {
		CS_Name = cS_Name;
	}
	public int getCS_Attribute() {
		return CS_Attribute;
	}
	public void setCS_Attribute(int cS_Attribute) {
		CS_Attribute = cS_Attribute;
	}
	public String getCS_Input() {
		return CS_Input;
	}
	public void setCS_Input(String cS_Input) {
		CS_Input = cS_Input;
	}
	public String getCS_SQL() {
		return CS_SQL;
	}
	public void setCS_SQL(String cS_SQL) {
		CS_SQL = cS_SQL;
	}
	public String getCS_OutPut() {
		return CS_OutPut;
	}
	public void setCS_OutPut(String cS_OutPut) {
		CS_OutPut = cS_OutPut;
	}
	
	
}
