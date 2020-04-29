/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.Date;
import java.util.HashMap;
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
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.service.DeptDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("deptDictService")
public class DeptDictServiceImpl implements DeptDictService {

	private static Logger logger = Logger.getLogger(DeptDictServiceImpl.class);

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	/**
	 * @Description 添加DeptDict
	 * @param DeptDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		DeptDict deptDict = queryDeptDictByCode(entityMap);

		if (deptDict != null) {

			return "{\"error\":\"编码：" + deptDict.getDept_code().toString() + "已存在.\"}";

		}

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("is_stop", 1);
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("dept_code", entityMap.get("dept_code"));
			map.put("dept_id", entityMap.get("dept_id"));
			deptDictMapper.updateDeptDictState(map);
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			deptDictMapper.addDeptDict(entityMap);

			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDeptDict\"}";

		}

	}

	/**
	 * @Description 批量添加DeptDict
	 * @param DeptDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchDeptDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			deptDictMapper.addBatchDeptDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchDeptDict\"}";

		}
	}

	/**
	 * @Description 查询DeptDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<DeptDict> list = deptDictMapper.queryDeptDictLike(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<DeptDict> list = deptDictMapper.queryDeptDictLike(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}

	}

	/**
	 * @Description 查询DeptDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public DeptDict queryDeptDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("is_flag", SessionManager.getCostParaMap().get("03001"));
		return deptDictMapper.queryDeptDictByCode(entityMap);

	}

	/**
	 * @Description 查询DeptDictByDeptNo(下拉框反查询)
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public DeptDict queryDeptDictByDeptNo(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		return deptDictMapper.queryDeptDictByDeptNo(entityMap);
	}

	/**
	 * @Description 批量删除DeptDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchDeptDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = deptDictMapper.deleteBatchDeptDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchDeptDict\"}";

		}

	}

	/**
	 * @Description 删除DeptDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			deptDictMapper.deleteDeptDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptDict\"}";

		}
	}

	/**
	 * @Description 更新DeptDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			deptDictMapper.updateDeptDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptDict\"}";

		}
	}

	/**
	 * @Description 批量更新DeptDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchDeptDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			deptDictMapper.updateBatchDeptDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptDict\"}";

		}

	}

	/**
	 * @Description 导入DeptDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryDeptDictByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<DeptDict> DeotDictList = deptDictMapper.queryDeptDict(entityMap);
		if (DeotDictList.size() > 0) {
			int row = 0;
			for (DeptDict deptDict : DeotDictList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + deptDict.getDept_code() + ",");
				// jsonResult.append("pId:" + menu.getPid() + ",");
				jsonResult.append("name:'" + deptDict.getDept_name() + "',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}

	@Override
	public List<DeptDict> queryDeptDictNo(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return deptDictMapper.queryDeptDictNo(entityMap);
	}

	@Override
	public String queryDeptDictBySelector(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<DeptDict> deptList = deptDictMapper.queryDeptDict(entityMap);
		if (deptList.size() > 0) {
			int row = 0;
			for (DeptDict dept : deptList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:'" + dept.getDept_code() + "',");
				jsonResult.append("dept_id:'" + dept.getDept_id() + "',");
				jsonResult.append("hos_id:" + dept.getHos_id() + ",");
				jsonResult.append("group_id:" + dept.getGroup_id() + ",");
				jsonResult.append("pId:'" + dept.getSuper_code() + "',");
				jsonResult.append("name:'" + dept.getDept_code() + " " + dept.getDept_name() + "'");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		logger.warn("json:" + jsonResult.toString());
		return jsonResult.toString();
	}

	@Override
	public List<DeptDict> queryDeptDictByType(Map<String, Object> entityMap) throws DataAccessException {
		List<DeptDict> list = deptDictMapper.queryDeptDictByType(entityMap);
		return list;
	}

	@Override
	public DeptDict queryDeptDictByDeptCodeMap(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return deptDictMapper.queryDeptDictByDeptCodeMap(entityMap);
	}

	@Override
	public DeptDict queryDeptDictByDeptCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return deptDictMapper.queryDeptDictByDeptCode(entityMap);
	}

	@Override
	public List<?> queryDeptDictByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = deptDictMapper.queryDeptDictByTree(entityMap);
		return l_map;
	}

	@Override
	public String queryDeptDictCost(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<DeptDict> list = deptDictMapper.queryDeptDictCost(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryDeptDictByPrmDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<DeptDict> list = deptDictMapper.queryDeptDictByPrmDept(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<DeptDict> list = deptDictMapper.queryDeptDictByPrmDept(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryDeptDictPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = deptDictMapper.queryDeptDictLikePrint(entityMap);
		
		return list;
	}
	/**
	 * 用于集团辅助账  --部门选择器
	 */
	@Override
	public String queryGroupDeptDictBySelector(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<DeptDict> deptList = deptDictMapper.queryGroupDeptDict(entityMap);
		if (deptList.size() > 0) {
			int row = 0;
			for (DeptDict dept : deptList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:'" + dept.getDept_code() + "',");
				jsonResult.append("dept_id:'" + dept.getDept_id() + "',");
				jsonResult.append("hos_id:" + dept.getHos_id() + ",");
				jsonResult.append("group_id:" + dept.getGroup_id() + ",");
				jsonResult.append("pId:'" + dept.getSuper_code() + "',");
				jsonResult.append("name:'" + dept.getDept_code() + " " + dept.getDept_name() + "'");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		logger.warn("json:" + jsonResult.toString());
		return jsonResult.toString();
	}
}
