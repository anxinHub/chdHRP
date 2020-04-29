/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.vouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccVouchDetail;

/**
 * @Title. @Description. 凭证明细表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccVouchDetailService {

	/**
	 * @Description 凭证明细表<BR>
	 *              添加AccVouchDetail
	 * @param AccVouchDetail
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addAccVouchDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              批量添加AccVouchDetail
	 * @param AccVouchDetail
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addBatchAccVouchDetail(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              查询AccVouchDetail分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccVouchDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              查询AccVouchDetailByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public AccVouchDetail queryAccVouchDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              查询AccVouchDetailByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public boolean queryAccVouchDetailBySubjCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              删除AccVouchDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteAccVouchDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              批量删除AccVouchDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteBatchAccVouchDetail(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              更新AccVouchDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateAccVouchDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              批量更新AccVouchDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateBatchAccVouchDetail(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证明细表<BR>
	 *              导入AccVouchDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String importAccVouchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询序列
	*/
	public int queryAccVouchDetailNextval(Map<String,Object> entityMap)throws DataAccessException;
	
	

	String queryAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException;

	String queryAccVouchCashItem(Map<String, Object> entityMap) throws DataAccessException;

	String queryAccVouchPayType(Map<String, Object> entityMap) throws DataAccessException;

	String queryAccVouchSubj(Map<String, Object> entityMap) throws DataAccessException;

	String addAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException;

	String queryAccVouchCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public String updateBatchAccVouchDetailOfMoney(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryAccMaxVouchNo(Map<String, Object> entityMap)throws DataAccessException;
	
}
