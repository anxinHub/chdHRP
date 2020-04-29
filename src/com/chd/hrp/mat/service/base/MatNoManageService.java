/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 04199 单据号管理表
 * @Table: MAT_NO_MANAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MatNoManageService{

	/**********需求计划********begin************/
	//科室需求计划，规则：前缀+年+月+流水码
	public String getMatReqDeptNextNo() throws DataAccessException;
	//仓库需求计划，规则：前缀+年+月+流水码
	public String getMatReqStoreNextNo() throws DataAccessException;
	/**********需求计划********end**************/

	/**********采购管理********begin************/
	//采购计划，规则：前缀+年+月+流水码
	public String getMatPurNextNo() throws DataAccessException;
	/**********采购管理********end**************/

	/**********订单管理********begin************/
	//订单，规则：前缀+年+月+流水码
	public String getMatOrderNextNo() throws DataAccessException;
	/**********订单管理********end**************/

	/**********库存管理********begin************/
	//注：代销管理单号规则与库存管理同业务一致
	//入库单，规则：业务类型简称+"-"+仓库简称+年+月+日+流水码
	public String getMatInNextNo(String store_id, String bus_type_code) throws DataAccessException;
	//出库单，规则：业务类型简称+"-"+仓库简称+年+月+日+流水码
	public String getMatOutNextNo(String store_id, String bus_type_code) throws DataAccessException;
	//调拨单，规则：业务类型简称+"-"+仓库简称+年+月+日+流水码
	public String getMatTranNextNo(String store_id, String bus_type_code) throws DataAccessException;
	//盘点单，规则：业务类型简称+"-"+仓库简称+年+月+日+流水码
	public String getMatCheckNextNo(String store_id, String bus_type_code) throws DataAccessException;
	//科室申领单，规则：前缀+年+月+流水码
	public String getMatAppNextNo() throws DataAccessException;
	/**********库存管理********end**************/
	
	/**********耐用品管理******begin************/
	//耐用品转移（库-库），规则：前缀+年+月+日+流水码
	public String getMatDuraTranStoreStoreNextNo() throws DataAccessException;
	//耐用品转移（库-科室），规则：前缀+年+月+日+流水码
	public String getMatDuraTranStoreDeptNextNo() throws DataAccessException;
	//耐用品转移（科室-科室），规则：前缀+年+月+日+流水码
	public String getMatDuraTranDeptDeptNextNo() throws DataAccessException;
	//耐用品库房报废，规则：前缀+年+月+日+流水码
	public String getMatDuraScrapStoreNextNo() throws DataAccessException;
	//耐用品科室报废，规则：前缀+年+月+日+流水码
	public String getMatDuraScrapDeptNextNo() throws DataAccessException;
	//耐用品库房盘点，规则：前缀+年+月+日+流水码
	public String getMatDuraCheckStoreNextNo() throws DataAccessException;
	//耐用品科室盘点，规则：前缀+年+月+日+流水码
	public String getMatDuraCheckDeptNextNo() throws DataAccessException;
	/**********耐用品管理******end**************/
	
	/**********付款管理********begin************/
	//期初未付款送货单，规则：前缀+年+月+流水码
	public String getMatNoPayDeliverNextNo() throws DataAccessException;
	//采购发票，规则：前缀+年+月+流水码
	public String getMatBillNextNo() throws DataAccessException;
	//预付款单，规则：前缀+年+月+流水码
	public String getMatPrePayNextNo() throws DataAccessException;
	//付款单，规则：前缀+年+月+流水码
	public String getMatPayNextNo() throws DataAccessException;
	/**********付款管理********end**************/
	
	/**********安全库存调整****begin************/
	public String getMatSafeChangeNextNo() throws DataAccessException;
	/**********安全库存调整****end**************/
	
	//公共方法：根据参数生成物流系统中下一个单号
	public String getMatNextNo(Map<String, Object> entityMap) throws DataAccessException;
}
