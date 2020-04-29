package com.chd.hrp.pac.entity.cmitype;

public class COSTBUSISOURECDICT {
	/**
	 * 数据来源类型(1.收入数据数据来源 2.成本数据数据来源)
	 */
	private Long BUSI_DATA_SOURCE_TYPE;
	/**
	 * 数据来源编码
	 */
	private String BUSI_DATA_SOURCE_CODE;
	/**
	 * 数据来源名称
	 */
	private String BUSI_DATA_SOURCE_NAME;
	/**
	 * 1.系统默认来源 0.用户添加来源
	 */
	private Long IS_SYS;
	
	public Long getBUSI_DATA_SOURCE_TYPE() {
		return BUSI_DATA_SOURCE_TYPE;
	}
	public String getBUSI_DATA_SOURCE_CODE() {
		return BUSI_DATA_SOURCE_CODE;
	}
	public String getBUSI_DATA_SOURCE_NAME() {
		return BUSI_DATA_SOURCE_NAME;
	}
	public Long getIS_SYS() {
		return IS_SYS;
	}
	public void setBUSI_DATA_SOURCE_TYPE(Long bUSI_DATA_SOURCE_TYPE) {
		BUSI_DATA_SOURCE_TYPE = bUSI_DATA_SOURCE_TYPE;
	}
	public void setBUSI_DATA_SOURCE_CODE(String bUSI_DATA_SOURCE_CODE) {
		BUSI_DATA_SOURCE_CODE = bUSI_DATA_SOURCE_CODE;
	}
	public void setBUSI_DATA_SOURCE_NAME(String bUSI_DATA_SOURCE_NAME) {
		BUSI_DATA_SOURCE_NAME = bUSI_DATA_SOURCE_NAME;
	}
	public void setIS_SYS(Long iS_SYS) {
		IS_SYS = iS_SYS;
	}

}
