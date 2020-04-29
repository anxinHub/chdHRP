/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.autovouch.HisAccMedPayType;
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayTypeRef;

/**
 * @Title. @Description
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HisAccMedPayTypeRefService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHisAccMedPayTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HisAccMedPayTypeRef queryHisAccMedPayTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String updateHisAccMedPayTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String addHisAccMedPayTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String delHisAccMedPayTypeRef(List<Map<String, Object>> entityMap) throws DataAccessException;

}
