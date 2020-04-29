/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.storage.special;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 常备药品期初入库
 * @Table:
 * MED_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedSpecialMapper extends SqlMapper{

	 /**
	 * @Description 
	 * 插入入库单订单关系表<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertOrderRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 删除入库单订单关系表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteOrderRela(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除专购品明细表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSpecialDetail(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 批量删除明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDetailBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 专购品 入库单查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedSpecial(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 专购品 入库单查询 分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  Map(group_id,hos_id,copy_code,in_ids)<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmCommonIn(Map<String,Object> entityMap)throws DataAccessException;
	
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
	 * @Description 
	 * 生成冲账主表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertOffsetMain(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 生成冲账明细<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertOffsetDetail(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 专购品入库单  批量审核或消审<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateStateIn(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 专购品出库单  批量审核或消审<BR>
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateStateOut(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 订单结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryOrder(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 订单结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryOrder(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 订单药品结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryOrderDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单 主数据结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAffiOut(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAffiOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单 明细数据结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryMedAffiDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 协议结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryProtocol(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 维护专购品与出入库对应关系
	 * @param relaMap
	 * @throws DataAccessException
	 */
	public int addMedSpecialRela(Map<String, Object> relaMap) throws DataAccessException;
	/**
	 * 批量维护专购品与出入库对应关系
	 * @param relaMap
	 * @throws DataAccessException
	 */
	public int addMedSpecialRelaBatch(List<Map<String, Object>> relaList) throws DataAccessException;
	
	/**
	 * 新增专购品入库主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addInMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 新增专购品出库主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addOutMain(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量新增 专购品出库明细数据
	 * @param detailList
	 * @throws DataAccessException
	 */
	public int addMedOutDetailBatch(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 专购品 更新页面回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedSpecialMainUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据专购品主表ID 查询专购品  出入库单Id、出入库单号
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMedInOutData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 批量删除 专购品出入库对应关系
	 * @param entityList
	 * @return
	 */
	public int deleteBatchSpecialRela(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 根据 专购品入库单Id 查询 专购品入库单明细
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> querySpecialDetail(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 确认前状态校验
	 * @param entityList
	 * @return
	 */
	public int existsMedSpecialStateForConfrim(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 确认使用
	 * @param entityList
	 * @return
	 */
	public List<Map<String, Object>> querySpecialDetailForConfrim(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 专购品 批量入库确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int confirmMedSpecialIn(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 专购品 批量出库确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int confirmMedSpecialOut(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * //查询 出入库结存表 MED_FIFO_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryFifoExists(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * //查询 药品药品结余表MED_INV_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryInvExists(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 药品药品结余表 MED_BATCH_BALANCE 是否存在该记录（存在则修改该记录 否则就添加
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBatchExists(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 药品药品结存表 MED_INV_HOLD 是否存在该记录（存在则不修改 否则就添加
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryInvHoldExists(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 批量删除 专购品入库资金来源
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int deleteMedInSourceBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 冲账 批量新增  专购品入库 主表数据
	 * @param outMainList
	 * @return
	 * @throws DataAccessException
	 */
	public int addOutMainBatch(List<Map<String, Object>> outMainList) throws DataAccessException;
	/**
	 * 冲账 批量新增 专购品出入库对应关系 数据
	 * @param inOutList
	 * @return
	 * @throws DataAccessException
	 */
	public int addSpecialRelaBatch(List<Map<String, Object>> inOutList) throws DataAccessException;

	public List<Map<String, Object>> queryOutDetial(Map<String, Object> outMainMap) throws DataAccessException;
	/**
	 * 根据业务类型编码 查询其业务类型标志
	 * @param typeMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryTypeFlag(Map<String, Object> typeMap) throws DataAccessException;
	
	/**
	 * 查询专购品主表 ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedSpecialNextval() throws DataAccessException;
	
	/**
	 * 添加  专购品主表 数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int addSpecialMain(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量添加添加 专购品明细表数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedSpecialDetailBatch(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 出库单明细 序列
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedOutDetailSeq() throws DataAccessException;
	/**
	 * 批量删除 专购品明细
	 * @param entityList
	 * @return
	 */
	public int deleteSpecialDetailBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 批量删除  出库资金来源表数据
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteMedOutSourceBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 修改 专购品主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateSpecialMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改 专购品出库主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedOutMain(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获取对象专购品明细数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMedSpecialDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	/** 
	 * 获取 专购品明细表 序列
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedSpecialDetailSeq() throws DataAccessException;
	/**
	 * 批量添加 专购品订单关系
	 * @param orderRelaList
	 * @throws DataAccessException
	 */
	public int addSpecialOrderRelaBatch(List<Map<String, Object>> orderRelaList) throws DataAccessException;
	/**
	 * 删除入库单订单关系
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int deleteInOrderRelaBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 批量删除 专购品订单关系
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int deleteSpecialOrderRelaBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 删除 专购品订单关系
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int deleteSpecialOrderRela(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 修改时  删除页面上删掉的  专购品明细记录
	 * @param pageMap
	 * @throws DataAccessException
	 */
	public int deleteMedSpecialDetailForUpdate(Map<String, Object> pageMap) throws DataAccessException;
	/**
	 * 专购品明细表修改（批量）
	 * @param detailUpdateList
	 * @return
	 */
	public int updateMedSpecialDetailBatch(List<Map<String, Object>> detailUpdateList) throws DataAccessException;
	/**
	 * 修改出库资金来源表
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateMedOutResource(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 专购品主表  批量审核、消审
	 * @param listVo
	 * @throws DataAccessException
	 */
	public void updateStateSpecial(List<Map<String, Object>> listVo) throws DataAccessException;
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
	 * 专购品批量  确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int confirmMedSpecial(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 根据 ID 查询 专购品主表 MED_SPECIAL_MAIN 数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedSpecialById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 ID查询专购品入库单主表 数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryInMainDataById(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 ID查询专购品出库单主表 数据
	 * @param m
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedOutMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 ID查询专购品入库单明细表 数据
	 * @param inMainMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInDetailById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 ID查询专购品出库单明细表 数据
	 * @param dataMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedOutDetailById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量新增专购品主表数据
	 * @param specialList
	 * @throws DataAccessException
	 */
	public int addMedSpecialMainBatch(List<Map<String, Object>> specialList) throws DataAccessException;
	/**
	 * 查询入库资金来源
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedInResource(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询出库资金来源
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedOutResource(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量新增 专购品入库 资金来源 数据
	 * @param inResourceList
	 * @throws DataAccessException
	 */
	public int addInResourceBatch(List<Map<String, Object>> inResourceList) throws DataAccessException;
	/**
	 * 批量新增 专购品出库 资金来源 数据
	 * @param outResourceList
	 * @return
	 * @throws DataAccessException
	 */
	public int addOutResourceBatch(List<Map<String, Object>> outResourceList) throws DataAccessException;
	/**
	 * 查询上一张 单据数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedSpecialBefore(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询下一张 单据数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedSpecialNext(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更改代销出库单  是否已生成专购品  状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAffiOutState(Map<String, Object> mapVo) throws DataAccessException;

	
	//入库主表模板打印
	public Map<String, Object> queryMedSpecialPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	//入库主表批量模板打印
	public List<Map<String, Object>> queryMedSpecialPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库明细表模板打印
	public List<Map<String, Object>> queryMedSpecialPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库明细表模板汇总打印
	public List<Map<String, Object>> queryMedSpecialPrintTemlateByDetailCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	//组装明细数据
	public List<Map<String, Object>> queryMedAffiOutDetail(Map<String, Object> entityList) throws DataAccessException;
	
	//代销生成专购品 查询
	public List<?> queryMedAffiSpecial(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryMedAffiSpecial(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		
	//根据页面所选数据查询结果集用于生成专购品
	public List<Map<String, Object>> queryAffiForAdd(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 更新发票号
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int updateMedSpecialInvoice(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMedSpecailInvBatch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedSpecailInvBatch(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMedSpecailInvDetail(List<Map<String, Object>> invList) throws DataAccessException;
	/**
	 * 明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedSpecialDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedSpecialDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 更新发票号
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int addMedOutMainBySpecialBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 更新发票号
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int addMedOutDetailBySpecialBatch(List<Map<String,Object>> entityList) throws DataAccessException;
}
