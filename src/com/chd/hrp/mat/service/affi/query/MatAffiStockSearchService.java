package com.chd.hrp.mat.service.affi.query;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description:
 * 代销-库存查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAffiStockSearchService {
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiStorageQueryStockDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 库存分布查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiStockRouting(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 供应商采购汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiSupCount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 供应商采购明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiSupDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 入库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiInDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 科室领用明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutDept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 出库明细-供应商信息 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutSupMessage(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutImplant(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 材料库存分布查询(供应商)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiStockRoutingBySup(Map<String, Object> entityMap) throws DataAccessException;
	 

	/**
	 * 代销入库台账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException 
	 */
	public String queryMatAffiOutCertDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销-库存明细查询-页面打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryMatAffiStorageQueryStockDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryMatAffiStockRoutingPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 库存分布打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAffiOutImplantPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销入库台账查询打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAffiOutCertDetailPrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatAffiInInvCollection(Map<String, Object> page);
	/**
	 * 代销材料入库查询打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiInDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 代销出库材料汇总查询 
	 * @param page
	 * @return
	 */
	public String queryMatAffiOutInvCollection(Map<String, Object> page);
	/**
	 * 代销出库查询表-打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	

}

