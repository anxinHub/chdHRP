package com.chd.hrp.htc.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.info.basic.HtcChargeItemDict;
/** 
* 2015-3-18 
* author:alfred
*/ 


public interface HtcChargeItemDictMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 添加CostChargeItemArrt
	 * @param CostChargeItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addHtcChargeItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量添加CostChargeItemArrt
	 * @param  CostChargeItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchHtcChargeItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostChargeItemArrt>
	 * @throws DataAccessException
	*/
	public List<HtcChargeItemDict> queryHtcChargeItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt所有数据
	 * @param  entityMap
	 * @return List<CostChargeItemArrt>
	 * @throws DataAccessException
	*/
	public List<HtcChargeItemDict> queryHtcChargeItemDict(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HtcChargeItemDict queryHtcChargeItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteHtcChargeItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchHtcChargeItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateHtcChargeItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchHtcChargeItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;

	
}
