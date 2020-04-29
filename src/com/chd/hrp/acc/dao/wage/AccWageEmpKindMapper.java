/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageEmp;

/**
* @Title. @Description. 
* 工资套职工关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageEmpKindMapper extends SqlMapper{
	
	public int addAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException;
 
	public int addBatchAccWageEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccWageEmp> queryAccWageEmpKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<AccWageEmp> queryAccWageEmpKind(Map<String,Object> entityMap) throws DataAccessException;
	
	//用于职工维护时通过kind_code查询wage_code
	public List<AccWageEmp> queryAccWageEmpKindBykindCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccWageEmp queryAccWageEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAccWageEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	public int updateAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchAccWageEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;

	//用户判断系统平台中职工是否存在，存在则不能进行删除  
	public int queryAccWageEmpCountByEmpId(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageEmpKindListPrint(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 删除本集团、医院、账套、工资套下的职工分类关系
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int deleteAccWageEmpKindByWageCode(Map<String, Object> mapVo) throws DataAccessException;
	//删除工资套下工资方案对应的职工分类
	public void deleteBatchAccWageSKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	//删除该工资套该职工分类的计算方法
	public void deleteBatchAccWageCal(List<Map<String, Object>> entityMap);
}
