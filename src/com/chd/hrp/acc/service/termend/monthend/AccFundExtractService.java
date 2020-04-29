/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.service.termend.monthend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description. 医疗风险基金提取<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccFundExtractService {
	/**
	 * @Description 医疗风险基金提取<BR>
	 *              生成凭证
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addAccFundExtractVouch(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              科室比例查询
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccFundExtractDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              添加
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String saveAccFundExtractDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              根据收入保存科室比例
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String saveAccFundExtractGetDeptIncom(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              根据收入保存科室比例 财务数据
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String saveAccFundExtractGetDeptIncomAcc(Map<String, Object> entityMap)throws DataAccessException;

}
