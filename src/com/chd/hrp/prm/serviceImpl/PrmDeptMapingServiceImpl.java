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
import com.chd.hrp.prm.dao.PrmDeptMapingMapper;
import com.chd.hrp.prm.entity.PrmDeptMaping;
import com.chd.hrp.prm.service.PrmDeptMapingService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8806 奖金科室映射表
 * @Table: Prm_DEPT_MAPING
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptMapingService")
public class PrmDeptMapingServiceImpl implements PrmDeptMapingService {

	private static Logger logger = Logger.getLogger(PrmDeptMapingServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptMapingMapper")
	private final PrmDeptMapingMapper prmDeptMapingMapper = null;

	/**
	 * @Description 添加8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptMaping(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象8806 奖金科室映射表
		PrmDeptMaping PrmDeptMaping = queryPrmDeptMapingByCode(entityMap);

		if (PrmDeptMaping != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmDeptMapingMapper.addPrmDeptMaping(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptMaping\"}";

		}

	}

	/**
	 * @Description 批量添加8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptMaping(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptMapingMapper.addBatchPrmDeptMaping(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptMaping\"}";

		}

	}

	/**
	 * @Description 更新8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptMaping(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptMapingMapper.updatePrmDeptMaping(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptMaping\"}";

		}

	}

	/**
	 * @Description 批量更新8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptMaping(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptMapingMapper.updateBatchPrmDeptMaping(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptMaping\"}";

		}

	}

	/**
	 * @Description 删除8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptMaping(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptMapingMapper.deletePrmDeptMaping(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptMaping\"}";

		}

	}

	/**
	 * @Description 批量删除8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptMaping(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptMapingMapper.deleteBatchPrmDeptMaping(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptMaping\"}";

		}
	}

	/**
	 * @Description 查询结果集8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptMaping(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptMaping> list = prmDeptMapingMapper.queryPrmDeptMaping(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptMaping> list = prmDeptMapingMapper.queryPrmDeptMaping(entityMap, rowBounds);

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
	public PrmDeptMaping queryPrmDeptMapingByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptMapingMapper.queryPrmDeptMapingByCode(entityMap);
	}

}
