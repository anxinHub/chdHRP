package com.chd.hrp.mat.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatAffiOutMapper extends SqlMapper{
	
	/**
	 * 获取代销出库主表自增序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatAffiOutMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMatAffiOutDetailSeq() throws DataAccessException;
	
	/**
	 * 代销出库 -- 增加主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库 -- 增加主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiOutMainBatch(List<Map<String, Object>> mainList) throws DataAccessException;
	
	/**
	 * 代销出库 --增加明细表信息
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void addMatAffiOutDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 医嘱核销-代销出库 --增加明细表信息
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void addMatAffiOutDetailInit(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据主键查询明细表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryByCodeDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改代销出库主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatAffiOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 批量更新明细表中数据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatAffiOutDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 更新 明细删除的数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatAffiOutDetailForUpdate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除明细表
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteBatchMatAffiOutDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 批量删除主表
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteBatchMatAffiOutMain(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 删除主表
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void deleteMatAffiOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除明细表
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void deleteMatAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取代销出库明细表自增序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryMatAffiOutDetailNextval(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取当前个体码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPerBar(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改个体码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatPerBar(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插入个体码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int insertMatPerBar(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除的数据是否是当前用户制的单据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNotByUserId(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量更新代销出库主表信息
	 * @param updateMatAffiOutList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchMatAffiOutMain(List<Map<String, Object>> updateMatAffiOutList) throws DataAccessException;
	
	/**
	 * 添加  出入库对应关系表
	 * @param realList
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiTranRela(List<Map<String, Object>> realList) throws DataAccessException;
	
	/**
	 * 删除 出入库对应关系表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatAffiTranRela(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量删除对应关系表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchMatAffiTranRela(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据ID查询明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAffiOutDetailById(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAffiOutDetailById(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryAffiOutDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销调拨  调入确认
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchConfirm(List<Map<String, Object>> entityList) throws DataAccessException;
	
	
}
