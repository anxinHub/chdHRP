
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
* 06服务项目消耗表 ASS_EQCServiceConsumable Service接口
*/
public interface AssEqcServiceConsumableService extends SqlService {
	
	/**
	 * 保存（新增、修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
