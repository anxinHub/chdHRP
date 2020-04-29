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
import com.chd.hrp.budg.entity.BudgType;
/**
 * 
 * @Description:
 * 04103 物资分类字典
 * @Table:
 * MAT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeByDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return MatType
	 * @throws DataAccessException
	*/
	public int updateBatchBudgType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04103 物资分类字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgType> queryBudgType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04103 物资分类字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgType> queryBudgType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为编码和名称用于判断是否重复
	 * @return MatType
	 * @throws DataAccessException
	*/
	public List<BudgType> queryBudgTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatType
	 * @throws DataAccessException
	*/
	public BudgType queryBudgTypeById(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatType
	 * @throws DataAccessException
	*/
	public BudgType queryBudgTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改上级类别末级标识<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgTypeSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
}
