/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.service.AphiDeptDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8804 科室字典变更表
 * @Table: Prm_DEPT_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptDictService")
public class AphiDeptDictServiceImpl implements AphiDeptDictService {

	private static Logger logger = Logger.getLogger(AphiDeptDictServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	/**
	 * @Description 添加8804 科室字典变更表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 获取对象8804 科室字典变更表
		AphiDeptDict PrmDeptDict = queryPrmDeptDictByCode(entityMap);

		if (PrmDeptDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = aphiDeptDictMapper.addPrmDeptDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptDict\"}";

		}

	}

	/**
	 * @Description 批量添加8804 科室字典变更表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptDictMapper.addBatchAphiDeptDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptDict\"}";

		}

	}

	/**
	 * @Description 更新8804 科室字典变更表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = aphiDeptDictMapper.updatePrmDeptDict(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptDict\"}";

		}

	}

	/**
	 * @Description 批量更新8804 科室字典变更表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptDictMapper.updateBatchPrmDeptDict(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptDict\"}";

		}

	}

	/**
	 * @Description 删除8804 科室字典变更表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = aphiDeptDictMapper.deletePrmDeptDict(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptDict\"}";

		}

	}

	/**
	 * @Description 批量删除8804 科室字典变更表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptDictMapper.deleteBatchPrmDeptDict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptDict\"}";

		}
	}

	/**
	 * @Description 查询结果集8804 科室字典变更表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptDict> list = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptDict> list = aphiDeptDictMapper.queryPrmDeptDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象8804 科室字典变更表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AphiDeptDict queryPrmDeptDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptDictMapper.queryPrmDeptDictByCode(entityMap);
	}

	@Override
	public String queryDeptIncomeAdd(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptDict> list = aphiDeptDictMapper.queryDeptIncomeAdd(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptDict> list = aphiDeptDictMapper.queryDeptIncomeAdd(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


}
