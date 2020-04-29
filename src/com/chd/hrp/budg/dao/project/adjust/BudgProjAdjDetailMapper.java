/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.adjust;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 项目预算调整明细
 * @Table:
 * BUDG_PROJ_ADJ_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjAdjDetailMapper extends SqlMapper{
	/**
	 * 根据项目调整主表 主键  查询项目调整明细集合
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAdjDetailByCode(Map<String, Object> map) throws DataAccessException;

	
}
