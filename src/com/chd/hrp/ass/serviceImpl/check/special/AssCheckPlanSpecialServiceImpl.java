/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.check.special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.check.special.AssCheckDdetailSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckDpDetailSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckDspecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckPlanSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckSdetailSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckSspecialMapper;
import com.chd.hrp.ass.entity.check.special.AssCheckDspecial;
import com.chd.hrp.ass.entity.check.special.AssCheckPlanSpecial;
import com.chd.hrp.ass.entity.check.special.AssCheckSspecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.special.AssCheckPlanSpecialService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 051101 盘点任务单(专用设备)
 * @Table: ASS_CHECK_PLAN_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assCheckPlanSpecialService")
public class AssCheckPlanSpecialServiceImpl implements AssCheckPlanSpecialService {

	private static Logger logger = Logger.getLogger(AssCheckPlanSpecialServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assCheckPlanSpecialMapper")
	private final AssCheckPlanSpecialMapper assCheckPlanSpecialMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "assCheckSdetailSpecialMapper")
	private final AssCheckSdetailSpecialMapper assCheckSdetailSpecialMapper = null;

	// 引入DAO操作
	@Resource(name = "assCheckSspecialMapper")
	private final AssCheckSspecialMapper assCheckSspecialMapper = null;

	@Resource(name = "assCheckDdetailSpecialMapper")
	private final AssCheckDdetailSpecialMapper assCheckDdetailSpecialMapper = null;

	// 引入DAO操作
	@Resource(name = "assCheckDspecialMapper")
	private final AssCheckDspecialMapper assCheckDspecialMapper = null;

	@Resource(name = "assCheckDpDetailSpecialMapper")
	private final AssCheckDpDetailSpecialMapper assCheckDpDetailSpecialMapper = null;

	/**
	 * @Description 添加051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象051101 盘点任务单(专用设备)
		AssCheckPlanSpecial assCheckPlanSpecial = queryByCode(entityMap);

		if (assCheckPlanSpecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		Map<String, Object> mv = new HashMap<String, Object>();
		mv.put("group_id", entityMap.get("group_id"));
		mv.put("hos_id", entityMap.get("hos_id"));
		mv.put("copy_code", entityMap.get("copy_code"));
		mv.put("user_id", SessionManager.getUserId());
		mv.put("naturs_code", "02");
		// 盘点是否存在未执行完的盘点计划单
		List<Map<String, Object>> planlist = (List<Map<String, Object>>) assBaseService.queryCheckPlanFin(mv);
		if (planlist.size()>0) {
			return "{\"error\":\"存在未执行完的计划单，不允许添加.\"}";
		}

		try {
			entityMap.put("bill_table", "ASS_CHECK_PLAN_SPECIAL");
			String check_plan_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("check_plan_no", check_plan_no);
			int state = assCheckPlanSpecialMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加051101 盘点任务单(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckPlanSpecialMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assCheckPlanSpecialMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新051101 盘点任务单(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckPlanSpecialMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);

		List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) queryExists(mapVo);

		for (AssCheckPlanSpecial cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());

			if (cs.getState() == 1) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}

		// 循环删除 盘点单（仓库 科室 主从表 ） 任务单
		for (Map<String, Object> m : listVo) {
			assCheckPlanSpecialMapper.delete(m);
		}

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为确认状态不能删除.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}

	}

	/**
	 * @Description 批量删除051101 盘点任务单(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			// 删除仓库盘点明细
			assCheckSdetailSpecialMapper.deleteBatch(entityList);
			// 删除科室盘点明细
			assCheckDdetailSpecialMapper.deleteBatch(entityList);
			// 删除科室盘点单
			assCheckDspecialMapper.deleteBatch(entityList);
			// 删除仓库盘点单
			assCheckSspecialMapper.deleteBatch(entityList);
			// 删除盘点计划
			assCheckPlanSpecialMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象051101 盘点任务单(专用设备)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) assCheckPlanSpecialMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assCheckPlanSpecialMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assCheckPlanSpecialMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) assCheckPlanSpecialMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) assCheckPlanSpecialMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanSpecialMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return AssCheckPlanSpecial
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanSpecialMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取051101 盘点任务单(专用设备)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<AssCheckPlanSpecial>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanSpecialMapper.queryExists(entityMap);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String audit(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) queryExists(mapVo);

		for (AssCheckPlanSpecial cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());

			map.put("audit_emp", SessionManager.getUserId());

			map.put("audit_date", DateUtil.getCurrenDate());
			Integer checeks = assCheckPlanSpecialMapper.queryCheckS(map);
			if (checeks == 0) {
				return "{\"error\":\"编码：【" + cs.getCheck_plan_no() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			Integer checekd = assCheckPlanSpecialMapper.queryCheckD(map);
			if (checeks == 0) {
				return "{\"error\":\"编码：【" + cs.getCheck_plan_no() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			map.put("state", "1");

			if (cs.getState() == 1) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据已确认不能重复确认.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		}

	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String unAudit(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) queryExists(mapVo);

		for (AssCheckPlanSpecial cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());

			map.put("audit_emp", "");

			map.put("audit_date", "");

			map.put("state", "0");

			if (cs.getState() == 0) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建状态不能取消确认.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
		}
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String finish(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) queryExists(mapVo);

		for (AssCheckPlanSpecial cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());

			map.put("fin_date", DateUtil.getCurrenDate());

			map.put("state", "2");

			if (cs.getState() == 2 || cs.getState() == 0) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建或者完成状态不能盘点完成.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"盘点完成.\",\"state\":\"true\"}";
		}

	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String unFinish(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlanSpecial> list = (List<AssCheckPlanSpecial>) queryExists(mapVo);

		for (AssCheckPlanSpecial cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());

			map.put("fin_date", "");

			map.put("state", "1");

			if (cs.getState() == 0 || cs.getState() == 1) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建和确认状态不能取消完成.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"取消完成.\",\"state\":\"true\"}";
		}
	}

	/**
	 * 生成盘点单 仓库和科室
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			AssCheckPlanSpecial s = assCheckPlanSpecialMapper.queryByCode(entityMap);
			if (s == null || s.getState() == 1) {
				return "{\"error\":\"盘点单已确认，不能生成盘点单.\",\"state\":\"true\"}";
			} else {

				addGenerateStore(entityMap);
				addGenerateDept(entityMap);

				// 添加科室盘点单 主从表
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	/**
	 * 生成盘点单 仓库和科室
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String generateDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("is_dept").toString().equals("0")) {
				addGenerateStoreDetail(entityMap);
			} else if (entityMap.get("is_dept").toString().equals("1")) {
				addGenerateDeptDetail(entityMap);
			} else {
				// 当前台传递的is_dept不为 0 或者 1 时 不进行操作
				return "{\"msg\":\"无效的盘点单 is_dept 取值错误.\",\"state\":\"true\"}";
			}

			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	/**
	 * 生成盘点单 仓库
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addGenerateStore(Map<String, Object> entityMap) throws DataAccessException {
		try {

			// 查询仓库信息 按照仓库生成盘点单
			List<Map<String, Object>> store = (List<Map<String, Object>>) assCheckPlanSpecialMapper.queryStores(entityMap);

			for (Map<String, Object> mapS : store) {
				Map<String, Object> s = new HashMap<String, Object>();
				s.putAll(entityMap);
				s.put("store_id", mapS.get("store_id"));
				s.put("store_no", mapS.get("store_no"));
				// 添加仓库盘点单 主表
				mapS.put("bill_table", "ASS_CHECK_S_SPECIAL");
				String check_no = assBaseService.getBillNOSeqNo(mapS);

				s.put("check_no", check_no);
				s.put("check_emp", SessionManager.getUserId());
				s.put("check_date", DateUtil.getCurrenDate());
				s.put("state", "0");
				s.put("summary", "");
				s.put("note", "");

				assCheckPlanSpecialMapper.addGenerateStore(s);
				// 更新最大单号
				assBaseService.updateAssBillNoMaxNo(mapS);
			}
			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	/**
	 * 生成盘点单 科室
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addGenerateDept(Map<String, Object> entityMap) throws DataAccessException {
		try {

			// 查询科室信息 按照科室生成盘点单
			List<Map<String, Object>> depts = (List<Map<String, Object>>) assCheckPlanSpecialMapper.queryDepts(entityMap);
			for (Map<String, Object> mapD : depts) {

				Map<String, Object> s = new HashMap<String, Object>();
				s.putAll(entityMap);
				s.put("dept_id", mapD.get("dept_id"));
				s.put("dept_no", mapD.get("dept_no"));
				mapD.put("bill_table", "ASS_CHECK_D_SPECIAL");
				String check_no = assBaseService.getBillNOSeqNo(mapD);

				s.put("check_no", check_no);
				s.put("check_emp", SessionManager.getUserId());
				s.put("check_date", DateUtil.getCurrenDate());
				s.put("state", "0");
				s.put("summary", "");
				s.put("note", "");
				assCheckPlanSpecialMapper.addGenerateDept(s);
				// 更新最大单号
				assBaseService.updateAssBillNoMaxNo(mapD);
			}
			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	// 拼接字符串
	private String vo_split(String str) {
		StringBuilder vo_no = new StringBuilder();

		for (String vo : str.split(",")) {
			vo_no.append("'");
			vo_no.append(vo);
			vo_no.append("',");
		}

		String sp = vo_no.toString().substring(0, vo_no.length() - 1);

		return "(" + sp + ")";
	}

	@Override
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException {

		return assCheckPlanSpecialMapper.queryByTree(entityMap);

	}

	@Override
	public String addGenerateStoreDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assCheckPlanSpecialMapper.addGenerateStoreDetail(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}

	}

	@Override
	public String addGenerateDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assCheckPlanSpecialMapper.addGenerateDeptDetail(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}

	}

	@Override
	public String auditCheckSpecial(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("is_dept").toString().equals("0")) {
				AssCheckSspecial s = assCheckSspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 0) {
					return "{\"msg\":\"不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckSspecialMapper.update(entityMap);
				}

			} else if (entityMap.get("is_dept").toString().equals("1")) {
				AssCheckDspecial s = assCheckDspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 0) {
					return "{\"msg\":\"不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckDspecialMapper.update(entityMap);
				}
			} else {
				// 当前台传递的is_dept不为 0 或者 1 时 不进行操作
				return "{\"msg\":\"无效的盘点单 is_dept 取值错误.\",\"state\":\"false\"}";
			}
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"提交失败\"}");
		}

	}

	@Override
	public String unAuditCheckSpecial(Map<String, Object> entityMap) throws DataAccessException {

		try {
			if (entityMap.get("is_dept").toString().equals("0")) {
				AssCheckSspecial s = assCheckSspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 1) {
					return "{\"msg\":\"不是提交状态不能取消提交.\",\"state\":\"true\"}";
				} else {
					assCheckSspecialMapper.update(entityMap);
				}

			} else if (entityMap.get("is_dept").toString().equals("1")) {
				AssCheckDspecial s = assCheckDspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 1) {
					return "{\"msg\":\"不是提交状态不能取消提交.\",\"state\":\"true\"}";
				} else {
					assCheckDspecialMapper.update(entityMap);
				}
			} else {
				// 当前台传递的is_dept不为 0 或者 1 时 不进行操作
				return "{\"msg\":\"无效的盘点单 is_dept 取值错误.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"取消提交成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"取消提交失败\"}");
		}

	}

}
