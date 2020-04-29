package com.chd.hrp.hr.util;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class ClobTypeHandler extends BaseTypeHandler<String>{

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Clob clob = rs.getClob(columnName);
		return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Clob clob = rs.getClob(columnIndex);
		return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Clob clob = cs.getClob(columnIndex);
		return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		
	}

}
