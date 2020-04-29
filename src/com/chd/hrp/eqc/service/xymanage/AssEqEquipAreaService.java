
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
* 13设备面积占比 ASS_EQEquipArea Service接口
*/
public interface AssEqEquipAreaService extends SqlService {

	/**
	 * 保存（新增/修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 卡片号  查询 建筑物 使用面积
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssUseArea(Map<String, Object> mapVo) throws DataAccessException;
	
}
