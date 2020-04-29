package com.chd.hrp.pac.entity.cmitype;
/**
 * 合同接口来源维护
 */
public class PACTInterfaceType {
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
	 * 分类编码
	 */
	private String PIT_TypeCode;
	/**
	 * 分类名称
	 */
	private String PIT_TypeName;
	/**
	 * 接口方法名
	 */
	private String PI_MethodName;
	/**
	 * 类名
	 */
	private String PI_ClassName;
	/**
	 * 类描述
	 */
	private String PI_ClassDesc;
	/**
	 * bean名
	 */
	private String PI_BEANNAME;
	/**
	 * 来源系统
	 */
	private String PIT_SYSFrom;
	/**
	 * 是否有效
	 */
	private int PIT_IsActive;
	public Long getIFB_GROUPID() {
		return IFB_GROUPID;
	}
	public Long getIFB_PrjName() {
		return IFB_PrjName;
	}
	public String getCOPY_CODE() {
		return COPY_CODE;
	}
	public String getPIT_TypeCode() {
		return PIT_TypeCode;
	}
	public String getPIT_TypeName() {
		return PIT_TypeName;
	}
	public String getPI_MethodName() {
		return PI_MethodName;
	}
	public String getPI_ClassName() {
		return PI_ClassName;
	}
	public String getPI_ClassDesc() {
		return PI_ClassDesc;
	}
	public String getPI_BEANNAME() {
		return PI_BEANNAME;
	}
	public String getPIT_SYSFrom() {
		return PIT_SYSFrom;
	}
	public int getPIT_IsActive() {
		return PIT_IsActive;
	}
	public void setIFB_GROUPID(Long iFB_GROUPID) {
		IFB_GROUPID = iFB_GROUPID;
	}
	public void setIFB_PrjName(Long iFB_PrjName) {
		IFB_PrjName = iFB_PrjName;
	}
	public void setCOPY_CODE(String cOPY_CODE) {
		COPY_CODE = cOPY_CODE;
	}
	public void setPIT_TypeCode(String pIT_TypeCode) {
		PIT_TypeCode = pIT_TypeCode;
	}
	public void setPIT_TypeName(String pIT_TypeName) {
		PIT_TypeName = pIT_TypeName;
	}
	public void setPI_MethodName(String pI_MethodName) {
		PI_MethodName = pI_MethodName;
	}
	public void setPI_ClassName(String pI_ClassName) {
		PI_ClassName = pI_ClassName;
	}
	public void setPI_ClassDesc(String pI_ClassDesc) {
		PI_ClassDesc = pI_ClassDesc;
	}
	public void setPI_BEANNAME(String pI_BEANNAME) {
		PI_BEANNAME = pI_BEANNAME;
	}
	public void setPIT_SYSFrom(String pIT_SYSFrom) {
		PIT_SYSFrom = pIT_SYSFrom;
	}
	public void setPIT_IsActive(int pIT_IsActive) {
		PIT_IsActive = pIT_IsActive;
	}
	
	
}
