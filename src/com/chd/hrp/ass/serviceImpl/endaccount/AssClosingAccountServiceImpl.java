package com.chd.hrp.ass.serviceImpl.endaccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.base.AssClosingAccountMapper;
import com.chd.hrp.ass.dao.base.AssYearMonthMapper;
import com.chd.hrp.ass.entity.base.AssYearMonth;
import com.chd.hrp.ass.service.endaccount.AssClosingAccountService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.github.pagehelper.PageInfo;

@Service("assClosingAccountService")
public class AssClosingAccountServiceImpl implements AssClosingAccountService {

	@Resource(name = "assYearMonthMapper")
	private final AssYearMonthMapper assYearMonthMapper = null;

	@Resource(name = "assClosingAccountMapper")
	private final AssClosingAccountMapper assClosingAccountMapper = null;

	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;

	@Override
	public String queryAssYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<AssYearMonth> list = assYearMonthMapper.queryAssYearMonth(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssYearMonth> list = assYearMonthMapper.queryAssYearMonth(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryAssCurYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		return assYearMonthMapper.queryAssCurYearMonth(entityMap);
	}

	private String collectDepre(Map<String, Object> map) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try {
			assClosingAccountMapper.collectDepreALL(map);

			String prm_AppCode = map.get("ass_AppCode").toString();

			String prm_ErrTxt = "";

			if ("0".equals(prm_AppCode)) {
				// 计算成功！提交事务
				transactionManager.commit(status);

				return "true";

			} else if ("-1".equals(prm_AppCode) || "100".equals(prm_AppCode)) {
				// 计算失败！回滚事务
				transactionManager.rollback(status);
			}

			if (!"".equals(map.get("ass_ErrTxt")) && null != map.get("ass_ErrTxt")) {
				prm_ErrTxt = map.get("ass_ErrTxt").toString();
			}
			return "{\"warn\":\"" + prm_ErrTxt + "\"}";
		} catch (BeansException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		} catch (TransactionException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String collectDepreALL(Map<String, Object> map) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		// 本月本年
		String cur_year = map.get("acc_year_month").toString().split("\\.")[0];
		String cur_month = map.get("acc_year_month").toString().split("\\.")[1];

		// 上年上月
		String top_year = "";
		String top_month = "";
		if (map.get("last_year_month") != null && !map.get("last_year_month").equals("")) {
			top_year = map.get("last_year_month").toString().split("\\.")[0];
			top_month = map.get("last_year_month").toString().split("\\.")[1];
		} else {
			// (map.get("last_year_month") == null &&
			// map.get("last_year_month").equals(""))
			top_year = cur_year;
			top_month = Integer.toString((Integer.parseInt(cur_month)) - 1);
		}

		map.put("operator", SessionManager.getUserName());
		map.put("ass_flag", "1");
		map.put("ass_user", SessionManager.getUserId());
		map.put("top_ass_year", top_year);
		map.put("top_ass_month", top_month);
		map.put("cur_ass_year", cur_year);
		map.put("cur_ass_month", cur_month);
		map.put("acc_year", cur_year);
		map.put("acc_month", cur_month);

		// 检查是否有未确认的单据
		assClosingAccountMapper.checkAssBillConfirm(map);

		String prm_AppCode = map.get("ass_AppCode").toString();

		String prm_ErrTxt = "";

		if ("-1".equals(prm_AppCode) || "100".equals(prm_AppCode)) {
			if (null != map.get("ass_ErrTxt") && !"".equals(map.get("ass_ErrTxt"))) {
				prm_ErrTxt = map.get("ass_ErrTxt").toString();
			}
			// 计算失败！回滚事务
			transactionManager.rollback(status);
			return "{\"warn\":\"" + prm_ErrTxt + "\"}";
		}

		String shareYearMonth = DateUtil.getNextYear_Month(map.get("acc_year_month").toString().replaceAll("\\.", ""));
		map.put("dept_year", shareYearMonth.substring(0, 4));
		map.put("dept_month", shareYearMonth.substring(4, 6));

		try {
			/**
			 * 复制使用科室
			 */
			assClosingAccountMapper.copyShareDeptRSpecial(map);
			assClosingAccountMapper.copyShareDeptRGeneral(map);
			assClosingAccountMapper.copyShareDeptRHouse(map);
			//assClosingAccountMapper.copyShareDeptRInassets(map);
			assClosingAccountMapper.copyShareDeptROther(map);
			//assClosingAccountMapper.copyShareDeptRLand(map);

			String calc = collectDepre(map);// 折旧计算

			if (!calc.equals("true")) {
				transactionManager.rollback(status);
				return calc;
			}

			/**
			 * 写报表数据
			 * 
			 */
			collectGenerate(map);
			/**
			 * 更新会计结账年月表
			 */
			assYearMonthMapper.updateAssYearMonth(map);
			transactionManager.commit(status);
			String year = "";
			String month = "";
			Integer acc_year = 0;
			Integer acc_month = 0;
			Map<String, Object> costCur = assYearMonthMapper.queryAssCurYearMonth(map);
			if (costCur.get("ass_flag").toString().equals("1") && !map.get("acc_month").equals("12")) {
				Integer mon = Integer.parseInt(shareYearMonth);
				// 查询系统启用日期
				String SysYearMonth = modStartMapper.queryModStartByModeCode(map);

				Integer sessmon = Integer.parseInt(SysYearMonth);
				// 月份比较
				if (mon >= sessmon) {

					year = shareYearMonth.substring(0, 4);
					month = shareYearMonth.substring(4, 6);
					// 获取上个物流期间
					acc_year = Integer.parseInt(map.get("acc_year").toString());
					acc_month = Integer.parseInt(map.get("acc_month").toString());
				} else {

					year = SysYearMonth.substring(0, 4);
					month = SysYearMonth.substring(4, 6);
					acc_year = Integer.parseInt(map.get("acc_year").toString());
					acc_month = Integer.parseInt(map.get("acc_month").toString());
				}

			} else {
				year = Integer.toString(Integer.parseInt(map.get("acc_year").toString()) + 1);
				month = "01";
				// 获取上个物流期间
				acc_year = Integer.parseInt(map.get("acc_year").toString());
				acc_month = 12;
			}
			Integer last_year = acc_year;
			Integer last_month = acc_month;
			/*
			 * if (acc_month == 1) {
			 * 
			 * last_month = 12;
			 * 
			 * last_year = acc_year - 1;
			 * 
			 * } else if (acc_month == 12) { last_month = acc_month;
			 * 
			 * last_year = acc_year; } else {
			 */

			last_month = acc_month;

			last_year = acc_year;
			/* } */
			// 判断下一期间是否存在
			Map<String, Object> existsMap = new HashMap<String, Object>();

			existsMap.put("group_id", SessionManager.getGroupId());

			existsMap.put("hos_id", SessionManager.getHosId());

			existsMap.put("copy_code", SessionManager.getCopyCode());

			existsMap.put("year", last_year);

			existsMap.put("month",
					(last_month.toString().length() == 1) ? ('0' + last_month.toString()) : last_month.toString());
			/*
			 * 
			 * 引用物流 判断日期是否在会计期间
			 * 
			 */
			int flag = matCommonMapper.existsAccYearMonthCheck(existsMap);

			String last_year_str = "";
			String last_month_str = "";

			if (flag == 0) {
				last_year_str = "";
				last_month_str = "";
			} else {
				last_year_str = last_year.toString();
				last_month_str = (last_month.toString().length() == 1) ? ('0' + last_month.toString())
						: last_month.toString();

			}

			sysConfigService.queryYearMonthInit(existsMap);

			return "{\"msg\":\"结转成功.\",\"state\":\"true\",\"last_year\":\"" + last_year + "\",\"year\":\"" + year
					+ "\",\"month\":\"" + month + "\",\"last_month\":\"" + last_month + "\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		}
	}

	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	private Map<String, Object> queryAccYearMonth() throws DataAccessException {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		// mapVo.put("acc_year", SessionManager.getAcctYear());

		return sysBaseService.queryAccYearMonthMap(mapVo);

	}

	private String collectDelDepre(Map<String, Object> map) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try {
			assClosingAccountMapper.collectDelDepreALL(map);

			String prm_AppCode = map.get("ass_AppCode").toString();

			String prm_ErrTxt = "";

			if ("0".equals(prm_AppCode)) {
				// 计算成功！提交事务
				transactionManager.commit(status);

				return "true";

			} else if ("-1".equals(prm_AppCode) || "100".equals(prm_AppCode)) {
				// 计算失败！回滚事务
				transactionManager.rollback(status);
			}

			if (!"".equals(map.get("ass_ErrTxt")) && null != map.get("ass_ErrTxt")) {
				prm_ErrTxt = map.get("ass_ErrTxt").toString();
			}
			return "{\"warn\":\"" + prm_ErrTxt + "\"}";
		} catch (BeansException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		} catch (TransactionException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String collectDelDepreALL(Map<String, Object> map) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		// 本月本年
		String cur_year = map.get("acc_year_month").toString().split("\\.")[0];
		String cur_month = map.get("acc_year_month").toString().split("\\.")[1];

		// 上年上月
		String top_year = "";
		String top_month = "";
		if (map.get("last_year_month") != null && !map.get("last_year_month").equals("")) {
			top_year = map.get("last_year_month").toString().split("\\.")[0];
			top_month = map.get("last_year_month").toString().split("\\.")[1];

		} else {
			transactionManager.rollback(status);
			return "{\"error\":\"当前年度没有结账信息,请勿反结账.\"}";
		}
		top_month = (top_month.length() == 1) ? ('0' + top_month.toString()) : top_month.toString();
		Integer topmon = Integer.parseInt(top_year + top_month);
		String SysYearMonth = modStartMapper.queryModStartByModeCode(map);
		Integer topsessmon = Integer.parseInt(SysYearMonth);
		if (topmon < topsessmon) {
			transactionManager.rollback(status);
			return "{\"error\":\"当前年度已经完成反结,请勿反结账.\"}";
		}
		map.put("operator", SessionManager.getUserName());
		// map.put("ass_flag", "1");
		map.put("ass_user", SessionManager.getUserId());
		map.put("top_ass_year", top_year);
		map.put("top_ass_month", top_month);
		map.put("cur_ass_year", cur_year);
		map.put("cur_ass_month", cur_month);
		map.put("acc_year", cur_year);
		map.put("acc_month", cur_month);
		String shareYearMonth = DateUtil.getNextYear_Month(map.get("acc_year_month").toString().replaceAll("\\.", ""));
		map.put("dept_year", shareYearMonth.substring(0, 4));
		map.put("dept_month", shareYearMonth.substring(4, 6));

		try {
			String calc = collectDelDepre(map);// 回退折旧计算

			if (!calc.equals("true")) {
				transactionManager.rollback(status);
				return calc;
			}

			assYearMonthMapper.updateAssDelYearMonth(map);// 回退会计结账
			transactionManager.commit(status);

			String year = "";
			String month = "";
			Integer acc_year = 0;
			Integer acc_month = 0;
			Map<String, Object> costCur = assYearMonthMapper.queryAssCurYearMonth(map);
			//if (costCur != null && costCur.get("ass_flag").toString()
					//.equals("0")/* &&!map.get("acc_month").equals("12") */) {
				Integer mon = Integer
						.parseInt(map.get("top_ass_year").toString() + map.get("top_ass_month").toString());
				Integer sessmon = Integer.parseInt(SysYearMonth);

				if (mon > sessmon) {

					year = map.get("top_ass_year").toString();
					month = map.get("top_ass_month").toString();
					// 获取上个物流期间
					acc_year = Integer.parseInt(map.get("top_ass_year").toString());
					acc_month = Integer.parseInt(map.get("top_ass_month").toString());
				} else if (mon.equals(sessmon)) {

					year = map.get("top_ass_year").toString();
					month = map.get("top_ass_month").toString();
					// 获取上个物流期间
					acc_year = 0000;
					acc_month = 00;
				} else {

					year = MyConfig.getCurAccYearMonth().substring(0, 4);
					month = MyConfig.getCurAccYearMonth().substring(4, 6);
					acc_year = Integer.parseInt(map.get("acc_year").toString());
					acc_month = Integer.parseInt(map.get("acc_month").toString());
				}

			//}
			/*
			 * else{ year="0000"; month = "00"; // 获取上个物流期间 acc_year =
			 * Integer.parseInt(SessionManager.getAcctYear()); acc_month = 12; }
			 */
			Integer last_year = acc_year;
			Integer last_month = acc_month;

			if (acc_month == 1) {

				last_month = 12;

				last_year = acc_year - 1;

			} else if (acc_month == 00) {

				last_month = acc_month;

				last_year = acc_year;
			} else {

				last_month = acc_month - 1;

				last_year = acc_year;

			}
			// 判断下一期间是否存在
			Map<String, Object> existsMap = new HashMap<String, Object>();

			existsMap.put("group_id", SessionManager.getGroupId());

			existsMap.put("hos_id", SessionManager.getHosId());

			existsMap.put("copy_code", SessionManager.getCopyCode());

			existsMap.put("year", last_year);

			existsMap.put("month",
					(last_month.toString().length() == 1) ? ('0' + last_month.toString()) : last_month.toString());
			/*
			 * 
			 * 引用物流 判断日期是否在会计期间
			 * 
			 */
			int flag = matCommonMapper.existsAccYearMonthCheck(existsMap);

			String last_year_str = "";
			String last_month_str = "";

			if (flag == 0) {
				last_year_str = "";
				last_month_str = "";
			} else {
				last_year_str = last_year.toString();
				last_month_str = (last_month.toString().length() == 1) ? ('0' + last_month.toString())
						: last_month.toString();

			}
			sysConfigService.queryYearMonthInit(existsMap);

			return "{\"msg\":\"反结成功.\",\"state\":\"true\",\"last_year\":\"" + last_year + "\",\"year\":\"" + year
					+ "\",\"month\":\"" + month + "\",\"last_month\":\"" + last_month + "\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		}
	}

	private String collectGenerate(Map<String, Object> map) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		try {
			assClosingAccountMapper.collectGenerate(map);//不带仓库
			assClosingAccountMapper.collectAssReportApp(map);//带仓库

			// String prm_AppCode = map.get("ass_AppCode").toString();

			// String prm_ErrTxt = "";

			// if ("0".equals(prm_AppCode)) {
			// 计算成功！提交事务
			transactionManager.commit(status);

			return "true";

			// } else if ("-1".equals(prm_AppCode) || "100".equals(prm_AppCode))
			// {
			// 计算失败！回滚事务
			// transactionManager.rollback(status);
			// }

			// if (!"".equals(map.get("ass_ErrTxt")) && null !=
			// map.get("ass_ErrTxt")) {
			// prm_ErrTxt = map.get("ass_ErrTxt").toString();
			// }
			// return "{\"warn\":\"" + prm_ErrTxt + "\"}";
		} catch (BeansException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		} catch (TransactionException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			transactionManager.rollback(status);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> queryAssSysYearMonth(Map<String, Object> mapVo) throws DataAccessException {
		return assYearMonthMapper.queryAssSysYearMonth(mapVo);
	}

}
