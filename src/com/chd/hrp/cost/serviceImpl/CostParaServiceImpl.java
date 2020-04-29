package com.chd.hrp.cost.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostParaMapper;
import com.chd.hrp.cost.entity.CostPara;
import com.chd.hrp.cost.serviceImpl.CostParaServiceImpl;
import com.chd.hrp.cost.service.CostParaService;
import com.github.pagehelper.PageInfo;

@Service("costParaService")
public class CostParaServiceImpl implements CostParaService {

	private static Logger logger = Logger.getLogger(CostParaServiceImpl.class);

	@Resource(name = "costParaMapper")
	private final CostParaMapper costParaMapper = null;

	/**
	 * @Description 系统参数<BR>
	 *              添加CostPara
	 * @param CostPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostPara(Map<String, Object> entityMap) throws DataAccessException {

		CostPara CostPara = queryCostParaByCode(entityMap);

		if (CostPara != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}

		try {

			costParaMapper.addCostPara(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostPara\"}";

		}

	}

	/**
	 * @Description 系统参数<BR>
	 *              批量添加CostPara
	 * @param CostPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostPara(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaMapper.addBatchCostPara(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostPara\"}";

		}
	}

	/**
	 * @Description 系统参数<BR>
	 *              查询CostPara分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostPara(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<CostPara> list = costParaMapper.queryCostPara(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 系统参数<BR>
	 *              查询CostParaByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostPara queryCostParaByCode(Map<String, Object> entityMap) throws DataAccessException {

		return costParaMapper.queryCostParaByCode(entityMap);

	}

	/**
	 * @Description 系统参数<BR>
	 *              批量删除CostPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostPara(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = costParaMapper.deleteBatchCostPara(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostPara\"}";

		}

	}

	/**
	 * @Description 系统参数<BR>
	 *              删除CostPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostPara(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costParaMapper.deleteCostPara(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostPara\"}";

		}
	}

	/**
	 * @Description 系统参数<BR>
	 *              更新CostPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostPara(Map<String, Object> entityMap) throws DataAccessException {

		try {

			costParaMapper.updateCostPara(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostPara\"}";

		}
	}

	/**
	 * @Description 系统参数<BR>
	 *              批量更新CostPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostPara(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaMapper.updateBatchCostPara(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostPara\"}";

		}

	}

	/**
	 * @Description 系统参数<BR>
	 *              导入CostPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importCostPara(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
    public String queryCostParaValue(Map<String, Object> entityMap, String para_code) throws DataAccessException {
		
		Map<String,Object> map_para = new HashMap<String,Object>();
		
		map_para.put("group_id", entityMap.get("group_id"));
		
		map_para.put("hos_id", entityMap.get("hos_id"));
		
		map_para.put("copy_code", entityMap.get("copy_code"));
		
		map_para.put("para_code", para_code);
		
		CostPara costPara = costParaMapper.queryCostParaByCode(map_para);
		
		return String.valueOf(costPara.getPara_value());
    }


}
