
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service.report;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 8804 科室字典变更表
 * @Table:
 * APHI_DEPT_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDimReportService {

	/**
	 * @Description 
	 * 查询报表Option<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDimReportOption(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询报表BottomGrid<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDimReportBottomGrid(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询报表BottoGrid<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDimReportRightGrid(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
