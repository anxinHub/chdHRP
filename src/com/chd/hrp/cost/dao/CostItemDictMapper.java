/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostItemDict;

/**
 * @Title. @Description. 成本项目字典<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostItemDictMapper extends SqlMapper {

	/**
	 * @Description 查询刚查询序列号
	 * @param
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryCurrentSequence() throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              添加CostItemDict
	 * @param CostItemDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              批量添加CostItemDict
	 * @param CostItemDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchCostItemDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostItemDict>
	 * @throws DataAccessException
	 */
	public List<CostItemDict> queryCostItemDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict所有数据
	 * @param entityMap
	 * @return List<CostItemDict>
	 * @throws DataAccessException
	 */
	public List<CostItemDict> queryCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict所有数据
	 * @param entityMap
	 * @return List<CostItemDict>
	 * @throws DataAccessException
	 */
	public List<?> queryCostItemDictByTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDictByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public CostItemDict queryCostItemDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDictByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public CostItemDict queryCostItemDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              删除CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              批量删除CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchCostItemDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              更新CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	public int updateCostItemDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	public int updateCostItemDictByName(Map<String, Object> entityMap) throws DataAccessException;

	public int updateCostItemBatch(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              批量更新CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchCostItemDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostItemDictPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
