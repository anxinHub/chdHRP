package com.chd.hrp.med.dao.order;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedOrderDetailMapper extends SqlMapper{
	
	/**
	 * 查询最大序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedOrderDetailNextval() throws DataAccessException;
	
	/**
	 * 更新 订单明细表中数据
	 * @param deleteMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteForUpdate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量插入到明细表
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 根据id查询从表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryByOrderIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 合并时插入明细表数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchDetailMerge(List<Map<String, Object>> detailList)  throws DataAccessException;

}
