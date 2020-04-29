/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWagePay;
 
/**
* @Title. @Description.
* 银行代发<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBankIssuingService {

	/**
	 * @Description 
	 * 银行代发<BR> 查询AccBankIssuing分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBankIssuing(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行代发<BR> 查询AccBankIssuingByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccBankIssuingByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行代发<BR> 更新AccBankIssuing
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccBankIssuing(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行代发<BR> 批量更新AccBankIssuing
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccBankIssuing(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>>  queryAccBankIssuingPrint(Map<String,Object> entityMap) throws DataAccessException;
}
