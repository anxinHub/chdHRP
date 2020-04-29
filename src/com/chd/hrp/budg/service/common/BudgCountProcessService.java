/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

 

public interface BudgCountProcessService extends SqlService{
	
	/**
	 * 计算过程查看 数据查询
	 * @param mapVo
	 * @param indexList 
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCountProcess(Map<String, Object> mapVo, List<String> indexList) throws DataAccessException;
	
}
