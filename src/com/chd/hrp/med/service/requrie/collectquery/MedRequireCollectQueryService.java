/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.requrie.collectquery;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedRequireMain;
/**
 * 
 * @Description:
 * 科室需求计划汇总查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedRequireCollectQueryService extends SqlService{
	
	/**
	 * 科室需求计划汇总查询--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectQ(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总查询   汇总单号查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总查询--明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectDept(Map<String, Object> entityMap) throws DataAccessException;

		
}
