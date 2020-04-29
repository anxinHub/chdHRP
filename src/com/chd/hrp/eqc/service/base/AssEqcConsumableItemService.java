
/*
 *
 */
 package com.chd.hrp.eqc.service.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 05服务消耗资源项定义表 ASS_EQCConsumableItem Service接口
*/
public interface AssEqcConsumableItemService extends SqlService{
	
	/**
	 * 保存 （添加/修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
