/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostDeptDriDataMapper;
import com.chd.hrp.cost.dao.CostImputationMapper;
import com.chd.hrp.cost.entity.CostBalanceAnalysis;
import com.chd.hrp.cost.entity.CostCostAnalysis;
import com.chd.hrp.cost.entity.CostDeptDriData;
import com.chd.hrp.cost.entity.CostVolumeAnalysis;
import com.chd.hrp.cost.service.CostDeptDriDataService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室直接成本表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costDeptDriDataService")
public class CostDeptDriDataServiceImpl implements CostDeptDriDataService {

	private static Logger logger = Logger.getLogger(CostDeptDriDataServiceImpl.class);

	@Resource(name = "costDeptDriDataMapper")
	private final CostDeptDriDataMapper costDeptDriDataMapper = null;

	@Resource(name = "costImputationMapper")
	private final CostImputationMapper costImputationMapper = null;

	/**
	 * @Description 科室直接成本表<BR>
	 *              添加CostDeptDriData
	 * @param CostDeptDriData
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostDeptDriData(Map<String, Object> entityMap) throws DataAccessException {

		CostDeptDriData costDeptDriData = queryCostDeptDriDataByCode(entityMap);

		if (costDeptDriData != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}

		try {

			costDeptDriDataMapper.addCostDeptDriData(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDeptDriData\"}";

		}

	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              批量添加CostDeptDriData
	 * @param CostDeptDriData
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostDeptDriData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptDriDataMapper.addBatchCostDeptDriData(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDeptDriData\"}";

		}
	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              查询CostDeptDriData分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostDeptDriData(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<CostDeptDriData> list = costDeptDriDataMapper.queryCostDeptDriData(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostDeptDriData> list = costDeptDriDataMapper.queryCostDeptDriData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}

	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              查询CostDeptDriDataByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostDeptDriData queryCostDeptDriDataByCode(Map<String, Object> entityMap) throws DataAccessException {

		return costDeptDriDataMapper.queryCostDeptDriDataByCode(entityMap);

	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              批量删除CostDeptDriData
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostDeptDriData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = costDeptDriDataMapper.deleteBatchCostDeptDriData(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDeptDriData\"}";

		}

	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              删除CostDeptDriData
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostDeptDriData(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costDeptDriDataMapper.deleteCostDeptDriData(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDeptDriData\"}";

		}
	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              更新CostDeptDriData
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostDeptDriData(Map<String, Object> entityMap) throws DataAccessException {

		try {

			costDeptDriDataMapper.updateCostDeptDriData(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptDriData\"}";

		}
	}

	/**
	 * @Description 科室直接成本表<BR>
	 *              批量更新CostDeptDriData
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostDeptDriData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptDriDataMapper.updateBatchCostDeptDriData(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptDriData\"}";

		}

	}

	@Override
	public String queryBillingRevenueBalance(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostBalanceAnalysis> list = costDeptDriDataMapper.queryBillingRevenueBalance(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostBalanceAnalysis> list = costDeptDriDataMapper.queryBillingRevenueBalance(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryVisitsCostAnalysis(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostCostAnalysis> list = costDeptDriDataMapper.queryVisitsCostAnalysis(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostCostAnalysis> list = costDeptDriDataMapper.queryVisitsCostAnalysis(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryOnTheBedCostAnalysis(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostCostAnalysis> list = costDeptDriDataMapper.queryOnTheBedCostAnalysis(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostCostAnalysis> list = costDeptDriDataMapper.queryOnTheBedCostAnalysis(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryOutpatientVolumeAnalysis(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostVolumeAnalysis> list = costDeptDriDataMapper.queryOutpatientVolumeAnalysis(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostVolumeAnalysis> list = costDeptDriDataMapper.queryOutpatientVolumeAnalysis(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryInpatientVolumeAnalysis(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostVolumeAnalysis> list = costDeptDriDataMapper.queryInpatientVolumeAnalysis(entityMap);

			return ChdJson.toJson(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostVolumeAnalysis> list = costDeptDriDataMapper.queryInpatientVolumeAnalysis(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
}
