
/*
 *
 */
 package com.chd.hrp.eqc.service.base;
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
* 02服务项目细项定义表 ASS_EQCServDetItem Service接口
*/
public interface AssEqcServDetItemService extends SqlService{

	/**
	 * 保存数据（添加、修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public  String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
}
