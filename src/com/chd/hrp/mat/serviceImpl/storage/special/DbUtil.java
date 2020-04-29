package com.chd.hrp.mat.serviceImpl.storage.special;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.chd.base.exception.SysException;

public class DbUtil {
	
	private static Logger logger = Logger.getLogger(DbUtil.class);
	
	public static Connection getConnection(){  
        String driverClass = null;  
        String jdbcUrl = null;  
        String user = null;  
        String password = null;  

        //读取类路径下的 jdbc.properties 文件，配置文件放在src包下
        InputStream in = DbUtil.class.getClassLoader().getResourceAsStream("sqlServer.properties");  
        Properties properties = new Properties();  
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        driverClass = properties.getProperty("driver");  
        jdbcUrl = properties.getProperty("jdbcUrl");  
        user = properties.getProperty("username");  
        password = properties.getProperty("password");  

        Driver driver = null;
        try {
            driver = (Driver) Class.forName(driverClass).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new SysException("{\"error\":\"加载配置文件失败\"}");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  

        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);  

        //通过 Driver 的 connect 方法获取数据库连接.   
        Connection connection = null;
        try {
            connection = driver.connect(jdbcUrl, info);
            if(connection==null){
				logger.info("sql数据库连接失败");
				throw new SysException("{\"error\":\"sql数据库连接失败\"}");
			}else {
				logger.info("sql数据库连接成功");
			}
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SysException("{\"error\":\"sql数据库连接失败\"}");
        }  
        return connection;  
    }
 
	public void close(CallableStatement sp,Connection conn){
		if (sp != null) {
			try {
				sp.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				sp = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				conn = null;
			}
		}
	}
}
