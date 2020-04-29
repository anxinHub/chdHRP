
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmDeptTargetDataCalculate;
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
 


public interface PrmDeptTargetDataCalculateMapper extends SqlMapper{
	/**
	 * @Description 添加0312 科室绩效指标数据表<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public PrmDeptTargetData queryPrmDeptTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
    public List<PrmDeptTargetDataCalculate> queryPrmDeptTargetPrmTargetDataCalculate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    /**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
    public List<PrmDeptTargetDataCalculate> queryPrmDeptTargetPrmTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
    /**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
    public int cleanPrmDeptTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
    
    /**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptTargetDataCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
}
