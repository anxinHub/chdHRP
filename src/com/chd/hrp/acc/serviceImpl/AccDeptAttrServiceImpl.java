/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccDeptAttrMapper;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.service.AccDeptAttrService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 部门字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accDeptAttrService")
public class AccDeptAttrServiceImpl implements AccDeptAttrService {

	private static Logger logger = Logger.getLogger(AccDeptAttrServiceImpl.class);

	@Resource(name = "accDeptAttrMapper")
	private final AccDeptAttrMapper accDeptAttrMapper = null;

	/**
	 * @Description 部门字典属性表<BR>
	 *              添加AccDeptAttr
	 * @param AccDeptAttr
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccDeptAttr(Map<String, Object> entityMap) throws DataAccessException {

		try {

			AccDeptAttr accDeptAttr = queryAccDeptAttrByCode(entityMap);

			if (accDeptAttr != null) {

				accDeptAttrMapper.updateAccDeptAttr(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

			} else {

				accDeptAttrMapper.addAccDeptAttr(entityMap);

			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccDeptAttr\"}";

		}

	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              批量添加AccDeptAttr
	 * @param AccDeptAttr
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccDeptAttr(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accDeptAttrMapper.addBatchAccDeptAttr(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccDeptAttr\"}";

		}
	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              查询AccDeptAttr分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccDeptAttr(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccDeptAttr> list = accDeptAttrMapper.queryAccDeptAttr(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              查询AccDeptAttrByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccDeptAttr queryAccDeptAttrByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accDeptAttrMapper.queryAccDeptAttrByCode(entityMap);

	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              批量删除AccDeptAttr
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccDeptAttr(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = accDeptAttrMapper.deleteBatchAccDeptAttr(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccDeptAttr\"}";

		}

	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              删除AccDeptAttr
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccDeptAttr(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accDeptAttrMapper.deleteAccDeptAttr(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccDeptAttr\"}";

		}
	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              更新AccDeptAttr
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccDeptAttr(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accDeptAttrMapper.updateAccDeptAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccDeptAttr\"}";

		}
	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              批量更新AccDeptAttr
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccDeptAttr(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			for (Map<String, Object> map : entityList) {
				AccDeptAttr accDeptAttr = queryAccDeptAttrByCode(map);

				if (accDeptAttr != null) {

					accDeptAttrMapper.updateAccDeptAttr(map);

				} else {

					accDeptAttrMapper.addAccDeptAttr(map);
					
				}
				
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccDeptAttr\"}";

		}

	}

	/**
	 * @Description 部门字典属性表<BR>
	 *              导入AccDeptAttr
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccDeptAttr(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 修改部门时<BR>
	 *              查询DeptByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccDeptAttr queryDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return accDeptAttrMapper.queryDeptByCode(entityMap);
	}

	/**
	 * @Description 添加部门时<BR>
	 *              查询HosDeptByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccDeptAttr queryHosDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return accDeptAttrMapper.queryHosDeptByCode(entityMap);
	}

	@Override
	public AccDeptAttr queryAccOutDeptByName(Map<String, Object> entityMap) throws DataAccessException {

		return accDeptAttrMapper.queryAccOutDeptByName(entityMap);
	}

	/**
	 * @Description 
	 *部门字典属性表(批量修改时部门数据可以为空)<BR> 更新AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchManageAccDeptAttr(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			accDeptAttrMapper.updateBatchManageAccDeptAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchManageAccDeptAttr\"}";

		}
	}
}
