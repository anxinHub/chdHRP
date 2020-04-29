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
import com.chd.hrp.prm.dao.PrmHosKpiLedMapper;
import com.chd.hrp.prm.entity.PrmHosKpiLed;
import com.chd.hrp.prm.service.PrmHosKpiLedService;
import com.github.pagehelper.PageInfo;

@Service("prmHosKpiLedService")
public class PrmHosKpiLedServiceImpl implements PrmHosKpiLedService{
	private static Logger logger = Logger.getLogger(PrmHosKpiLedServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmHosKpiLedMapper")
	private final PrmHosKpiLedMapper PrmHosKpiLedMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmHosKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmHosKpiLed PrmHosKpiLed = queryPrmHosKpiLedByCode(entityMap);

		if (PrmHosKpiLed != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = PrmHosKpiLedMapper.addPrmHosKpiLed(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiLed\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmHosKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmHosKpiLedMapper.addBatchPrmHosKpiLed(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiLed\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmHosKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmHosKpiLedMapper.updatePrmHosKpiLed(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiLed\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmHosKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmHosKpiLedMapper.updateBatchPrmHosKpiLed(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiLed\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmHosKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmHosKpiLedMapper.deletePrmHosKpiLed(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiLed\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmHosKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmHosKpiLedMapper.deleteBatchPrmHosKpiLed(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiLed\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmHosKpiLed> list = PrmHosKpiLedMapper.queryPrmHosKpiLed(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosKpiLed> list = PrmHosKpiLedMapper.queryPrmHosKpiLed(entityMap, rowBounds);

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
	public PrmHosKpiLed queryPrmHosKpiLedByCode(Map<String, Object> entityMap) throws DataAccessException {
		return PrmHosKpiLedMapper.queryPrmHosKpiLedByCode(entityMap);
	}
}
