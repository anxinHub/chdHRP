
/*
 *
 */
 package com.chd.hrp.eqc.service.xymanage;
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
*  21设备使用记录-服务细项 ASS_EQ_Use_RecordD  Service接口
*/
public interface AssEqUseRecordDService extends SqlService{
	
	/**
	 * 保存(新增添加)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

	
	
}
