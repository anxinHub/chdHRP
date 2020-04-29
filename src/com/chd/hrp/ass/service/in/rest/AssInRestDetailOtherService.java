/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.in.rest;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.in.rest.AssInRestDetailOther;
/**
 * 
 * @Description:
 * 050701 资产其他入库明细(其他固定资产)
 * @Table:
 * ASS_IN_REST_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInRestDetailOtherService extends SqlService {
	public List<Map<String,Object>> queryByInit(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssInRestDetailOther> queryByAssInNo(Map<String,Object> entityMap) throws DataAccessException;
}
