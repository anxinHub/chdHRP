/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.dura.init;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * @Description:  耐用品期初记账
 * @Table: MAT_DURA_(STORE/DEPT)_REG
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatDuraInitChargeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addStore(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDept(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询树形菜单<BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryTree(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选库房的期初库存数据用于Balance记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreBalanceAddListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选库房的期初库存数据用于Balance记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreBalanceUpdateListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选库房的期初库存数据用于Surplus记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreSurplusListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选库房的Surplus账表中每月的数据用于记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreSurplusPeriodListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选库房的期初条码数据用于记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreInvBarListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选科室的期初库存数据用于Balance记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptBalanceAddListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选科室的期初库存数据用于Balance记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptBalanceUpdateListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选科室的期初库存数据用于Surplus记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptSurplusListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选科室的Surplus账表中每月的数据用于记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptSurplusPeriodListForCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取所选科室的期初条码数据用于记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptInvBarListForCharge(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 所选库房未记账列表用于记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryStoreListForChange(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 所选科室未记账列表用于记账 <BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptListForChange(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 所选库房是否可期初记账，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsStoreAccount(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 仓库是否含有未审核的期初单据，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsStoreInitNotCheck(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 所选科室是否可期初记账，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsDeptAccount(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 科室是否含有未审核的期初单据，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsDeptInitNotCheck(Map<String, Object> entityMap)throws DataAccessException;
}
