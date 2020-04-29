/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.dura.query;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description: 耐用品查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatDuraQueryService {

	/**
	 * @Description 
	 * 耐用品流转查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryTran(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 耐用品在库库存查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryStoreStock(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 耐用品在库库存查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatDuraQueryStoreStockPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 耐用品科室在用量查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryDeptUse(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 耐用品明细账<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryAccountStated(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 全院耐用品数量分布<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryDistribution(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 耐用品库存查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryStock(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 
	 * @param entityMap
	 * @return耐用品库存查询<BR> 
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatDuraQueryStockPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 耐用品收发存报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryBalanceReport(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 耐用品收发存报表打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/

	public List<Map<String,Object>> queryMatDuraQueryBalanceReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	
	/**
	 * @Description 
	 * 耐用品五五摊销报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryAmortize(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 耐用品领用查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryReception(Map<String,Object> entityMap) throws DataAccessException;

	
	
	/**
	 * @Description 
	 * 耐用品报废明细表查询科室<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryScrapDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 耐用品报废明细表查询仓库<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDuraQueryScrapDetailStore(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 入库明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatDuraQueryAccountStatedPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 耐用品领用查询<BR> 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatDuraQueryReceptionPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	

	/**
	 * 耐用品报废明细表(科室)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatDuraQueryScrapDetailPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 耐用品选择 
	 * @param mapVo
	 * @return
	 */
	public String queryInvBySelector(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 全院库存打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraQueryDistributionPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 耐用品二维码查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatDuraQueryQrCode(Map<String, Object> entityMap) throws DataAccessException;
	
}
