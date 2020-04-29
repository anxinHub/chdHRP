/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmDeptNatureMapper;
import com.chd.hrp.prm.entity.PrmDeptNature;
import com.chd.hrp.prm.service.PrmDeptNatureService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8802 科室性质字典表
 * @Table: Prm_DEPT_NATURE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptNatureService")
public class PrmDeptNatureServiceImpl implements PrmDeptNatureService {

	private static Logger logger = Logger.getLogger(PrmDeptNatureServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptNatureMapper")
	private final PrmDeptNatureMapper prmDeptNatureMapper = null;

	/**
	 * @Description 添加8802 科室性质字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptNature(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象8802 科室性质字典表
		PrmDeptNature PrmDeptNature = queryPrmDeptNatureByCode(entityMap);

		if (PrmDeptNature != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptNatureMapper.addPrmDeptNature(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptNature\"}";

		}

	}

	/**
	 * @Description 批量添加8802 科室性质字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptNature(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptNatureMapper.addBatchPrmDeptNature(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptNature\"}";

		}

	}

	/**
	 * @Description 更新8802 科室性质字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptNature(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptNatureMapper.updatePrmDeptNature(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptNature\"}";

		}

	}

	/**
	 * @Description 批量更新8802 科室性质字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptNature(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptNatureMapper.updateBatchPrmDeptNature(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptNature\"}";

		}

	}

	/**
	 * @Description 删除8802 科室性质字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptNature(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptNatureMapper.deletePrmDeptNature(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptNature\"}";

		}

	}

	/**
	 * @Description 批量删除8802 科室性质字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptNature(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptNatureMapper.deleteBatchPrmDeptNature(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptNature\"}";

		}
	}

	/**
	 * @Description 查询结果集8802 科室性质字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptNature(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptNature> list = prmDeptNatureMapper.queryDeptNature(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptNature> list = prmDeptNatureMapper.queryDeptNature(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象8802 科室性质字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDeptNature queryPrmDeptNatureByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptNatureMapper.queryPrmDeptNatureByCode(entityMap);
	}

}
