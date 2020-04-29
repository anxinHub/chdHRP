package com.chd.hrp.med.dao.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedOrderMainMapper extends SqlMapper{

	
	
	/**
	 * 查询最大序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedOrderNextval() throws DataAccessException;
	
	/**
	 * 批量插入主表数据
	 * @param mainList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchMain(List<Map<String, Object>> mainList) throws DataAccessException;
	/**
	 * 根据订单号返回主表行数
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedOrderMainCounts(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新被合并的单据状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int updateOldMedOrderState(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获得删除的采购单
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeleteOldIds(Map<String, Object> entityMap) throws DataAccessException;
}
