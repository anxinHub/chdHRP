
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
import com.chd.hrp.hr.entity.sysstruc.HrFunPara;
/**
 * 
 * @Description:
 * 9911 绩效函数参数表
 * @Table:
 * PRM_FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface HrFunParaMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmFunPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmFunPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmFunPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return PrmFunPara
	 * @throws DataAccessException
	*/
	public int updateBatchPrmFunPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmFunPara(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmFunPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9911 绩效函数参数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFunPara> queryPrmFunPara(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9911 绩效函数参数表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrFunPara> queryPrmFunPara(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return PrmFunPara
	 * @throws DataAccessException
	*/
	public HrFunPara queryPrmFunParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<HrFunPara> queryComTypePara(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrFunPara> queryComTypeParaByDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrFunPara> queryComTypeParaByEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrFunPara> queryComTypeParaByHos(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrFunPara> queryPrmFunParaByFunCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HrFunPara> queryPrmFunParaByFunCode(Map<String,Object> entityMap) throws DataAccessException;
	
}
