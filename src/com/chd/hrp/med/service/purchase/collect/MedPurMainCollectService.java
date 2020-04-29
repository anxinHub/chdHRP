package com.chd.hrp.med.service.purchase.collect;

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
public interface MedPurMainCollectService extends SqlService{
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按药品ID 查询药品当前库存明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInvCurAmountDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPurMainByCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>汇总生成采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String genByCollectMedPurMain(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划数量明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPurMainAmountDetail(Map<String,Object> entityMap)throws DataAccessException;
}
