/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.yearend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 年度授权/直接支出预算下达数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgTargetMapper extends SqlMapper{
	
	/**
	 * @Description 添加
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccBudgTarget(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 修改
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccBudgTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询
	 * @param  entityMap
	 * @return List<AccBudgTarget>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccBudgTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 精准查询
	 * @param  entityMap
	 * @return AccBudgTarget
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryAccBudgTargetByCode(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 按年查询
	 * @param  entityMap
	 * @return AccBudgTarget
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryAccBudgTargetByYear(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 删除
	 * @param  Map<String, Object> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBudgTarget(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 审核/消审
	 * @param  Map<String, Object> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditAccBudgTarget(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 获取数据审核状态
	 * @param  Map<String, Object> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryAccBudgTargetState(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 校验年度是否已存在数据
	 * @param  Map<String, Object> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsAccBudgTarget(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 校验年度是否已生成过凭证
	 * @param  Map<String, Object> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsVouchByTemplateYear(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 获取授权支付实际发生额(1011零余额账户用款额度本年累计借方发生额)
	 * @param  Map<String, Object> 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double querySumDebitBySubj1011(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 获取财政拨款收入本年贷方发生额
	 * @param  Map<String, Object> 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double querySumCreditBySubj4001(Map<String, Object> entityMap)throws DataAccessException;
	
}
