/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.check.house;

import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.ass.dao.check.house.AssCheckDetailHouseMapper;
import com.chd.hrp.ass.dao.check.house.AssCheckHouseMapper;
import com.chd.hrp.ass.dao.check.house.AssCheckPlanHouseMapper;
import com.chd.hrp.ass.entity.check.house.AssCheckHouse;
import com.chd.hrp.ass.entity.check.house.AssCheckPlanHouse;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.house.AssCheckPlanHouseService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 051101 盘点任务单(房屋及建筑)
 * @Table: ASS_CHECK_PLAN_House
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assCheckPlanHouseService")
public class AssCheckPlanHouseServiceImpl implements AssCheckPlanHouseService {

	private static Logger logger = Logger.getLogger(AssCheckPlanHouseServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assCheckPlanHouseMapper")
	private final AssCheckPlanHouseMapper assCheckPlanHouseMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assCheckDetailHouseMapper")
	private final AssCheckDetailHouseMapper assCheckDetailHouseMapper = null;
	
	// 引入DAO操作
	@Resource(name = "assCheckHouseMapper")
	private final AssCheckHouseMapper assCheckHouseMapper = null;

	/**
	 * @Description 添加051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象051101 盘点任务单(房屋及建筑)
		AssCheckPlanHouse assCheckPlanHouse = queryByCode(entityMap);

		if (assCheckPlanHouse != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		Map<String, Object> mv = new HashMap<String, Object>();
		mv.put("group_id", entityMap.get("group_id"));
		mv.put("hos_id", entityMap.get("hos_id"));
		mv.put("copy_code", entityMap.get("copy_code"));
		mv.put("user_id", SessionManager.getUserId());
		mv.put("naturs_code", "01");
		// 盘点是否存在未执行完的盘点计划单
		List<Map<String, Object>> planlist = (List<Map<String, Object>>) assBaseService.queryCheckPlanFin(mv);
		if (planlist.size()>0) {
			return "{\"error\":\"存在未执行完的计划单，不允许添加.\"}";
		}
		try {
			entityMap.put("bill_table", "ASS_CHECK_PLAN_HOUSE");
			String check_plan_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("check_plan_no", check_plan_no);
			int state = assCheckPlanHouseMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckPlanHouseMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assCheckPlanHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckPlanHouseMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除051101 盘点任务单(房屋及建筑)<BR>
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

		List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) queryExists(mapVo);

		for (AssCheckPlanHouse cs : list) {

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
			assCheckPlanHouseMapper.delete(m);
		}

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为确认状态不能删除.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}

	}

	/**
	 * @Description 批量删除051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			//删除盘点明细
			assCheckDetailHouseMapper.deleteBatch(entityList);
			//删除科室盘点单
			assCheckHouseMapper.deleteBatch(entityList);
			assCheckPlanHouseMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加051101 盘点任务单(房屋及建筑)<BR>
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
		// 判断是否存在对象051101 盘点任务单(房屋及建筑)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) assCheckPlanHouseMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assCheckPlanHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assCheckPlanHouseMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) assCheckPlanHouseMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) assCheckPlanHouseMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanHouseMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return AssCheckPlanHouse
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanHouseMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取051101 盘点任务单(房屋及建筑)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<AssCheckPlanHouse>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanHouseMapper.queryExists(entityMap);
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
		List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) queryExists(mapVo);

		for (AssCheckPlanHouse cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());

			map.put("audit_emp", SessionManager.getUserId());

			map.put("audit_date", DateUtil.getCurrenDate());

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
		List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) queryExists(mapVo);

		for (AssCheckPlanHouse cs : list) {

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
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
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
		List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) queryExists(mapVo);

		for (AssCheckPlanHouse cs : list) {

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
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
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
		List<AssCheckPlanHouse> list = (List<AssCheckPlanHouse>) queryExists(mapVo);

		for (AssCheckPlanHouse cs : list) {

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
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
	}

	/**
	 * 生成盘点单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {
		//addGenerateStore(entityMap);
		//addGenerateDept(entityMap);
		try {

			// 添加盘点单 主表
			entityMap.put("bill_table", "ASS_CHECK_HOUSE");
			String check_no = assBaseService.getBillNOSeqNo(entityMap);

			entityMap.put("check_no", check_no);
			entityMap.put("check_emp", SessionManager.getUserId());
			entityMap.put("check_date", DateUtil.getCurrenDate());
			entityMap.put("state", "0");
			entityMap.put("summary", "");
			entityMap.put("note", "");

			assCheckPlanHouseMapper.addGenerate(entityMap);
			// 更新最大单号
			assBaseService.updateAssBillNoMaxNo(entityMap);
		// 添加盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

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
			String check_no = "";
			entityMap.put("bill_table", "ASS_CHECK_House");
			if(entityMap.get("check_no") == null || "".equals(entityMap.get("check_no"))){
				check_no = assBaseService.getBillNOSeqNo(entityMap);

				entityMap.put("check_no", check_no);
				entityMap.put("check_emp", SessionManager.getUserId());
				entityMap.put("check_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
				entityMap.put("state", "0");
				entityMap.put("summary", "");
				entityMap.put("note", "");
				
				assCheckPlanHouseMapper.addAssCheckHouse(entityMap);
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}else{
				check_no = entityMap.get("check_no").toString();
			}
			assCheckPlanHouseMapper.addGenerateDetail(entityMap);
			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\",\"check_no\":\""+check_no+"\"}";

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

		return assCheckPlanHouseMapper.queryByTree(entityMap);

	}

	@Override
	public String auditCheckHouse(Map<String, Object> entityMap) throws DataAccessException {
		try {
			AssCheckHouse s=	assCheckHouseMapper.queryByCode(entityMap);
			if(s==null || s.getState()!=0){
				return "{\"msg\":\"不是新建状态不能提交.\",\"state\":\"true\"}";
			}else{
				assCheckHouseMapper.update(entityMap);
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
	public String unAuditCheckHouse(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			AssCheckHouse s=	assCheckHouseMapper.queryByCode(entityMap);
			if(s==null || s.getState()!=1){
				return "{\"msg\":\"不是提交状态不能取消提交.\",\"state\":\"true\"}";
			}else{
				assCheckHouseMapper.update(entityMap);
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"操作失败\"}");
		}
		
	}

	
}
