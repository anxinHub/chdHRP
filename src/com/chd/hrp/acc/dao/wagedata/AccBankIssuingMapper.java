/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWagePay;

/**
* @Title. @Description.
* 银行代发<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBankIssuingMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 银行代发<BR> 查询AccBankIssuing分页
	 * @param  entityMap RowBounds
	 * @return List<AccBankIssuing>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccBankIssuing(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 银行代发<BR> 查询AccBankIssuing所有数据
	 * @param  entityMap
	 * @return List<AccBankIssuing>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccBankIssuing(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行代发<BR> 查询AccBankIssuingByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccBankIssuingByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行代发<BR> 更新AccBankIssuing
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccBankIssuing(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行代发<BR> 批量更新AccBankIssuing
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccBankIssuing(List<Map<String, Object>> entityMap)throws DataAccessException;

}
