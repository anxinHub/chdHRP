/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.InternetBank;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Title. @Description. 现金流量标注<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccBankNetPaymentMapper extends SqlMapper {

	/**
	 * 批量添加
	 * 
	 * */
	public int addBatchAccBankNetPayment(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 批量添加
	 * 
	 * */
	public int addBatchAccBankNetPaymentRd(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccPaymentApply(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccPaymentApply(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description <BR>
	 *              查询
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankNetPayment(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankNetPayment(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankNetPaymentRd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankNetPaymentRd(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 修改
	 * 
	 * */
	public int updateAccBankNetPayment(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量修改
	 * 
	 * */
	public int updateBatchAccBankNetPaymentRd(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description <BR>
	 * 查询最大序列包号
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public String queryMaxFSeqNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 * 查询最大序列号
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public String queryMaxISeqNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 * 
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public Double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException;

}
