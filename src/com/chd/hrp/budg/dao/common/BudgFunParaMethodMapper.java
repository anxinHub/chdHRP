/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgFunParaMethod;
/**
 * 
 * @Description:
 * 函数参数取值表
 * @Table:
 * FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgFunParaMethodMapper extends SqlMapper{

	/**
	 * 参数下拉框 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryFunParaByDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询参数名称 是否已被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
}
