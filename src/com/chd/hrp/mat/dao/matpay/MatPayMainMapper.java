/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.matpay;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatPayMain;
/**
 * 
 * @Description:
 * state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MAT_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPayMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return MatPayMain
	 * @throws DataAccessException
	*/
	public int updateBatchMatPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatPayMain> queryMatPayMain(Map<String,Object> entityMap) throws DataAccessException;
	
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
	public List<MatPayMain> queryMatPayMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatPayMain
	 * @throws DataAccessException
	*/
	public MatPayMain queryMatPayMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPayMain
	 * @throws DataAccessException
	*/
	public MatPayMain queryMatPayMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据查询条件查询出没有被付款单引用过的发票  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatBillMain_Pay(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 修改页面添加的查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */                              
	public List<Map<String, Object>> queryMatBillMain_PayAdd(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据查询条件查询出没有被付款单引用过的发票 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatBillMain_Pay(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 修改页面添加的查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */								
	public List<Map<String, Object>> queryMatBillMain_PayAdd(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 根据发票ID 查询发票明细信息  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatBillDetail_Pay(Map<String, Object> entityMap) throws DataAccessException;
	/***
	 * 修改页面的添加按钮的查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatBillDetail_PayAdd(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryMatBillDetail_PayN(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据发票ID 查询发票明细信息  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatBillDetail_Pay(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
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
	public Map<String, Object> queryMatPayMainUnit(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 审核、消审、确认、取消确认
	 * @param entityList
	 */
	public void updatePayState(List<Map<String, Object>> entityList) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMatPayMainPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
    public List<Map<String, Object>>queryMatPayMainPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		//入库明细表模板打印
    public List<Map<String, Object>> queryMatPayMainPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
    /**
     * 查询 付款单序列（nextval）
     * @return
     * @throws DataAccessException
     */
	public Long queryPaySeqNextval() throws DataAccessException;
	/**
	 * 查询付款单明细打印数据-主表
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryMatPayDetailByPrintByMain(
			Map<String, Object> entityMap);
	/**
	 * 查询付款单明细打印数据-明细
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatPayDetailByPrintByDetail(
			Map<String, Object> entityMap);
			
	
	 /**
	 * @Description 
	 *查询分批次付款的单子是否审核
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public String queryMatMainlYesNo(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * 查看是否入库单全部付款
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatPayMainByBill(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatBillDetail_PayNsa(
			Map<String, Object> entityMap) throws DataAccessException;
	
	public List<String> queryMatPayState(
			Map<String, Object> entityMap) throws DataAccessException;

}
