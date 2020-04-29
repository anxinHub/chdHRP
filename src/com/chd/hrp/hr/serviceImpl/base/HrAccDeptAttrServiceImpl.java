/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hr.serviceImpl.base;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.base.HrAccDeptAttrMapper;
import com.chd.hrp.hr.entity.base.HrAccDeptAttr;
import com.chd.hrp.hr.service.base.HrAccDeptAttrService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 部门字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hrAccDeptAttrService")
public class HrAccDeptAttrServiceImpl implements HrAccDeptAttrService {

	private static Logger logger = Logger.getLogger(HrAccDeptAttrServiceImpl.class);

	@Resource(name = "hrAccDeptAttrMapper")
	private final HrAccDeptAttrMapper hrAccDeptAttrMapper = null;

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

			HrAccDeptAttr accDeptAttr = queryAccDeptAttrByCode(entityMap);

			if (accDeptAttr != null) {

				hrAccDeptAttrMapper.updateAccDeptAttr(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

			} else {

				hrAccDeptAttrMapper.addAccDeptAttr(entityMap);

			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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

			hrAccDeptAttrMapper.addBatchAccDeptAttr(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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
		List<HrAccDeptAttr> list = hrAccDeptAttrMapper.queryAccDeptAttr(entityMap, rowBounds);
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
	public HrAccDeptAttr queryAccDeptAttrByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hrAccDeptAttrMapper.queryAccDeptAttrByCode(entityMap);

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

			int state = hrAccDeptAttrMapper.deleteBatchAccDeptAttr(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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
			hrAccDeptAttrMapper.deleteAccDeptAttr(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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

			hrAccDeptAttrMapper.updateAccDeptAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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
				HrAccDeptAttr accDeptAttr = queryAccDeptAttrByCode(map);

				if (accDeptAttr != null) {

					hrAccDeptAttrMapper.updateAccDeptAttr(map);

				} else {

					hrAccDeptAttrMapper.addAccDeptAttr(map);
					
				}
				
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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

			throw new SysException(e.getMessage());

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
	public HrAccDeptAttr queryDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrAccDeptAttrMapper.queryDeptByCode(entityMap);
	}

	/**
	 * @Description 添加部门时<BR>
	 *              查询HosDeptByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public HrAccDeptAttr queryHosDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrAccDeptAttrMapper.queryHosDeptByCode(entityMap);
	}

	@Override
	public HrAccDeptAttr queryAccOutDeptByName(Map<String, Object> entityMap) throws DataAccessException {

		return hrAccDeptAttrMapper.queryAccOutDeptByName(entityMap);
	}
}
