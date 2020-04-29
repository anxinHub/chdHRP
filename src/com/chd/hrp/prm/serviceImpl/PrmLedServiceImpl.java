
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmLedMapper;
import com.chd.hrp.prm.entity.PrmLed;
import com.chd.hrp.prm.service.PrmLedService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0503 指示灯
 * @Table: PRM_LED
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmLedService")
public class PrmLedServiceImpl implements PrmLedService {

	private static Logger logger = Logger.getLogger(PrmLedServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmLedMapper")
	private final PrmLedMapper prmLedMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmLed(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmLed prmLed = queryPrmLedByCode(entityMap);

		if (prmLed != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmLedMapper.addPrmLed(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmLed\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmLedMapper.addBatchPrmLed(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmLed\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmLedMapper.updatePrmLed(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmLed\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmLedMapper.updateBatchPrmLed(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmLed\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmLedMapper.deletePrmLed(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmLed\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmLedMapper.deleteBatchPrmLed(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmLed\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmLed(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmLed> list = prmLedMapper.queryPrmLed(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmLed> list = prmLedMapper.queryPrmLed(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmLed queryPrmLedByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmLedMapper.queryPrmLedByCode(entityMap);
	}

	@Override
	public String queryPrmLedByEditerGrid(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmLed> list = prmLedMapper.queryPrmLedByEditerGrid(entityMap);

		return ChdJson.toJson(list);
	}

}
