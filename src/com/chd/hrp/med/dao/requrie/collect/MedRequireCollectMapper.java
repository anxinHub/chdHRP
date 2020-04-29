/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.requrie.collect;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedRequireMain;
/**
 * 
 * @Description:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedRequireCollectMapper extends SqlMapper{
	
	/**
	 * 汇总页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedRequireMain> queryCollect(Map<String, Object> entityMap) throws DataAccessException;
	public List<MedRequireMain> queryCollect(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查看计划单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedRequireMain> queryCollectDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<MedRequireMain> queryCollectDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 保存&提交插入med_require_main表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addCollect(Map<String, Object> entityMap)  throws DataAccessException;
	
	/**
	 * 保存&提交插入对应关系表 MED_REQUIRE_RELA 
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int addBatchReal(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 更新 保存&提交的单据 的状态  state = 3
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateBatchCollect(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 定向汇总--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryDirCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总提交   组中明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedCollectDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总页面查询 根据系统参数查询未审核科室需求
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedRequireMain> queryCollectNotCheck(Map<String, Object> entityMap) throws DataAccessException;
	public List<MedRequireMain> queryCollectNotCheck(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 定向汇总页面查询 根据系统参数查询未审核科室需求
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedRequireMain> queryDirCollectNotCheck(Map<String, Object> entityMap) throws DataAccessException;
	public List<MedRequireMain> queryDirCollectNotCheck(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
