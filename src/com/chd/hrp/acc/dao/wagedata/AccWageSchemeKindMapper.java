/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）录入科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageSKind;
import com.chd.hrp.acc.entity.AccWageSchemeKind;

/**
* @Title. @Description.
* 工资录入<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface AccWageSchemeKindMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资录入<BR> 添加AccWageSchemeKind
	 * @param AccWageSchemeKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 批量添加AccWageSchemeKind
	 * @param  AccWageSchemeKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageSchemeKind(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 查询AccWageSchemeKind分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageSchemeKind>
	 * @throws DataAccessException
	*/
	public List<AccWageSchemeKind> queryAccWageSchemeKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资录入<BR> 查询AccWageSchemeKind所有录入
	 * @param  entityMap
	 * @return List<AccWageSchemeKind>
	 * @throws DataAccessException
	*/
	public List<AccWageSchemeKind> queryAccWageSchemeKind(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询工资方案与职工类别关系
	 */
	public List<AccWageSKind> queryAccWageSKind(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询工资方案与职工类别关系（带分页）
	 */
	public List<AccWageSKind> queryAccWageSKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 查询AccWageSchemeKindByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageSchemeKind queryAccWageSchemeKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 删除AccWageSchemeKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 批量删除AccWageSchemeKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageSchemeKind(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资录入<BR> 更新AccWageSchemeKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 批量更新AccWageSchemeKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageSchemeKind(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccWageSchemeKindList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 通过工资方案ID删除工资方案与职工分类关系
	 */
	public void deleteAccWageSKindBySchemeId(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 添加工资方案与职工分类关系（批量）
	 * @param list
	 * @throws DataAccessException
	 */
	public void addAccWageSKindBatch(List<AccWageSKind> list) throws DataAccessException;

	/**
	 * 删除工资方案与职工分类关系（批量）
	 */
	public void deleteAccWageSKindBatch(List<AccWageSKind> list) throws DataAccessException;

	/**
	 * 检查是工资条的方案下包含的职工
	 */
	public List<AccWageSchemeKind> queryGztIncludeEmp(Map<String, Object> map) throws DataAccessException;

	/**
	 * 通过方案与职工id(字符串集合)批量删除
	 */
	public void deleteBySchemeIdAndEmpIds(Map<String, Object> paraMap) throws DataAccessException;
}
