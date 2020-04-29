package com.chd.task.ass;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.json.JsonException;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.chd.base.SessionManager;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.service.check.mobile.AssCheckMobileService;
import com.chd.task.ass.bean.AssCard;
import com.chd.task.ass.bean.AssCardCheckData;
import com.chd.task.ass.bean.AssCardMainTainData;
import com.chd.task.ass.bean.AssCardMeasureData;
import com.chd.task.ass.bean.AssCardPollingData;
import com.chd.task.ass.bean.AssCardRepairsData;
import com.chd.task.ass.bean.DeptData;
import com.chd.task.ass.bean.Fault;
import com.chd.task.ass.bean.Info;
import com.chd.task.ass.bean.Location;
import com.chd.task.ass.bean.User;

/**
 * @Title:
 * @Description: 资产移动盘点定时传输数据业务处理
 * @Copyright: Copyright (c) 2017年4月27日 下午5:42:04
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Component
public class AssMobileInventory  implements Job {

	private static Logger logger = Logger.getLogger(AssMobileInventory.class);

	// 引入Service服务
	@Resource(name = "assCheckMobileService")
	private final AssCheckMobileService assCheckMobileService = null;

	// 注入Dao实例 变量名dao 要与配置文件中一致 配置文件为 dao.js
	protected Dao dao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");

	Properties p;

	public AssMobileInventory() {
		try {
			getP();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();

		}
	}

	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		String jobName = jobExecutionContext.getJobDetail().getKey().getName();
		System.out.println(":测试--->" + jobName);
		autoCheckData();
		Class c=AssMobileInventory.class;
		Method method;
		try {
			method = c.getMethod(jobName,null);
			 try {
				String str2= (String) method.invoke(this, null);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //invoke是执行该方法,并携带参数值
       
	//	doJob(jobName);
	}
	/**
	 * 获取连接SQLITE数据库配置信息
	 * 
	 * @throws Exception
	 */
	public void getP() throws Exception {
		p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream("ioc/task.properties"));
	}

	/**
	 * 定时读取盘点数据 并删除数据文件
	 * 
	 * <pre>
	 * 注解方式：
	 * "@Scheduled(fixedRate = 1000 * 60 * 5) "
	 * "@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次 "
	 * cron 说明
	 * "0 0 12 * * ?"    每天中午十二点触发 
	 * "0 15 10 ? * *"    每天早上10：15触发 
	 * "0 15 10 * * ?"    每天早上10：15触发 
	 * "0 15 10 * * ? *"    每天早上10：15触发 
	 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
	 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
	 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
	 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
	 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
	 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
	 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
	 * </pre>
	 */
	public void autoCheckData() {
		System.out.println("************autoCheckData()************");
		try {
			File[] files = findfilesBySqliteDb();
			for (File file : files) {
				importData(file);
				// 删除文件
				Files.deleteFile(file);
			}
			if (files.length > 0) {
				// 同步盘点单
				autoCheckDataGenerate();
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}
	//报修
	public void autoRepairsData() {
		try {
			File[] files = findfilesBySqliteDbRep();
			for (File file : files) {
				importRepData(file);
				// 删除文件
				Files.deleteFile(file);
			}
			if (files.length > 0) {
				//同步报修单
				autoRepairsDataGenerate();
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}
	//巡检
	public void autoPollingData() {
		try {
			File[] files = findfilesBySqliteDbPoll();
			for (File file : files) {
				importPollData(file);
				// 删除文件
				Files.deleteFile(file);
			}
			if (files.length > 0) {
				//同步巡检单
				autoPollingDataGenerate();
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}
	//保养
	public void autoMaintainData() {
		try {
			File[] files = findfilesBySqliteDbMainTain();
			for (File file : files) {
				importMainTainData(file);
				// 删除文件
				Files.deleteFile(file);
			}
			if (files.length > 0) {
				//同步保养单
				autoMainTainDataGenerate();
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}
	//计量
	public void autoMeasureData() {
		try {
			File[] files = findfilesBySqliteDbMeasure();
			for (File file : files) {
				importMeasureData(file);
				// 删除文件
				Files.deleteFile(file);
			}
			if (files.length > 0) {
				//同步计量单
				autoMeasureDataGenerate();
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 定时同步卡片信息并生成文件共移动APP读取
	 * 
	 * <pre>
	 * 注解方式：
	 * "@Scheduled(fixedRate = 1000 * 60 * 5) "
	 * "@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次 "
	 * cron 说明
	 * "0 0 12 * * ?"    每天中午十二点触发 
	 * "0 15 10 ? * *"    每天早上10：15触发 
	 * "0 15 10 * * ?"    每天早上10：15触发 
	 * "0 15 10 * * ? *"    每天早上10：15触发 
	 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
	 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
	 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
	 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
	 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
	 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
	 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
	 * </pre>
	 */
	public void autoCreateCardFile() {

		try {
			createCheckCardDataFile();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 定时同步卡片信息并生成文件共移动APP读取
	 * 
	 * <pre>
	 * 注解方式：
	 * "@Scheduled(fixedRate = 1000 * 60 * 5) "
	 * "@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次 "
	 * cron 说明
	 * "0 0 12 * * ?"    每天中午十二点触发 
	 * "0 15 10 ? * *"    每天早上10：15触发 
	 * "0 15 10 * * ?"    每天早上10：15触发 
	 * "0 15 10 * * ? *"    每天早上10：15触发 
	 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
	 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
	 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
	 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
	 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
	 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
	 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
	 * </pre>
	 */
	public void autoCreateDeptFile() {

		try {
			//同步科室信息
			createCheckDeptDataFile();
			//同步用户信息
			createCheckUserDataFile();
			//同步系统信息 集团 医院 账套
			createCheckInfoDataFile();
			//同步故障分类信息
			createFaultDataFile();
			//同步资产位置信息
			createLocationDataFile();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 导入盘点数据</br> 判断数据是否存在存在即不再重复添加</br>
	 * 
	 * @param file
	 *            文件
	 * 
	 *            <pre>
	 *    几种盘点单处理业务
	 *    1、移动端扫码  
	 *      上传数据   
	 *      在数据库中创建一张表 存储原始扫描数据  
	 *      手动做盘盈盘亏处理
	 *    2、把卡片信息同步到移动端  
	 *      在扫码时判断盘盈还是盘亏
	 *      整理数据后 上传数据
	 * </pre>
	 * @throws Exception
	 */
	private void importData(File file) throws Exception {
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String url = "jdbc:sqlite:" + file.getPath();
		// 查询盘点数据
		String sql = (p.getProperty("sql") == null) ? "select * from CheckData" : p.getProperty("sql");

		Class.forName(driver);
		Connection connection = null;
		Statement statement=null;
		ResultSet rs=null;
		try {
			// create a database connection
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);

			List<AssCardCheckData> checkData = new ArrayList<AssCardCheckData>();
			Set<String> setData = new HashSet<String>();
			while (rs.next()) {
				// 判断重复
				String key = rs.getString("dept_id") + rs.getString("card_ori");
				if (null == (Integer) rs.getInt("dept_id") || null == rs.getString("card_ori")) {
					continue;
				}
				if (setData.contains(key)) {
					continue;
				} else {
					setData.add(key);
				}

				AssCardCheckData card = new AssCardCheckData();
				card.setId(rs.getInt("_id"));
				card.setDept_id(rs.getInt("dept_id"));
				card.setCheck_name(rs.getString("name"));
				card.setAss_card_no(Strings.trim(rs.getString("card")));
				card.setBar_code(Strings.trim(rs.getString("card_ori")));
				card.setAss_id(rs.getInt("ass_id"));
				card.setCheck_date(DateUtil.stringToDate(rs.getString("time"), "yyyy-MM-dd"));
				card.setCheck_state(rs.getInt("state"));
				card.setGroup_id(rs.getInt("group_id"));
				card.setHos_id(rs.getInt("hos_id"));
				card.setCopy_code(rs.getString("copy_code"));
				checkData.add(card);
			}
			// 创建表 已经创建忽略
			dao.create(AssCardCheckData.class, false);

			Daos.migration(dao, AssCardCheckData.class, true, false, false); // 新增字段true,删除
			logger.debug(checkData.size()); // 字段false,检查索引false

			logger.debug("|-------------自动上传开始------------------|");
			dao.delete(checkData);
			dao.fastInsert(checkData);
			logger.debug("|-------------自动上传结束--------------|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {  
				rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	rs = null;  
            } 
			try {  
				statement.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	statement = null;  
            }  
			try {  
				connection.close();  
  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	connection = null;  
            }  

		}

	}
	////报修
	private void importRepData(File file) throws Exception {
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String url = "jdbc:sqlite:" + file.getPath();
		// 查询报修数据
		String sql = (p.getProperty("sqlR") == null) ? "select * from RepairsData" : p.getProperty("sqlR");

		Class.forName(driver);
		Connection connection = null;
		Statement statement=null;
		ResultSet rs=null;
		try {
			// create a database connection
			connection = DriverManager.getConnection(url);
		    statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);

			List<AssCardRepairsData> repData = new ArrayList<AssCardRepairsData>();
			Set<String> setData = new HashSet<String>();
			while (rs.next()) {
				// 判断重复
				String key = rs.getString("dept_id") + rs.getString("card")+ rs.getString("time")+ rs.getString("name");
				if (null == (Integer) rs.getInt("dept_id") || null == rs.getString("card") || null == rs.getString("time") || null == rs.getString("name")) {
					continue;
				}
				if (setData.contains(key)) {
					continue;
				} else {
					setData.add(key);
				}

				AssCardRepairsData card = new AssCardRepairsData();
				card.setId(rs.getInt("_id"));
				card.setFau_code(rs.getString("fault"));
				card.setLoc_code(rs.getString("position"));
				card.setEme_status(rs.getInt("level"));
				card.setRep_user(rs.getString("name"));
				card.setRep_user_id(rs.getInt("user_id"));
				card.setRep_dept(rs.getInt("dept_id"));
				card.setPhone(rs.getString("phone"));
				card.setFau_note(rs.getString("desc"));
				card.setApp_time(DateUtil.stringToDate(rs.getString("time"), "yyyy-MM-dd"));
				card.setAss_card_no(Strings.trim(rs.getString("card")));
				card.setAss_name(rs.getString("ass_name"));
				card.setIs_urg(rs.getInt("is_urg"));
				card.setState(rs.getInt("state"));
				card.setFlag(rs.getInt("flag"));
				card.setIs_any(rs.getInt("is_any"));
				card.setIs_up(rs.getInt("is_up"));
				card.setGroup_id(rs.getInt("group_id"));
				card.setHos_id(rs.getInt("hos_id"));
				card.setCopy_code(rs.getString("copy_code"));
				repData.add(card);
			}
			// 创建表 已经创建忽略
			dao.create(AssCardRepairsData.class, false);

			Daos.migration(dao, AssCardRepairsData.class, true, false, false); // 新增字段true,删除
			logger.debug(repData.size()); // 字段false,检查索引false

			logger.debug("|-------------自动上传开始R------------------|");
			dao.delete(repData);
			dao.fastInsert(repData);
			logger.debug("|-------------自动上传结束R--------------|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {  
				rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	rs = null;  
            } 
			try {  
				statement.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	statement = null;  
            }  
			try {  
				connection.close();  
  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	connection = null;  
            }  

		}

	}
	
	////巡检
	private void importPollData(File file) throws Exception {
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String url = "jdbc:sqlite:" + file.getPath();
		// 查询报修数据
		String sql = (p.getProperty("sqlP") == null) ? "select * from PollingData" : p.getProperty("sqlP");

		Class.forName(driver);
		Connection connection = null;
		Statement statement=null;
		ResultSet rs=null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);

			List<AssCardPollingData> repData = new ArrayList<AssCardPollingData>();
			Set<String> setData = new HashSet<String>();
			while (rs.next()) {
				// 判断重复
				String key = rs.getString("dept_id") + rs.getString("ass_card_no")+ rs.getString("time")+ rs.getString("user_name");
				if (null == (Integer) rs.getInt("dept_id") || null == rs.getString("ass_card_no") || null == rs.getString("time") || null == rs.getString("user_name")) {
					continue;
				}
				if (setData.contains(key)) {
					continue;
				} else {
					setData.add(key);
				}

				AssCardPollingData card = new AssCardPollingData();
				String year=rs.getString("time").split("-")[0];
				String month=rs.getString("time").split("-")[1];
				card.setId(rs.getInt("_id"));
				card.setGroup_id(rs.getInt("group_id"));
				card.setHos_id(rs.getInt("hos_id"));
				card.setCopy_code(rs.getString("copy_code"));
				card.setAss_year(year);
				card.setAss_month(month);
				card.setAss_card_no(rs.getString("ass_card_no"));
				card.setAss_name(rs.getString("ass_name"));
				card.setDept_id(rs.getInt("dept_id"));
				card.setDept_no(rs.getInt("dept_no"));
				card.setAss_nature(rs.getString("ass_nature"));
				card.setCreate_emp(rs.getInt("user_id"));
				card.setCreate_date(DateUtil.stringToDate(rs.getString("time"), "yyyy-MM-dd"));
				card.setState(rs.getInt("state"));
				card.setResult(rs.getInt("result"));
				
				repData.add(card);
			}
			// 创建表 已经创建忽略
			dao.create(AssCardPollingData.class, false);

			Daos.migration(dao, AssCardPollingData.class, true, false, false); // 新增字段true,删除
			logger.debug(repData.size()); // 字段false,检查索引false

			logger.debug("|-------------自动上传开始R------------------|");
			dao.delete(repData);
			dao.fastInsert(repData);
			logger.debug("|-------------自动上传结束R--------------|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {  
				rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	rs = null;  
            } 
			try {  
				statement.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	statement = null;  
            }  
			try {  
				connection.close();  
  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	connection = null;  
            }  

		}

	}
	////保养
	private void importMainTainData(File file) throws Exception {
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String url = "jdbc:sqlite:" + file.getPath();
		// 查询报修数据
		String sql = (p.getProperty("sqlMT") == null) ? "select * from MainTainData" : p.getProperty("sqlMT");

		Class.forName(driver);
		Connection connection = null;
		Statement statement=null;
		ResultSet rs=null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);

			List<AssCardMainTainData> repData = new ArrayList<AssCardMainTainData>();
			Set<String> setData = new HashSet<String>();
			while (rs.next()) {
				// 判断重复
				String key = rs.getString("dept_id") + rs.getString("ass_card_no")+ rs.getString("time")+ rs.getString("user_name");
				if (null == (Integer) rs.getInt("dept_id") || null == rs.getString("ass_card_no") || null == rs.getString("time") || null == rs.getString("user_name")) {
					continue;
				}
				if (setData.contains(key)) {
					continue;
				} else {
					setData.add(key);
				}

				AssCardMainTainData card = new AssCardMainTainData();
				String year=rs.getString("time").split("-")[0];
				String month=rs.getString("time").split("-")[1];
				card.setId(rs.getInt("_id"));
				card.setGroup_id(rs.getInt("group_id"));
				card.setHos_id(rs.getInt("hos_id"));
				card.setCopy_code(rs.getString("copy_code"));
				card.setAss_year(year);
				card.setAss_month(month);
				card.setAss_card_no(rs.getString("ass_card_no"));
				card.setAss_name(rs.getString("ass_name"));
				card.setDept_id(rs.getInt("dept_id"));
				card.setDept_no(rs.getInt("dept_no"));
				card.setAss_nature(rs.getString("ass_nature"));
				card.setCreate_emp(rs.getInt("user_id"));
				card.setCreate_date(DateUtil.stringToDate(rs.getString("time"), "yyyy-MM-dd"));
				card.setMaintain_hours(rs.getInt("maintain_hours"));
				card.setMaintain_money(rs.getInt("maintain_money"));
				card.setMaintain_unit(rs.getInt("maintain_unit"));
				
				repData.add(card);
			}
			// 创建表 已经创建忽略
			dao.create(AssCardMainTainData.class, false);

			Daos.migration(dao, AssCardMainTainData.class, true, false, false); // 新增字段true,删除
			logger.debug(repData.size()); // 字段false,检查索引false

			logger.debug("|-------------自动上传开始R------------------|");
			dao.delete(repData);
			dao.fastInsert(repData);
			logger.debug("|-------------自动上传结束R--------------|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {  
				rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	rs = null;  
            } 
			try {  
				statement.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	statement = null;  
            }  
			try {  
				connection.close();  
  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	connection = null;  
            }  

		}

	}
	////计量
	private void importMeasureData(File file) throws Exception {
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String url = "jdbc:sqlite:" + file.getPath();
		// 查询报修数据
		String sql = (p.getProperty("sqlM") == null) ? "select * from MeasureData" : p.getProperty("sqlM");

		Class.forName(driver);
		Connection connection = null;
		Statement statement=null;
		ResultSet rs=null;
		
		try {
			// create a database connection
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);

			List<AssCardMeasureData> repData = new ArrayList<AssCardMeasureData>();
			Set<String> setData = new HashSet<String>();
			while (rs.next()) {
				// 判断重复
				String key = rs.getString("dept_id") + rs.getString("ass_card_no")+ rs.getString("time")+ rs.getString("user_name");
				if (null == (Integer) rs.getInt("dept_id") || null == rs.getString("ass_card_no") || null == rs.getString("time") || null == rs.getString("user_name")) {
					continue;
				}
				if (setData.contains(key)) {
					continue;
				} else {
					setData.add(key);
				}

				AssCardMeasureData card = new AssCardMeasureData();
				card.setId(rs.getInt("_id"));
				card.setGroup_id(rs.getInt("group_id"));
				card.setHos_id(rs.getInt("hos_id"));
				card.setCopy_code(rs.getString("copy_code"));
				card.setAss_card_no(rs.getString("ass_card_no"));
				card.setAss_name(rs.getString("ass_name"));
				card.setDept_id(rs.getInt("dept_id"));
				card.setDept_no(rs.getInt("dept_no"));
				card.setAss_nature(rs.getString("ass_nature"));
				card.setCreate_emp(rs.getInt("user_id"));
				card.setCreate_date(DateUtil.stringToDate(rs.getString("time"), "yyyy-MM-dd"));
				card.setMeasure_hours(rs.getInt("measure_hours"));
				card.setMeasure_money(rs.getInt("measure_money"));
				card.setOther_money(rs.getInt("other_money"));
				card.setMeasure_kind(rs.getInt("measure_kind"));
				card.setMeasure_type(rs.getInt("measure_type"));
				card.setMeasure_result(rs.getInt("measure_result"));
				
				repData.add(card);
			}
			// 创建表 已经创建忽略
			dao.create(AssCardMeasureData.class, false);

			Daos.migration(dao, AssCardMeasureData.class, true, false, false); // 新增字段true,删除
			logger.debug(repData.size()); // 字段false,检查索引false

			logger.debug("|-------------自动上传开始R------------------|");
			dao.delete(repData);
			dao.fastInsert(repData);
			logger.debug("|-------------自动上传结束R--------------|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {  
				rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	rs = null;  
            } 
			try {  
				statement.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	statement = null;  
            }  
			try {  
				connection.close();  
  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
            	connection = null;  
            }  

		}

	}

	/**
	 * <pre>
	 * 获取sqlite db文件 移动端app上传盘点数据文件 </br> 格式为sqlite 数据库文件</br>
	 * 每次上传文件重新命名不要覆盖原有文件</br>
	 * 因为存在多个app同时上传数据文件</br>
	 * 可按照时间来命名文件名称</br> 
	 * 数据同步后将会删除文件避免重复读取数据
	 * </pre>
	 * 
	 * @return 文件集合
	 */
	private File[] findfilesBySqliteDb() {
		// 获取项目根路径
		String url = LoadSystemInfo.getProjectUrl();
		// 创建盘点数据存储目录
		Files.createDirIfNoExists(url + "ass\\asscheckdata");
		// 返回上传文件集合
		return Files.lsFile(url + "ass\\asscheckdata", null);
	}
	//报修
	private File[] findfilesBySqliteDbRep() {
		// 获取项目根路径
		String url = LoadSystemInfo.getProjectUrl();
		// 创建盘点数据存储目录
		Files.createDirIfNoExists(url + "ass\\assrepairsdata");
		// 返回上传文件集合
		return Files.lsFile(url + "ass\\assrepairsdata", null);
	}
	//报修
	private File[] findfilesBySqliteDbPoll() {
		// 获取项目根路径
		String url = LoadSystemInfo.getProjectUrl();
		// 创建盘点数据存储目录
		Files.createDirIfNoExists(url + "ass\\asspollingdata");
		// 返回上传文件集合
		return Files.lsFile(url + "ass\\asspollingdata", null);
	}
	//保养
	private File[] findfilesBySqliteDbMainTain() {
		// 获取项目根路径
		String url = LoadSystemInfo.getProjectUrl();
		// 创建盘点数据存储目录
		Files.createDirIfNoExists(url + "ass\\assmaintaindata");
		// 返回上传文件集合
		return Files.lsFile(url + "ass\\assmaintaindata", null);
	}
	//计量
	private File[] findfilesBySqliteDbMeasure() {
		// 获取项目根路径
		String url = LoadSystemInfo.getProjectUrl();
		// 创建盘点数据存储目录
		Files.createDirIfNoExists(url + "ass\\assmeasuredata");
		// 返回上传文件集合
		return Files.lsFile(url + "ass\\assmeasuredata", null);
	}

	/**
	 * 生成资产卡片文件同步到移动端</be>
	 * 
	 * <pre>
	 *      格式为json或者txt 
	 *      最终格式按照实际业务和技术实现而定。
	 *      默认生成JSON文件
	 * </pre>
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
	private void createCheckCardDataFile() throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass\\download";
		String sprit = "\\";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(sprit).append(ass).append(sprit).append("Card.db");
		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(sb.toString());
		if (null != f) {
			long create_begin = System.currentTimeMillis() / 1000; // 生成文件
			
			card(sb.toString());
			long create_end = System.currentTimeMillis() / 1000; // 生成文件

			logger.debug("|总时间时间： " + (create_end - create_begin) + "s|");
		} 

	}

	/**
	 * 生成资产卡片文件同步到移动端</be>
	 * 
	 * <pre>
	 *      格式为json或者txt 
	 *      最终格式按照实际业务和技术实现而定。
	 *      默认生成JSON文件
	 * </pre>
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
	private void createCheckDeptDataFile() throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass\\download";
		String sprit = "\\";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(sprit).append(ass).append(sprit).append("Dept.db");
		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(sb.toString());
		if (null != f) {
			long create_begin = System.currentTimeMillis() / 1000; // 生成文件
			dept(sb.toString());
			long create_end = System.currentTimeMillis() / 1000; // 生成文件

			logger.debug("|总时间时间： " + (create_end - create_begin) + "s|");
		} 

	}

	
	/**
	 * 生成用户文件同步到移动端</be>
	 * 
	 * <pre>
	 *      格式为json或者txt 
	 *      最终格式按照实际业务和技术实现而定。
	 *      默认生成JSON文件
	 * </pre>
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
	private void createCheckUserDataFile() throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass\\download";
		String sprit = "\\";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(sprit).append(ass).append(sprit).append("User.db");
		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(sb.toString());
		if (null != f) {
			long create_begin = System.currentTimeMillis() / 1000; // 生成文件
			
			user(sb.toString());
			long create_end = System.currentTimeMillis() / 1000; // 生成文件

			logger.debug("|总时间时间： " + (create_end - create_begin) + "s|");
		} 

	}
	
	/**
	 * 生成故障分类文件同步到移动端 </be>
	 * 
	 * <pre>
	 *      格式为json或者txt 
	 *      最终格式按照实际业务和技术实现而定。
	 *      默认生成JSON文件
	 * </pre>
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
    private void createFaultDataFile() throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass\\download";
		String sprit = "\\";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(sprit).append(ass).append(sprit).append("Fault.db");
		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(sb.toString());
		if (null != f) {
			long create_begin = System.currentTimeMillis() / 1000; // 生成文件
			
			fault(sb.toString());
			long create_end = System.currentTimeMillis() / 1000; // 生成文件

			logger.debug("|总时间时间： " + (create_end - create_begin) + "s|");
		} 

	}
    
    /**
	 * 生成资产位置文件同步到移动端 </be>
	 * 
	 * <pre>
	 *      格式为json或者txt 
	 *      最终格式按照实际业务和技术实现而定。
	 *      默认生成JSON文件
	 * </pre>
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
    private void createLocationDataFile() throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass\\download";
		String sprit = "\\";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(sprit).append(ass).append(sprit).append("Location.db");
		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(sb.toString());
		if (null != f) {
			long create_begin = System.currentTimeMillis() / 1000; // 生成文件
			
			location(sb.toString());
			long create_end = System.currentTimeMillis() / 1000; // 生成文件

			logger.debug("|总时间时间： " + (create_end - create_begin) + "s|");
		} 

	}
    
    /**
	 * 生成系统信息文件同步到移动端 其中包括 集团 医院 账套 信息</be>
	 * 
	 * <pre>
	 *      格式为json或者txt 
	 *      最终格式按照实际业务和技术实现而定。
	 *      默认生成JSON文件
	 * </pre>
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
    private void createCheckInfoDataFile() throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass\\download";
		String sprit = "\\";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(sprit).append(ass).append(sprit).append("Info.db");
		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(sb.toString());
		if (null != f) {
			long create_begin = System.currentTimeMillis() / 1000; // 生成文件
			
			info(sb.toString());
			long create_end = System.currentTimeMillis() / 1000; // 生成文件

			logger.debug("|总时间时间： " + (create_end - create_begin) + "s|");
		} 

	}
	
	/**
	 * 自动同步移动盘点数据到盘点表
	 */
	public void autoCheckDataGenerate() {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		String moblie = assCheckMobileService.generate(mapVo);
	}
	
	/**
	 * 自动同步移动报修数据到报修表
	 */
	public void autoRepairsDataGenerate() {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		String moblie = assCheckMobileService.repairsGenerate(mapVo);
	}
	
	/**
	 * 自动同步移动巡检数据到巡检表
	 */
	public void autoPollingDataGenerate() {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		String moblie = assCheckMobileService.pollingGenerate(mapVo);
	}
	/**
	 * 自动同步移动保养数据到巡检表
	 */
	public void autoMainTainDataGenerate() {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		String moblie = assCheckMobileService.maintainGenerate(mapVo);
	}
	/**
	 * 自动同步移动计量数据到巡检表
	 */
	public void autoMeasureDataGenerate() {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		String moblie = assCheckMobileService.measureGenerate(mapVo);
	}

	@SuppressWarnings("resource")
	public void card(String url) throws Exception {

		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;

		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;

		long create_begin = System.currentTimeMillis() / 1000; // 生成文件

		List<AssCard> card = dao.query(AssCard.class, null);

		long create_query = System.currentTimeMillis() / 1000; // 生成文件

		logger.debug("|数据库提取卡片时间： " + (create_query - create_begin) + "s|");

		try {
			// create a database connection
			connection = DriverManager.getConnection(fileurl);
			// 删除已存在的数据
			pstm = connection.prepareStatement("delete from Card");
			pstm.execute();

			final String stSql = "insert into Card(ass_card_no,ass_ori_card_no,ass_id,ass_no,ass_code,ass_name,dept_id,dept_no,dept_code,dept_name,ass_state,group_id,hos_id,copy_code,ass_spec,ass_mondl,ass_nature) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 批量添加卡片数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);
			long file_begin = System.currentTimeMillis() / 1000; // 生成文件
			for (AssCard assCard : card) {

				pstm.setString(1, assCard.getAss_card_no());
				pstm.setString(2, assCard.getAss_ori_card_no());
				pstm.setString(3, assCard.getAss_id());
				pstm.setString(4, assCard.getAss_no());
				pstm.setString(5, assCard.getAss_code());
				pstm.setString(6, assCard.getAss_name());
				pstm.setString(7, assCard.getDept_id());
				pstm.setString(8, assCard.getDept_no());
				pstm.setString(9, assCard.getDept_code());
				pstm.setString(10, assCard.getDept_name());
				pstm.setString(11, assCard.getAss_state());
				pstm.setString(12, assCard.getGroup_id());
				pstm.setString(13, assCard.getHos_id());
				pstm.setString(14, assCard.getCopy_code());
				pstm.setString(15, assCard.getAss_spec());
				pstm.setString(16, assCard.getAss_mondl());
				pstm.setString(17, assCard.getAss_nature());
				pstm.addBatch();
			}

			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			long file_end = System.currentTimeMillis() / 1000; // 生成文件
			logger.debug("|生成文件时间： " + (file_end - file_begin) + "s|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if (pstm != null) {
					pstm.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				// connection close failed
				logger.error(e);
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void info(String url) throws Exception {
		
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;
		
		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;
		
		long create_begin = System.currentTimeMillis() / 1000; // 生成文件
		
		List<Info> info = dao.query(Info.class, null);
		
		long create_query = System.currentTimeMillis() / 1000; // 生成文件
		
		logger.debug("|数据库提取卡片时间： " + (create_query - create_begin) + "s|");
		
		try {
			// create a database connection
			connection = DriverManager.getConnection(fileurl);
			// 删除已存在的数据
			pstm = connection.prepareStatement("delete from Info");
			pstm.execute();
			
			final String stSql = "insert into Info(group_id,group_name,hos_id,hos_name,copy_code,copy_name)values(?,?,?,?,?,?)";
			// 批量添加卡片数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);
			long file_begin = System.currentTimeMillis() / 1000; // 生成文件
			for (Info infos : info) {
				
				pstm.setString(1, infos.getGroup_id());
				pstm.setString(2, infos.getGroup_name());
				pstm.setString(3, infos.getHos_id());
				pstm.setString(4, infos.getHos_name());
				pstm.setString(5, infos.getCopy_code());
				pstm.setString(6, infos.getCopy_name());
				pstm.addBatch();
			}
			
			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			long file_end = System.currentTimeMillis() / 1000; // 生成文件
			logger.debug("|生成文件时间： " + (file_end - file_begin) + "s|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				
				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				// connection close failed
				logger.error(e);
			}
		}
	}
	@SuppressWarnings("resource")
	public void user(String url) throws Exception {
		
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;
		
		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;
		
		long create_begin = System.currentTimeMillis() / 1000; // 生成文件
		
		List<User> user = dao.query(User.class, null);
		
		long create_query = System.currentTimeMillis() / 1000; // 生成文件
		
		logger.debug("|数据库提取卡片时间： " + (create_query - create_begin) + "s|");
		
		try {
			// create a database connection
			connection = DriverManager.getConnection(fileurl);
			// 删除已存在的数据
			pstm = connection.prepareStatement("delete from User");
			pstm.execute();
			
			final String stSql = "insert into User(group_id,hos_id,user_id,user_code,user_name,spell_code,wbx_code) values(?,?,?,?,?,?,?)";
			// 批量添加卡片数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);
			long file_begin = System.currentTimeMillis() / 1000; // 生成文件
			for (User u : user) {
				pstm.setString(1, u.getGroup_id());
				pstm.setString(2, u.getHos_id());
				pstm.setString(3, u.getUser_id());
				pstm.setString(4, u.getUser_code());
				pstm.setString(5, u.getUser_name());
				pstm.setString(6, u.getSpell_code());
				pstm.setString(7, u.getWbx_code());
				pstm.addBatch();
			}
			
			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			long file_end = System.currentTimeMillis() / 1000; // 生成文件
			logger.debug("|生成文件时间： " + (file_end - file_begin) + "s|");
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				
				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				// connection close failed
				logger.error(e);
			}
		}
	}

	@SuppressWarnings("resource")
	public void dept(String url) throws Exception {

		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;

		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;

		long create_begin = System.currentTimeMillis() / 1000; // 生成文件

		List<DeptData> dept = dao.query(DeptData.class, null);

		long create_query = System.currentTimeMillis() / 1000; // 生成文件

		logger.debug("|数据库提取卡片时间： " + (create_query - create_begin) + "s|");

		try {
			// create a database connection
			connection = DriverManager.getConnection(fileurl);
			// 删除已存在的数据
			pstm = connection.prepareStatement("delete from Dept");
			pstm.execute();

			final String stSql = "insert into Dept(group_id,hos_id,dept_id,dept_no,dept_code,dept_name,spell_code,wbx_code) values(?,?,?,?,?,?,?,?)";
			// 批量添加卡片数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);

			long file_begin = System.currentTimeMillis() / 1000; // 生成文件

			for (DeptData d : dept) {
				pstm.setString(1, d.getGroup_id());
				pstm.setString(2, d.getHos_id());
				pstm.setString(3, d.getDept_id());
				pstm.setString(4, d.getDept_no());
				pstm.setString(5, d.getDept_code());
				pstm.setString(6, d.getDept_name());
				pstm.setString(7, d.getSpell_code());
				pstm.setString(8, d.getWbx_code());
				pstm.addBatch();
			}
			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			long file_end = System.currentTimeMillis() / 1000; // 生成文件
			logger.debug("|生成文件时间： " + (file_end - file_begin) + "s|");

		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if (pstm != null) {
					pstm.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				// connection close failed
				logger.error(e);
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void fault(String url) throws Exception {

		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;

		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;

		long create_begin = System.currentTimeMillis() / 1000; // 生成文件

		List<Fault> fault = dao.query(Fault.class, null);

		long create_query = System.currentTimeMillis() / 1000; // 生成文件

		logger.debug("|数据库提取卡片时间： " + (create_query - create_begin) + "s|");

		try {
			// create a database connection
			connection = DriverManager.getConnection(fileurl);
			// 删除已存在的数据
			pstm = connection.prepareStatement("delete from Fault");
			pstm.execute();

			final String stSql = "insert into Fault(group_id,hos_id,copy_code,fau_code,fau_name) values(?,?,?,?,?)";
			// 批量添加卡片数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);

			long file_begin = System.currentTimeMillis() / 1000; // 生成文件

			for (Fault f : fault) {
				pstm.setString(1, f.getGroup_id());
				pstm.setString(2, f.getHos_id());
				pstm.setString(3, f.getCopy_code());
				pstm.setString(4, f.getFau_code());
				pstm.setString(5, f.getFau_name());
				
				pstm.addBatch();
			}
			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			long file_end = System.currentTimeMillis() / 1000; // 生成文件
			logger.debug("|生成文件时间： " + (file_end - file_begin) + "s|");

		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if (pstm != null) {
					pstm.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				// connection close failed
				logger.error(e);
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void location(String url) throws Exception {

		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;

		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;

		long create_begin = System.currentTimeMillis() / 1000; // 生成文件

		List<Location> location = dao.query(Location.class, null);

		long create_query = System.currentTimeMillis() / 1000; // 生成文件

		logger.debug("|数据库提取卡片时间： " + (create_query - create_begin) + "s|");

		try {
			// create a database connection
			connection = DriverManager.getConnection(fileurl);
			// 删除已存在的数据
			pstm = connection.prepareStatement("delete from Location");
			pstm.execute();

			final String stSql = "insert into Location(group_id,hos_id,copy_code,loc_name,loc_code) values(?,?,?,?,?)";
			// 批量添加卡片数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);

			long file_begin = System.currentTimeMillis() / 1000; // 生成文件

			for (Location l : location) {
				pstm.setString(1, l.getGroup_id());
				pstm.setString(2, l.getHos_id());
				pstm.setString(3, l.getCopy_code());
				pstm.setString(4, l.getLoc_name());
				pstm.setString(5, l.getLoc_code());
				
				pstm.addBatch();
			}
			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			long file_end = System.currentTimeMillis() / 1000; // 生成文件
			logger.debug("|生成文件时间： " + (file_end - file_begin) + "s|");

		}
		catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if (pstm != null) {
					pstm.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				// connection close failed
				logger.error(e);
			}
		}
	}
	
}
