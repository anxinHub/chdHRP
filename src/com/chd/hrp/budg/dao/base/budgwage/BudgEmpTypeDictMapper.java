/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgwage;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 职工类别字典
 * @Table:
 * BUDG_EMP_TYPE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgEmpTypeDictMapper extends SqlMapper{
	
	/**
	 * 查询类别名称是否已被占用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException;
	
}
