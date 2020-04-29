
/*
 *
 */
 package com.chd.hrp.eqc.service.query;
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
* 使用记录查询  Service接口
*/
public interface AssEqUseRecordQueryService extends SqlService{
	
	/**
	 * 使用记录查询 -服务项目
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryEqUseMain(Map<String, Object> mapVo ) throws DataAccessException;
	
	/**
	 * 使用记录查询-服务细项
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryEqUseDetail(Map<String, Object> mapVo) throws DataAccessException;

}
