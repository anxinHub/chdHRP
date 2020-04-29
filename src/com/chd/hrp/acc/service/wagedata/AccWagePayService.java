/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.entity.AccWagePay;
/**
* @Title. @Description.
* 工资数据<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWagePayService {

	/**
	 * @Description 
	 * 工资数据<BR> 添加AccWagePay
	 * @param AccWagePay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 合并工资套
	 * @param AccWagePay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWagePayCombine(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量添加AccWagePay
	 * @param  AccWagePay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWagePay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePay分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWagePay(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWagePayPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePayByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccWagePayByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 删除AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量删除AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWagePay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 计算工资
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String collectBatchAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 导入AccWagePay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWagePay(List<Map<String, Object>>  entityMap)throws DataAccessException;
	
	/**
	 *添加页面动态显示列
	 */
	public String queryTrendColumn(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 *查询页面动态显示列
	 */
	public String queryAccWagePayGrid(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 *根据方案编码查询工资项
	 */
	public List<Map<String, Object>> queryAccWageItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryEmpByWageCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageItemPay(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addAccWagePayByList(Map<String,Object> entityMap)throws DataAccessException;
	
	public String funCollect(Map<String,Object> entityMap,List<AccWageItems> list) throws DataAccessException;
	/**
	 *根据方案编码查询工资项  
	 */
	public List<AccWagePay> queryAccEmpDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccWagePay> queryWageState(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageEmpDetailByAdd(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 *根据工资套编码查询工资项
	 */
	public List<AccWageItems> queryAccWageItem(Map<String,Object> entityMap) throws DataAccessException;
	//生成本月工资表
	public String addAccWagePayByWageEmp(Map<String,Object> entityMap)throws DataAccessException;

	public String queryAccSchemeEmpKindList(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccSchemeWageItemList(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccWagePayTree(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccEmpTetail(Map<String,Object> entityMap)throws DataAccessException;

	public String extendAccEmpDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccWageEmpDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String extendAccWagePay(Map<String,Object> entityMap)throws DataAccessException;

	public String collectAccWagePayByPerson(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryAccWageCulumn(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateAccWagePayOfBatch(Map<String,Object> entityMap)throws DataAccessException;

	int checkEmpNameAndCode(Map<String, Object> mapVo);
	
}
