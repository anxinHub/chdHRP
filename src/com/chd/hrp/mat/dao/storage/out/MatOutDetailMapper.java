/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.storage.out;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatOutDetail;
/**
 * 
 * @Description:
 * 04307 物流出库明细表
 * @Table:
 * MAT_OUT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatOutDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMatOutDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 定向导入材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutDetailByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_fifo_balance 表 返回材料 历史导入 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutDetailByHistory(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_fifo_balance 表 返回材料 历史导入 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutDetailByHistory(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_out_detail 表 返回名细 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutDetailByOutId(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatOutDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_out_detail 表 返回名细材料 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutDetailByOutId(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public MatOutDetail queryAmountByInvId(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询选择单据中的所有材料用于确认<BR> 
	 * @param  entityList
	 * @return List<MatOutDetail>
	 * @throws DataAccessException
	*/
	public List<MatOutDetail> queryMatOutDetailForConfirm(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description  
	 * 查询选择单据中的所有耐用品材料<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatOutForDura(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库明细<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStorageQueryOutDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库明细<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStorageQueryOutDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 *
	 * @Description 
	 * 查询出库明细<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStorageQueryOutDetailNew(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 * @Description 
	 * 查询出库明细<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStorageQueryOutDetailNew(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库单供应商信息<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutSupMessage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库单供应商信息<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutSupMessage(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 查询出库明细用于复制单据
	 * @param entityMap
	 * @return  List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetailForCopy(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 合并勾选的冲账单据明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> mergeOffsetMatOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int queryMatOutDetailNextval(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询存在申请对应关系表中明细Id
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatApplyOutRela(Map<String, Object> entityMap) throws DataAccessException;
	//public List<Map<String, Object>> queryMatApplyOutRela(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 删除对应关系表中的明细
	 * @param relaList
	 * @throws DataAccessException
	 */
	public void deleteMatApplyOutRela(List<Map<String, Object>> relaList) throws DataAccessException;
	/**
	 * 查看出库单是否是科室申领生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryMatOutMainIsApply(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查看科室申领生成出库单的数量
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatOutDetailAmount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新对应关系的数量
	 * @param relaList
	 * @throws DataAccessException
	 */
	public void updateMatApplyOutRela(List<Map<String, Object>> relaList) throws DataAccessException;

	public List<Map<String, Object>> queryInvOutDetail(Map<String, Object> mapVo);
	
	/**
	 * 常备材料出库 按材料供应商汇总
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatStorageOutInvCollection(Map<String, Object> entityMap);
	public List<Map<String, Object>> queryMatStorageOutInvCollection(Map<String, Object> entityMap, RowBounds rowBounds);

	
}
