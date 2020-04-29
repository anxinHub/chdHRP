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

import com.chd.hrp.cost.entity.CostDeptTypeDict;

/**
 * @Title. @Description. 成本类型字典<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostDeptTypeDictMapper extends SqlMapper {

	/**
	 * @Description 查询刚查询序列号
	 * @param
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryCurrentSequence() throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              添加CostDeptTypeDict
	 * @param CostDeptTypeDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              批量添加CostDeptTypeDict
	 * @param CostDeptTypeDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchCostDeptTypeDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              查询CostDeptTypeDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostDeptTypeDict>
	 * @throws DataAccessException
	 */
	public List<CostDeptTypeDict> queryCostDeptTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              查询CostDeptTypeDict所有数据
	 * @param entityMap
	 * @return List<CostDeptTypeDict>
	 * @throws DataAccessException
	 */
	public List<CostDeptTypeDict> queryCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              查询CostDeptTypeDictByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public CostDeptTypeDict queryCostDeptTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	public CostDeptTypeDict queryCostDeptTypeDictByTypeCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              删除CostDeptTypeDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              批量删除CostDeptTypeDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchCostDeptTypeDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              更新CostDeptTypeDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateCostDeptTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public int updateCostDeptTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	public int updateCostDeptTypeDictByName(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              批量更新CostDeptTypeDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchCostDeptTypeDict(List<Map<String, Object>> entityMap) throws DataAccessException;
}
