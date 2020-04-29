/**
 * @author: LFH
 * @Version: 1.0
 *
 */
package com.chd.hrp.hr.dao.ss;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

//dao 接口
public interface HrNoRuleMapper extends SqlMapper{
	
	/**
	 * @Description  查询 Rule 分页
	 * @param： entityMap  RowBounds
	 * @return: List<Result>
	 * @throws: DataAccessException
	 */
	//mapper 接口 在resource/mapper  中对应
	//public List<Map<String, Object>> queryRule(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description  查询 Rule 的全部记录
	 * @param： entityMap
	 * @return: List<Result>
	 * @throws: DataAccessException
	 */
	//mapper 接口
	public List<Map<String, Object>> queryRule(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description  查询 RuleByCode
	 * @Param: entityMap
	 * @return: int
	 * @throws: DataAccessException
	 */
	public int queryRuleByCode()throws DataAccessException;
	
//	public int updataRule(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description  保存 HrNoRule 的修改
	 * @param listVo
	 * @return int
	 * @throws DataAccessException
	 */
	public int saveHrNoRule(List<Map<String, Object>> listVo) throws DataAccessException;

	/**
	 * @Description 删除 HrNoRule 记录
	 * @param listVo
	 */
	public void deleHrNoRule(List<Map<String, Object>> listVo) throws DataAccessException;

	/**
	 * @Description 新增 HrNoRule 记录
	 * @param listVo
	 * @throws DataAccessException
	 */
	public void addHrNoRule(Map<String, Object> mapVo) throws DataAccessException;
}