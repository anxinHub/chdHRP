
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
* 13设备面积占比 ASS_EQEquipArea DB管理
* Table("ASS_EQEquipOperator")
*/
public interface AssEqEquipAreaMapper extends SqlMapper{
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 根据 卡片号 查询 建筑物 使用面积
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssUseArea(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
}
