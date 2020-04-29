
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
* 18设备公共消耗资源占比 ASS_EQPublicConsumRate Service接口
*/
public interface AssEqPublicConsumRateService extends SqlService {

	/**
	 * 保存（新增/修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
}
