package com.chd.base.tool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.ResultSetLooping;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.sql.SqlContext;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

/**
 * @Title:
 * @Description:获取数据库表以及字段相关信息工具辅助类
 * @Copyright: Copyright (c) 2018年4月17日 下午4:15:13
 * @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class DbMetaData {

	private static final Log log = Logs.get();
	SqlManager s=null;
	// 注入Dao实例 变量名dao 要与配置文件中一致 配置文件为 dao.js
	protected static Dao dao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
	
	/**
	 * 获取数据库中查询字段的相关信息<br>
	 * 
	 * <pre>
	 * 列名  * 字段类型    * 长度    * 是否为空    * 是否默认值    * 列描述     * ...
	 * </pre>
	 * 
	 * @param table_name 
	 * 查询的表名
	 * 
	 * <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where 1<>1
	 * ********************************************************************************************
	 * </pre>
	
	 * @return
	 */
	public static List<MetaData> getTableMetaData(String table_name) {
		Map<String, Object> paras = new HashMap<String, Object>();
		return getMetaData("select * from "+table_name+" where 1!=1", paras, dao);
	}
	/**
	 * 获取数据库中查询字段的相关信息<br>
	 * 
	 * <pre>
	 * 列名  * 字段类型    * 长度    * 是否为空    * 是否默认值    * 列描述     * ...
	 * </pre>
	 * 
	 * @param table_name 
	 * 查询的表名
	 * 
	 * <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where 1<>1
	 * ********************************************************************************************
	 * </pre>
	
	 * @return
	 */
	public static List<MetaData> getTableMetaData(String table_name, Dao ortherDao) {
		Map<String, Object> paras = new HashMap<String, Object>();
		return getMetaData("select * from "+table_name+" where 1!=1", paras, ortherDao);
	}

	/**
	 * 获取数据库中查询字段的相关信息<br>
	 * 
	 * <pre>
	 * 列名  * 字段类型    * 长度    * 是否为空    * 是否默认值    * 列描述     * ...
	 * </pre>
	 * 
	 * @param prm_sql
	 *            自定义SQL语句
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @return
	 */
	public static List<MetaData> getMetaData(String prm_sql) {
		Map<String, Object> paras = new HashMap<String, Object>();
		return getMetaData(prm_sql, paras, dao);
	}

	/**
	 * 获取数据库中查询字段的相关信息<br>
	 * 
	 * <pre>
	 * 列名  * 字段类型    * 长度    * 是否为空    * 是否默认值    * 列描述     * ...
	 * </pre>
	 * 
	 * @param prm_sql
	 *            自定义SQL语句
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param ortherDao
	 *            数据源 dao实例
	 * @return
	 */
	public static List<MetaData> getMetaData(String prm_sql, Dao ortherDao) {
		Map<String, Object> paras = new HashMap<String, Object>();
		return getMetaData(prm_sql, paras, ortherDao);
	}

	/**
	 * 获取数据库中查询字段的相关信息<br>
	 * 
	 * <pre>
	 * 列名  * 字段类型    * 长度    * 是否为空    * 是否默认值    * 列描述     * ...
	 * </pre>
	 * 
	 * @param prm_sql
	 *            自定义SQL语句
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param paras
	 *            参数
	 * @return
	 */
	public static List<MetaData> getMetaData(String prm_sql, Map<String, Object> paras) {
		return getMetaData(prm_sql, paras, dao);
	}

	/**
	 * 通过自定义sql获取查询结果集
	 * 
	 * @param prm_sql
	 *            自定义sql
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param paras
	 *            参数
	 * @param dao
	 *            数据源
	 * @return
	 */
	public static List<MetaData> getMetaData(String prm_sql, Map<String, Object> paras, Dao dao) {

		final List<MetaData> list = new ArrayList<MetaData>();
		Sql sql = Sqls.create(prm_sql);

		if (!Lang.isEmpty(paras)) {
			for (String key : paras.keySet()) {
				sql.params().set(key, Strings.isEmpty(paras.get(key).toString()) ? "\'\'" : paras.get(key));
			}
		}

		sql.setCallback(new SqlCallback() {

			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {

				ResultSetMetaData data = rs.getMetaData();

				for (int i = 1; i <= data.getColumnCount(); i++) {
					MetaData md = new MetaData();
					// 列名
					md.setColumnName(data.getColumnName(i).toLowerCase());
					// 获得指定列的数据类型
					md.setColumnType(data.getColumnType(i));
					// 获得指定列的数据类型名
					md.setColumnTypeName(data.getColumnTypeName(i));
					// 在数据库中类型的最大字符个数
					md.setColumnDisplaySize(data.getColumnDisplaySize(i));
					// 默认的列的标题
					md.setColumnLabel(data.getColumnLabel(i).toLowerCase());
					// 某列类型的精确度(类型的长度)
					md.setPrecision(data.getPrecision(i));
					// 小数点后的位数
					md.setScale(data.getScale(i));
					// 是否自动递增
					md.setAutoInctemen(data.isAutoIncrement(i));
					// 是否为空
					md.setIsNullable(data.isNullable(i));
					// 是否为只读
					md.setReadOnly(data.isReadOnly(i));
					// 能否出现在where中
					md.setSearchable(data.isSearchable(i));
					// 在数据库中是否为货币型
					md.setCurrency(data.isCurrency(i));
					list.add(md);
				}

				return list;

			}
		});
		dao.execute(sql);
		return sql.getList(MetaData.class);
	}

	/**
	 * 通过自定义sql获取查询结果集
	 * 
	 * @param <T>
	 *            封装的对象
	 * @param prm_sql
	 *            自定义sql
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param calssTT
	 *            封装的对象
	 * @return
	 */
	public static <T> List<T> getResultData(String prm_sql, Class<T> calssTT) {
		Map<String, Object> paras = new HashMap<String, Object>();
		return getResultData(prm_sql, paras, calssTT, dao);
	}

	/**
	 * 通过自定义sql获取查询结果集
	 * 
	 * @param <T>
	 *            封装的对象
	 * @param prm_sql
	 *            自定义sql
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param paras
	 *            参数
	 * @param calssTT
	 *            封装的对象
	 * @return
	 */
	public static <T> List<T> getResultData(String prm_sql, Map<String, Object> paras, Class<T> calssTT) {
		return getResultData(prm_sql, paras, calssTT, dao);
	}

	/**
	 * 通过自定义sql获取查询结果集
	 * 
	 * @param <T>
	 *            封装的对象
	 * @param prm_sql
	 *            自定义sql
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param calssTT
	 *            封装的对象
	 * @param dao
	 *            数据源
	 * @return
	 */
	public static <T> List<T> getResultData(String prm_sql, Class<T> calssTT, Dao ortherDao) {
		Map<String, Object> paras = new HashMap<String, Object>();
		return getResultData(prm_sql, paras, calssTT, ortherDao);
	}

	/**
	 * 通过自定义sql获取查询结果集
	 * 
	 * @param <T>
	 *            封装的对象
	 * @param prm_sql
	 *            自定义sql
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param paras
	 *            参数
	 * @param calssTT
	 *            封装的对象
	 * @param dao
	 *            数据源
	 * @return
	 */
	public static <T> List<T> getResultData(String prm_sql, Map<String, Object> paras, final Class<T> calssTT, Dao dao) {

		Sql sql = Sqls.create(prm_sql);

		if (!Lang.isEmpty(paras)) {
			for (String key : paras.keySet()) {
				sql.params().set(key, Strings.isEmpty(paras.get(key).toString()) ? "\'\'" : paras.get(key));
			}
		}
		sql.setCallback(new SqlCallback() {

			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				ResultSetLooping ing = new ResultSetLooping() {

					protected boolean createObject(int index, ResultSet rs, SqlContext context, int rowCout) {
						list.add(Record.create(rs).toPojo(calssTT));
						return true;
					}
				};
				ing.doLoop(rs, sql.getContext());
				return ing.getList();
			}
		});
		dao.execute(sql);
		return sql.getList(calssTT);
	}
	
	/**
	 * 通过自定义sql获取查询结果集
	 * 
	 * @param <T>
	 *            封装的对象
	 * @param prm_sql
	 *            自定义sql
	 * 
	 *            <pre>
	 * ********************************************************************************************
	 * 查询实例
	 * select * from table where a.id=@id and a.name=@name
	 * ********************************************************************************************
	 *            </pre>
	 * 
	 * @param sql
	 *            SQL类 自行封装sql 和参数
	 * @param calssTT
	 *            封装的对象
	 * @param dao
	 *            数据源
	 * @return  
	 */
	public static <T> List<T> getResultData(Sql sql, final Class<T> calssTT, Dao dao) {
		
		sql.setCallback(new SqlCallback() {

			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				ResultSetLooping ing = new ResultSetLooping() {

					protected boolean createObject(int index, ResultSet rs, SqlContext context, int rowCout) {
						list.add(Record.create(rs).toPojo(calssTT));
						return true;
					}
				};
				ing.doLoop(rs, sql.getContext());
				return ing.getList();
			}
		});
		dao.execute(sql);
		return sql.getList(calssTT);
	}

}
