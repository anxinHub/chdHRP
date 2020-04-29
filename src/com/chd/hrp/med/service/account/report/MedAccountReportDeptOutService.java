/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.account.report;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAccountReportDeptOutService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountReportDeptOut(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 其它入库查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInByType(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 其它入库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOtherInDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 其他出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOtherOutDetail(Map<String, Object> entityMap)  throws DataAccessException;
}
