/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgindexdict;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

 * @Table:
 * BUDG_INDEX_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgIndexDictService extends SqlService {
	
	public String queryBudgIndexDeptSet(Map<String, Object> page) throws DataAccessException;
	
	public String addBudgIndexDeptSet(List<Map<String, Object>> listVo)throws DataAccessException;
	
	

	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException;

}
