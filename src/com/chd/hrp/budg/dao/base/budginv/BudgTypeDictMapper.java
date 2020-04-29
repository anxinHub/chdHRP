/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budginv;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgTypeDict;
import com.chd.hrp.mat.entity.MatTypeDict;

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
 

public interface BudgTypeDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增变更表删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgTypeDictForDelete(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 更新04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeDictIsStop(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeDictSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新是否末级标志<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeDictIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public int updateBatchBudgTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 批量删除04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	
	public List<BudgTypeDict> queryBudgTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgTypeDict> queryBudgTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public BudgTypeDict queryBudgTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatTypeDict
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
