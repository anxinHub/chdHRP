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
import com.chd.base.util.DateUtil;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.service.EmpDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("empDictService")
public class EmpDictServiceImpl implements EmpDictService {

	private static Logger logger = Logger.getLogger(EmpDictServiceImpl.class);

	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;

	

	/**
	 * @Description 添加EmpDict
	 * @param EmpDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addEmpDict(Map<String, Object> entityMap) throws DataAccessException {

		EmpDict empDict = queryEmpDictByCode(entityMap);

		if (empDict != null) {

			return "{\"error\":\"编码：" + empDict.getEmp_code().toString() + "已存在.\"}";

		}

		

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("is_stop", 1);
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("emp_code", entityMap.get("emp_code"));
			map.put("emp_id", entityMap.get("emp_id"));
			empDictMapper.updateEmpDictState(map);
			entityMap.put("user_code", SessionManager.getUserCode());
		/*	entityMap.put("create_date", new Date());*/
			entityMap.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
			
			empDictMapper.addEmpDict(entityMap);

			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addEmpDict\"}";

		}

	}

	/**
	 * @Description 批量添加EmpDict
	 * @param EmpDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchEmpDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			empDictMapper.addBatchEmpDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchEmpDict\"}";

		}
	}

	/**
	 * @Description 查询EmpDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryEmpDict(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<EmpDict> list = empDictMapper.queryEmpDict(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryEmpDictList(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<EmpDict> list = empDictMapper.queryEmpDictList(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<EmpDict> list = empDictMapper.queryEmpDictList(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 查询EmpDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public EmpDict queryEmpDictByCode(Map<String, Object> entityMap) throws DataAccessException {

		return empDictMapper.queryEmpDictByCode(entityMap);

	}

	/**
	 * @Description 批量删除EmpDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchEmpDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = empDictMapper.deleteBatchEmpDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchEmpDict\"}";

		}

	}

	/**
	 * @Description 删除EmpDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteEmpDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			empDictMapper.deleteEmpDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteEmpDict\"}";

		}
	}

	/**
	 * @Description 更新EmpDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateEmpDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			empDictMapper.updateEmpDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmpDict\"}";

		}
	}

	/**
	 * @Description 批量更新EmpDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchEmpDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			empDictMapper.updateBatchEmpDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmpDict\"}";

		}

	}

	/**
	 * @Description 导入EmpDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importEmpDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryEmpDictPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = empDictMapper.queryEmpDictPrint(entityMap);

		return list;
	}

}
