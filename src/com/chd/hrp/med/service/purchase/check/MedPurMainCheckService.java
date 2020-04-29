package com.chd.hrp.med.service.purchase.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description:
 * 04114 采购计划审核
 * @Table:
 * MED_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedPurMainCheckService{
	/**
	 * @Description 
	 * 采购计划审核<BR>查询 采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPurMain(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 采购计划审核<BR>审核
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String checkMedPurMain(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>取消审核
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String cancelCheckMedPurMain(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>根据主表ID查询采购明细
	 * @param entityMap
	 * @return <T>
	 * @throws DataAccessException
	*/
	public <T> T queryMedPurMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPurMainDetail(Map<String, Object> entityMap) throws DataAccessException; 
	
	//入库模板打印（包含主从表）
		public String queryMedCheckByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
}
