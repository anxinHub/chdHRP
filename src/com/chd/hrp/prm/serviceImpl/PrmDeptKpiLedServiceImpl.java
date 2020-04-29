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
import com.chd.hrp.prm.dao.PrmDeptKpiLedMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiLed;
import com.chd.hrp.prm.service.PrmDeptKpiLedService;
import com.github.pagehelper.PageInfo;

@Service("prmDeptKpiLedService")
public class PrmDeptKpiLedServiceImpl implements PrmDeptKpiLedService{
	private static Logger logger = Logger.getLogger(PrmDeptKpiLedServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptKpiLedMapper")
	private final PrmDeptKpiLedMapper PrmDeptKpiLedMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmDeptKpiLed PrmDeptKpiLed = queryPrmDeptKpiLedByCode(entityMap);

		if (PrmDeptKpiLed != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = PrmDeptKpiLedMapper.addPrmDeptKpiLed(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKpiLed\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmDeptKpiLedMapper.addBatchPrmDeptKpiLed(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiLed\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmDeptKpiLedMapper.updatePrmDeptKpiLed(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiLed\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmDeptKpiLedMapper.updateBatchPrmDeptKpiLed(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiLed\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmDeptKpiLedMapper.deletePrmDeptKpiLed(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiLed\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmDeptKpiLedMapper.deleteBatchPrmDeptKpiLed(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiLed\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmDeptKpiLed> list = PrmDeptKpiLedMapper.queryPrmDeptKpiLed(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptKpiLed> list = PrmDeptKpiLedMapper.queryPrmDeptKpiLed(entityMap, rowBounds);

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
	public PrmDeptKpiLed queryPrmDeptKpiLedByCode(Map<String, Object> entityMap) throws DataAccessException {
		return PrmDeptKpiLedMapper.queryPrmDeptKpiLedByCode(entityMap);
	}
}
