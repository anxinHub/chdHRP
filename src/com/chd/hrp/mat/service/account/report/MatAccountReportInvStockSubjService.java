/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.account.report;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 材料库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAccountReportInvStockSubjService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	//public String queryMatAccountReportInvStockSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询本期增加减少字段<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAccountReportInvStockSubjColumns(Map<String,Object> entityMap) throws DataAccessException;

	public String collectMatAccountReportInvStockSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 * @Description 
	 * PageOffice报表打印功能
	 * 2017年9月19日09点14分
	 */
	public List<Map<String, Object>> collectMatAccountReportInvStockSubjPrint(Map<String, Object> entityMap) throws DataAccessException;
}
