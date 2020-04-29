/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.entity.AphiDeptKind;
import com.chd.hrp.prm.dao.PrmDeptKindMapper;
import com.chd.hrp.prm.entity.PrmDeptKind;
import com.chd.hrp.prm.service.PrmDeptKindService;
import com.chd.hrp.sys.dao.DeptKindMapper;
import com.chd.hrp.sys.entity.DeptKind;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8803 科室分类字典表
 * @Table: Prm_DEPT_KIND
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptKindService")
public class PrmDeptKindServiceImpl implements PrmDeptKindService {

	private static Logger logger = Logger.getLogger(PrmDeptKindServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptKindMapper")
	private final PrmDeptKindMapper prmDeptKindMapper = null;

	@Resource(name = "deptKindMapper")
	private final DeptKindMapper deptKindMapper = null;
	/**
	 * @Description 添加8803 科室分类字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptKind(Map<String, Object> entityMap) throws DataAccessException {
		// 获取对象8803 科室分类字典表
		PrmDeptKind PrmDeptKind = queryPrmDeptKindByCode(entityMap);
	

		if (PrmDeptKind != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptKindMapper.addPrmDeptKind(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKind\"}";

		}


	}

	/**
	 * @Description 批量添加8803 科室分类字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptKind(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptKindMapper.addBatchPrmDeptKind(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKind\"}";

		}

	}

	/**
	 * @Description 更新8803 科室分类字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptKind(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptKindMapper.updatePrmDeptKind(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKind\"}";

		}
	}

	/**
	 * @Description 批量更新8803 科室分类字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptKind(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptKindMapper.updateBatchPrmDeptKind(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKind\"}";

		}


	}

	/**
	 * @Description 删除8803 科室分类字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptKind(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptKindMapper.deletePrmDeptKind(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKind\"}";

		}


	}

	/**
	 * @Description 批量删除8803 科室分类字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptKind(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			prmDeptKindMapper.deleteBatchPrmDeptKind(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKind\"}";

		}
	}

	/**
	 * @Description 查询结果集8803 科室分类字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptKind(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptKind> list = prmDeptKindMapper.queryPrmDeptKind(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptKind> list = prmDeptKindMapper.queryPrmDeptKind(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}


	}

	/**
	 * @Description 获取对象8803 科室分类字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDeptKind queryPrmDeptKindByCode(Map<String, Object> entityMap) throws DataAccessException {

		return prmDeptKindMapper.queryPrmDeptKindByCode(entityMap);
	}

	}

