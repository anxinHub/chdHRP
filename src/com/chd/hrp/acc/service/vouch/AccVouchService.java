/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.vouch;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccVouch;

/**
* @Title. @Description.
* 凭证主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchService {

	/**
	 * @Description 
	 * 凭证主表<BR> 添加AccVouch
	 * @param AccVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 批量添加AccVouch
	 * @param  AccVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 查询AccVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccVouchPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 凭证主表<BR> 查询AccVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccVouch queryAccVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 删除AccVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 批量删除AccVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 更新AccVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 批量更新AccVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 处理凭证之间的断号
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccVouchLen(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证主表<BR> 导入AccVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询<BR> 现金日记账
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashJournal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 打印<BR> 现金日记账
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccCashJournalPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询序列
	*/
	public int queryAccVouchNextval(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 *  查询 返回科目挂的辅助核算信息 add by alfred    调用AccSubjMapper 中queryAccSubjWithCheckType方法
	*/
	public Map<String,String> queryAccSubjWithCheckType(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccState(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询断号凭证
	public String queryAccVouchNeaten(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchNeatenPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 凭证主表,出纳签字，凭证审核  按凭证分录展示
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouchPayTypeCode(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchPayTypeCodePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryAccVouchFlowByNodeId(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<String> queryAccVouchDetailByVouchIdList(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<String> queryAccVouchListByBank(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
