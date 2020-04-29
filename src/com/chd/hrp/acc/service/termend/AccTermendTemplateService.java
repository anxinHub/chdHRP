/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.termend;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 期末处理模板<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTermendTemplateService {

	/**
	 * @Description 
	 * 期末处理<BR> 保存模板
	 * @param AccTermendTemplate entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccTermendTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 修改模板
	 * @param AccTermendTemplate entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccTermendTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 查询模板
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccTermendTemplate(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 期末处理<BR> 查询模板明细
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccTermendTemplateDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 批量删除
	 * @param  List
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccTermendTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理<BR> 查询科目
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccTermendTemplateSubj(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理<BR> 查询凭证
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccTermendTemplateVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理<BR> 批量删除明细
	 * @param  List
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccTermendTemplateDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAccTermendTemplateVouchPrint(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 根据list中的辅助核算列来过滤并汇总map中的辅助核算
	 * 即：list中含有check3和check7，map中含有check1、check3、check7，则调用方法返回的map中只有check3和check7并且把金额汇总
	 * @param map
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Map<String, Object>> getMapByCheckCol(Map<String, Map<String, Object>> map, List<Map<String, Object>> list) throws DataAccessException;
}
