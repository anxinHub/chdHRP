package com.chd.hrp.prm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmEmpDuty;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmEmpDutyMapper  extends SqlMapper{
	
	/**
	 * 
	 */
	public int addPrmEmpDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public List<PrmEmpDuty> queryPrmEmpDuty(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<PrmEmpDuty> queryPrmEmpDuty(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public PrmEmpDuty queryPrmEmpDutyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteEmpDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public int deleteBatchPrmEmpDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateEmpDuty(Map<String,Object> entityMap)throws DataAccessException;
	

}
