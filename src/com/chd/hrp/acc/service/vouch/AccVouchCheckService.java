/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.vouch;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.entity.PeopleReminder;

/**
* @Title. @Description.
* 凭证辅助核算表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchCheckService {

	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 添加AccVouchCheck
	 * @param AccVouchCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量添加AccVouchCheck
	 * @param  AccVouchCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccVouchCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheck分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouchCheck(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryReceivableAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccVouchCheck queryAccVouchCheckByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public boolean queryAccVouchCheckBySbujCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 删除AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量删除AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccVouchCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 更新AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量更新AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccVouchCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 导入AccVouchCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询序列
	*/
	public int queryAccVouchCheckNextval(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<PeopleReminder> queryVeriVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 看是否结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String getIsAccFlag(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询<BR> 银行日记账
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBankJournal(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 打印<BR> 银行日记账
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccBankJournalPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addAccVouchCheckByNostro(Map<String, Object> entityMap)throws DataAccessException;
}
