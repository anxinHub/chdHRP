/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.affi.out;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatOutDetail;
/**
 * 
 * @Description:
 * 代销材料期初入库
 * @Table:
 * MAT_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatAffiOutCommonMapper extends SqlMapper{
	/**
	 * 配套导入查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiOutMatchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 审核 代销出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int auditMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 取消审核  代销出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int unAuditMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 整单出库--查询主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAffiInMain(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryAffiInMain(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 整单出库--查询材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAffiInInv(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryAffiInInv(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 整单出库--查询要添加的明细材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAffiInWholeDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--查询材料即时库存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutByInvHold(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代销出库--查询材料即时库存
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryMatAffiOutByInvHold(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 代销出库--查询 mat_affi_fifo 查有批次结存表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutByFifoBalance(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代销出库--查询 mat_affi_fifo 查有批次结存表
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutByFifoBalance(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询被冲销的出库单明细表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetailByCodeOffSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询被冲销的出库单主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMainByCodeOffSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--冲销与被冲销的对应关系
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiBackRela(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 代销出库--下一张ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiNextOutId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--上一张ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiUpOutId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取选中要确认单据中的明细材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatAffiOutDetailForConfirm(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据材料查询及时库存
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */ 
	public Map<String, Object> queryAmountByInvId(Map<String, Object> map) throws DataAccessException;

	//入库主表模板打印
	public Map<String, Object> queryMatAffiOutPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
			
	public List<Map<String, Object>> queryMatAffiOutPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	//入库明细表模板打印
	public List<Map<String, Object>> queryMatAffiOutPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatAffiInvBatchList(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatAffiInvBatchList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 显示明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public void deleteMatAffiOutRela(List<Map<String, Object>> relaList) throws DataAccessException;
		
	
	public List<Map<String, Object>> queryMatAffiOutRela(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 查看出库单是否是科室申领生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryMatOutMainIsApply(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
