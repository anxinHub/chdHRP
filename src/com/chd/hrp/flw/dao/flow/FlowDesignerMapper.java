/**
* @Copyright: Copyright (c) 2016-1-24 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.flw.dao.flow;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.flw.entity.flow.ActGeBytearray;
import com.chd.hrp.flw.entity.flow.ActReProcdef;



public interface FlowDesignerMapper extends SqlMapper{
	
	/**
	 * 流程定义数据表<BR>查询ACT_RE_PROCDEF所有数据
	*/
	public List<ActReProcdef> queryFlowActReProcdef(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 流程定义数据表<BR> 查询ACT_RE_PROCDEF,ByKey
	*/
	public ActReProcdef queryFlowActReProcdefByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 根据部署ID查询流程的二进制
	*/
	public ActGeBytearray queryFlowByBlob(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询最新版本的流程类别
	*/
	public List<String> queryFlowCategory(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询历史版本的流程
	*/
	public List<ActReProcdef> queryFlowActReProcdefVersion(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询激活状态的流程类别、部署ID
	*/
	public List<ActReProcdef> queryFlowCategoryDeploymentId(Map<String,Object> entityMap) throws DataAccessException;	
}
