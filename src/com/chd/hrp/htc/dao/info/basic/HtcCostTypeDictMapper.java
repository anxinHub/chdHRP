package com.chd.hrp.htc.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.info.basic.HtcCostTypeDict;
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

public interface HtcCostTypeDictMapper extends SqlMapper{
	
	/**
	 * @Description 查询刚查询序列号
	 * @param
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryCurrentSequence() throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              添加HtcCostTypeDict
	 * @param CostDeptTypeDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addHtcCostTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              批量添加HtcCostTypeDict
	 * @param CostDeptTypeDict
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchHtcCostTypeDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              查询HtcCostTypeDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostDeptTypeDict>
	 * @throws DataAccessException
	 */
	public List<HtcCostTypeDict> queryHtcCostTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              查询HtcCostTypeDict所有数据
	 * @param entityMap
	 * @return List<CostDeptTypeDict>
	 * @throws DataAccessException
	 */
	public List<HtcCostTypeDict> queryHtcCostTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              查询HtcCostTypeDictByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public HtcCostTypeDict queryHtcCostTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * @Description 成本类型字典<BR>
	 *              批量删除HtcCostTypeDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchHtcCostTypeDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 成本类型字典<BR>
	 *              更新HtcCostTypeDict
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateHtcCostTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	
}
