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
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccSubj;

/**
* @Title. @Description.
* 财务辅助账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVerificationMapper extends SqlMapper{
	
	

	
	
	/*------------------往来核销   页面查询  begin------------------------*/
	/**
	 * 往来核销 左侧查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccVerificationL(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<AccLederCheck> queryAccVerificationL(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 往来核销  右侧查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccVerificationR(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<AccLederCheck> queryAccVerificationR(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 往来核销--查看自定义辅助核算挂在哪个科目下面
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AccLederCheck queryColumnName(Map<String, Object> entityMap) throws DataAccessException;
	
	/*------------------往来核销   页面查询  end------------------------*/
	
	/*------------------往来核销   页面查询  begin------------------------*/
	/**
	 * 往来核销--借方已核销数据查询 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccVerDetailJ( Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 往来核销--贷方已核销数据查询 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccLederCheck> queryAccVerDetailD( Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*------------------往来核销   页面查询  end------------------------*/
	
	
	/*------------------往来核销   批量取消核销  begin------------------------*/
	/**
	 * 往来核销--批量取消--查询选定期间是否有核销记录
	 * @param entityMap
	 * @return
	 */
	public List<Map<String,Object>> queryAccVericationCount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 往来核销--批量取消 --批量核销删除acc_vouch_veri表中的记录  -
	 * @param entityMap
	 */
	public int detelBatchAccVeriByTime(Map<String, Object> entityMap) throws DataAccessException;
	/*------------------往来核销   批量取消核销 end------------------------*/
	
	
	
	/*------------------往来核销   勾选核销   begin------------------------*/
	/**
	 * 往来核销 --勾选核销  -- acc_vouch_veri表中添加记录
	 * @param entityList
	 * @return 
	 */
	public  int addAccVericationVer(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 往来核销--勾选核销-- acc_vouch_veri_log表中添加记录
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int addAccVericationLog(Map<String, Object> entityList) throws DataAccessException;
	
	/*------------------往来核销   勾选核销   end------------------------*/
	
	
	/*------------------往来核销   勾选取消核销   begin------------------------*/
	
	/**
	 * 勾选取消核销 --删除acc_vouch_veri中的记录 -- 借方
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteAccVericationVeri(List<Map<String, Object>> entityList) throws DataAccessException;
	
	
	/*------------------往来核销   勾选取消核销   end------------------------*/
	
	/**
	 * 往来管理--应付账龄分析
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryReceivableAccount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 会计科目--带方向
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> querySubjDirect(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> querySubjIsCheck(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> queryVeriByCheckId(Map<String, Object> entityMap);
	/**
	 * 更新辅助核算表的核销金额
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateAccVouchCheck(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public List<AccLederCheck> queryAccVerification(List<AccLederCheck> listLN, RowBounds rowBounds);
	
	public String queryVeriCheckId(Map<String, Object> entityMap);
}
