/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.special;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.check.special.AssCheckDspecial;
/**
 * 
 * @Description:
 * 051101 科室盘点单(专用设备)
 * @Table:
 * ASS_CHECK_D_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDspecialService extends SqlService {
	/**
	 * @Description 
	 * 审核数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditBatch(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditBatch(Map<String,Object> entityMap)throws DataAccessException;
	public AssCheckDspecial queryState(Map<String, Object> mapVo)throws DataAccessException;
	
}
