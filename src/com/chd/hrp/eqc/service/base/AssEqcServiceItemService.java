
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
* 01服务项目定义表 ASS_EQCServiceItem Service接口
*/
public interface AssEqcServiceItemService extends SqlService {
	
	/**
	 * 查询添加数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public  String save(List<Map<String, Object>> listVo) throws DataAccessException;

	
}
