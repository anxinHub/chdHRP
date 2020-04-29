/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.dao.business.intangible;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * 现有固定资产折旧预算
 * @Table:
 * BUDG_ASSET_NOW
 * @Author: silent
 * @email:  silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgAssetNowIntangibleMapper extends SqlMapper{
	/**
	 * 查询资产现状
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgAssCard(Map<String,Object> entityMap) throws DataAccessException;
}
