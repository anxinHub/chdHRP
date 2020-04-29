/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccVouchCheck;

/**
* @Title. @Description.
* 凭证辅助核算表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchCheckMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 添加AccVouchCheck
	 * @param AccVouchCheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量添加AccVouchCheck
	 * @param  AccVouchCheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccVouchCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入凭证辅助核算表<BR> 批量添加AccVouchCheck
	 * @param  AccVouchCheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccVouchCheckImport(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheck分页
	 * @param  entityMap RowBounds
	 * @return List<AccVouchCheck>
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * 应收账龄分析
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccVouchCheck> queryReceivableAccount(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	
	//已核销
	public List<AccVouchCheck> queryVeriVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//未核销
	public List<AccVouchCheck> queryNotVeriVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//部分核销
	public List<AccVouchCheck> queryPartVeriVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//全部
	public List<AccVouchCheck> queryAllVeriVouchCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheck所有数据
	 * @param  entityMap
	 * @return List<AccVouchCheck>
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccVouchCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheckByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccVouchCheck queryAccVouchCheckByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询AccVouchCheckByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccVouchCheckBySubjCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 删除AccVouchCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 是否结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AccVouchCheck getIsAccFlag(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量删除AccVouchCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccVouchCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 更新AccVouchCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 批量更新AccVouchCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccVouchCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryAccVouchCheckNextval(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询 银行日记账
	 * @param  entityMap 
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccBankJournal(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询 银行日记账
	 * @param  entityMap 
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccBankJournal(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 应收账龄分析
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccVouchCheck> queryReceivableAccount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询银行未达账(会计与银行对账)(结果集带合计借贷方)分页
	 * @param  entityMap RowBounds
	 * @return List<AccVouchCheck>
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccVouchCheckAndSum(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证辅助核算表<BR> 查询银行未达账(会计与银行对账)(结果集带合计借贷方)
	 * @param  entityMap RowBounds
	 * @return List<AccVouchCheck>
	 * @throws DataAccessException
	*/
	public List<AccVouchCheck> queryAccVouchCheckAndSum(Map<String,Object> entityMap) throws DataAccessException;

	public int addBatchAccVouchCheckOfMoney(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public List<AccVouchCheck> queryAccVouchCheckCountByVouchId(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addBatchAccCashVouchOfMoney(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int addAccVouchCheckOfCopy(List<Map<String, Object>> entityMap) throws DataAccessException;

	public int addAccCashVouchOfCopy(List<Map<String, Object>> entityMap) throws DataAccessException;

	public Long queryAccVouchVeri(Map<String, Object> item) throws DataAccessException;
	
	//用户判断系统平台中职工是否存在，存在则不能进行删除  
	public int queryAccVouchCheckCountByEmpId(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccVouchCheckAndSumPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccBankJournalPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public int saveAccVouchCheck(Map<String,Object> entityMap)throws DataAccessException;

	//往来核销初始账删除
	public int deleteAccVouchCheckByNostro(Map<String, Object> entityMap) throws DataAccessException;
}
