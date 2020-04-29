/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.verification;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBudgLeder;


/**
* @Title. @Description.
* 坏账提取表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgLederService {

	/**
	 * @Description 
	 * 坏账提取表<BR> 添加AccBudgLeder
	 * @param AccBudgLeder entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量添加AccBudgLeder
	 * @param  AccBudgLeder entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccBudgLeder(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLeder分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBudgLeder(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccBudgLederBySubjCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLederByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccBudgLeder queryAccBudgLederByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量删除AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBudgLeder(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 更新AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量更新AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccBudgLeder(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 导入AccBudgLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 坏账科目
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBadSubj(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查看当前会计期间 ACC_BUDG_LEDER 表中是否有数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgLederCounts(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 管理费用科目
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryManageSubj(Map<String, Object> entityMap) throws DataAccessException;


    /**
     * 应收科目为末级
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public String queryAllSubj(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查看坏账表中的计提方式和比例
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AccBudgLeder queryBadSubjBean(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查看当前会计期间是否结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccMonthIsAcc(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 计提坏账  生成凭证
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addAccBadDebtsExtract(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 撤销计提
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String delBadDebtsCancel(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * @Description 医疗风险基金提取<BR>
	 *              科室比例查询
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBadDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              添加
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String saveAccBadDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              根据收入保存科室比例
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String saveAccBadGetDeptIncom(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 医疗风险基金提取<BR>
	 *              根据收入保存科室比例 财务数据
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String saveAccBadGetDeptIncomAcc(Map<String, Object> entityMap)throws DataAccessException;
}
