package com.chd.hrp.budg.service.budgsysset;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgSysSet;
import com.chd.hrp.sys.service.base.SysBaseService;



public interface BudgSysSetService{
	/**
	 * 预算编制方案设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addBudgSysSet(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 *  查询   预算编制方案设置
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public String queryBudgSysSet(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 *主页面跳转 根据当前年度  查询对象
	 * @param map
	 * @return
	 */
	public BudgSysSet queryBudgSysSetByCode(Map<String, Object> map) throws DataAccessException;
}
