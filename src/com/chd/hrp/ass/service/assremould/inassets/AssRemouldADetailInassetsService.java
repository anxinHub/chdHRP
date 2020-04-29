﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldAdetailHouse;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldAdetailInassets;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldADetailInassetsService extends SqlService {

	List<AssRemouldAdetailInassets> queryByDisANo(Map<String, Object> mapVo);
	
}