/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.mednopaydeliver;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 期初未付款供货单
 * @Table:
 * MED_NOPAY_DELIVER
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedNopayDeliverMapper extends SqlMapper{

	/**
	 * @Description 
	 * 删除期初未付款送货单明细表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedNopayDeliverBatch(List<Map<String, Object>> list)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除期初未付款送货单明细表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedNopayDeliverDetail(Map<String, Object> entityMap)throws DataAccessException;
	 /**
	 * @Description 
	 * 批量删除明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedNopayDeliverDetailBatch(List<Map<String, Object>> list)throws DataAccessException;
	
	/**
	 * 期初未付款送货单 入库单查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedNopayDeliver(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 期初未付款送货单 入库单查询 分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedNopayDeliver(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	 /**
	 * @Description 
	 * 复制主表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertCopyMain(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 复制明细<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertCopyDetail(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 期初未付款送货单 更新页面回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedNopayDeliverMainUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据 期初未付款送货单入库单Id 查询 期初未付款送货单入库单明细
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryMedNopayDeliverDetail(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * //查询 药品药品结余表MED_INV_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryInvExists(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询期初未付款送货单主表 ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedNopayDeliverNextval() throws DataAccessException;
	
	/**
	 * 添加  期初未付款送货单主表 数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int addMedNopayDeliver(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量添加添加 期初未付款送货单明细表数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedNopayDeliverDetailBatch(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 修改 期初未付款送货单主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedNopayDeliver(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获取对象期初未付款送货单明细数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMedNopayDeliverDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	/** 
	 * 获取 期初未付款送货单明细表 序列
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedNopayDeliverDetailSeq() throws DataAccessException;

	/**
	 * 期初未付款送货单明细表修改（批量）
	 * @param detailUpdateList
	 * @return
	 */
	public int updateMedNopayDeliverDetailBatch(List<Map<String, Object>> detailUpdateList) throws DataAccessException;
	
	/**
	 * 期初未付款送货单主表  批量审核、消审
	 * @param listVo
	 * @throws DataAccessException
	 */
	public void updateStateMedNopayDeliver(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 查询 药品灭菌日期表	 MED_BATCH_DESINFECT 是否存在该记录（存在则不修改 否则就添加
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryDisinfectExists(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 药品期效表  MED_BATCH_VALIDITY 是否存在该记录（存在则不修改 否则就添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryValidityExists(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量添加  药品灭菌日期表  MED_BATCH_DISINFECT
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int addBatchDisinfect(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 批量添加 药品期效表  MED_BATCH_VALIDITY
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int addBatchValidity(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 ID 查询 期初未付款送货单主表 MED_NOPAY_DELIVER 数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedNopayDeliverById(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量新增期初未付款送货单主表数据
	 * @param list
	 * @throws DataAccessException
	 */
	public int addMedNopayDeliverBatch(List<Map<String, Object>> list) throws DataAccessException;
	/**
	 * 查询上一张 单据数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedNopayDeliverBefore(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询下一张 单据数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedNopayDeliverNext(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键Id获取 期初未付款供货单 明细表List
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedNopayDeliverDetailById(Map<String, Object> item) throws DataAccessException;
	/**
	 * 查询 期初未付款供货单是否已生成发票
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBillOrNot(Map<String, Object> mapVo) throws DataAccessException;
	
	//主表模板打印
	public Map<String, Object> queryPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	//主表批量模板打印
	public List<Map<String, Object>> queryPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	//明细表模板打印
    public List<Map<String, Object>> queryPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
}
