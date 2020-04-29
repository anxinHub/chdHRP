/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgcharge;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 是否停用（IS_STOP):0否，1是
费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室
 * @Table:
 * BUDG_CHARGE_STANDARD_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgChargeStandardDictMapper extends SqlMapper{
	
	/**
	 * 判断费用标准名称是否被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询费用标准性质编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryStanNatureExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算科室查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryChargeStanDeptSet(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 预算科室查询  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryChargeStanDeptSet(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
