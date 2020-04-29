package com.chd.hrp.budg.dao.base.budgdrug;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgDrugType;
import com.chd.hrp.mat.entity.MatType;
import com.chd.hrp.med.entity.MedType;
/**
 * 
 * @Description:药品分类
 * @Copyright: Copyright (c) 2016-11-28 上午10:46:39
 * @Author: xuyongwei
 * @Version: 6.0
 */
public interface BudgDrugTypeMapper extends SqlMapper {

	
	/**
	 * @Description 
	 * 获取 药品分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatType
	 * @throws DataAccessException
	*/
	public BudgDrugType queryBudgDrugTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改上级类别末级标识<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgDrugTypeIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgDrugTypeSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBudgDrugTypeByDict(Map<String,Object> entityMap)throws DataAccessException;
	

	
	
}
