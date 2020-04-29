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
import com.chd.hrp.cost.dao.CostDeptTypeDictMapper;
import com.chd.hrp.cost.dao.CostItemDictMapper;
import com.chd.hrp.cost.entity.CostDeptTypeDict;
import com.chd.hrp.cost.entity.CostItemDict;
import com.chd.hrp.cost.service.CostDeptTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 成本类型字典<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costDeptTypeDictService")
public class CostDeptTypeDictServiceImpl implements CostDeptTypeDictService {

	private static Logger logger = Logger.getLogger(CostDeptTypeDictServiceImpl.class);

	@Resource(name = "costDeptTypeDictMapper")
	private final CostDeptTypeDictMapper costDeptTypeDictMapper = null;


	@Resource(name = "costItemDictMapper")
	private final CostItemDictMapper costItemDictMapper = null;

	/**
	 * @Description 成本类型字典<BR>
	 *              添加CostDeptTypeDict
	 * @param CostDeptTypeDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		CostDeptTypeDict costDeptTypeDict = queryCostDeptTypeDictByCode(entityMap);

		if (costDeptTypeDict != null) {

			return "{\"error\":\"编码：" + entityMap.get("cost_type_code").toString() + "重复.\"}";

		}

		try {

			int result = costDeptTypeDictMapper.addCostDeptTypeDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDeptTypeDict\"}";

		}

	}

	/**
	 * @Description 成本类型字典<BR>
	 *              批量添加CostDeptTypeDict
	 * @param CostDeptTypeDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostDeptTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptTypeDictMapper.addBatchCostDeptTypeDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDeptTypeDict\"}";

		}
	}

	/**
	 * @Description 成本类型字典<BR>
	 *              查询CostDeptTypeDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CostDeptTypeDict> list = costDeptTypeDictMapper.queryCostDeptTypeDict(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDeptTypeDict> list = costDeptTypeDictMapper.queryCostDeptTypeDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * @Description 成本类型字典<BR>
	 *              查询CostDeptTypeDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostDeptTypeDict queryCostDeptTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException {

		return costDeptTypeDictMapper.queryCostDeptTypeDictByCode(entityMap);

	}

	/**
	 * @Description 成本类型字典<BR>
	 *              批量删除CostDeptTypeDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostDeptTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			for (int i = 0; i < entityList.size(); i++) {
				Map<String, Object> map = entityList.get(i);
				CostItemDict costItemDict = costItemDictMapper.queryCostItemDictByCode(map);
				if (costItemDict != null) {
					return "{\"error\":\"当前类型已被项目引用,不能删除.\"}";
				}
			}
			int state = costDeptTypeDictMapper.deleteBatchCostDeptTypeDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDeptTypeDict\"}";

		}

	}

	/**
	 * @Description 成本类型字典<BR>
	 *              删除CostDeptTypeDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costDeptTypeDictMapper.deleteCostDeptTypeDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDeptTypeDict\"}";

		}
	}

	/**
	 * @Description 成本类型字典<BR>
	 *              更新CostDeptTypeDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			costDeptTypeDictMapper.updateCostDeptTypeDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptTypeDict\"}";

		}
	}

	/**
	 * @Description 成本类型字典<BR>
	 *              批量更新CostDeptTypeDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostDeptTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptTypeDictMapper.updateBatchCostDeptTypeDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptTypeDict\"}";

		}

	}

	@Override
	public CostDeptTypeDict queryCostDeptTypeDictByTypeCode(Map<String, Object> entityMap) throws DataAccessException {
		return costDeptTypeDictMapper.queryCostDeptTypeDictByTypeCode(entityMap);
	}
}
