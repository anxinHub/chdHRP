
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 


public interface HrFunParaMethodMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmFunParaMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return PrmFunParaMethod
	 * @throws DataAccessException
	*/
	public int updateBatchPrmFunParaMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmFunParaMethod(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9912 绩效函数参数取值表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFunParaMethod> queryPrmFunParaMethod(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9912 绩效函数参数取值表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFunParaMethod> queryPrmFunParaMethod(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return PrmFunParaMethod
	 * @throws DataAccessException
	*/
	public HrFunParaMethod queryPrmFunParaMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 应用模式
	 */
	public List<Map<String, Object>> queryPrmFunParaByDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询name是否已经存在
	 * @param entityMap
	 * @return
	 */
	public int queryExistsByName(Map<String, Object> entityMap) throws DataAccessException;
	
}
