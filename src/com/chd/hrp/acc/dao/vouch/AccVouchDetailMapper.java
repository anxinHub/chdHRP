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
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccVouchSummary;
import com.chd.hrp.acc.entity.AccVouchDetail;

/**
* @Title. @Description.
* 凭证明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 添加AccVouchDetail
	 * @param AccVouchDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccVouchDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 批量添加AccVouchDetail
	 * @param  AccVouchDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccVouchDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 查询AccVouchDetail分页
	 * @param  entityMap RowBounds
	 * @return List<AccVouchDetail>
	 * @throws DataAccessException
	*/
	public List<AccVouchDetail> queryAccVouchDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证明细表<BR> 查询AccVouchDetail所有数据
	 * @param  entityMap
	 * @return List<AccVouchDetail>
	 * @throws DataAccessException
	*/
	public List<AccVouchDetail> queryAccVouchDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证明细表<BR> 查询AccVouchDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccVouchDetail queryAccVouchDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 查询AccVouchDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AccVouchDetail> queryAccVouchDetailBySubjCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 删除AccVouchDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccVouchDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 批量删除AccVouchDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccVouchDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 凭证明细表<BR> 更新AccVouchDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccVouchDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证明细表<BR> 批量更新AccVouchDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccVouchDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryAccVouchDetailNextval(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<AccVouchDetail> queryAccVouchDetailAccounting(Map<String,Object> entityMap) throws DataAccessException;

	public int addBatchAccVouchDetailOfMoney(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int addAccVouchDetailOfCopy(List<Map<String, Object>> entityMap) throws DataAccessException;
	
}
