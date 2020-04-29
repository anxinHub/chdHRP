package com.chd.hrp.hr.service.salarymanagement.empWage;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrWageQueryService {
    /**
     * 薪资查询返回前台grid表格内容
     * @param page
     * @return
     * @throws DataAccessException
     */
	String queryWageCheckQueryGrid(Map<String, Object> page) throws DataAccessException ;

	String queryWageQueryForm(Map<String, Object> mapVo, int colNum) throws DataAccessException ;
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
List<Map<String,Object>> queryWageQueryByPrint(Map<String, Object> page)throws DataAccessException;
}
