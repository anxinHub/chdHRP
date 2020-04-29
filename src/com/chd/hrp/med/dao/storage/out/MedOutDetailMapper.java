/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.out;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedOutDetail;
/**
 * 
 * @Description:
 * 08307 物流出库明细表
 * @Table:
 * MED_OUT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedOutDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMedOutDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 定向导入药品信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutDetailByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_fifo_balance 表 返回药品 历史导入 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutDetailByHistory(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_fifo_balance 表 返回药品 历史导入 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutDetailByHistory(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_out_detail 表 返回名细 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutDetailByOutId(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_out_detail 表 返回名细药品 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutDetailByOutId(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public MedOutDetail queryAmountByInvId(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询选择单据中的所有药品用于确认<BR> 
	 * @param  entityList
	 * @return List<MedOutDetail>
	 * @throws DataAccessException
	*/
	public List<MedOutDetail> queryMedOutDetailForConfirm(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description  
	 * 查询选择单据中的所有耐用品药品<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedOutForDura(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库明细<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedStorageQueryOutDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库明细<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedStorageQueryOutDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库单供应商信息<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutSupMessage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * @Description 
	 * 查询出库单供应商信息<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutSupMessage(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
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
	public List<?> mergeOffsetMedOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int queryMedOutDetailNextval(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询存在申请对应关系表中明细Id
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedApplyOutRela(Map<String, Object> entityMap) throws DataAccessException;
	//public List<Map<String, Object>> queryMedApplyOutRela(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 删除对应关系表中的明细
	 * @param relaList
	 * @throws DataAccessException
	 */
	public void deleteMedApplyOutRela(List<Map<String, Object>> relaList) throws DataAccessException;
	/**
	 * 查看出库单是否是科室申领生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryMedOutMainIsApply(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查看科室申领生成出库单的数量
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedOutDetailAmount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新对应关系的数量
	 * @param relaList
	 * @throws DataAccessException
	 */
	public void updateMedApplyOutRela(List<Map<String, Object>> relaList) throws DataAccessException;

	
}
