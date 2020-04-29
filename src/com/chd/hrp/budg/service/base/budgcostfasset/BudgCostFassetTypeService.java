
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.service.base.budgcostfasset;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
/**
 * 
 * @Description:
 * 050101 资产分类字典
 * @Table:
 * ASS_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgCostFassetTypeService extends SqlService{

	/**
	 * @Description 
	 * 删除050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deleteAssTypeDictBySuperCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 获取050101 资产分类字典树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public List<?> queryAssTypeDictByTree(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AssTypeDict> queryAssTypeDictList(Map<String, Object> entityMap) throws DataAccessException;
}
