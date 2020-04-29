package com.chd.base.tool;

/**
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2018年4月17日 下午4:17:09
 * @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class MetaData {
	// 获得指定列的列名
	private String columnName;
	// 获得指定列的数据类型
	private int columnType;
	// 获得指定列的数据类型名
	private String columnTypeName;
	// 在数据库中类型的最大字符个数
	private int columnDisplaySize;
	// 默认的列的标题
	private String columnLabel;
	// 某列类型的精确度(类型的长度)
	private int precision;
	// 小数点后的位数
	private int scale;
	// 是否自动递增
	private boolean autoInctemen;
	// 在数据库中是否为货币型
	private boolean currency;
	// 是否为空
	private int isNullable;
	// 是否为只读
	private boolean readOnly;
	// 能否出现在where中
	private boolean searchable;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public void setColumnDisplaySize(int columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public boolean isAutoInctemen() {
		return autoInctemen;
	}

	public void setAutoInctemen(boolean autoInctemen) {
		this.autoInctemen = autoInctemen;
	}

	public boolean isCurrency() {
		return currency;
	}

	public void setCurrency(boolean currency) {
		this.currency = currency;
	}

	public int getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(int isNullable) {
		this.isNullable = isNullable;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

}
