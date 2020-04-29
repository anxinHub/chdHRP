/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.service.business.intangible;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description:
 * 固定资产折旧预算编制
 * @Table:
 * BUDG_ASSET
 * @Author: silent
 * @email:  silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgAssetIntangibleService extends SqlService{
	
	/**
	 * 固定资产折旧预算编制
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgAsset(Map<String,Object> entityMap) throws DataAccessException;
	
}
