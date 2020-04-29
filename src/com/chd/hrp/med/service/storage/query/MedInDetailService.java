/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.query;
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

public interface MedInDetailService{
	
	/**
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryStockDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryMedStorageQueryWorkDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 库存分布查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryStockRouting(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 库存分布查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryInvBarRouting(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 入库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryInDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商采购汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */ 
	public String queryMedStorageQueryMedInSupCount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商采购明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryMedInSupDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室领用明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryMedOutDept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageQueryOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/12/19 lxj
	 * 出库单供应商信息查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOutSupMessage(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 入库台账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStorageInvCertDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 科室申领统计报表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedApplyCount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 供应商入库汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInSupBusTypeSum(Map<String, Object> entityMap) throws DataAccessException;

}
