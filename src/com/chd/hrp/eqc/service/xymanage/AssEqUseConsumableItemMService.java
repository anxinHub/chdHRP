
/*
 *
 */
 package com.chd.hrp.eqc.service.xymanage;
import java.util.*;

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
* 23设备使用消耗资源记录ASS_EQ_Use_Consum_ItemD Service接口
*/
public interface AssEqUseConsumableItemMService extends SqlService{
	/**
	 * 保存（新增、修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
