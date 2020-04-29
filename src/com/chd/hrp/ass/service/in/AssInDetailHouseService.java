/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.in;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 资产入库明细(房屋及建筑物)
 * @Table:
 * ASS_IN_DETAIL_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInDetailHouseService extends SqlService {

	List<Map<String, Object>> queryByInit(Map<String, Object> mapVo);

}
