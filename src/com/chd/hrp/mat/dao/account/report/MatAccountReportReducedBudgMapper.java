package com.chd.hrp.mat.dao.account.report;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 预算降本报表
 * @Table:
 * @Author: weixiaofneg
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportReducedBudgMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryReducedBudg(Map<String,Object> entityMap) throws DataAccessException;
	
}
