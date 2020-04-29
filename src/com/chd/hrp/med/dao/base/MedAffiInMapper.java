package com.chd.hrp.med.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedAffiInMapper extends SqlMapper{
	
	/**
	 * 添加代销主表数据<BR> 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiInMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量添加主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiInMainBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 添加代销明细表数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiInDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 更新 代销主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedAffiInMain(Map<String, Object> entityMap) throws DataAccessException;
	public int updateBatchMedAffiInMain(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改代销明细表<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedAffiInDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 删除代销主表数据<BR> 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedAffiInMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除代销明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedAffiInDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量删除 代销主表
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchMedAffiInMain(List<Map<String, Object>> entityList) throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除 明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedAffiInDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 更新明细表中 已经删除的明细
	 * @param deleteMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedAffiInDetailForUpdate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据编码查询 代销主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 根据编码查询 代销明细表信息
	 * @param  entityMap <BR>
	 * @throws DataAccessException
	*/
	public List<?> queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 根据in_id 查询资金配套信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T querySourceById(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取代销入库序号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAffiInMainSeq() throws DataAccessException;
	
	/**
	 * 获得代销明细序号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAffiInDetailSeq() throws DataAccessException;
	
	/**
	 * 获取个体码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPerBar(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插入个体码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int insertMedPerBar(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新个体码
	 * @param barCodeMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedPerBar(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 新增入库单订单关系表
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int insertOrderRela(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 删除入库单订单关系表
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteOrderRela(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据主表ID删除明细表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量删除入库单订单关系表
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchOrderRela(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据ID查询主表集合
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInMainByIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据ID查询明细集合
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInDetailByIds(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 获取上一张的ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInBefore(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获取下一张的ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInNext(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代销调拨  调入确认
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchConfirm(List<Map<String, Object>> entityList) throws DataAccessException;

	public <T>T queryMedAffiInByCode(Map<String, Object> entityMap)  throws DataAccessException;
	
}
