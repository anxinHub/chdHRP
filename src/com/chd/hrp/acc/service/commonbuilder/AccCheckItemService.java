/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCheckItem;

/**
* @Title. @Description.
* 核算项<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCheckItemService {

	/**
	 * @Description 
	 * 核算项<BR> 添加AccCheckItem
	 * @param AccCheckItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 批量添加AccCheckItem
	 * @param  AccCheckItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCheckItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItem(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccCheckItemPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 部门查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统核算项<BR> 职工查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统核算项<BR> 项目查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemProj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统核算项<BR> 客户查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemCus(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 库房查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemStore(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 供应商查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemSup(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 资金来源查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemSource(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 系统核算项<BR> 单位查询AccCheckItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemHos(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 核算项<BR> 查询AccCheckItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCheckItem queryAccCheckItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 删除AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 批量删除AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCheckItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 更新AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 批量更新AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCheckItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算项<BR> 导入AccCheckItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/ 
	public String importAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String insertBatchAccCheckItem(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查验核算项编码或名称是否重复
	 * @param mapVo
	 * @return
	 */
	public String checkItemCodeOrNameRepeat(Map<String, Object> mapVo);
	
	public String queryAccCheckEmpSet(Map<String,Object> entityMap) throws DataAccessException;
	
	public String saveAccCheckEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteAccCheckEmpSet(List<Map<String,Object>> entityList)throws DataAccessException;
	
}
