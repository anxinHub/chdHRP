/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.disposal;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.disposal.AssDisposalMain;
/**
 * 
 * @Description:
 * 051001资产处置主表
 * @Table:
 * ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssDisposalMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssDisposalMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return AssDisposalMain
	 * @throws DataAccessException
	*/
	public int updateBatchAssDisposalMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新期末结转使用
	 * @param  entityMap
	 * @return AssDisposalMain
	 * @throws DataAccessException
	*/
	public int 	updateBatchAssDisposalCheckOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssDisposalMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051001资产处置主表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalMain> queryAssDisposalMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051001资产处置主表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalMain> queryAssDisposalMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051001资产处置主表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssDisposalMain
	 * @throws DataAccessException
	*/
	public AssDisposalMain queryAssDisposalMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051001资产处置主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssDisposalMain
	 * @throws DataAccessException
	*/
	public AssDisposalMain queryAssDisposalMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051001资产处置主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssDisposalMain>
	 * @throws DataAccessException
	*/
	public List<AssDisposalMain> queryAssDisposalMainExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryCurrentSequence()throws DataAccessException;

	public List<AssDisposalMain> queryAssDisposalState(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<AssDisposalMain> queryAssDisposalState(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public void updateAssDisposalApproveAudit(
			List<Map<String, Object>> entityMap)throws DataAccessException;

	public void updateBatchAssDisposalMain1(Map<String, Object> entityMap)throws DataAccessException;

	public void updateAccountAssDisposalMain(Map<String, Object> entityMap)throws DataAccessException;

	public void updateRemoveAssDisposalMain(Map<String, Object> entityMap) throws DataAccessException;

	
	//资产处置结转查询
	
	public List<AssDisposalMain>  queryDisposalMainCheckOut(Map<String, Object> entityMap) throws DataAccessException;
	
}
