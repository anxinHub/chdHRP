/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.service.business.fixedassets;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description:
 * 预购固定资产折旧预算
 * @Table:
 * BUDG_ASSET_PUR
 * @Author: silent
 * @email:  silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgAssetPurService extends SqlService{
	
	/**
	 * 计算 预购资产折旧预算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgAssetPur(Map<String,Object> entityMap) throws DataAccessException;
	
}
