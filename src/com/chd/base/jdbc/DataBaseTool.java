/** 
* 2014-4-25 
* DataBaseTool.java 
* author:pengjin
*/ 
package com.chd.base.jdbc; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class DataBaseTool {

	private ResultSet rs;

	private PreparedStatement stm;

	private static DataBaseTool dbt = new DataBaseTool();
	private static Logger logger = Logger.getLogger(DataBaseTool.class);
	public static DataBaseTool getDataBaseTool() {
		return dbt;
	}

	public DataBaseTool() {

	}

	/**
	 * 返回结果集,返回PreparedStatement
	
	 */
	public PreparedStatement insertDeleteUpdateQuery(Connection conn, String sql) {
		try {
			// CachedRowSet cst=new CachedRowSet();
			stm = conn.prepareStatement(sql);
			// stm.executeQuery();
			// cst.populate(rs);
			return stm;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 检查数据,返回boolean

	 */
	public boolean queryByCode(Connection conn, String sql) {
		boolean returnFlag = false;
		try {
			// / CachedRowSet cst=new CachedRowSet();
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			while (rs.next()) {
				returnFlag = true;
			}
			// cst.populate(rs);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stm != null) {
					stm.close();
					stm = null;
				}
			}
			catch (SQLException e) {
				logger.error(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnFlag;
	}

	/**
	 * 关闭连接
	 */
	public void closeConnection(ResultSet rs, PreparedStatement stm) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stm != null) {
				stm.close();
				stm = null;
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public String getConnectionExists(String dataSourceName,String drvierName,String url,String userName,String passWord,String dataComp,String dataCopy){
		String returnStr=""; 
		Connection conn=null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		int count = 0;
		try {
			Class.forName(drvierName);
		// 	conn=DriverManager.getConnection(url+";DatabaseName="+dataBaseName,userName,passWord);
			conn=DriverManager.getConnection(url,userName,passWord);
		 	if(conn!=null){
		 		//验证单位、账套
		 		if(dataSourceName.equals("hrp") && dataComp!=null && dataCopy!=null ){
		 			psm=conn.prepareStatement("select top 1 1 from sys_copy where comp_code='"+dataComp+"' and copy_code='"+dataCopy+"' ");
		 			rs = psm.executeQuery();
		 			if (rs.next()) {
						count = rs.getInt(1);
					}
		 			if(count==0){
		 				returnStr="，单位编码或账套编码不正确！";
		 			}
		 		}else{
		 			returnStr="";
		 		}
		 	}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage(),e);
			returnStr=e.getMessage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage(),e);
			returnStr=e.getMessage();
		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
				if(psm!=null){
					psm.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.error(e.getMessage(),e);
				returnStr=e.getMessage();
			}
		}
		return returnStr;
	}

}
