
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
* 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable Service接口
*/
public interface AssEqEquipConsumableService extends SqlService {
	
	/**
	 * 保存（新增、修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
