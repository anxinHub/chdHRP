/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.cost.dao.CostHrpDeptRefMapper;
import com.chd.hrp.cost.service.CostCustomDeptService;
import com.chd.hrp.cost.service.CostHrpDeptRefService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.service.DeptService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costHrpDeptRefService")
public class CostHrpDeptRefServiceImpl implements CostHrpDeptRefService {

	private static Logger logger = Logger.getLogger(CostHrpDeptRefServiceImpl.class);

	@Resource(name = "costHrpDeptRefMapper")
	private final CostHrpDeptRefMapper costHrpDeptRefMapper = null;

	/**
	 * @Description 添加
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		int count = costHrpDeptRefMapper.queryExist(entityMap);

		if (count > 0) {

			return "{\"error\":\"所选系统科室对应关系已存在(系统科室只能对应一个成本自定义科室).\"}";
		}

		try {
			DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");

			entityMap.put("start_date", df.format(new Date()));
			
			int result = costHrpDeptRefMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 错误编码 add\"}";

		}

	}

	/**
	 * @Description 批量添加Dept
	 * @param Dept
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costHrpDeptRefMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 错误编码 addBatch\"}";

		}
	}

	/**
	 * @Description 查询Dept分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = (List<Map<String, Object>>) costHrpDeptRefMapper.query(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 查询DeptByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return costHrpDeptRefMapper.queryByCode(entityMap);

	}

	/**
	 * @Description 批量删除Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
	
			 costHrpDeptRefMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costHrpDeptRefMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 错误编码  deleteDept\"}";

		}
	}


	@Transactional(rollbackFor = Exception.class)
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		int count = costHrpDeptRefMapper.queryExist(entityMap);

		if (count > 0) {

			return "{\"error\":\"所选系统科室对应关系已存在(系统科室只能对应一个成本自定义科室).\"}";
		}

		try {
			costHrpDeptRefMapper.update(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败! 错误编码  update\"}";

		}
	}

	/**
	 * @Description 批量更新Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costHrpDeptRefMapper.updateBatch(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败! 错误编码  updateBatch\"}";

		}

	}


	
	/**
	 * 导入时 校验  系统科室编码是否合法
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryHrpDeptDate(Map<String, Object> map) {
		
		return costHrpDeptRefMapper.queryHrpDeptDate(map);
	}
	
	/**
	 * 导入时 校验  自定义科室编码是否合法
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryCostDeptDate(Map<String, Object> map) {
		
		return costHrpDeptRefMapper.queryCostDeptDate(map);
	}

	@Override
	public int queryExist(Map<String, Object> map) throws DataAccessException {

		return costHrpDeptRefMapper.queryExist(map);
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 60);
	
	@Override
	public String queryHrpDept(Map<String, Object> entityMap) 	throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(costHrpDeptRefMapper.queryHrpDept(entityMap, rowBounds));
	}

	@Override
	public String queryCostCustomDept(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(costHrpDeptRefMapper.queryCostCustomDept(entityMap, rowBounds));
	}

	@Override
	public String endCostHrpDeptRef(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			costHrpDeptRefMapper.endCostHrpDeptRef(entityList);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败! 错误编码  endCostHrpDeptRef\"}";

		}
	}


}
