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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.cost.dao.CostItemDictMapper;
import com.chd.hrp.cost.dao.CostItemDictNoMapper;
import com.chd.hrp.cost.entity.CostItemDict;
import com.chd.hrp.cost.service.CostItemDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 成本项目字典<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costItemDictService")
public class CostItemDictServiceImpl implements CostItemDictService {

	private static Logger logger = Logger.getLogger(CostItemDictServiceImpl.class);

	@Resource(name = "costItemDictMapper")
	private final CostItemDictMapper costItemDictMapper = null;

	@Resource(name = "costItemDictNoMapper")
	private final CostItemDictNoMapper costItemDictNoMapper = null;

	/**
	 * @Description 成本项目字典<BR>
	 *              添加CostItemDict
	 * @param CostItemDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostItemDict(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("cost_item_code", entityMap.get("cost_item_code"));
		// 判断编码是否重复
		CostItemDict data_exc_extis_code = costItemDictMapper.queryCostItemDictByUniqueness(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}
		Map<String, Object> map_name = new HashMap<String, Object>();
		map_name.put("group_id", entityMap.get("group_id"));
		map_name.put("hos_id", entityMap.get("hos_id"));
		map_name.put("copy_code", entityMap.get("copy_code"));
		map_name.put("cost_item_name", entityMap.get("cost_item_name"));

		/*CostItemDict data_exc_extis_name = costItemDictMapper.queryCostItemDictByUniqueness(map_name);

		if (data_exc_extis_name != null) {

			return "{\"error\":\"名称重复,请重新添加.\"}";

		}*/

		// 判断上级编码是否为空 不为空则反查上级编码所属分类
		if (!"0".equals(entityMap.get("supp_item_code").toString())) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("cost_item_code", entityMap.get("supp_item_code"));

			CostItemDict data_exc_extis_super = costItemDictMapper.queryCostItemDictByCode(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("cost_type_id", data_exc_extis_super.getCost_type_id());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("cost_item_id", data_exc_extis_super.getCost_item_id());
				update_is_last.put("is_last", "0");
				costItemDictMapper.updateCostItemDict(update_is_last);
			}
		}
		try {

			int result = costItemDictMapper.addCostItemDict(entityMap);
			if (result > 0) {
				entityMap.put("cost_item_id", costItemDictMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("note", "添加");
				entityMap.put("is_stop", 0);
				costItemDictNoMapper.addCostItemDictNo(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostItemDict\"}";

		}

	}
	public String addCostItemDictInput(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int result = costItemDictMapper.addCostItemDict(entityMap);
			if (result > 0) {
				entityMap.put("cost_item_id", costItemDictMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("note", "添加");
				entityMap.put("is_stop", 0);
				costItemDictNoMapper.addCostItemDictNo(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostItemDict\"}";
			
		}
		
	}

	/**
	 * @Description 成本项目字典<BR>
	 *              批量添加CostItemDict
	 * @param CostItemDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostItemDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costItemDictMapper.addBatchCostItemDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostItemDict\"}";

		}
	}

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostItemDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CostItemDict> list = costItemDictMapper.queryCostItemDict(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostItemDict> list = costItemDictMapper.queryCostItemDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostItemDict queryCostItemDictByCode(Map<String, Object> entityMap) throws DataAccessException {

		return costItemDictMapper.queryCostItemDictByCode(entityMap);

	}

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostItemDict queryCostItemDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException {

		return costItemDictMapper.queryCostItemDictByUniqueness(entityMap);

	}

	/**
	 * @Description 成本项目字典<BR>
	 *              批量删除CostItemDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostItemDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			costItemDictNoMapper.deleteBatchCostItemDictNo(entityList);
			int state = costItemDictMapper.deleteBatchCostItemDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostItemDict\"}";

		}

	}

	/**
	 * @Description 成本项目字典<BR>
	 *              删除CostItemDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostItemDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			costItemDictNoMapper.deleteCostItemDictNo(entityMap);
			costItemDictMapper.deleteCostItemDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostItemDict\"}";

		}
	}

	/**
	 * @Description 成本项目字典<BR>
	 *              更新CostItemDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostItemDict(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("cost_item_code", entityMap.get("cost_item_code"));
		map_code.put("cost_item_id", entityMap.get("cost_item_id"));

		// 判断编码是否重复
		CostItemDict data_exc_extis_code = costItemDictMapper.queryCostItemDictByUniqueness(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}
		Map<String, Object> map_name = new HashMap<String, Object>();
		map_name.put("group_id", entityMap.get("group_id"));
		map_name.put("hos_id", entityMap.get("hos_id"));
		map_name.put("copy_code", entityMap.get("copy_code"));
		map_name.put("cost_item_name", entityMap.get("cost_item_name"));
		map_name.put("cost_item_id", entityMap.get("cost_item_id"));

		/*CostItemDict data_exc_extis_name = costItemDictMapper.queryCostItemDictByUniqueness(map_name);

		if (data_exc_extis_name != null) {

			return "{\"error\":\"名称重复,请重新添加.\"}";

		}*/

		// 判断上级编码是否为空 不为空则反查上级编码所属分类
		if (!"0".equals(entityMap.get("supp_item_code").toString())) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("cost_item_code", entityMap.get("supp_item_code"));

			CostItemDict data_exc_extis_super = costItemDictMapper.queryCostItemDictByCode(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("cost_type_id", data_exc_extis_super.getCost_type_id());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("cost_item_id", data_exc_extis_super.getCost_item_id());
				update_is_last.put("is_last", "0");
				costItemDictMapper.updateCostItemDict(update_is_last);
			}
		}
		try {

			costItemDictMapper.updateCostItemDict(entityMap);

			costItemDictNoMapper.updateCostItemDictNo(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemDict\"}";

		}
	}
	public String updateCostItemDictInput(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			costItemDictMapper.updateCostItemDict(entityMap);
			
			costItemDictNoMapper.updateCostItemDictNo(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemDict\"}";
			
		}
	}

	/**
	 * @Description 成本项目字典<BR>
	 *              批量更新CostItemDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostItemDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costItemDictMapper.updateBatchCostItemDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemDict\"}";

		}

	}

	@Override
	public List<?> queryCostItemDictByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = costItemDictMapper.queryCostItemDictByTree(entityMap);
		return l_map;
	}
	@Override
    public String updateCostItemBatch(Map<String, Object> entityMap) throws DataAccessException {
		try {

			costItemDictMapper.updateCostItemBatch(entityMap);
			
			costItemDictNoMapper.updateCostItemNoBatch(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemBatch\"}";

		}
    }
	@Override
	public List<Map<String, Object>> queryCostItemDictPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		
		List<Map<String, Object>> list=costItemDictMapper.queryCostItemDictPrint(entityMap);
		
		return list;

	}
}
