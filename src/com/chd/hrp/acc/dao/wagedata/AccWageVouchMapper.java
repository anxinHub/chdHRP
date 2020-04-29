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
import com.chd.hrp.acc.entity.AccWageVouch;
   
/** 
* @Title. @Description.
* 工资转账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageVouchMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资转账<BR> 添加AccWageVouch
	 * @param AccWageVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量添加AccWageVouch
	 * @param  AccWageVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageVouch>
	 * @throws DataAccessException
	*/
	public List<AccWageVouch> queryAccWageVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch所有数据
	 * @param  entityMap
	 * @return List<AccWageVouch>
	 * @throws DataAccessException
	*/
	public List<AccWageVouch> queryAccWageVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouchByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageVouch queryAccWageVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 删除AccWageVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量删除AccWageVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资转账<BR> 更新AccWageVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量更新AccWageVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public AccWageVouch queryAccWageVouchBySchemeCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账职工分类表 <BR> 批量添加工资转账职工分类表 
	 * @param  AccWageVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageVouchKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账科目表 <BR> 批量添加工资转账科目表 
	 * @param  AccWageVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageVouchSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账职工分类表 <BR> 删除工资转账职工分类表 
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageVouchKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账科目表 <BR> 删除工资转账科目表 
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageVouchSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageVouch>
	 * @throws DataAccessException
	*/
	public List<AccWageVouch> queryAccWageVouchSubj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch所有数据
	 * @param  entityMap
	 * @return List<AccWageVouch>
	 * @throws DataAccessException
	*/
	public List<AccWageVouch> queryAccWageVouchSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccWageVouch queryAccCheckItem(Map<String,Object> entityMap) throws DataAccessException;

	public List<AccWageVouch> queryAccWageVouchSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccWageVouch queryAccVouchMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEmpId(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> collectAccWageVouchCal(Map<String,Object> entityMap)throws DataAccessException;
	
	public int extendAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	public int extendAccWageVouchSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	public int extendAccWageVouchKind(Map<String,Object> entityMap)throws DataAccessException;
	
	
	//保存 自动凭证日志表
	public int saveAutoVouchLogTemp(Map<String,Object> entityMap)throws DataAccessException;
	
}
