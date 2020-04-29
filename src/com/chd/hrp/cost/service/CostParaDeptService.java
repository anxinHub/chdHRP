﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.cost.entity.CostParaDept;
/**
 * 
 * @Description:
 * 成本_分摊服务科室
 * @Table:
 * COST_PARA_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface CostParaDeptService extends SqlService {
	public List<CostParaDept> queryList(Map<String,Object> entityMap);
	public List<Map<String,Object>> queryCostParaDeptPrint(Map<String,Object> entityMap) throws DataAccessException;
}