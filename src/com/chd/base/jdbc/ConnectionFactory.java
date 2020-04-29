/** 
* 2014-6-5 
* ConnectionFactory.java 
* author:pengjin
*/ 
package com.chd.base.jdbc; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class ConnectionFactory {
	private static Logger logger = Logger.getLogger(ConnectionFactory.class);
	
	private static ThreadLocal<Connection> threadLocalSystem = new ThreadLocal<Connection>();
//	private static ThreadLocal<Connection> threadLocalDrp = new ThreadLocal<Connection>();
//	private static ThreadLocal<Connection> threadLocalHrp = new ThreadLocal<Connection>();
//	private static ThreadLocal<Connection> threadLocalHis = new ThreadLocal<Connection>();

	private static Properties config = ConfigInit.getConfiguration();

	/**
	 * 获得system数据源的链接
	 * @return
	 * @throws Exception
	 */
	public static Connection getSystemConnection() throws Exception {
		Connection connSystem=threadLocalSystem.get();
		if(connSystem==null){
			connSystem=DruidDataSourceFactory.createDataSource(config).getConnection();
			threadLocalSystem.set(connSystem);
		}
		
		
		return connSystem;
	}
	
	/**
	 * 获得drp数据源的链接
	 * @return
	 * @throws Exception
	 */
//	public static Connection getDrpConnection() throws Exception {
//		Connection connDrp=threadLocalDrp.get();
//		if(connDrp==null){
//			if(config.getProperty( "drpUrl" )==null || config.getProperty( "drpUrl" ).trim().equals("")){
//				return null;
//			}
//			Class.forName( config.getProperty( "drpDriver" ));
//			connDrp = DriverManager.getConnection(  config.getProperty( "drpUrl" ),
//																	config.getProperty( "drpUsername" ),
//																	config.getProperty( "drpPassword" ));
//			threadLocalDrp.set(connDrp);
//		}	
//		return connDrp;
//	}
	
	/**
	 * 获得hrp数据源的链接
	 * @return
	 * @throws Exception
	 */
//	public static Connection getHrpConnection() throws Exception {
//		Connection connHrp=threadLocalHrp.get();
//		if(connHrp==null){
//			if(config.getProperty( "hrpUrl" )==null || config.getProperty( "hrpUrl" ).trim().equals("")){
//				return null;
//			}
//			Class.forName( config.getProperty( "hrpDriver" ));
//			connHrp = DriverManager.getConnection(  config.getProperty( "hrpUrl" ),
//																	config.getProperty( "hrpUsername" ),
//																	config.getProperty( "hrpPassword" ));
//			threadLocalHrp.set(connHrp);
//		}	
//		return connHrp;
//	}
	
	/**
	 * 获得HIS数据源的链接
	 * @return
	 * @throws Exception
	 */
//	public static Connection getHisConnection() throws Exception {
//		Connection connHis = threadLocalHis.get();
//		if( connHis == null ){
//			if(config.getProperty( "hisUrl" )==null || config.getProperty( "hisUrl" ).trim().equals("")){
//				return null;
//			}
//			Class.forName( config.getProperty( "hisDriver" ));
//			connHis = DriverManager.getConnection(  config.getProperty( "hisUrl" ),
//																	config.getProperty( "hisUsername" ),
//																	config.getProperty( "hisPassword" ));
//			threadLocalHis.set( connHis );
//		}
//		return connHis ;
//	}
	
	//关闭所有驱动连接
	public static void closeAllConn(Connection conn,String sys,Statement pstm,ResultSet rs){
		try {
			if(rs!=null){rs.close();rs=null;}
			if(pstm!=null){pstm.close();pstm=null;}
			if(conn != null){
				conn.close();
				conn=null;
				if(sys.equals("system")){
					threadLocalSystem.remove();
				}
//				if(sys.equals("hrp")){
//					threadLocalHrp.remove();
//				}else if(sys.equals("his")){
//					threadLocalHis.remove();
//				}else if(sys.equals("drp")){
//					threadLocalDrp.remove();
//				}else if(sys.equals("system")){
//					threadLocalSystem.remove();
//				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
