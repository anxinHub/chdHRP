
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.dao;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptMaping;

/**
 * 
 * @Description:
 * 8806 奖金科室映射表
 * @Table:
 * Prm_DEPT_MAPING
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiDeptMapingMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptMaping(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptMaping(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptMaping(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return PrmDeptMaping
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptMaping(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptMaping(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptMaping(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8806 奖金科室映射表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiDeptMaping> queryPrmDeptMaping(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8806 奖金科室映射表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiDeptMaping> queryPrmDeptMaping(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取8806 奖金科室映射表<BR> 
	 * @param  entityMap
	 * @return PrmDeptMaping
	 * @throws DataAccessException
	*/
	public AphiDeptMaping queryPrmDeptMapingByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
