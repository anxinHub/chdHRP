package com.chd.hrp.med.dao.purchase.collect;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 04114 统购采购计划汇总
 * @Table:
 * MED_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedPurMainCollectMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>分页查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurDetail(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 材料当前库存明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedInvCurAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurMainByCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>批量查询 采购计划明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurMainDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>批量添加 采购计划编制明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedPurDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	

	/**
	 * 当前年月单据号管理表中是否存在数据
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryIsExists(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取最大的流水号
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMaxCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新 med_no_manage表
	 * @param entityMap
	 * @return int 
	 */
	public int updateMedNoMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 向 单据号表中插入数据
	 * @param entityMap
	 * @return int 
	 * @throws DataAccessException
	 */
	public int addMedNoMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询采购计划主表ID
	 * */
	public Integer queryMedPurMainPurId()throws DataAccessException;
	
	/**
	 * 统购采购计划汇总<BR>添加采购计划对应关系
	 * @param List<Map<String,Object>>
	 * @return int 
	 * @throws DataAccessException
	 */
	public int addMedPurRela(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 统购采购计划汇总<BR>修改采购计划状态
	 * @param List<Map<String,Object>>
	 * @return int 
	 * @throws DataAccessException
	 */
	public int updateBatchPurState(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>批量删除 采购计划明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedPurDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>修改采购计划单状态为已审核
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedPurMainState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>删除 采购计划对应关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>修改 采购计划对应关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMedPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划申请单位、采购单位、付款单位
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurUnit(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划数量明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurMainAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
}
