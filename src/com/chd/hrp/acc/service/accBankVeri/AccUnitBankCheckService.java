/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.accBankVeri;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBankCheck;




public interface AccUnitBankCheckService {
	/**
	 * 单位银行对账  银行账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccTellOrVouchData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 单位银行对账 银行账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccBankCheckData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 单位银行对账--对账
	 * @param entityList
	 * @param logMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addAccBankVeriMain(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 单位银行对账--取消对账
	 * @param entityList
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteAccBankVeriIsChecked(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量对账
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addBatchAccUnitBankVeri(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 单位银行对账--批量取消对账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBatchAccBankVeri(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 单位银行对账--查看单位账明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccBankVerDetailD(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 单位银行对账--查看银行账明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccBankVerDetailB(Map<String, Object> entityMap) throws DataAccessException;
	

}
