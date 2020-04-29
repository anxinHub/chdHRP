/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWagePayDesc;
/**
 * 
 * @Description:
 * 
 * @Table:
 * ACC_WAGE_PAY_DESC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AccWagePayDescMapper extends SqlMapper{
	
	public int addAccWagePayDesc(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchAccWagePayDesc(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccWagePayDesc> queryAccWagePayDesc(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<AccWagePayDesc> queryAccWagePayDesc(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccWagePayDesc queryAccWagePayDescByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteAccWagePayDesc(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAccWagePayDesc(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	public int updateAccWagePayDesc(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchAccWagePayDesc(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
