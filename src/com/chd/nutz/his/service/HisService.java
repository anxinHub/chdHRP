package com.chd.nutz.his.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import com.chd.nutz.his.bean.User;


/**
 * @Title:
 * @Description: his接口服务类例子</br>
 * @Copyright: Copyright (c) 2017年4月27日 下午5:42:04
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@IocBean
public class HisService {
	private static Logger logger = Logger.getLogger(HisService.class);
	// 注入Dao实例 变量名dao 要与配置文件中一致 配置文件为 dao.js
		protected Dao dao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");

		// 注入Dao实例 变量名dao1 要与配置文件中一致 配置文件为 dao.js
		protected Dao daoV = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao1");

		/**
		 * 自定义sql 处理，调用的是dao数据源
		 */
		public void testDao() {
			Sql sql = Sqls.create("select * from sys_user");
			sql.setCallback(new SqlCallback() {

				public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
					List<String> list = new LinkedList<String>();
					while (rs.next())
						list.add(rs.getString("user_name"));
					return list;
				}
			});
			dao.execute(sql);
			List<String> list_vo = sql.getList(String.class);
			for (String s : list_vo) {
				logger.debug("dao  用户名：" + s);
			}
		}
		/**
		 * 自定义sql 处理，调用的是dao1数据源
		 */
		public void testDao1() {
			logger.debug("@@@@@@@@@@@@@@");
			Sql sql = Sqls.create("select * from sys_user");
			sql.setCallback(new SqlCallback() {

				public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
					List<String> list = new LinkedList<String>();
					while (rs.next())
						list.add(rs.getString("user_name"));
					return list;
				}
			});
			daoV.execute(sql);
			List<String> list_vo = sql.getList(String.class);
			for (String s : list_vo) {
				logger.debug("dao1  用户名：" + s);
			}
		}
		/**
		 * 采用javabean方式获取数据，调用的是dao1数据源
		 */
		public void testDao2() {
		 List<User> u=	daoV.query(User.class, null);
			for (User v_u : u) {
				logger.debug("dao1  用户名：" + v_u.getUser_code()+" "+v_u.getUser_name());
			}
		}
}
