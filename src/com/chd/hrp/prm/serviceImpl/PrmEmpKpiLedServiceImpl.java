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
import com.chd.hrp.prm.dao.PrmEmpKpiLedMapper;
import com.chd.hrp.prm.entity.PrmEmpKpiLed;
import com.chd.hrp.prm.service.PrmEmpKpiLedService;
import com.github.pagehelper.PageInfo;

@Service("prmEmpKpiLedService")
public class PrmEmpKpiLedServiceImpl implements PrmEmpKpiLedService{
	private static Logger logger = Logger.getLogger(PrmEmpKpiLedServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmEmpKpiLedMapper")
	private final PrmEmpKpiLedMapper PrmEmpKpiLedMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmEmpKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmEmpKpiLed PrmEmpKpiLed = queryPrmEmpKpiLedByCode(entityMap);

		if (PrmEmpKpiLed != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = PrmEmpKpiLedMapper.addPrmEmpKpiLed(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiLed\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmEmpKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmEmpKpiLedMapper.addBatchPrmEmpKpiLed(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpKpiLed\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmEmpKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmEmpKpiLedMapper.updatePrmEmpKpiLed(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpiLed\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmEmpKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmEmpKpiLedMapper.updateBatchPrmEmpKpiLed(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiLed\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmEmpKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmEmpKpiLedMapper.deletePrmEmpKpiLed(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpiLed\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmEmpKpiLed(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmEmpKpiLedMapper.deleteBatchPrmEmpKpiLed(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpKpiLed\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmEmpKpiLed(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmEmpKpiLed> list = PrmEmpKpiLedMapper.queryPrmEmpKpiLed(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpKpiLed> list = PrmEmpKpiLedMapper.queryPrmEmpKpiLed(entityMap, rowBounds);

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
	public PrmEmpKpiLed queryPrmEmpKpiLedByCode(Map<String, Object> entityMap) throws DataAccessException {
		return PrmEmpKpiLedMapper.queryPrmEmpKpiLedByCode(entityMap);
	}
}
