/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wage;
import java.util.*; 

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageEmp;

/**
* @Title. @Description.
* 工资套职工关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageEmpKindService {

	public String addAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String queryAccWageEmpKind(Map<String,Object> entityMap) throws DataAccessException;
	 
	public AccWageEmp queryAccWageEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 工资套与职工分类关系 批量删除
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBatchAccWageEmpKind(Map<String, Object> paramMap)throws DataAccessException;

	public String updateAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageEmpKindListPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 保存职工分类与工资套的关系
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveAccWageEmpKind(Map<String, Object> mapVo) throws DataAccessException;
}
