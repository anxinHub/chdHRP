package com.chd.hrp.htc.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.info.basic.HtcChargeKindDict;
/** 
* 2015-3-18 
* author:alfred
*/ 


public interface HtcChargeKindDictMapper extends SqlMapper{

	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 添加CostChargeKindArrt
	 * @param CostChargeKindArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addHtcChargeKindDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量添加CostChargeKindArrt
	 * @param  CostChargeKindArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchHtcChargeKindDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostChargeKindArrt>
	 * @throws DataAccessException
	*/
	public List<HtcChargeKindDict> queryHtcChargeKindDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrt所有数据
	 * @param  entityMap
	 * @return List<CostChargeKindArrt>
	 * @throws DataAccessException
	*/
	public List<HtcChargeKindDict> queryHtcChargeKindDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HtcChargeKindDict queryHtcChargeKindDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteHtcChargeKindDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchHtcChargeKindDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateHtcChargeKindDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchHtcChargeKindDict(List<Map<String, Object>> entityMap)throws DataAccessException;

}
