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


public interface AccVerificationService {

	/**
     * 往来核销 左侧查询
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public String queryAccVerificationL(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 往来核销 右侧查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccVerificationR(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryVeriCheckId(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 往来核销--自定义辅助核算挂在哪个 zcheck 下
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryColumnName(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来核销--批量取消
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBatchAccVeri(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来核销  已核销数据查询 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccVerDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * 往来核销--勾选取消核销
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteAccVeriIsChecked(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来核销--应收账龄分析
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String collectReceivableAccount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 会计科目 带方向
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySubjDirect(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 会计科目 是否带辅助核算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySubjIsCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> collectReceivableAccountPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量核销
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addBatchAccVerica(Map<String, Object> entityMap) throws DataAccessException;
}
