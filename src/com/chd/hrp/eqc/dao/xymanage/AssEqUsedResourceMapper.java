
/*
 *
 */
 package com.chd.hrp.eqc.dao.xymanage;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
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
* 15其他设备月度资源消耗 ASS_EQUsedResource DB管理
* Table("ASS_EQUsedResource")
*/
public interface AssEqUsedResourceMapper extends SqlMapper{
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	
}
