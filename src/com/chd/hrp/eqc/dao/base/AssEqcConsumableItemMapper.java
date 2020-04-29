
/*
 *
 */
 package com.chd.hrp.eqc.dao.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 05服务消耗资源项定义表 ASS_EQCConsumableItem DB管理
* Table("ASS_EQCConsumableItem")
*/
public interface AssEqcConsumableItemMapper extends SqlMapper{
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	
}
