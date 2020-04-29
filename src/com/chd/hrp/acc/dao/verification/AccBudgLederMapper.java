/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.verification;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.entity.AccTermendTemplate;


/**
* @Title. @Description.
* 坏账提取表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgLederMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 添加AccBudgLeder
	 * @param AccBudgLeder entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量添加AccBudgLeder
	 * @param  AccBudgLeder entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccBudgLeder(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLeder分页
	 * @param  entityMap RowBounds
	 * @return List<AccBudgLeder>
	 * @throws DataAccessException
	*/
	public List<AccBudgLeder> queryAccBudgLeder(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryAccBudgLederBySubjCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLeder所有数据
	 * @param  entityMap
	 * @return List<AccBudgLeder>
	 * @throws DataAccessException
	*/
	public List<AccBudgLeder> queryAccBudgLeder(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBudgLederByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccBudgLeder queryAccBudgLederByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除AccBudgLeder
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量删除AccBudgLeder
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccBudgLeder(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 坏账提取表<BR> 更新AccBudgLeder
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccBudgLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量更新AccBudgLeder
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccBudgLeder(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 坏账科目
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<AccBudgLeder> queryBadSubj(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 查看当前会计期间 ACC_BUDG_LEDER 表中是否有数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public AccBudgLeder queryBudgLederCounts(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 管理费用科目
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<AccBudgLeder> queryManageSubj(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 应收科目
     * @param entityMap
     * @param rowBounds
     * @return
     * @throws DataAccessException
     */
	public List<AccBudgLeder> queryAllSubj(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 查看当前会计期间是否结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccMonthIsAcc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查看应收科目的当前期间的科目余额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public double queryRecevieSubjEndOs(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查看应收科目是否计提过坏账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryRecevieSubjJtIsOrNot(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 检查所有应收科目所在凭证是否记账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryRecevieSubjIsAcc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 计提坏账  生成凭证
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccBadDebtsExtractAdd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 计提坏账  撤销计提
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String delBadDebtsCancel(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 坏账计提 查看费用科目下面是否挂有辅助核算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryCreditSubjIdIsCheck(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 获取模板
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AccTermendTemplate queryAccTermendTemplate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 余额分析法 坏账科目的期末余额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public double queryBadSubjEndOs(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 账龄分析法获得科目到期日期、未核销金额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccSubjCodeRate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 组装生成凭证的辅助核算信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccBudgLederCheckInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 部门辅助核算挂在哪个下面
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> querySubjCheckColumnBySubjId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取序列号
	 * @return
	 */
	public Long queryAccBudgLederSeq();
	
	//获取坏账准备科目期末余额
	public Double queryEndMoneyBySubj(Map<String, Object> map) throws DataAccessException;
	
	//获取明细收入科目期末余额
	public Double queryEndMoneyByDetailSubj(Map<String, Object> map) throws DataAccessException;
	
	//获取账龄区间
	public List<Map<String, Object>> queryAccBudgRangeList(Map<String, Object> map) throws DataAccessException;
	
	//获取辅助核算期末余额
	public List<Map<String, Object>> queryCheckEndMoneyByDetailSubj(Map<String, Object> map) throws DataAccessException;
}
