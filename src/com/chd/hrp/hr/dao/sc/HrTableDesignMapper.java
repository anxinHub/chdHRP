/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
/**
 * 
 * @Description:
 * 查询设计器
 * @Table:
 * HR_TAB_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTableDesignMapper extends SqlMapper{

	int queryByCodeOrName(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryHrTableDesignTree(Map<String, Object> entityMap) throws DataAccessException;

	int queryMaxSrotNO(Map<String, Object> entityMap) throws DataAccessException;
	
	int updateLastStateByCode(Map<String, Object> entityMap) throws DataAccessException;

	int updateDesignQueryColByCode(Map<String, Object> entityMap) throws DataAccessException;

	List<HrTableDesign> queryHrTableDesignExport(Map<String, Object> entityMap) throws DataAccessException;

	int queryChildNode(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryDBTableTree(Map<String, Object> entityMap) throws DataAccessException;

	int updateDesignQuerySqlByCode(Map<String, Object> entityMap) throws DataAccessException;

	int updateDesignQueryPageByCode(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryHrStatisticTableTree(Map<String, Object> entityMap) throws DataAccessException;


}
