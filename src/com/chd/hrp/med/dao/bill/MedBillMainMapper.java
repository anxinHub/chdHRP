/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.bill;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedBillMain;
/**
 * 
 * @Description:
 * 保存采购发票的主要信息
 * @Table:
 * MED_BILL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedBillMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedBillMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedBillMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedBillMain(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 更新发票金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedBillMainBillMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return MedBillMain
	 * @throws DataAccessException
	*/
	public int updateBatchMedBillMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedBillMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedBillMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存采购发票的主要信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedBillMain> queryMedBillMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存采购发票的主要信息<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedBillMain> queryMedBillMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存采购发票的主要信息<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedBillMain
	 * @throws DataAccessException
	*/
	public MedBillMain queryMedBillMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存采购发票的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedBillMain
	 * @throws DataAccessException
	*/
	public MedBillMain queryMedBillMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 入库单/期初未付款送货单查询 （明细没有全部被发票引用过的采购入库单/期初未付款送货单）
	 * @param entityMap
	 * @return
	 */
	public List<Map<String,Object>> queryMedCommonInBill(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 入库单/期初未付款送货单查询 （明细没有全部被发票引用过的采购入库单/期初未付款送货单）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryMedCommonInBill(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 入库单明细查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 入库单明细查询  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 新增时查询发票明细对应的发票主表的Id
	 * @return
	 */
	public Long queryBillMainMaxId() throws DataAccessException;
	/**
	 * 根据 入库单Id 查询入库单明细Id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Long> queryIn_detail_id(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据 发票信息 查询 其发票号是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public long queryMedBillMainExists(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 发票号和发票日期更新到对应入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchMedIn(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 发票号和发票日期更新到对应专购品
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchMedSpecial(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 多表联合查询 发票详细信息（修改页面回值用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedBillMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 审核、消审
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBillState(List<Map<String, Object>> entityList) throws DataAccessException;

	
	//入库主表模板打印
	public Map<String, Object> queryMedBillMainPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	  public List<Map<String, Object>> queryMedBillMainPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		//入库明细表模板打印
    public List<Map<String, Object>> queryMedBillMainPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
    /**
     * 查询 发票主表序列
     * @return
     * @throws DataAccessException
     */
	public Long queryBillMainNextSeq() throws DataAccessException;
	/**
	 * 查询 发票是否已付款 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryPayOrNot(Map<String, Object> mapVo)throws DataAccessException;

	public int updateBatchMedInMain(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMedBillInIds(List<Map<String, Object>> entityList) throws DataAccessException;

			
	
}
