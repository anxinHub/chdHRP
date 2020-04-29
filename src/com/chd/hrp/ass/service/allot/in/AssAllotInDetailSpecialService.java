/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.allot.in;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.allot.in.AssAllotInDetailSpecial;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库明细(专用设备)
 * @Table:
 * ASS_ALLOT_IN_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotInDetailSpecialService extends SqlService {
	public List<AssAllotInDetailSpecial> queryByAssInNo(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryByInit(Map<String,Object> entityMap) throws DataAccessException;
}
