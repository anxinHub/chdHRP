package com.chd.hrp.hr.util.enums;

public enum ComponentType {
	SELECT("01", "select"), // 下拉框
	TEXT("02", "text"), // 文本框
	DATE("03", "date"), // 日期框
	SUPLOAD("04", "file"), // 单文件上传
	MUPLOAD("05", "file"), // 多文件上传
	TEXTAREA("06", "text"), // 多行文本框
	INT("07", "int"), // 整数框
	NUMBER("08", "float");// 浮点框

	private final String code;

	private final String desc;

	ComponentType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static String getDesc(String code) {
		ComponentType[] comTypes = values();
		for (ComponentType comType : comTypes) {
			if (comType.getCode().equals(code)) {
				return comType.getDesc();
			}
		}
		return null;
	}

}
