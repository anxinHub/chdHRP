/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.ModStart;

/**
* @Title. @Description.
* 系统启用<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ModStartMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 系统启用<BR> 添加ModStart
	 * @param ModStart entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量添加ModStart
	 * @param  ModStart entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchModStart(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStart分页
	 * @param  entityMap RowBounds
	 * @return List<ModStart>
	 * @throws DataAccessException
	*/
	public List<ModStart> queryModStart(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStart所有数据
	 * @param  entityMap
	 * @return List<ModStart>
	 * @throws DataAccessException
	*/
	public List<ModStart> queryModStart(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStartByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public ModStart queryModStartByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 删除ModStart
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量删除ModStart
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchModStart(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 系统启用<BR> 更新ModStart
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量更新ModStart
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchModStart(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 系统启用时查询 财务账表(acc_leder)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccLeder(Map<String, Object> entityMap);
	/**
	 * 系统启用时查询 凭证主表(acc_vouch)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccVouch(Map<String, Object> entityMap);
	/**
	 * 系统启用时查询 凭证辅助账核算表(acc_vouch_cheeck)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccVouchCheck(Map<String, Object> entityMap);
	/**
	 * 系统启用时查询 出纳账表(acc_tell)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccTell(Map<String, Object> entityMap);
	
	/**
	 * 系统启用时查询 银行对账单表(acc_bank_check)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccBankCheck(Map<String, Object> entityMap);
	/**
	 * 系统启用时查询 日结表(acc_tell_day)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccTellDay(Map<String, Object> entityMap);
	/**
	 * 系统启用时查询 工资发放表(acc_wage_pay)是否有数据（有数据不能启用）
	 * @param entityMap
	 * @return
	 */
	public Integer queryAccWagePay(Map<String, Object> entityMap);
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStartByCode是否存在启用记录
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public ModStart existsModStartByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 物流系统启用时 查询MAT_INV_DICT 当前单位账套下面有数据不允许保存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 药品系统启用时 查询MED_CAT_DICT 当前单位账套下面有数据不允许保存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvDict(Map<String, Object> entityMap) throws DataAccessException;

	//查询系统启用日期
	public String queryModStartByModeCode(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> querySysModStart(Map<String, Object> mapVo) throws DataAccessException;
}
