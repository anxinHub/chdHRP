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
import com.chd.hrp.cost.dao.CostAllocationProcessMapper;
import com.chd.hrp.cost.service.CostAllocationProcessService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costAllocationProcessService")
public class CostAllocationProcessServiceImpl implements CostAllocationProcessService {

	private static Logger logger = Logger.getLogger(CostAllocationProcessServiceImpl.class);

	@Resource(name = "costAllocationProcessMapper")
	private final CostAllocationProcessMapper costAllocationProcessMapper = null;

	/**
	 * @Description 科室成本明细数据表_医辅分摊<BR>
	 *              批量添加CostAllocationProcess
	 * @param CostAllocationProcess
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostAllocationProcess(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costAllocationProcessMapper.addBatchCostAllocationProcess(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostAllocationProcess\"}";

		}
	}


	/**
	 * @Description 科室成本明细数据表_医辅分摊<BR>
	 *              删除CostAllocationProcess
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostAllocationProcess(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			costAllocationProcessMapper.deleteCostAllocationProcess(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostAllocationProcess\"}";

		}
	}


	@Override
	public String queryCostAllocationProcess(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = costAllocationProcessMapper.queryCostAllocationProcess(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = costAllocationProcessMapper.queryCostAllocationProcess(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}


	@Override
	public List<Map<String, Object>> queryCostAllocationProcessPrint(Map<String, Object> entityMap)
			throws DataAccessException {

			List<Map<String, Object>> list = costAllocationProcessMapper.queryCostAllocationProcess(entityMap);
			return list;
	}
}
