package com.chd.hrp.med.service.affi.query;

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
public interface MedAffiStockSearchService {
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiStorageQueryStockDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 库存分布查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiStockRouting(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 供应商采购汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiSupCount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * 供应商采购明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiSupDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 入库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 科室领用明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiOutDept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * 出库明细-供应商信息 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiOutSupMessage(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiOutImplant(Map<String, Object> entityMap) throws DataAccessException;
}


