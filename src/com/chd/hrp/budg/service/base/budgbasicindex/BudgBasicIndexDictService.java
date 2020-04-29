/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgbasicindex;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 基本指标
 * @Table:
 * BUDG_BASIC_INDEX_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgBasicIndexDictService extends SqlService {

	public String queryBudgBasicIndexDeptSet(Map<String, Object> page) throws DataAccessException;
	/**
	 * @Description 
	 * 添加基本指标对应科室维护<BR> 
	 * @param  listVo
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBudgBasicIndexDeptSet(List<Map<String, Object>> listVo)throws DataAccessException;
	/**
	 * 判断基本指标名称是否被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
}
