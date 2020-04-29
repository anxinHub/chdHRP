/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.bill;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 

public interface MedBillMainService {

	/**
	 * @Description 
	 * 添加保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedBillMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedBillMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedBillMain(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 更新发票金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedBillMainBillMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedBillMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedBillMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedBillMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedBillMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedBillMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象保存采购发票的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
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
	 * 查询当前序列的最大ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryBillMainMaxId() throws DataAccessException;
	/**
	 * 更新页面 回值用查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedBillMainReturnUpdate (	Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 入库单/期初未付款送货单查询 （明细没有全部被发票引用过的采购入库单/期初未付款送货单）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedCommonInBill(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 入库单明细查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInDetail(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBillState(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 生成发票流水号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setBillCode(Map<String, Object> mapVo) throws DataAccessException;

	
	

	//入库模板打印（包含主从表）
	public String queryMedBillMainByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询 发票是否已付款
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryPayOrNot(Map<String, Object> mapVo) throws DataAccessException;

	public String updateBatchMedInMain(List<Map<String, Object>> listVo) throws DataAccessException;
	
	public Map<String,Object> queryMedBillMainByPrintPage(Map<String, Object> entityMap)throws DataAccessException;
		
	
}
