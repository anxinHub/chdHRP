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
import com.chd.hrp.acc.entity.HrpAccSelect;

/**
 * @Title. @Description. 现金流量标注<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccBankNetCommonMapper extends SqlMapper {
	/**
	 * @Description <BR>
	 *              查询 银行信息
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBankForInternet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询 供应商银行信息
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySupBankForInternet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询 供应商银行信息
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryAccBankNetICBCCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description <BR>
	 *              查询 工资奖金项目
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryAccWageItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 查询 跨行行名行号信息
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccICBCIBPSMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询 跨行行名行号信息
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccICBCIBPSMain(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 查询 跨行行名行号信息
	 * @param entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAccICBCIBPSCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 批量修改
	 * 
	 * */
	public int updateBatchICBCIBPS(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 批量新增
	 * 
	 * */
	public int addBatchICBCIBPS(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 批量新增
	 * 
	 * */
	public int deleteBatchICBCIBPS() throws DataAccessException;

}
