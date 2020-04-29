package com.chd.hrp.budg.dao.budgsysset;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgSysSet;


public interface BudgSysSetMapper extends SqlMapper {
	/**
	 * 添加预算系统设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addBudgSysSet(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据主键查询 单条预算设置方案 记录
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public BudgSysSet queryBudgSysSetByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 预算设置方案 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryBudgSysSet(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改 预算系统设置方案 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgSysSet(Map<String, Object> entityMap) throws DataAccessException;
}
