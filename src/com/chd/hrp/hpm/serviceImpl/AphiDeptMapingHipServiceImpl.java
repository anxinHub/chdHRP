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

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapingHipMapper;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiDeptMapingHip;
import com.chd.hrp.hpm.service.AphiDeptMapingHipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8806 奖金科室映射表
 * @Table: Prm_DEPT_MAPING
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptMapingHipService")
public class AphiDeptMapingHipServiceImpl implements AphiDeptMapingHipService {

	private static Logger logger = Logger.getLogger(AphiDeptMapingHipServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "aphiDeptMapingHipMapper")
	private final AphiDeptMapingHipMapper aphiDeptMapingHipMapper = null;
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;

	/**
	 * @Description 添加8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAphiDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象8806 奖金科室映射表
		AphiDeptMapingHip AphiDeptMapingHip = queryAphiDeptMapingHipByCode(entityMap);

		if (AphiDeptMapingHip != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = aphiDeptMapingHipMapper.addAphiDeptMapingHip(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addAphiDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 批量添加8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAphiDeptMapingHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptMapingHipMapper.addBatchAphiDeptMapingHip(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchAphiDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 更新8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAphiDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = aphiDeptMapingHipMapper.updateAphiDeptMapingHip(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateAphiDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 批量更新8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAphiDeptMapingHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptMapingHipMapper.updateBatchAphiDeptMapingHip(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchAphiDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 删除8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAphiDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = aphiDeptMapingHipMapper.deleteAphiDeptMapingHip(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteAphiDeptMapingHip\"}";

		}

	}

	/**
	 * @Description 批量删除8806 奖金科室映射表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAphiDeptMapingHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptMapingHipMapper.deleteBatchAphiDeptMapingHip(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchAphiDeptMapingHip\"}";

		}
	}

	/**
	 * @Description 查询结果集8806 奖金科室映射表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAphiDeptMapingHip(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptMapingHip> list = aphiDeptMapingHipMapper.queryAphiDeptMapingHip(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptMapingHip> list = aphiDeptMapingHipMapper.queryAphiDeptMapingHip(entityMap, rowBounds);

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
	public AphiDeptMapingHip queryAphiDeptMapingHipByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiDeptMapingHipMapper.queryAphiDeptMapingHipByCode(entityMap);
	}

	@Override
	public String queryAphiDeptHipByMaping(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptHip> list = aphiDeptHipMapper.queryAphiDeptHipByMaping(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptHip> list = aphiDeptHipMapper.queryAphiDeptHipByMaping(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

}
