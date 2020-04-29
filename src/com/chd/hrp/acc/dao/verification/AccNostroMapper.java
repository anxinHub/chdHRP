/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.verification;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccVouchCheck;

/**
* @Title. @Description.
* 财务辅助账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccNostroMapper extends SqlMapper{
	/*------------------往来初始校验   begin------------------------*/
	/**
	 * 往来初始账 主页面查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	
	public List<AccSubj> queryCurrentAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccSubj> queryCurrentAccount(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 往来初始账 查询科目初始余额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AccLederCheck queryAccLederCheckBySubjId(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来初始账更新页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccCurrentAccountInit(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryAccCurrentAccountInit(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 往来初始账  删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchAccVouchCheck(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/*------------------往来初始校验 end------------------------*/

	/*------------------往来初始校验   begin------------------------*/
	/**
	 * 查看会计科目挂有哪些辅助核算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySubjCheckColumnBySubjList(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 往来初始校验 页面查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccSubj> queryCurrentAccountCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AccSubj> queryCurrentAccountCheck(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 往来初始校验 明细页面查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCurrentAccountCheckDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*------------------往来初始校验 end------------------------*/
	
	
	
	/*------------------往来查询   余额查询  begin------------------------*/
	/**
	 * 查看当前会计科目挂哪个辅助核算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> querySubjCheckColumnBySubj(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 往来查询-余额查询
	 * @param entityMap
	 * @param rowBounds
	 * @return List<AccLederCheck>
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccLederCheckBalanceO(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<AccLederCheck> queryAccLederCheckBalanceZ(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*------------------往来查询   余额查询  end------------------------*/
	
	/*------------------往来查询   明细账查询  begin------------------------*/
	/**
	 * @Description 
	 * 往来查询-明细账查询
	 * @param entityMap
	 * @param rowBounds
	 * @return List<AccLederCheck>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccDetailAccount(Map<String,Object> entityMap) throws DataAccessException;
	public List<AccLederCheck> queryAccDetailAccountAndZItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*------------------往来查询   明细账查询  end------------------------*/
	
	/*------------------往来查询  核销清册查询  begin------------------------*/
	/**
	 * 往来查询--核销清册查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccVerificationDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 往来查询--核销清册查询--自定义
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccVerificationDetailZ(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*------------------往来查询  核销清册查询  end------------------------*/
	
	/*------------------往来查询  个人往来催款单查询  begin------------------------*/
	/**
	 * 往来查询--个人往来催款单查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryContactsReminderDetail( Map<String, Object> entityMap) throws DataAccessException;
	/*------------------往来查询  个人往来催款单查询  begin------------------------*/

	
	public List<Map<String,Object>> queryCurrentAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCurrentAccountCheckPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccLederCheckBalanceOPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccLederCheckBalanceZPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccVerificationDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccVerificationDetailZPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryContactsReminderDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
}
