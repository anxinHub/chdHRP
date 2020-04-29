package com.chd.hrp.mat.dao.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatOrderMainMapper extends SqlMapper{  

	
	
	/**
	 * 查询最大序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatOrderNextval() throws DataAccessException;
	
	/**
	 * 批量插入主表数据
	 * @param mainList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchMain(List<Map<String, Object>> mainList) throws DataAccessException; 
	//根据list中的order_id把明细金额汇总更新到主表金额中
	public int updateMainMoneyForAddBatch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;   
	
	/**
	 * 根据订单号返回主表行数
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatOrderMainCounts(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新被合并的单据状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int updateOldMatOrderState(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获得删除的采购单
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeleteOldIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 订单编制 显示明细查询
	 * @param entityMap
	 * @return
	 */
	public List<?> queryShowDetail(Map<String, Object> entityMap);
	public List<?> queryShowDetail(Map<String, Object> entityMap,
			RowBounds rowBounds);

	public int updateState(List<Map<String, Object>> detailList)throws DataAccessException;
}
