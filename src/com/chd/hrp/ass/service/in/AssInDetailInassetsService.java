/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.in;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.in.AssInDetailInassets;
/**
 * 
 * @Description:
 * 资产入库明细(专用设备)
 * @Table:
 * ASS_IN_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInDetailInassetsService extends SqlService {
	public List<AssInDetailInassets> queryByAssInNo(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryByInit(Map<String,Object> entityMap) throws DataAccessException;

	public List<AssInDetailInassets> queryAssBackInassetsDetail(
			Map<String, Object> mapVo);
}
