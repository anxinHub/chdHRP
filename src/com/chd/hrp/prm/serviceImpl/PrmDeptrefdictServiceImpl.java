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
import com.chd.hrp.prm.dao.PrmDeptrefdictMapper;
import com.chd.hrp.prm.entity.PrmDeptrefdict;
import com.chd.hrp.prm.service.PrmDeptrefdictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8805 科室映射字典参数表
 * @Table: Prm_DEPTREFDICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptrefdictService")
public class PrmDeptrefdictServiceImpl implements PrmDeptrefdictService {

	private static Logger logger = Logger.getLogger(PrmDeptrefdictServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptrefdictMapper")
	private final PrmDeptrefdictMapper prmDeptrefdictMapper = null;

	/**
	 * @Description 添加8805 科室映射字典参数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptrefdict(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象8805 科室映射字典参数表
		PrmDeptrefdict PrmDeptrefdict = queryPrmDeptrefdictByCode(entityMap);

		if (PrmDeptrefdict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptrefdictMapper.addPrmDeptrefdict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptrefdict\"}";

		}

	}

	/**
	 * @Description 批量添加8805 科室映射字典参数表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptrefdict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptrefdictMapper.addBatchPrmDeptrefdict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptrefdict\"}";

		}

	}

	/**
	 * @Description 更新8805 科室映射字典参数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptrefdict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptrefdictMapper.updatePrmDeptrefdict(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptrefdict\"}";

		}

	}

	/**
	 * @Description 批量更新8805 科室映射字典参数表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptrefdict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptrefdictMapper.updateBatchPrmDeptrefdict(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptrefdict\"}";

		}

	}

	/**
	 * @Description 删除8805 科室映射字典参数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptrefdict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptrefdictMapper.deletePrmDeptrefdict(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptrefdict\"}";

		}

	}

	/**
	 * @Description 批量删除8805 科室映射字典参数表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptrefdict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptrefdictMapper.deleteBatchPrmDeptrefdict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptrefdict\"}";

		}
	}

	/**
	 * @Description 查询结果集8805 科室映射字典参数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptrefdict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptrefdict> list = prmDeptrefdictMapper.queryPrmDeptrefdict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptrefdict> list = prmDeptrefdictMapper.queryPrmDeptrefdict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象8805 科室映射字典参数表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDeptrefdict queryPrmDeptrefdictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptrefdictMapper.queryPrmDeptrefdictByCode(entityMap);
	}

}
