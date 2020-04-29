/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.medpay;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedPayMain;
/**
 * 
 * @Description:
 * state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MED_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedPayMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return MedPayMain
	 * @throws DataAccessException
	*/
	public int updateBatchMedPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedPayMain> queryMedPayMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedPayMain> queryMedPayMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedPayMain
	 * @throws DataAccessException
	*/
	public MedPayMain queryMedPayMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayMain
	 * @throws DataAccessException
	*/
	public MedPayMain queryMedPayMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据查询条件查询出没有被付款单引用过的发票  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedBillMain_Pay(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据查询条件查询出没有被付款单引用过的发票 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedBillMain_Pay(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 根据发票ID 查询发票明细信息  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedBillDetail_Pay(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryMedBillDetail_PayN(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据发票ID 查询发票明细信息  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedBillDetail_Pay(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询 付款单主表当前最大的 ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryPayID() throws DataAccessException;
	/**
	 * 查询 付款单详细信息 （修改页面回值用)
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryMedPayMainUnit(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 审核、消审、确认、取消确认
	 * @param entityList
	 */
	public void updatePayState(List<Map<String, Object>> entityList) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMedPayMainPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
    public List<Map<String, Object>>queryMedPayMainPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		//入库明细表模板打印
    public List<Map<String, Object>> queryMedPayMainPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
    /**
     * 查询 付款单序列（nextval）
     * @return
     * @throws DataAccessException
     */
	public Long queryPaySeqNextval() throws DataAccessException;
			
	
}
