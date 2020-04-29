package com.chd.hrp.mat.dao.adjustMan.changePriceBill;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 调价单
 * @Table:
 * MAT_ADJUST_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatAdjustMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 调价单<BR>批量删除 调价单明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatAdjustDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>按主表ID 查询明细表数据
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryMatAdjustDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>按主表ID 查询明细表数据 分页
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryMatAdjustDetailByCode(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>批量添加 调价单明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatAdjustDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>更新材料表材料零售价
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvSellPrice(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>更新材料变更表材料零售价
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvDictSellPrice(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>审核 修改状态
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatAdjustState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	
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
	 * @Description 
	 * 调价单<BR>根据多个id查询明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryBatchMatAdjustDetailByCode(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * 材料查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvList(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 材料查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvList(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>更新材料表计划价
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvPlanPrice(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>更新材料变更表计划价
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvDictPlanPrice(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调价单<BR>查询材料信息 用于导入
	 * @param entityMap
	 * @return map
	 * @throws DataAccessException
	*/
	public Map<String,Object> queryMatAdjustInvByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryIsExistsInOrder(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新业务表中数据
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void updateMatDetailTable(Map<String, Object> entityMap)  throws DataAccessException;

	public void updateBatchInvIsStop(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryChoiceInvBySupData(List<Map<String, Object>> detailList);

	public List<Map<String, Object>> queryMatChoiceInvBySup(Map<String, Object> entityMap, RowBounds rowBounds);

	public void updateCheckMatAdjustState(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryMatChoiceInvBySup(Map<String, Object> entityMap);

}
