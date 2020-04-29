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
import com.chd.hrp.prm.dao.PrmDeptHipMapper;
import com.chd.hrp.prm.dao.PrmDeptMapingHipMapper;
import com.chd.hrp.prm.entity.PrmDeptHip;
import com.chd.hrp.prm.entity.PrmDeptMapingHip;
import com.chd.hrp.prm.service.PrmDeptMapingHipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8806 奖金科室映射表
 * @Table: Prm_DEPT_MAPING
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptMapingHipService")
public class PrmDeptMapingHipServiceImpl implements PrmDeptMapingHipService {

	private static Logger logger = Logger.getLogger(PrmDeptMapingHipServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptMapingHipMapper")
	private final PrmDeptMapingHipMapper prmDeptMapingHipMapper = null;
	@Resource(name = "prmDeptHipMapper")
	private final PrmDeptHipMapper prmDeptHipMapper = null;

	/**
	 * @Description 添加8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象8806 奖金科室映射表
		PrmDeptMapingHip PrmDeptMapingHip = queryPrmDeptMapingHipByCode(entityMap);

		if (PrmDeptMapingHip != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptMapingHipMapper.addPrmDeptMapingHip(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 批量添加8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptMapingHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptMapingHipMapper.addBatchPrmDeptMapingHip(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 更新8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptMapingHipMapper.updatePrmDeptMapingHip(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 批量更新8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptMapingHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptMapingHipMapper.updateBatchPrmDeptMapingHip(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 删除8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptMapingHipMapper.deletePrmDeptMapingHip(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 批量删除8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptMapingHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptMapingHipMapper.deleteBatchPrmDeptMapingHip(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptMapingHip\"}";

		}
	}

	/**
	 * @Description 查询结果集8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptMapingHip> list = prmDeptMapingHipMapper.queryPrmDeptMapingHip(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptMapingHip> list = prmDeptMapingHipMapper.queryPrmDeptMapingHip(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDeptMapingHip queryPrmDeptMapingHipByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptMapingHipMapper.queryPrmDeptMapingHipByCode(entityMap);
	}

	@Override
	public String queryPrmDeptHipByMaping(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptHip> list = prmDeptHipMapper.queryPrmDeptHipByMaping(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptHip> list = prmDeptHipMapper.queryPrmDeptHipByMaping(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

}
