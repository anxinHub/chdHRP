package com.chd.hrp.hr.util;

import com.chd.base.SessionManager;

/**
 * 处理SQL参数设置
 * 
 * @ClassName: ParameterHandler
 * @Description:
 * @author zn
 * @date 2017年12月19日 下午2:09:39
 * 
 *
 */
public class ParameterHandler {
	private String group_id;
	private String hos_id;
	private String copy_code;
	private String user_id;

	public ParameterHandler() {
		group_id = SessionManager.getGroupId();
		hos_id = SessionManager.getHosId();
		copy_code = SessionManager.getCopyCode();
		user_id = SessionManager.getUserId();
	}

	public String setParameters(String sql) {
		if (sql.contains("@group_id")) {
			sql = sql.replace("@group_id", group_id);
		}

		if (sql.contains("@hos_id")) {
			sql = sql.replace("@hos_id", hos_id);
		}

		if (sql.contains("@copy_code")) {
			sql = sql.replace("@copy_code", copy_code);
		}

		if (sql.contains("@user_id")) {
			sql = sql.replace("@user_id", user_id);
		}

		return sql;
	}
}
