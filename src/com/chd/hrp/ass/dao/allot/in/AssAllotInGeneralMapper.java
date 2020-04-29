﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.allot.in;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表（一般设备）
 * @Table:
 * ASS_ALLOT_IN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotInGeneralMapper extends SqlMapper{
	
	public int updateInMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 资产调剂入库 主页面 入库主表查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssAllotInGeneralPrintTemlateByMainBatch(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备  资产调剂入库 入库明细表查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssAllotInGeneralPrintTemlateByDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 资产调剂入库 修改页面 入库主表查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssAllotInGeneralPrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备  资产调剂入库  入库单状态查询（打印校验数据用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssAllotInGeneralState(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
	
}