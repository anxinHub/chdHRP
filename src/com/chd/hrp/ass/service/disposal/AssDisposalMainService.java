/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.disposal;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 

public interface AssDisposalMainService {

	/**
	 * @Description 
	 * 添加051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssDisposalMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssDisposalMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssDisposalMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssDisposalMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051001资产处置主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
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

	public String queryAssDisposalState(Map<String, Object> entityMap)throws DataAccessException;

	public String updateAssDisposalApproveAudit(Map<String, Object> entityMap)throws DataAccessException;

	public String updateBatchAssDisposalMain1(Map<String, Object> entityMap)throws DataAccessException;

	public String updateAccountAssDisposalMain(Map<String, Object> entityMap)throws DataAccessException;

	public String updateRemoveAssDisposalMain(Map<String, Object> entityMap)throws DataAccessException;

	
}
