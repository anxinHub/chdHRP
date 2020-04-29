package com.chd.hrp.mat.dao.purchase.collect;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 04114 统购采购计划汇总
 * @Table:
 * MAT_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatPurMainCollectMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>分页查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurDetail(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 材料当前库存明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatInvCurAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurMainByCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>批量查询 采购计划明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurMainDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>批量添加 采购计划编制明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatPurDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	

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
	 * 更新 mat_no_manage表
	 * @param entityMap
	 * @return int 
	 */
	public int updateMatNoMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 向 单据号表中插入数据
	 * @param entityMap
	 * @return int 
	 * @throws DataAccessException
	 */
	public int addMatNoMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询采购计划主表ID
	 * */
	public Integer queryMatPurMainPurId()throws DataAccessException;
	
	/**
	 * 统购采购计划汇总<BR>添加采购计划对应关系
	 * @param List<Map<String,Object>>
	 * @return int 
	 * @throws DataAccessException
	 */
	public int addMatPurRela(List<Map<String,Object>> entityMap)throws DataAccessException;
	
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
	public int deleteMatPurDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>修改采购计划单状态为已审核
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatPurMainState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>删除 采购计划对应关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>修改 采购计划对应关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMatPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划申请单位、采购单位、付款单位
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurUnit(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划数量明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurMainAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
}
