/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.verification;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccLederCheck;

/**
* @Title. @Description.
* 财务辅助账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccNostroService {
	
	/**
	 * 往来初始账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCurrentAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来初始账 根据科目查余额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AccLederCheck queryAccLederCheckBySubjId(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 往来初始账 更新页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccCurrentAccountInit(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 往来初始账 删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteAccVouchCheckInit(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 往来初始校验 页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCurrentAccountCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来初始校验 明细页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCurrentAccountCheckDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 往来查询 -- 余额查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccLederCheckBalanceO(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来处理--明细账查询  自定义辅助核算
	 */
	public String queryAccDetailAccountAndZItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来明细查询  调用存储过程
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String collectQueryAccDetailAccount(Map<String, Object> entityMap) throws DataAccessException;
   
	/**
	 * 往来核销--核销清册查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccVerificationDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来核销--个人往来催款单查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryContactsReminderDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryCurrentAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCurrentAccountCheckPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccLederCheckBalanceOPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> collectQueryAccDetailAccountPrint(Map<String, Object> entityMap) throws DataAccessException;
	  
	public List<Map<String,Object>> queryAccVerificationDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryContactsReminderDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
}
