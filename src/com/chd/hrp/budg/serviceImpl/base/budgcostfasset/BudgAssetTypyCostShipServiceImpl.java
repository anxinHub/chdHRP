/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.base.budgcostfasset;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgcostfasset.BudgAssetTypyCostShipMapper;
import com.chd.hrp.budg.entity.BudgAssetTypyCostShip;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgAssetTypyCostShipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资金性质取自系统平台
 * @Table: BUDG_ASSET_TYPY_COST_SHIP
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgAssetTypyCostShipService")
public class BudgAssetTypyCostShipServiceImpl implements BudgAssetTypyCostShipService {

	private static Logger logger = Logger.getLogger(BudgAssetTypyCostShipServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "budgAssetTypyCostShipMapper")
	private final BudgAssetTypyCostShipMapper budgAssetTypyCostShipMapper = null;

	/**
	 * @Description 添加资金性质取自系统平台<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象资金性质取自系统平台
		BudgAssetTypyCostShip budgAssetTypyCostShip = queryByCode(entityMap);

		if (budgAssetTypyCostShip != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			budgAssetTypyCostShipMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}

	}

	/**
	 * @Description 批量添加资金性质取自系统平台<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgAssetTypyCostShipMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}

	}

	/**
	 * @Description 更新资金性质取自系统平台<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 同集团医院帐套同一年度不允许重复，验证时注意刨除自身
			/*
			 * int isExist =
			 * budgAssetTypyCostShipMapper.queryBudgSubjExist(entityMap);
			 * if(isExist>0){ return
			 * "{\"error\":\"更新失败,科目编码重复,请重新选择.</br>如未做修改,点击关闭按钮.\",\"state\":\"false\"}";
			 * }
			 */
			budgAssetTypyCostShipMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}

	/**
	 * @Description 批量更新资金性质取自系统平台<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgAssetTypyCostShipMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}

	}

	/**
	 * @Description 删除资金性质取自系统平台<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			budgAssetTypyCostShipMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}

	}

	/**
	 * @Description 批量删除资金性质取自系统平台<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			budgAssetTypyCostShipMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	/**
	 * @Description 添加资金性质取自系统平台<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象资金性质取自系统平台
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("budg_year", entityMap.get("budg_year"));

		List<BudgAssetTypyCostShip> list = (List<BudgAssetTypyCostShip>) budgAssetTypyCostShipMapper.queryExists(mapVo);

		if (list.size() > 0) {
			budgAssetTypyCostShipMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			budgAssetTypyCostShipMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}

	}

	/**
	 * @Description 查询结果集资金性质取自系统平台<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) budgAssetTypyCostShipMapper.query(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) budgAssetTypyCostShipMapper.query(entityMap,
					rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * @Description 获取对象资金性质取自系统平台<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgAssetTypyCostShipMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取资金性质取自系统平台<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return BudgAssetTypyCostShip
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgAssetTypyCostShipMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取资金性质取自系统平台<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<BudgAssetTypyCostShip>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgAssetTypyCostShipMapper.queryExists(entityMap);
	}

	@Override
	public String queryAssTypes(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgAssetTypyCostShipMapper.queryAssTypes(mapVo));
	}
	
	@Override
	public String queryAssTypesFilter(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgAssetTypyCostShipMapper.queryAssTypesFilter(mapVo));
	}

	@Override
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}

		List<Map<String, Object>> list = budgAssetTypyCostShipMapper.queryBudgSubj(mapVo, rowBounds);
		return JSON.toJSONString(list);
	}

	/**
	 * 资金性下拉框 添加页面用
	 */
	@Override
	public String querySourceNature(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}
		return JSON.toJSONString(budgAssetTypyCostShipMapper.querySourceNature(mapVo, rowBounds));
	}

	@Override
	public int queryNatureExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgAssetTypyCostShipMapper.queryNatureExist(mapVo);
	}

	@Override
	public String extendBudgCostFassetShip(Map<String, Object> mapVo) {
		// 点击弹出提示框“确定继承上一年度资产分类与预算科目对应关系”，若点击确认，继承上年数据。新增数据满足：预算支出科目在本年度预算支出科目字典中。
		mapVo.put("prev_year", Integer.parseInt(String.valueOf(mapVo.get("budg_year"))) - 1);
		List<Map<String, Object>> prevYearData = budgAssetTypyCostShipMapper.queryPrevYearData(mapVo);
		if (prevYearData != null && prevYearData.size() > 0) {
			for (Map<String, Object> map : prevYearData) {
				map.put("budg_year", mapVo.get("budg_year"));
			}
		} else {
			return "{\"error\":\"继承失败 不存在可继承数据! \"}";
		}

		try {
			budgAssetTypyCostShipMapper.addBatch(prevYearData);
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
