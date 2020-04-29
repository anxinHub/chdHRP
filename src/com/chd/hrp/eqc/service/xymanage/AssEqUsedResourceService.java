
/*
 *
 */
 package com.chd.hrp.eqc.service.xymanage;
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
* 15其他设备月度资源消耗 ASS_EqUsedResource Service接口
*/
public interface AssEqUsedResourceService  extends SqlService{
	/**
	 * 保存（新增修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo)throws DataAccessException;
	
}
