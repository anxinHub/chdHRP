package com.chd.task.ass;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.json.JsonException;
import org.nutz.lang.Files;
import org.nutz.mvc.Mvcs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.chd.base.startup.LoadSystemInfo;

/**
 * @Title:
 * @Description: 库存移动盘点定时传输业务处理
 * @Copyright: Copyright (c) 2017年4月27日 下午5:42:04
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Component
public class MatMobileInventory implements Job {

	private static Logger logger = Logger.getLogger(MatMobileInventory.class);

	// 注入Dao实例 变量名dao 要与配置文件中一致 配置文件为 dao.js 操作后台数据库dao
	protected Dao dao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
	
	Properties p;

	public MatMobileInventory() {
		try {
			// 获取连接SQLITE数据库配置信息
			p = new Properties();
			p.load(getClass().getClassLoader().getResourceAsStream("ioc/task.properties"));
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 定时任务处理  获取上传的数据同步任务单盘点数据 并删除上传文件
	 */
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		autoCheckData();
	}


	/**
	 * 定时读取盘点数据 并删除数据文件 主流程 方法
	 */
	public void autoCheckData() {
		try {
			// 获取客户上传的 文件
			File[] files = findfilesBySqliteDb();
			if (files.length > 0) {
				logger.info("************高值材料盘点上传 - 定时加载数据************");
				for (File file : files) {
					// 导入到项目数据库(oracle)
					importData(file);
					// 删除文件
					Files.deleteFile(file);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入盘点数据
	 * @param file 客户端(安卓) 上传的数据库文件 xx.db 
	 * 1. 查询db数据
	 * 2. 将此任务单之前所有的任务单数据状态改为 3 (结束)
	 * 3. 更新此次任务单  数量/状态/上传日期/盘点人等
	 * @throws Exception
	 */
	private void importData(File file) throws Exception {
		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String url = "jdbc:sqlite:" + file.getPath();
		// 查询盘点上传的数据
		String sql = "select * from matcheckmobile";

		Class.forName(driver);
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);

			while (rs.next()) {
				// 只对盘点数量大于0 的记录进行更新 (更新盘点数量为1,上传日期,盘点人,备注信息)
				if (rs.getInt("chk_amount") > 0) {
					// 使用zutz框架做数据库更新操作
					// 其实也可以使用spring的service/或mapper 不过在操作过程中 总是注入不进去 
					Sql updateSql = Sqls.create("UPDATE MAT_CHECK_MOBILE SET CHK_AMOUNT = 1, UPLOAD_DATE = SYSDATE, STATE = 2, EMP_ID = @empId, NOTE = @note"
							+ " WHERE DETAIL_ID = @detailId AND IS_COM = @isCom");
					updateSql.setParam("empId",rs.getLong("emp_id")).setParam("note", rs.getString("note")).setParam("detailId", rs.getLong("detail_id")).setParam("isCom", rs.getInt("is_com"));
					
					dao.execute(updateSql);
				}
				
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
			}
			try {
				connection.close();
			} catch (SQLException e) {
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
		Files.createDirIfNoExists(url + "ass\\matcheckmobiledata");
		// 返回上传文件集合
		return Files.lsFile(url + "ass\\matcheckmobiledata", null);
	}

	/**
	 * 生成任务单时 对下载的数据库Sqlite文件 添加数据
	 * @param list 生成的任务单数据
	 */
	public void autoCreateCardFile(List<Map<String, Object>> list) {

		try {
			createCheckCardDataFile(list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
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
	 * @param list 
	 * 
	 * @throws Exception
	 * @throws JsonException
	 */
	private void createCheckCardDataFile(List<Map<String, Object>> list) throws JsonException, Exception {

		String url = LoadSystemInfo.getProjectUrl();
		String ass = "ass" + File.separator + "download";
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(ass).append(File.separator).append("MatCheckMobile.db");

		// 判断是否存在文件，不存在则不做任何操作
		File f = Files.findFile(url);
		if (null != f) {
			checkMobile(sb.toString(), list);
		}
	}

	/**
	 * 添加盘点数据
	 * @param url
	 * @param checks
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public void checkMobile(String url, List<Map<String, Object>> checks) throws Exception {

		// 加载数据库配置
		String driver = (p.getProperty("driverClassName") == null) ? "org.sqlite.JDBC" : p.getProperty("driverClassName");
		String fileurl = "jdbc:sqlite:" + url;

		Class.forName(driver);
		Connection connection = null;
		PreparedStatement pstm = null;

		try {
			connection = DriverManager.getConnection(fileurl);
			
			// 删除之前存在的数据
			if (!CollectionUtils.isEmpty(checks)) {
				connection.setAutoCommit(false);
				Map<String, Object> entityMap = checks.get(0);
				
				String deleteSql = "DELETE FROM MATCHECKMOBILE where hos_id = ? AND group_id = ? AND copy_code = ?";
				pstm = connection.prepareStatement(deleteSql);
				
				pstm.setString(1, entityMap.get("HOS_ID").toString());
				pstm.setString(2, entityMap.get("GROUP_ID").toString());
				pstm.setString(3, entityMap.get("COPY_CODE").toString());
				pstm.execute();
				
				connection.commit();
			}

			// 创建数据
			final String stSql = "insert into matcheckmobile(hos_id,group_id,copy_code,is_com,check_id,check_code,detail_id,store_id,store_no,store_name,create_date,upload_date,inv_id,inv_no,inv_code,inv_name,inv_model,unit_name,fac_name,sup_name,batch_no,bar_code,location_name,price,cur_amount,chk_amount,disinfect_date,inva_date,state,note) values"
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 批量添加数据
			pstm = connection.prepareStatement(stSql);
			connection.setAutoCommit(false);

			for (Map<String, Object> check : checks) {
				
				pstm.setString(1,check.get("HOS_ID") == null ? "" : check.get("HOS_ID").toString());
				pstm.setString(2,check.get("GROUP_ID") == null ? "" : check.get("GROUP_ID").toString());
				pstm.setString(3,check.get("COPY_CODE") == null ? "" : check.get("COPY_CODE").toString());
				pstm.setString(4,check.get("IS_COM") == null ? "" : check.get("IS_COM").toString());
				pstm.setString(5,check.get("CHECK_ID") == null ? "" : check.get("CHECK_ID").toString());
				pstm.setString(6,check.get("CHECK_CODE") == null ? "" : check.get("CHECK_CODE").toString());
				pstm.setString(7,check.get("DETAIL_ID") == null ? "" : check.get("DETAIL_ID").toString());
				pstm.setString(8,check.get("STORE_ID") == null ? "" : check.get("STORE_ID").toString());
				pstm.setString(9,check.get("STORE_NO") == null ? "" : check.get("STORE_NO").toString());
				pstm.setString(10,check.get("STORE_NAME") == null ? "" : check.get("STORE_NAME").toString());
				pstm.setString(11,check.get("CREATE_DATE") == null ? "" : check.get("CREATE_DATE").toString());
				pstm.setString(12,check.get("UPLOAD_DATE") == null ? "" : check.get("UPLOAD_DATE").toString());
				pstm.setString(13,check.get("INV_ID") == null ? "" : check.get("INV_ID").toString());
				pstm.setString(14,check.get("INV_NO") == null ? "" : check.get("INV_NO").toString());
				pstm.setString(15,check.get("INV_CODE") == null ? "" : check.get("INV_CODE").toString());
				pstm.setString(16,check.get("INV_NAME") == null ? "" : check.get("INV_NAME").toString());
				pstm.setString(17,check.get("INV_MODEL") == null ? "" : check.get("INV_MODEL").toString());
				pstm.setString(18,check.get("UNIT_NAME") == null ? "" : check.get("UNIT_NAME").toString());
				pstm.setString(19,check.get("FAC_NAME") == null ? "" : check.get("FAC_NAME").toString());
				pstm.setString(20,check.get("SUP_NAME") == null ? "" : check.get("SUP_NAME").toString());
				pstm.setString(21,check.get("BATCH_NO") == null ? "" : check.get("BATCH_NO").toString());
				pstm.setString(22,check.get("BAR_CODE") == null ? "" : check.get("BAR_CODE").toString());
				pstm.setString(23,check.get("LOCATION_NAME") == null ? "" : check.get("LOCATION_NAME").toString());
				pstm.setString(24,check.get("PRICE") == null ? "" : check.get("PRICE").toString());
				pstm.setString(25,check.get("CUR_AMOUNT") == null ? "" : check.get("CUR_AMOUNT").toString());
				pstm.setString(26,check.get("CHK_AMOUNT") == null ? "" : check.get("CHK_AMOUNT").toString());
				pstm.setString(27,check.get("DISINFECT_DATE") == null ? "" : check.get("DISINFECT_DATE").toString());
				pstm.setString(28,check.get("INVA_DATE") == null ? "" : check.get("INVA_DATE").toString());
				pstm.setString(29,check.get("STATE") == null ? "" : check.get("STATE").toString());
				pstm.setString(30,check.get("NOTE") == null ? "" : check.get("NOTE").toString());

				pstm.addBatch();
			}

			pstm.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				// 若出现异常，对数据库中所有已完成的操作全部撤销，则回滚到事务开始状态
				if (!connection.isClosed()) {
					connection.rollback();// 4,当异常发生执行catch中SQLException时，记得要rollback(回滚)；
					logger.error("插入失败，回滚！");
					connection.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
}
