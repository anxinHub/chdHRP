/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.accBankVeri;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccBankCheckTell;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.entity.AccVouchCheck;


/**
* @Title. @Description.
* 坏账提取表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccUnitBankCheckMapper extends SqlMapper{
	/**
	 * 出纳与银行对账 查出纳数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankTellData(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccBankTellData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 会计与银行对账 查会计数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankVouchCheckData(Map<String, Object> entityMap)  throws DataAccessException;
	public List<Map<String, Object>> queryAccBankVouchCheckData(Map<String, Object> entityMap, RowBounds rowBounds)  throws DataAccessException;
	public Integer queryAccBankVouchCheckDataCount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 单位银行对账  银行账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccBankCheckMain(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryAccBankCheckMain(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public Integer queryAccBankCheckMainCount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 单位银行对账--对账表中插入记录
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void addAccBankVeriMain(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 单位银行对账--对账日志表中插入记录
	 * @param logMap
	 * @throws DataAccessException
	 */
	public void addAccBankVeriMainLog(Map<String, Object> logMap) throws DataAccessException;
	
	/**
	 * 单位银行对账--取消对账
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteAccBankVericationVeri(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 单位银行对账--查看选定期间是否有对账数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankVericationCount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 单位银行对账--批量删除对账数据
	 */
	public void deteleBatchAccBankVeri(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 单位银行对账--出纳帐数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccVouchCheck> queryAccBankVerAccTellDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 单位银行对账--辅助核算项数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccVouchCheck> queryAccBankVerAccVouchCheckDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 单位银行对账--查看银行账数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<AccBankCheck> queryAccBankVerAccBankCheckDetail(Map<String, Object> entityMap, RowBounds rowBounds);
	
	public void updateBatchAccTellCheckState(List<Map<String, Object>> entityList) throws DataAccessException;
	public void updateBatchAccBankCheckState(List<Map<String, Object>> entityList) throws DataAccessException;
	public long queryAccBankTellDataCount(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	
}
