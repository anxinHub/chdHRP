/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWagePay;
/**
* @Title. @Description.
* 工资数据<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface AccWagePayMapper extends SqlMapper{    
	
	/**
	 * @Description 
	 * 工资数据<BR> 添加AccWagePay
	 * @param AccWagePay entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量添加AccWagePay
	 * @param  AccWagePay entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWagePay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int addAccWagePayByWage(Map<String, Object> entityMap)throws DataAccessException;
	
	public int addAccWagePayByWageExtend(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePay分页
	 * @param  entityMap RowBounds
	 * @return List<AccWagePay>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWagePay(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePay所有数据
	 * @param  entityMap
	 * @return List<AccWagePay>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWagePay(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePayByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccWagePayByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 删除AccWagePay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量删除AccWagePay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWagePay(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccWagePay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWagePay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量更新AccWagePay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWagePay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询动态表头
	 * @param  entityMap
	 * @return List<AccWagePay>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccWagePayGrid(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 工资数据<BR> 查询动态表头
	 * @param  entityMap
	 * @return List<AccWagePay>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccWageItemGrid(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccWagePay> queryEmpByWageCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccWageItemPay(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public int addAccWagePayByList(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccWageItemPayOfCalName(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccWagePay> queryAccEmpDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccWagePay> extendAccEmpDetailByQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 生成本月工资表(通过工资套与职工的关联关系)
	 */
	public int addAccWagePayByWageEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 生成本月工资表(通过工资套与职工分类的关联关系)
	 */
	public int addAccWagePayByEmpKindBatch(Map<String, Object> paramMap) throws DataAccessException;
	
	//导入工资项
	public int addBatchAccWagePayImport(List<Map<String, Object>>  entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWagePayByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryAccWageItemSum(Map<String,Object> entityMap)throws DataAccessException;
	//启用工资管理系统时判断是否存在业务数据
	public List<AccWagePay> queryAccWagePayByMod(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccSchemeEmpKindList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccSchemeWageItemList(Map<String,Object> entityMap) throws DataAccessException;
	//查询生成本月工资表时上月是否有数据
	public int queryAccWagePayByLastMonth(Map<String,Object> entityMap) throws DataAccessException;
	//继承上月工资项（工资项目is_js=1）
	public int extendAccWagePay(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public AccWagePay queryAccWagePayByColumn(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccWagePay> queryAccWagePayTree(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccEmpTetail(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryAccEmpTetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<Map<String,Object>> extendAccEmpDetail(Map<String,Object> entityMap)throws DataAccessException;

	public Map<String,Object> collectAccWagePay(Map<String,Object> entityMap) throws  DataAccessException;
	
	public List<Map<String,Object>> queryWageByExtend(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> collectAccWagePayByPerson(Map<String,Object> entityMap) throws  DataAccessException;

	public Map<String, Object> queryAccWageCulumn(Map<String,Object> entityMap)throws DataAccessException;

	public int auditBatchAccWagePay(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWagePayByItemCal(Map<String, Object> entityMap)throws DataAccessException;

	public List<AccWagePay> queryAccWagePayByDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAccWagePayOfBatch(Map<String,Object> entityMap)throws DataAccessException;

	public int checkEmpNameAndCode(Map<String, Object> mapVo);
	
	public int updateAccWagePayByEmpDict(Map<String, Object> mapVo);
	
	public int existsAccCheckEmpPayByCode(List<Map<String, Object>> entityMap) throws DataAccessException;

	public List<AccWagePay> queryWageState(Map<String, Object> mapVo)  throws DataAccessException;
	
}
