
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
/**
 * 
 * @Description:
 * 0312 科室绩效指标数据表
 * @Table:
 * PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptTargetDataService {

	/**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmDeptTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDeptTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmDeptTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptTargetPrmTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 打印结果集0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrmDeptTargetPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmDeptTargetData queryPrmDeptTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 生成
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String createPrmDeptTargetData(Map<String,Object> entityMap,String paramVo)throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditPrmdeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 所有科室
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<PrmDeptDict> queryPrmDeptDictList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptTargetDataByImport(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String importPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
}
