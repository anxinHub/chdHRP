package com.chd.hrp.budg.service.base.budgdrug;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgDrugType;

/**
 * 
 * @Description:药品分类
 * @Copyright: Copyright (c) 2016-11-28 上午10:15:27
 * @Author: xuyongwei
 * @Version: 6.0
 */
public interface BudgDrugTypeService extends SqlService {

	/**
	 * @Description 查药品分类菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryBudgDrugTypeByTree(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象 08102 药品分类字典BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return BudgDrugType
	 * @throws DataAccessException
	*/
	public BudgDrugType queryBudgDrugTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取 08102 药品分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDrugType
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryBudgDrugTypeByCodeName(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impMedType(Map<String,Object> entityMap,Map<String,Object> utilMap)throws DataAccessException;
	

}
