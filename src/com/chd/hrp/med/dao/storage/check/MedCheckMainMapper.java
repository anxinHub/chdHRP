﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.check;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedCheckMain;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedCheckMainMapper extends SqlMapper{
	
	/**
	 * @Description 主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Long queryMedCheckMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_check_main  表 库存盘点返回 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_check_main  表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckMainByMedInv(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckMainByMedInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 主表<BR>
	 *              审核
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAuditBatch(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 主表<BR>
	 *              单独修改状态
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateStateBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              包含left join 查询
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public MedCheckMain queryMedCheckMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 * 				根据ID查询主表信息用于生成出入库单
	 * @param entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedCheckMainForInOut(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 盘点单与出入库单对应关系<BR>
	 *              添加
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addMedCheckRela(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 盘点单打印主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCheckPrintTemlateByMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 盘点单打印明细表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCheckPrintTemlateByDetail(Map<String, Object> entityMap)  throws DataAccessException; 
	
	public List<Map<String, Object>> queryCheckPrintTemlateByMainBatch(Map<String, Object> entityMap)  throws DataAccessException;
	/**
	 * 引入仓库药品明细
	 * @param invMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedStoreInvDetail(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 盘点打印模板主表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedCheckByPrintBatch(Map<String, Object> map);
	/**
	 * 盘点打印模板从表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedCheckByPrintBatch_detail(
			Map<String, Object> map);
}