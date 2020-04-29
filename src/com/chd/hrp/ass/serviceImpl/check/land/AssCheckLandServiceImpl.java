/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.check.land;

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
import com.chd.hrp.ass.dao.check.land.AssCheckDetailLandMapper;
import com.chd.hrp.ass.dao.check.land.AssCheckLandMapper;
import com.chd.hrp.ass.entity.check.land.AssCheckLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.land.AssCheckLandService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 051101 科室盘点单(土地)
 * @Table: ASS_CHECK_D_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assCheckLandService")
public class AssCheckLandServiceImpl implements AssCheckLandService {

	private static Logger logger = Logger.getLogger(AssCheckLandServiceImpl.class);

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	// 引入DAO操作
	@Resource(name = "assCheckLandMapper")
	private final AssCheckLandMapper assCheckLandMapper = null;

	// 引入Service服务
	@Resource(name = "assCheckDetailLandMapper")
	private final AssCheckDetailLandMapper assCheckDetailLandMapper = null;

	/**
	 * @Description 添加051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象051101 科室盘点单(土地)
		AssCheckLand assCheckLand = queryByCode(entityMap);

		if (assCheckLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {
			entityMap.put("bill_table", "ASS_CHECK_D_LAND");
			String check_no = assBaseService.getBillNOSeqNo(entityMap);

			entityMap.put("check_no", check_no);
			entityMap.put("check_emp", SessionManager.getUserId());
			entityMap.put("check_date", DateUtil.getCurrenDate());
			entityMap.put("state", "0");
			entityMap.put("summary", "");
			entityMap.put("note", "");
			int state = assCheckLandMapper.add(entityMap);

			// 更新最大单号
			assBaseService.updateAssBillNoMaxNo(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"check_no\":\"" + check_no + "\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加051101 科室盘点单(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assCheckLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新051101 科室盘点单(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			assCheckDetailLandMapper.delete(entityMap);

			int state = assCheckLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"删除失败\"}");

		}

	}

	/**
	 * @Description 批量删除051101 科室盘点单(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCheckLandMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加051101 科室盘点单(土地)<BR>
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
		// 判断是否存在对象051101 科室盘点单(土地)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssCheckLand> list = (List<AssCheckLand>) assCheckLandMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assCheckLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assCheckLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCheckLand> list = (List<AssCheckLand>) assCheckLandMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCheckLand> list = (List<AssCheckLand>) assCheckLandMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return AssCheckDland
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取051101 科室盘点单(土地)<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<AssCheckDland>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckLandMapper.queryExists(entityMap);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String auditBatch(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_no", "");
		String vr = vo_split(mapVo.get("check_no_all").toString());
		mapVo.put("check_no_all", vr);
		List<AssCheckLand> list = (List<AssCheckLand>) queryExists(mapVo);

		for (AssCheckLand cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());
			map.put("check_no", cs.getCheck_no());

			map.put("audit_emp", SessionManager.getUserId());

			map.put("audit_date", DateUtil.getCurrenDate());

			map.put("state", "1");

			if (cs.getState() == 1) {
				sb.append(cs.getCheck_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据已审核不能重复审核.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
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
	public String unAuditBatch(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_no", "");
		String vr = vo_split(mapVo.get("check_no_all").toString());
		mapVo.put("check_no_all", vr);
		List<AssCheckLand> list = (List<AssCheckLand>) queryExists(mapVo);

		for (AssCheckLand cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());
			map.put("check_no", cs.getCheck_no());

			map.put("audit_emp", "");

			map.put("audit_date", "");

			map.put("state", "0");

			if (cs.getState() == 0) {
				sb.append(cs.getCheck_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建状态不能销审.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		}
	}

	/**
	 * 拼接字符串 每个业务类型单独处理，暂不共用
	 * @param str
	 * @return
	 */
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
	public AssCheckLand queryState(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assCheckLandMapper.queryState(mapVo);
	}
}
