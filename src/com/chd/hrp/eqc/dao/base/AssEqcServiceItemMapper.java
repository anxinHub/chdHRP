
/*
 *
 */
 package com.chd.hrp.eqc.dao.base;
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
* 01服务项目定义表 ASS_EQCServiceItem DB管理
* Table("ASS_EQCServiceItem")
*/
public interface AssEqcServiceItemMapper extends SqlMapper{
	
	/**
	 * 查询添加数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;

}
