/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostOhterDetailMapper;
import com.chd.hrp.cost.entity.CostOhterDetail;
import com.chd.hrp.cost.service.CostOhterDetailService;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室其他费用表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costOhterDetailService")
public class CostOhterDetailServiceImpl implements CostOhterDetailService {

	private static Logger logger = Logger.getLogger(CostOhterDetailServiceImpl.class);

	@Resource(name = "costOhterDetailMapper")
	private final CostOhterDetailMapper costOhterDetailMapper = null;

	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;

	/**
	 * @Description 科室其他费用表<BR>
	 *              添加CostOhterDetail
	 * @param CostOhterDetail
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostOhterDetail(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_code", "001");

		Source source = sourceMapper.querySourceByCode(entityMap);
		entityMap.put("source_id", source.getSource_id());

		CostOhterDetail costOhterDetail = queryCostOhterDetailByCode(entityMap);

		if (costOhterDetail != null) {

			return "{\"error\":\"数据重复.\"}";

		}

		try {

			costOhterDetailMapper.addCostOhterDetail(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostOhterDetail\"}";

		}

	}

	/**
	 * @Description 科室其他费用表<BR>
	 *              批量添加CostOhterDetail
	 * @param CostOhterDetail
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostOhterDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costOhterDetailMapper.addBatchCostOhterDetail(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostOhterDetail\"}";

		}
	}

	/**
	 * @Description 科室其他费用表<BR>
	 *              查询CostOhterDetail分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostOhterDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CostOhterDetail> list = costOhterDetailMapper.queryCostOhterDetail(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostOhterDetail> list = costOhterDetailMapper.queryCostOhterDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}
	@Override
	public List<Map<String, Object>> queryCostOhterDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costOhterDetailMapper.queryCostOhterDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 科室其他费用表<BR>
	 *              查询CostOhterDetailByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostOhterDetail queryCostOhterDetailByCode(Map<String, Object> entityMap) throws DataAccessException {

		return costOhterDetailMapper.queryCostOhterDetailByCode(entityMap);

	}

	/**
	 * @Description 科室其他费用表<BR>
	 *              批量删除CostOhterDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostOhterDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = costOhterDetailMapper.deleteBatchCostOhterDetail(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostOhterDetail\"}";

		}

	}

	/**
	 * @Description 科室其他费用表<BR>
	 *              删除CostOhterDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostOhterDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costOhterDetailMapper.deleteCostOhterDetail(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostOhterDetail\"}";

		}
	}

	/**
	 * @Description 科室其他费用表<BR>
	 *              更新CostOhterDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostOhterDetail(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("source_code", "001");

		Source source = sourceMapper.querySourceByCode(entityMap);
		entityMap.put("source_id", source.getSource_id());

		try {

			costOhterDetailMapper.updateCostOhterDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostOhterDetail\"}";

		}
	}

	/**
	 * @Description 科室其他费用表<BR>
	 *              批量更新CostOhterDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostOhterDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costOhterDetailMapper.updateBatchCostOhterDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostOhterDetail\"}";

		}

	}
}
