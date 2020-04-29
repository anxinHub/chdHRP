/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budginv;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgTypeDict;

/**
 * 
 * @Description:
 * 04104 物资分类变更表
 * @Table:
 * MAT_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgTypeDictService  {
	
	/**
	 * @Description 
	 * 查询04104 物资分类变更表生成Tree字符串<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBudgTypeDictByTree(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String saveBudgTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBudgTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象04104 物资分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public BudgTypeDict queryBudgTypeDictById(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public BudgTypeDict queryBudgTypeDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
