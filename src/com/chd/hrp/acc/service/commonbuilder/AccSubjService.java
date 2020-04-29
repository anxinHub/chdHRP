/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccSubj;

/**
* @Title. @Description.
* 会计科目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSubjService {

	/**
	 * @Description 
	 * 会计科目<BR> 添加AccSubj
	 * @param AccSubj entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 会计科目<BR> 批量添加AccSubj
	 * @param AccSubj entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccSubj(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 查询AccSubj分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubj(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccSubjPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 *  查询 科目是否是辅助核算或者是现金流量 add by alfrd
	*/
	public String queryAccSubjByCashOrCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 查询AccSubjByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 删除AccSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 批量删除AccSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 更新AccSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 批量更新AccSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 会计科目<BR> 导入AccSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<AccSubj> accSubjImport(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	//继承会计科目
	public String addBatchAccSubjExtend(Map<String, Object> entityMap)throws DataAccessException;
	//集团同步会计科目
	public String addSynGroupSubj(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 账簿 辅助账<BR>自定义辅助账页面科目选择查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubjByMenu(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccSubjBySelector(Map<String,Object> entityMap) throws DataAccessException;
	
	//判断辅助核算是否使用
	public int existsVouchLederBySubjCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	//判断科目是否使用
	public String existsSubjCheck(Map<String,Object> entityMap) throws DataAccessException;

	//根据父级编码查询科目全称
	public String querySubjBySuperCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * @Description 
	 * 会计科目<BR> 导入时查询科目编码是否存在
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccSubj queryAccSubjCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccSubj queryAccSubj_id(Map<String, Object> mapVo) throws DataAccessException;
	
	public AccSubj querySubjByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	//根据科目查询所选科目是否挂了辅助核算
	public String querySubjIsCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public AccSubj queryAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 会计科目导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> importAccSubjData(Map<String,Object> entityMap)throws DataAccessException;
	
	public  List<Map<String, Object>> queryAccSubjByVouch(Map<String,Object> entityMap) throws DataAccessException;
}
