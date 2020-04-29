package com.chd.hrp.ass.dao.balance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssBalanceGeneralMapper extends SqlMapper{

	/**
	 * @Description 
	 * 查询结果集<BR>全部 资产总账对账
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryBalanceAccountGeneralMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 资产总账对账
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryBalanceAccountGeneralMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 资产明细账对账
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryBalanceAccountGeneralDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 资产明细账对账
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryBalanceAccountGeneralDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结转更新上个月期末余额
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCheckOutEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末结转 期末结转到下个月期初
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCheckOutEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCheckOutEnds(Map<String,Object> entityMap)throws DataAccessException;
}
