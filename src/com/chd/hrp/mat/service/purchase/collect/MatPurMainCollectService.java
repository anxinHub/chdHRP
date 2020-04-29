package com.chd.hrp.mat.service.purchase.collect;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description:
 * 04113 统购采购计划汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatPurMainCollectService extends SqlService{
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按材料ID 查询材料当前库存明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvCurAmountDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatPurMainByCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>汇总生成采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String genByCollectMatPurMain(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划数量明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatPurMainAmountDetail(Map<String,Object> entityMap)throws DataAccessException;
}
