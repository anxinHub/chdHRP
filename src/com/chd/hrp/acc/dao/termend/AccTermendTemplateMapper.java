/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.entity.AccVouch;

/**
* @Title. @Description.
* 期末处理模板<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTermendTemplateMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 期末处理模板<BR> 添加
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTermendTemplate(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理模板<BR> 修改
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccTermendTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理模板<BR> 查询
	 * @param  entityMap
	 * @return List<AccTermendTemplate>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplate> queryAccTermendTemplate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理模板<BR> 查询
	 * @param  entityMap
	 * @return AccTermendTemplate
	 * @throws DataAccessException
	*/
	public AccTermendTemplate queryAccTermendTemplateByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理模板<BR> 查询
	 * @param  entityMap
	 * @return AccTermendTemplate
	 * @throws DataAccessException
	*/
	public AccTermendTemplate queryAccTermendTemplateByTypeCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 期末处理模板<BR> 批量删除
	 * @param  List<entityMap> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTermendTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理科目<BR> 查询科目
	 * @param  entityMap
	 * @return List<AccTermendTemplate>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplate> queryAccTermendTemplateSubj(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * @Description 
	 * 期末处理<BR> 查询凭证(分页)
	 * @param  entityMap RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccTermendTemplateVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理<BR> 查询凭证
	 * @param  entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccTermendTemplateVouch(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccTermendTemplateVouchPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 批量删除凭证
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTermendTemplateVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 查询指定辅助核算在指定科目中的字段
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String querySubjCheckColumnByTableCode(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 查询所有辅助核算项字段
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAllSubjCheckColumn(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 根据科目查询辅助核算项字段
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySubjCheckColumnBySubj(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 查询本期是否有未记账凭证
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryAccUnAccountVouch(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 查询本期间最后一天的日期
	 * @param  entityMap 
	 * @return Date
	 * @throws DataAccessException
	*/
	public Date queryAccVouchDateByYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 根据科目和余额方向获取辅助核算本期发生额（返回老自动凭证格式）
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckMoneyBySubjDireOld(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 根据科目和余额方向获取辅助核算本期发生额（返回自动凭证格式）
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckMoneyBySubjDire(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理<BR> 根据科目方向获取辅助核算本期期末余额
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckEndMoneyBySubj(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 根据科目获取辅助核算本期发生额（除金额外都是凭证格式）
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckThisMoneyBySubj(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 根据科目获取辅助核算本期借方发生额（返回凭证格式）
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckCreditMoneyBySubj(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 根据科目获取辅助核算本期贷方发生额（返回凭证格式）
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheckDebitMoneyBySubj(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 判断该模板本期间是否已生成凭证
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public int existsAccVouchByTemplate(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 获取模板最大序列号
	 * @return
	 */
	public Long queryAccTermendTemplateSeq();

	/**
	 * @Description 
	 * 期末处理<BR> 判断科目是否含辅助核算
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public int existsHaveCheckBySubjCode(@Param(value="group_id")String group_id, @Param(value="hos_id")String hos_id, @Param(value="copy_code")String copy_code, @Param(value="acc_year")String acc_year, @Param(value="subj_code")String subj_code)throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理<BR> 插入临时日志表
	 * @param  entityMap 
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public int saveAutoVouchLogTemp(Map<String, Object> entityMap)throws DataAccessException;
}
