/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.dpetexecute;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室业务执行数据
 * @Table:
 * BUDG_WORK_DEPT_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptYearExecuteService extends SqlService {

public 	String budgWorkDeptYearExecuteImport(Map<String, Object> mapVo) throws DataAccessException;

/**
 * 末级科目基本信息
 * @param paraMap
 * @return
 * @throws DataAccessException
 */
public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap) throws DataAccessException;

/**
 * 预算指标信息
 * @param paraMap
 * @return
 * @throws DataAccessException
 */
public List<Map<String, Object>> queryIndexData(Map<String, Object> paraMap) throws DataAccessException;
/**
 * 获取打印数据
 * @param paraMap
 * @return
 * @throws DataAccessException
 */
public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo)throws DataAccessException;

}
