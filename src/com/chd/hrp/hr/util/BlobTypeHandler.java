package com.chd.hrp.hr.util;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BlobTypeHandler extends BaseTypeHandler<String> {

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Blob blob = rs.getBlob(columnName);
		if(blob != null && blob.length() > 0){
			byte[] arr = blob.getBytes(1, (int)blob.length());
			String str = new String(arr);
			return str;
		}
		
		return null;
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Blob blob = rs.getBlob(columnIndex);
		if(blob != null && blob.length() > 0){
			byte[] arr = blob.getBytes(1, (int)blob.length());
			String str = new String(arr);
			return str;
		}
		
		return null;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Blob blob = cs.getBlob(columnIndex);
		if(blob != null && blob.length() > 0){
			byte[] arr = blob.getBytes(1, (int)blob.length());
			String str = new String(arr);
			return str;
		}
		
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		
	}

}
