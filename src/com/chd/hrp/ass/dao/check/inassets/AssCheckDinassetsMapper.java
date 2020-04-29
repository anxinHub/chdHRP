/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.inassets;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.inassets.AssCheckDinassets;
/**
 * 
 * @Description:
 * 051101 科室盘点单(无形资产)
 * @Table:
 * ASS_CHECK_D_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDinassetsMapper extends SqlMapper{

	AssCheckDinassets queryState(Map<String, Object> mapVo);
	
}
