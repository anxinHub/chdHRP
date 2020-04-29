
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.dao;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmHosScheme;
/**
 * 
 * @Description:
 * 0203 医院绩效方案表
 * @Table:
 * PRM_HOS_SCHEME
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmHosSchemeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmHosScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return PrmHosScheme
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmHosScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteBatchPrmHosSchemeByImport(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosScheme> queryPrmHosScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosScheme> queryPrmHosScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosScheme> queryPrmHosSchemeLeftName(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosScheme> queryPrmHosSchemeLeftName(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return PrmHosScheme
	 * @throws DataAccessException
	*/
	public PrmHosScheme queryPrmHosSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public PrmHosScheme queryPrmHosSchemeMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<PrmHosScheme> queryPrmHosSchemeBySuperKpiCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
