package com.chd.hrp.budg.dao.base.budgdrug;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgDrugTypeDict;

public interface BudgDrugTypeDictMapper extends SqlMapper {

	/**
	 * @Description 
	 * 更新是否末级标志<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgDrugTypeDictIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgDrugTypeDictSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增变更表删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgDrugTypeDictForDelete(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新药品分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgDrugTypeDictIsStop(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取药品分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public BudgDrugTypeDict queryMedTypeDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
