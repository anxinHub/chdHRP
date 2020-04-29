/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.InternetBank;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description. 现金流量标注<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccBankNetCommonService {

	/**
	 * @Description 查询付款银行信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankForInternet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询供应商银行信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String querySupBankForInternet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询银行地区码
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetICBCCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询 工资项目
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccWageItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询 跨行行名行号信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccICBCIBPSMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询IBPS代码
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccICBCIBPSCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询 跨行行名行号信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccICBCIBPS(Map<String, Object> entityMap) throws DataAccessException;
	
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
	 * 批量删除
	 * 
	 * */
	public int deleteBatchICBCIBPS() throws DataAccessException;
	
	public int getCharCount(String s);//获取字节长度
	
	public String subChar(String s, int length) throws Exception;//按字节截取长度
	

}
