
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.service.sysstruc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrFunParaMethod;

/**
 * 
 * @Description:
 * 9912 绩效函数参数取值表
 * @Table:
 * PRM_FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface HrFunParaMethodService {

	/**
	 * @Description 
	 * 添加9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmFunParaMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmFunParaMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmFunParaMethod(List<HrFunParaMethod> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmFunParaMethod(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public HrFunParaMethod queryPrmFunParaMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 应用模式
	 */
	public String queryPrmFunParaByDict(Map<String, Object> map) throws DataAccessException;
}
