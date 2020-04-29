
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
import com.chd.hrp.hr.entity.sysstruc.HrFun;
/**
 * 
 * @Description:
 * 9908 绩效函数表
 * @Table:
 * PRM_FUN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface HrFunMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmFun(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * @Description 
	 * 批量添加9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmFun(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmFun(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return PrmFun
	 * @throws DataAccessException
	*/
	public int updateBatchPrmFun(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmFun(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmFun(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9908 绩效函数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFun> queryPrmFun(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9908 绩效函数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFun> queryPrmFunByFile(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9908 绩效函数表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFun> queryPrmFun(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取9908 绩效函数表<BR> 
	 * @param  entityMap
	 * @return PrmFun
	 * @throws DataAccessException
	*/
	public HrFun queryPrmFunByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * @Description 
	 * 查询结果集9908 绩效函数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public HrFun queryPrmFunComtype(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9908 绩效函数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public HrFun queryPrmFunByPrcName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 创建函数
	 */
	public int createPrmFun(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询函数返回值
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public Map<String,Object> collectFun(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询函数返回值
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public Map<String,Object> collectPro(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
