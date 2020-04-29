/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.query;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 入库明细查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MatInDetailService{
	
	/**
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryStockDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 库存明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStorageQueryStockDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	//业务明细
	public String queryMatStorageQueryWorkDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	//业务明细打印
	public List<Map<String, Object>> queryMatStorageQueryWorkDetailPrint(Map<String, Object> entityMap)throws DataAccessException; 
	
	
	/**
	 * 库存分布查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryStockRouting(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 库存分布打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMatStorageQueryStockRoutingPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 材料条码查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryInvBarRouting(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 材料条码打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatStorageQueryInvBarRoutingPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 入库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryInDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 入库明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatStorageQueryInDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商采购汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */ 
	public String queryMatStorageQueryMatInSupCount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商采购汇总打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */ 
	public List<Map<String, Object>> queryMatStorageQueryMatInSupCountPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商采购明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryMatInSupDetail(Map<String, Object> entityMap) throws DataAccessException;
	public String queryMatStorageQueryMatInSupDetailSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商采购明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStorageQueryMatInSupDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室领用明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryMatOutDept(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室领用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryMatOutDeptNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室领用明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStorageQueryMatOutDeptPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageQueryOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * 出库明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStorageQueryOutDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * 出库单供应商信息查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOutSupMessage(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 入库台账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatStorageInvCertDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 入库台账打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStorageInvCertDetailPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 科室申领统计报表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatApplyCount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室申领统计报表打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryMatApplyCountPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 供应商入库汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInSupBusTypeSum(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 供应商入库打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInSupBusTypeSumPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 2016/12/19 lxj
	 * 出库明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStorageQueryOutDetailPrintNew(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatInOutDetail(Map<String, Object> page);
    /**
     * 仓库材料结余
     * @param page
     * @return
     * @throws ParseException 
     */
	public String queryMatStoreInvBalanceDetail(Map<String, Object> page) throws ParseException;
    /**
     * 仓库材料结余 打印
     * @param page
     * @return
     * @throws ParseException 
     */
	public List<Map<String, Object>> queryMatStoreInvBalanceDetailPrint(Map<String, Object> page) throws ParseException;
	/**
	 * 仓库材料出入库明细查询
	 * @param mapVo
	 * @return
	 * @throws ParseException 
	 */
	public String queryMatStoreInvInOutDetail(Map<String, Object> mapVo) throws ParseException;
	/**
	 * 通过store_id查询store的信息
	 * @param mapVo
	 * @return
	 */
	public String queryStoreMsg(Map<String, Object> mapVo);
	/**
	 * 通过inv_id 查询材料信息
	 * @param mapVo
	 * @return
	 */
	public String queryInvMsg(Map<String, Object> mapVo); 
	/**
	 * 入出库查询表页面打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryMatInOutDetailPrint(Map<String, Object> entityMap);
	
	public String queryOccurMatTypeDictForVariableHead(Map<String, Object> mapVo);
	/**
	 * 入库材料汇总查询
	 * @param page
	 * @return
	 */
	public String queryMatStorageInInv(Map<String, Object> page);

	public String queryMatStorageOutInvCollection(Map<String, Object> page);
	/**
	 * 调拨明细汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAccountReportTranCollection(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 调拨明细汇总查-明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAccountReportTranDetail(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 调拨查询打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryMatAccountReportTranCollectionOrDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
