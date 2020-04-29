/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWagePay;

/**
* @Title. @Description.
* 工资条信息<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEmpWageItemService {

	/**
	 * @Description 
	 * 工资条信息<BR> 查询AccEmpWageItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccEmpWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 查询AccEmpWageItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccEmpWageItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 更新AccEmpWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccEmpWageItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 批量更新AccEmpWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccEmpWageItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccEmpWageItemPrint(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 生成工资条excel数据
	 */
	public void generatePayrollExcelData(Map<String, Object> paraMap) throws DataAccessException;
}
