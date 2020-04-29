package com.chd.hrp.htc.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.info.basic.HtcCostItemDict;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcCostItemDictMapper extends SqlMapper{
	
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
	public int addHtcCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              批量添加CostItemDict
	 * @param CostItemDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchHtcCostItemDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostItemDict>
	 * @throws DataAccessException
	 */
	public List<HtcCostItemDict> queryHtcCostItemDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict所有数据
	 * @param entityMap
	 * @return List<CostItemDict>
	 * @throws DataAccessException
	 */
	public List<HtcCostItemDict> queryHtcCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDict所有数据
	 * @param entityMap
	 * @return List<CostItemDict>
	 * @throws DataAccessException
	 */
	public List<?> queryHtcCostItemDictByTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              查询CostItemDictByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public HtcCostItemDict queryHtcCostItemDictByCode(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * @Description 成本项目字典<BR>
	 *              删除CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteHtcCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              批量删除CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchHtcCostItemDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              更新CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateHtcCostItemDict(Map<String, Object> entityMap) throws DataAccessException;

	public int updateHtcCostItemDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	public int updateHtcCostItemDictByName(Map<String, Object> entityMap) throws DataAccessException;

	public int updateHtcCostItemBatch(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本项目字典<BR>
	 *              批量更新CostItemDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchHtcCostItemDict(List<Map<String, Object>> entityMap) throws DataAccessException;


}
